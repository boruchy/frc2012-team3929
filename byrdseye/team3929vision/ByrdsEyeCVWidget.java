package team3929.vision;

import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.cpp.opencv_core;
import com.googlecode.javacv.cpp.opencv_core.*;
import com.googlecode.javacv.cpp.opencv_imgproc;
import com.googlecode.javacv.cpp.opencv_imgproc.*;
import edu.wpi.first.smartdashboard.camera.WPICameraExtension;
import edu.wpi.first.smartdashboard.robot.Robot;
import edu.wpi.first.wpijavacv.DaisyExtensions;
import edu.wpi.first.wpijavacv.WPIBinaryImage;
import edu.wpi.first.wpijavacv.WPIColor;
import edu.wpi.first.wpijavacv.WPIColorImage;
import edu.wpi.first.wpijavacv.WPIContour;
import edu.wpi.first.wpijavacv.WPIImage;
import edu.wpi.first.wpijavacv.WPIPoint;
import edu.wpi.first.wpijavacv.WPIPolygon;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.TreeMap;
import javax.imageio.ImageIO;

/* HOW TO GET THIS COMPILING IN NETBEANS:
 *  1. Install the SmartDashboard using the installer (if on Windows)
 *      1a. Verify that the OpenCV libraries are in your PATH (on Windows)
 *  2. Add the following libraries to the project:
 *     SmartDashboard.jar
 *     extensions/WPICameraExtension.jar
 *     lib/NetworkTable_Client.jar
 *     extensions/lib/javacpp.jar
 *     extensions/lib/javacv-*your environment*.jar
 *     extensions/lib/javacv.jar
 *     extensions/lib/WPIJavaCV.jar
 *
 */
/**
 *
 * @author jrussell
 * @author
 */
public class ByrdsEyeCVWidget extends WPICameraExtension
{
    public static final String NAME = "ByrdsEye Target Tracker";
    private WPIColor targetColor = new WPIColor(0, 255, 0);

    // Constants that need to be tuned
    private static final double kNearlyHorizontalSlope = Math.tan(Math.toRadians(20));
    private static final double kNearlyVerticalSlope = Math.tan(Math.toRadians(90-20));
    private static final int kMinWidth = 20;
    private static final int kMaxWidth = 200;

    // range for RPM purposes
    private static final double kRangeOffset = 0.0;

    // TODO: FIGURE OUR ACTUAL SHOOTING DRIFT IN DEGREES
    private static final double kShooterOffsetDeg = 0.0;
    private static final double kHorizontalFOVDeg = 47.0;

    private static final double kVerticalFOVDeg = 480.0/640.0*kHorizontalFOVDeg;
    private static final double kCameraHeightIn = 54.0;
    private static final double kCameraPitchDeg = 20.0;
    
   // 98 to rim, +2 to bottom of target, +9 to center of target
    // source:  FRC Drawing GE-12096, 2012GameSpecificDrawings_rev2.pdf
    private static final double BOTTOM_OF_TARGET_OFFSET = 2.0;
    private static final double CENTER_OF_TARGET_OFFSET = 9.0;
    private static final double COMBINED_TARGET_OFFSETS = BOTTOM_OF_TARGET_OFFSET + CENTER_OF_TARGET_OFFSET;
    private static final double kTopTargetHeightIn = 98.0 + COMBINED_TARGET_OFFSETS;
    private static final double kMiddleTargetHeightIn = 62.0 + COMBINED_TARGET_OFFSETS;
    private static final double kLowerTargetHeightIn = 28 + COMBINED_TARGET_OFFSETS;

    private TreeMap<Double, Double> rangeTable;

    private boolean m_debugMode = false;

    // Store JavaCV temporaries as members to reduce memory management during processing
    private CvSize size = null;
    private WPIContour[] contours;
    private ArrayList<WPIPolygon> polygons;
    private IplConvKernel morphKernel;
    private IplImage bin;
    private IplImage hsv;
    private IplImage hue;
    private IplImage sat;
    private IplImage val;
    private WPIPoint linePt1;
    private WPIPoint linePt2;
    private int horizontalOffsetPixels;

    public ByrdsEyeCVWidget()
    {
        this(false);
    }

    public ByrdsEyeCVWidget(boolean debug)
    {
        m_debugMode = debug;
        morphKernel = IplConvKernel.create(3, 3, 1, 1, opencv_imgproc.CV_SHAPE_RECT, null);

        // range in inches; range is 10'10" .. 22'6"
        // TODO: RPMs should be translated to power levels b/c we have no encoder
        rangeTable = new TreeMap<Double,Double>();
        rangeTable.put(130.0, 4000.0+kRangeOffset);
        rangeTable.put(140.0, 4100.0+kRangeOffset);
        rangeTable.put(150.0, 4250.0+kRangeOffset);
        rangeTable.put(160.0, 4400.0+kRangeOffset);
        rangeTable.put(170.0, 4600.0+kRangeOffset);
        rangeTable.put(180.0, 4800.0+kRangeOffset);
        rangeTable.put(190.0, 5000.0+kRangeOffset);
        rangeTable.put(200.0, 5200.0+kRangeOffset);
        rangeTable.put(210.0, 5400.0+kRangeOffset);
        rangeTable.put(220.0, 5600.0+kRangeOffset);
        rangeTable.put(230.0, 5800.0+kRangeOffset);
        rangeTable.put(240.0, 6000.0+kRangeOffset);
        rangeTable.put(250.0, 6200.0+kRangeOffset);
        rangeTable.put(260.0, 6200.0+kRangeOffset);
        rangeTable.put(270.0, 6200.0+kRangeOffset);

        DaisyExtensions.init();

        addMouseListener(new TargetingMouseListener());
    }


    public double getRPMsForRange(double range)
    {
        double lowKey = -1.0;
        double lowVal = -1.0;
        for( double key : rangeTable.keySet() )
        {
            if( range < key )
            {
                double highVal = rangeTable.get(key);
                if( lowKey > 0.0 )
                {
                    double m = (range-lowKey)/(key-lowKey);
                    return lowVal+m*(highVal-lowVal);
                }
                else
                    return highVal;
            }
            lowKey = key;
            lowVal = rangeTable.get(key);
        }

        return 6200.0+kRangeOffset;
    }
    
    @Override
    public WPIImage processImage(WPIColorImage rawImage)
    {
        double heading = 0.0;
/*
 *      NOT APPLICABLE TO LARRYBYRD
        // Get the current heading of the robot first
        if( !m_debugMode )
        {
            try
            {
                heading = Robot.getTable().getDouble("Heading");
            }
            catch( NoSuchElementException e)
            {
            }
            catch( IllegalArgumentException e )
            {
            }
        }
*/
        if( size == null || size.width() != rawImage.getWidth() || size.height() != rawImage.getHeight() )
        {
            size = opencv_core.cvSize(rawImage.getWidth(),rawImage.getHeight());
            bin = IplImage.create(size, 8, 1);
            hsv = IplImage.create(size, 8, 3);
            hue = IplImage.create(size, 8, 1);
            sat = IplImage.create(size, 8, 1);
            val = IplImage.create(size, 8, 1);
            // 3929 offset the offset!
            //TODO:  CHECK OFFSET ERROR
            horizontalOffsetPixels =  (int)Math.round(kShooterOffsetDeg*(size.width()/kHorizontalFOVDeg));
            // Daisy lines
            linePt1 = new WPIPoint(size.width()/2+horizontalOffsetPixels,size.height()-1);
            linePt2 = new WPIPoint(size.width()/2+horizontalOffsetPixels,0);
            // 3929 lines
//            linePt1 = new WPIPoint(size.width()/2,size.height()-1);
//            linePt2 = new WPIPoint(size.width()/2,0);


        }
        // Get the raw IplImages for OpenCV
        IplImage input = DaisyExtensions.getIplImage(rawImage);

        // Convert to HSV color space
        opencv_imgproc.cvCvtColor(input, hsv, opencv_imgproc.CV_BGR2HSV);
        opencv_core.cvSplit(hsv, hue, sat, val, null);

        // Threshold each component separately
        // Hue, Daisy 45..255
        // NOTE: Red is at the end of the color space, so you need to OR together
        // a thresh and inverted thresh in order to get points that are red
        int hueThresholdLow = 0;
        int hueThresholdHigh = 255;
        opencv_imgproc.cvThreshold(hue, bin, hueThresholdLow, hueThresholdHigh, opencv_imgproc.CV_THRESH_BINARY);
//        opencv_imgproc.cvThreshold(hue, bin, 60-15, 255, opencv_imgproc.CV_THRESH_BINARY);
//        opencv_imgproc.cvThreshold(hue, hue, 60+15, 255, opencv_imgproc.CV_THRESH_BINARY_INV);

        // Saturation, Daisy was 200..255
        // 3929 Blue:  100..255
        int satThresholdLow = 0;
        int satThresholdHigh = 255;
        opencv_imgproc.cvThreshold(sat, sat, satThresholdLow, satThresholdHigh, opencv_imgproc.CV_THRESH_BINARY);

        // Value Daisy was 55..255
        // 3929 Blue:  135..255
        int valThresholdLow = 250;
        int valThresholdHigh = 255;
        opencv_imgproc.cvThreshold(val, val, valThresholdLow, valThresholdHigh, opencv_imgproc.CV_THRESH_BINARY);

        // Combine the results to obtain our binary image which should for the most
        // part only contain pixels that we care about
        opencv_core.cvAnd(hue, bin, bin, null);
        opencv_core.cvAnd(bin, sat, bin, null);
        opencv_core.cvAnd(bin, val, bin, null);

        // Uncomment the next two lines to see the raw binary image
//        CanvasFrame result = new CanvasFrame("binary");
//        result.showImage(bin.getBufferedImage());

        // Fill in any gaps using binary morphology
        opencv_imgproc.cvMorphologyEx(bin, bin, null, morphKernel, opencv_imgproc.CV_MOP_CLOSE, 9);

        // Uncomment the next two lines to see the image post-morphology
//        CanvasFrame result2 = new CanvasFrame("morph");
//        result2.showImage(bin.getBufferedImage());

        // Find contours
        WPIBinaryImage binWpi = DaisyExtensions.makeWPIBinaryImage(bin);
        contours = DaisyExtensions.findConvexContours(binWpi);

        polygons = new ArrayList<WPIPolygon>();
        for (WPIContour c : contours)
        {
            double ratio = ((double) c.getHeight()) / ((double) c.getWidth());
            if (ratio < 1.0 && ratio > 0.5 && c.getWidth() > kMinWidth && c.getWidth() < kMaxWidth)
            {
                polygons.add(c.approxPolygon(20));
            }
        }

        WPIPolygon square = null;
        int highest = Integer.MAX_VALUE;

        for (WPIPolygon p : polygons)
        {
            if (p.isConvex() && p.getNumVertices() == 4)
            {
                // We passed the first test...we fit a rectangle to the polygon
                // Now do some more tests

                WPIPoint[] points = p.getPoints();
                // We expect to see a top line that is nearly horizontal, and two side lines that are nearly vertical
                int numNearlyHorizontal = 0;
                int numNearlyVertical = 0;
                for( int i = 0; i < 4; i++ )
                {
                    double dy = points[i].getY() - points[(i+1) % 4].getY();
                    double dx = points[i].getX() - points[(i+1) % 4].getX();
                    double slope = Double.MAX_VALUE;
                    if( dx != 0 )
                        slope = Math.abs(dy/dx);

                    if( slope < kNearlyHorizontalSlope )
                        ++numNearlyHorizontal;
                    else if( slope > kNearlyVerticalSlope )
                        ++numNearlyVertical;
                }

                // TODO:  HIGHEST TARGET IS ALWAYS CHOSEN
                // QUESTION WHETHER WE WANT TO PUT OTHER TARGETS
                // ONTO THE FIRING TABLE
                if(numNearlyHorizontal >= 1 && numNearlyVertical == 2)
                {
                    rawImage.drawPolygon(p, WPIColor.BLUE, 2);

                    int pCenterX = (p.getX() + (p.getWidth() / 2));
                    int pCenterY = (p.getY() + (p.getHeight() / 2));

                    rawImage.drawPoint(new WPIPoint(pCenterX, pCenterY), targetColor, 5);
                    if (pCenterY < highest) // Because coord system is funny
                    {
                        square = p;
                        highest = pCenterY;
                    }
                }
            }
            else
            {
                rawImage.drawPolygon(p, WPIColor.YELLOW, 1);
            }
        }

        if (square != null)
        {
            double x = square.getX() + (square.getWidth() / 2);
            x = (2 * (x / size.width())) - 1;
            double y = square.getY() + (square.getHeight() / 2);
            y = -((2 * (y / size.height())) - 1);

            double azimuth = this.boundAngle0to360Degrees(x*kHorizontalFOVDeg/2.0 + heading - kShooterOffsetDeg);
            double range = (kTopTargetHeightIn-kCameraHeightIn)/Math.tan((y*kVerticalFOVDeg/2.0 + kCameraPitchDeg)*Math.PI/180.0);
            double rpms = getRPMsForRange(range);

            if (!m_debugMode)
            {

                Robot.getTable().beginTransaction();
                Robot.getTable().putBoolean("found", true);
                Robot.getTable().putDouble("azimuth", azimuth);
                Robot.getTable().putDouble("rpms", rpms);
                Robot.getTable().endTransaction();
            } else
            {
                System.out.println("Target found");
                System.out.println("x: " + x);
                System.out.println("y: " + y);
                System.out.println("azimuth: " + azimuth);
                System.out.println("range: " + range);
                System.out.println("rpms: " + rpms);
            }
            rawImage.drawPolygon(square, targetColor, 7);
        } else
        {

            if (!m_debugMode)
            {
                Robot.getTable().putBoolean("found", false);
            } else
            {
                System.out.println("Target not found");
            }
        }

        // Draw a crosshair
        rawImage.drawLine(linePt1, linePt2, targetColor, 2);

        DaisyExtensions.releaseMemory();

        //System.gc();

        return rawImage;
    }

    private double boundAngle0to360Degrees(double angle)
    {
        // Naive algorithm
        while(angle >= 360.0)
        {
            angle -= 360.0;
        }
        while(angle < 0.0)
        {
            angle += 360.0;
        }
        return angle;
    }

    public static void main(String[] args)
    {
        if (args.length == 0)
        {
            args = new String[6];
            args[0] = "C:\\Users\\bhgray\\Desktop\\vision results\\20120212_16-05-34.jpg";
            args[1] = "C:\\Users\\bhgray\\Desktop\\vision results\\20120212_16-02-37.jpg";
            args[2] = "C:\\Users\\bhgray\\Desktop\\vision results\\20120212_14-31-52.jpg";
            args[3] = "C:\\Users\\bhgray\\Desktop\\vision results\\20120212_14-48-00.jpg";
            args[4] = "C:\\Users\\bhgray\\Desktop\\vision results\\20120212_15-02-47.jpg";
            args[5] = "C:\\Users\\bhgray\\Desktop\\vision results\\20120212_15-02-47.jpg";

            //System.out.println("Usage: Arguments are paths to image files to test the program on");
            //return;
        } else {

        }

        // Create the widget
        ByrdsEyeCVWidget widget = new ByrdsEyeCVWidget(true);

        long totalTime = 0;
        for (int i = 0; i < args.length; i++)
        {
            // Load the image
            WPIColorImage rawImage = null;
            try
            {
                rawImage = new WPIColorImage(ImageIO.read(new File(args[i%args.length])));
            } catch (IOException e)
            {
                System.err.println("Could not find file!");
                return;
            }
            
            //shows the raw image before processing to eliminate the possibility
            //that both may be the modified image.
            CanvasFrame original = new CanvasFrame("Raw");
            original.showImage(rawImage.getBufferedImage());

            WPIImage resultImage = null;

            // Process image
            long startTime, endTime;
            startTime = System.nanoTime();
            resultImage = widget.processImage(rawImage);
            endTime = System.nanoTime();

            // Display results
            totalTime += (endTime - startTime);
            double milliseconds = (double) (endTime - startTime) / 1000000.0;
            System.out.format("Processing took %.2f milliseconds%n", milliseconds);
            System.out.format("(%.2f frames per second)%n", 1000.0 / milliseconds);
            
            CanvasFrame result = new CanvasFrame("Result");
            result.showImage(resultImage.getBufferedImage());

            System.out.println("Waiting for ENTER to continue to next image or exit...");
            Scanner console = new Scanner(System.in);
            console.nextLine();

            if (original.isVisible())
            {
                original.setVisible(false);
                original.dispose();
            }
            if (result.isVisible())
            {
                result.setVisible(false);
                result.dispose();
            }
        }

        double milliseconds = (double) (totalTime) / 1000000.0 / (args.length);
        System.out.format("AVERAGE:%.2f milliseconds%n", milliseconds);
        System.out.format("(%.2f frames per second)%n", 1000.0 / milliseconds);
        System.exit(0);
    }
}
