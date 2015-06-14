

# Introduction #

This is intended to document some of the steps we've taken to setup the tool base for vision processing.  The tools are intended to support

  1. vision processing on the CRIO, the firing solutions selected from the operator station, and/or
  1. vision processing on the operator station, with firing solutions selected on the op station, and sent back to the robot

# Required Tools #
  1. Java SDK 1.6.29 (NOT 1.7.X)
  1. NetBeans 6.9.1 (NOT 7.X.X)
  1. FRC Plugins
  1. SmartDashboard full install (from [WPI](http://firstforge.wpi.edu/sf/projects/smartdashboard))

# Helpful Documents #
  1. FRC Getting Started with Java
  1. FRC Getting Started with Control Systems
  1. FRC WPI Libraries Users Guide
  1. FRC WPI Cookbook
  1. 2012 Vision Targets Whitepaper

# Op Station Processing Overview #

SmartDashboard is a standalone executable that uses the full Java JVM. The cRIO uses the SQUAWK JVM which is optimized for embedded mobile platforms.  Squawk does NOT include Swing, JNA wrapping, or other features of the full JVM (see page 23 of the Getting Started with Java document).  Therefore, to implement vision processing that communicates with the SmartDashboard, you must create a project separate from that of the robot code so that it links against the full Java SDK (currently 1.6.29).

For a quick tutorial, see the WPI Cookbook, page 69 et seq.

  1. Set up a SmartDashboard Extension (see WPI Cookbook)
    * Create a new Project of type "Java Application"
    * Right-click on the project name in the Projects tab, and choose "Properties."
    * Under the "Libraries" node, choose the "Compile" tab, and make sure the following libraries are included:
      * C:\Program Files\SmartDashboard\SmartDashboard.jar
      * C:\Program Files\SmartDashboard\lib\NetworkTable\_Client.jar
      * C:\Program Files\SmartDashboard\extensions\WPIJavaCV.jar
      * C:\Program Files\SmartDashboard\extensions\WPICameraExtension.jar
      * C:\Program Files\SmartDashboard\extensions\lib\javacpp.jar
      * C:\Program Files\SmartDashboard\extensions\lib\javacv-windows-x86.jar
      * C:\Program Files\SmartDashboard\extensions\lib\javacv.jar
    * Create a new package (if desired) and then a new file of type "Java Class"
    * You can use the sample code from [here](http://pastebin.com/cYtQiKpF) as an initial test.  It uses RGB thresholding, though most of the Chief Delphi posts say that HSV thresh holding is more accurate.  TBD.  (Please don't read that prior sentence to indicate that I currently have anything above a rudimentary understanding of what the thresholding does....)
    * When you compile, a JAR file is created in the 

<PROJECT\_HOME>

/dist directory by default.  Copy this JAR to the SmartDashboard\extensions\ directory. (You can also set the build phase to copy this for you in the ANT file -- more on this later.)
    * Once the JAR file is in the proper directory, start SmartDashboard.
      * In the "View" menu, choose "Add" and then "Laptop Camera Square Tracker" (or the name you gave it in the code.)
      * Click on View->Editable, then resize and position the extension as you wish.  Right-click on the extension, choose Properties, then input the IP address for the robot's camera:  10.39.29.11.  (See notes on setting up the Axis Camera, coming later today).
      * Make sure the robot it powered, and that you are connected to the Team3929 network (on the robots router).  Images should start to appear in the extension, including its attempts at recognizing polygons.

MORE COMING LATER TODAY