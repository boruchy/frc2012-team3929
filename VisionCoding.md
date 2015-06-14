

# Introduction #

The programming sub team has been investigating the various methods by which we can perform machine vision tasks.  The goal is to have the robot identify the parameters necessary for a firing solution.  The turret will use this information to program it's z-axis rotation, hood elevation, and firing velocity.

The vision whitepaper and Chief Delphi give several techniques, but they basically converge as follows:

  1. grab a color image from the onboard Axis camera
  1. create a binary image using a HSL filter
  1. use convex hull algorithms to identify closed polygons
  1. use  scoring  (rectangularity and aspect ratio metrics) to identify the polygons that have a high likelihood of being the four retroreflective tape rectangles on the backboards
  1. based on the skew of the polygons caused by perspective, calculate the distance and angle to the target in order to compute a firing solution.

The processing steps can be accomplished in several ways

  1. on the cRio using LabView and the NI Vision Assistant
  1. on the cRio using Java and jna wrapped java functions that call the NI Vision C functions
  1. on the laptop using the WPI libraries
  1. on the laptop using Java and OpenCV

# Laptop - Java - OpenCV #

OpenCV is an Open-Source Computer Vision library.  It is written in C++, but a Google Code project (http://code.google.com/p/javacv/) wraps OpenCV c calls into Java.  Using these wrappers, we can call any OpenCV function and use it in our Java code.

Steps for Use:
  1. Install OpenCV for Windows (unpack the ZIP file to the root directory as its default)
  1. Download the JavaCV jar files (copy and store them anywhere)
  1. In NetBeans, create a new Library that points to javacpp.jar, javacv.jar, and javacv-windows-x86.jar
  1. Create a new project, and set the Project -> Libraries preference to the new Library you created in the previous step.
  1. In the project, you can import, for example

```
import com.googlecode.javacpp.Loader;
import com.googlecode.javacv.*;
import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;
import static com.googlecode.javacv.cpp.opencv_calib3d.*;
import static com.googlecode.javacv.cpp.opencv_objdetect.*;
```

**Note** The WPIImage class uses the opencv IplImage class as its backing store.  The backing store is protected but I saw the idea to write a WPIImage extension that does nothing more than return the IplImage when requested.  That means we can use opencv in our SmartDashboard extensions in the processImage method.

## Daisy Code Analysis ##
Team 341 have posted their 2012 code for DaisyBot.  They have been extremely generous, and the code is incredibly instructive for us.  341's vision subsystem uses two interlocking components: first, it has a WPICameraExtension that runs on the SmartDashboard and does all the vision processing on the laptop, sending a firing solution (a flag, the azimuth, and the RPMs for the shooter) to the robot via the NetworkTable; second, it has a system of drive- and shootercontrollers that process the targeting information and adjust the robot's angle and shooter's rpm accordingly.  DaisyBot uses the drivetrain for aiming on the z-axis, and has a fixed angle shooter.  They adjust the RPM of their shooter motor to select the shot velocity appropriate for the range, as specified in a lookup table in the WPICameraExtension.  They also use a navigation system to track Daisy's trajectory (storing position, velocity, and acceleration).

In teleop mode, they use teleopPeriodic (no teleopContinuous override) to poll and process driver inputs via the OI file's processDriverInputs() method.  The driver has four states:  manual, shifting, autoaim, and autobalance.

### autoaim mode ###
The driver enters autoaim mode by holding the left trigger.  The drive changes its controller to the AutoAimDriveController, and the shooter changes to AutoAimShooterController.  The light on the shooter is set to ON if both the drive and shooter report that they are on target.  (On target means different things depending on the subsystem:  for the drive, it means that it has adjusted the robot's angle so that the shooter is facing the target; for the shooter, it means that the ShooterSpeedController has gotten the shooter up to the target RPM level, as read off the lookup table in the DaisyCVWidget.)
Once the light is on, the driver can shoot by pressing the A button.

The **AutoAimDriveController** class polls the Vision subsystem (a state wrapper that polls the NetworkTable for the keys "found," "azimuth," and "rpms.") for the current target azimuth.  It then calls the TurnController's run method, which shifts into low gear and starts the process of calculating (through its FeedForwardPIV) the adjustments necessary to head towards the target azimuth (via the driveSpeedTurn method).

In our case, we use the Turret to change azimuth, but the process will be much the same.

The **AutoAimShooterController** class also polls the Vision subsystem for the target RPM level, and calls the shooter's run() method to advance toward the target RPM level.  Our system does much the same.

We currently have a hood that is variable in about 60 degrees (from -60 to straight), has a window motor, and is controlled with a Victor.  Given that the firing solution can be determined using only azimuth and velocity (but with a limited range therefore), we are contemplating setting two stops on the hood, and generating two firing lookup tables -- a long range and a short range.

### Image Processing ###
The WPICameraExension takes care of the heavy processing on the laptop side using opencv and the java wrappers described above.  The overridden processImage method runs as follows:

  1. convert WPIColorImage to an IplImage, then to HSV
  1. threshold each H-S-V component separately, then AND them back together into a BinaryImage
  1. fill in gaps, then perform the convex hull using opencv\_imgproc.cvFindContours bridged method
  1. loop through the contours, looking for convex polygons that match aspect ratio and raw size tests
  1. test each polygon to find a "square" -- tests each polygon to check whether it has two "nearly" vertical and at least 1 "nearly" horizontal edges
  1. the square is the target -> find the center of mass, and use that to find azimuth (offset from center point) and range
  1. put the information onto the Robot's networktable

# cRIO - Java - WPI/NiVision libraries #
The Team 399 code is based on the sample targeting code included with the NetBeans plugins.  It takes a ColorImage from the camera, uses a thresholdHSL to produce a BinaryImage.  The BinaryImage is then run through a convex hull, then filtered for particles that are too small.  Each remaining particle is then run through area, rectangularity and aspect ratio scoring.  The results are then presumed to be targets.

**Libraries** The WPI classes, Image, ColorImage, and BinaryImage, use the NIVision c libraries wrapped through the WPI wrappers (NIVision.java).  Image and ColorImage are abstract.  Trying to instantiate an HSLImage or RGBImage (concrete subclasses of ColorImage) results in a lovely stack trace.  Error is the ```
java.lang.ClassCastException: java.lang.String cannot be cast to com.sun.squawk.Address```. The backing store to the Image class is a Pointer to an image in memory.  The Image constructor calls NIVision.imaqCreateImage with an enumerated type.  imaqCreateImage allocates the pointer, which causes the JNA error.

The results were encouraging so far.  In a single target test, the algorithm identified the center of the target within 3 pixels.  Distance was 98% accurate. Tests were conducted at angles from +-45 degrees off of orthogonal from the basket. After that, the results were mixed, and further tests will be required.