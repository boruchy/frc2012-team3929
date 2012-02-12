/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package team3929.templates;


import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import team3929.commands.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import team3929.commands.DriveCommands.DriveStraight;
import team3929.commands.DriveCommands.DriveWithJoystick;
import team3929.commands.DriveCommands.Turn;
import team3929.subsystems.DriveSubsystems.Chassis;
import team3929.subsystems.ExampleSubsystem;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class LarryByrd extends IterativeRobot {

    Command autonomousCommand;
    SendableChooser autoChooser;
    
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        // instantiate the command used for the autonomous period
        NetworkTable.initialize();

        autoChooser = new SendableChooser();
        autoChooser.addDefault("Turn", new Turn(4));
        autoChooser.addObject("Drive Straight", new DriveStraight(4));
        SmartDashboard.putData("autoChooser", autoChooser);
        SmartDashboard.putData("Scheduler", Scheduler.getInstance());
        
        CommandBase.init();
    }
    
    public void autonomousInit() {
        // schedule the autonomous command (example)
       autonomousCommand = (Command) autoChooser.getSelected();
       autonomousCommand.start();

    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        autonomousCommand = (Command) autoChooser.getSelected();
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		
                CommandBase.init();
                //initializing commandbase is crucial.  Without this small class, nothing would happen.  

                

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {

                    
                    Scheduler.getInstance().run();
                
        
    }
}