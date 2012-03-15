/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
         
package team3929.vision;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.image.BinaryImage;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.NIVision;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import java.util.Vector;
import team3929.templates.RobotMap;


public class ImageTracker {

    

    public static double rectangularityScore(double area, double width, double height) {
        // A perfect rectangle will have a score of 100
        double rectangularityScore = area;
        rectangularityScore /= width;
        rectangularityScore /= height;
        rectangularityScore *= 100;
        return rectangularityScore;
    }

    public static double aspectRatioScore(double width, double height) {
        double aspectRatioScore = width / height;
        // 24 x 18 target
        aspectRatioScore /= 1.33; // normalize
        aspectRatioScore = Math.abs(1.0 - aspectRatioScore); //Teepee at 1
        aspectRatioScore = 100.0 * (1.0 - aspectRatioScore);
        if (aspectRatioScore > 100) {
            aspectRatioScore = 100;
        } else if (aspectRatioScore < 0) {
            aspectRatioScore = 0;
        }
        return aspectRatioScore;
    }

    public static Target[] processImage(ColorImage image) {

        if (image == null) {
            return null;   //If the image is null, return a null target
        }
        BinaryImage masked;
        BinaryImage hulled;
        BinaryImage filtered;
        Vector rects = new Vector();
        rects.ensureCapacity(4);
        ParticleAnalysisReport[] all;

        try {
            masked = image.thresholdHSL(RobotMap.lowHThresh, RobotMap.highHThresh, RobotMap.lowSThresh, RobotMap.highSThresh, RobotMap.lowLThresh, RobotMap.highLThresh);
            System.out.print("masking at H: " + RobotMap.lowHThresh + ", " + RobotMap.highHThresh + ";");
            System.out.print("S: " + RobotMap.lowSThresh + ", " + RobotMap.highSThresh + ";");
            System.out.println("L: " + RobotMap.lowLThresh + ", " + RobotMap.highLThresh);
            // NIVision399.convexHull(masked.image, masked.image, 1);   //Convex hull(close any particles) on input image
            hulled = masked.convexHull(true);
            filtered = hulled.removeSmallObjects(true, 2);
            all = filtered.getOrderedParticleAnalysisReports(10);  //Get sorted particle report. sorted in order of size
            System.out.println(RobotMap.TEAM_PREFIX_VISION + "ParticleAnalysisReport returned " + all.length + " analyses.");
            try {
                image.write("C:\\Users\\Carter\\Desktop\\1-image.jpg");
                hulled.write("C:\\Users\\Carter\\Desktop\\1-hulled.jpg");
                masked.write("C:\\Users\\Carter\\Desktop\\1-masked.jpg");
            } catch (Exception e) {
                System.out.println(e.toString());
            }

            image.free();
            hulled.free();
            masked.free();
        } catch (NIVisionException e) {
            return null;
        }

        for (int i = 0; i < all.length; i++) {               //Store the targets that pass the size limit
            System.out.println(RobotMap.TEAM_PREFIX_VISION + "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            System.out.println(RobotMap.TEAM_PREFIX_VISION + "all[" + i + "] = " + all[i]);
            System.out.println(RobotMap.TEAM_PREFIX_VISION + "area threshhold > " + RobotMap.areaThresh);
            System.out.println(RobotMap.TEAM_PREFIX_VISION + "all[" + i + "] area = " + all[i].particleArea);
            if (all[i].particleArea > RobotMap.areaThresh) {

                double rectangularityScore =
                        rectangularityScore(all[i].particleArea,
                        all[i].boundingRectWidth,
                        all[i].boundingRectHeight);
                System.out.println(RobotMap.TEAM_PREFIX_VISION + "rectangularity threshhold > " + RobotMap.rectThresh);
                System.out.println(RobotMap.TEAM_PREFIX_VISION + "all[" + i + "] rectangularity score = " + rectangularityScore);

                double aspectRatioScore =
                        aspectRatioScore(all[i].boundingRectWidth,
                        all[i].boundingRectHeight);

                System.out.println(RobotMap.TEAM_PREFIX_VISION + "aspectRatio threshhold > " + RobotMap.aspectThresh);
                System.out.println(RobotMap.TEAM_PREFIX_VISION + "all[" + i + "] aspectRatio score = " + aspectRatioScore);
                if (rectangularityScore > RobotMap.rectThresh
                        && aspectRatioScore > RobotMap.aspectThresh) {
                    Target current = new Target(all[i]);
                    rects.addElement(current);          //Target found, return it
                }
            }
        }
        rects.trimToSize();
        Target[] targets = new Target[rects.size()];
        rects.copyInto(targets);
        System.out.println(RobotMap.TEAM_PREFIX_VISION + "targets found " + targets.length);
        if (targets.length == 0) {
            return null;
        }
        return targets;
    }

    /*
     * Get a certain target
     * @param image the input image
     * @param index 0 for the lowest target. 1 for the left target. 2 for the right target. 3 for the top target
     * @return a target object if that target was found. null if no target found
     */
    public static Target getTarget(Target[] targets, int index) {

        if (targets != null) {
            for (int i = 0; i < targets.length; i++) {
                System.out.println("Target #" + i + ":");
                System.out.println(targets[i].toString());
            }
            ImageTracker.Target focus = targets[0];
            if (index == 0) {
                for (int i = 0; i < targets.length; i++) {
                    if (focus.y < targets[i].y) {
                        focus = targets[i];
                    }
                }
            } else if (index == 1) {
                for (int i = 0; i < targets.length; i++) {
                    if (focus.x < targets[i].x) {
                        focus = targets[i];
                    }
                }
            } else if (index == 2) {
                for (int i = 0; i < targets.length; i++) {
                    if (focus.x > targets[i].x) {
                        focus = targets[i];
                    }
                }
            } else if (index == 3) {
                for (int i = 0; i < targets.length; i++) {
                    if (focus.y > targets[i].y) {
                        focus = targets[i];
                    }
                }
            }
            return focus;

        }

        return null;

    }

    public static class Target {

        public int x;
        public int y;
        public int width;
        public int height;
        public double distance;
        public double area;

        public Target(ParticleAnalysisReport par) {
            x = par.center_mass_x;
            y = par.center_mass_y;
            width = par.boundingRectWidth;
            height = par.boundingRectHeight;
            area = par.particleArea;
            distance = calculateDistance();
        }

        public String toString() {
            int feet = (int) distance;
            int inches = (int) ((distance - feet) * 12);
            return " X: " + x + " Y: " + y + " H: " + height + " W: "
                    + width + " Distance: " + feet + " " + inches + " Time: " + System.currentTimeMillis();
        }

        public int getPixelsAboveCenter() {
            return x - (120);
        }

        public double getTargetHeight(double cameraAngle) {
            double scalingA = -1 / 50.0;    //1 foot per 42 pixels
            double yVal = y;                //Y pixel value
            double scalingB = 1.1;
            double range = distance;        //
            double theta = cameraAngle + MathUtils.asin(scalingA * (yVal - RobotMap.cameraHeight / 2.0) / range);
            return range * Math.sin(theta) + RobotMap.cameraOnTurretHeight;
        }
        private final double cameraHalfTanAngle = Math.tan(48.0 * Math.PI / 360.0);

        public double calculateDistance() {
            return RobotMap.cameraWidth / (width * cameraHalfTanAngle);
        }
    }

    public static void main(String[] args)
    {
        System.err.println("test err");
    }
}
