/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package team3929.templates;


import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import team3929.commands.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import team3929.commands.DriveCommands.DriveStraight;
import team3929.commands.DriveCommands.DriveWithJoystick;
import team3929.commands.DriveCommands.LockDrivetrain;
import team3929.commands.DriveCommands.Turn;
import team3929.commands.DriveCommands.UnlockDriveTrain;
import team3929.commands.ManualShooterControl;
import team3929.ShooterTestCommands.*;

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
    SendableChooser shooterTester;
     Command shooterCommand;
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        // instantiate the command used for the autonomous period
        NetworkTable.initialize();

        autoChooser = new SendableChooser();
        autoChooser.addObject("Turn", new Turn(4));
        autoChooser.addObject("Drive Straight", new DriveStraight(4));
        autoChooser.addObject("Lock drivetrain", new LockDrivetrain());
        autoChooser.addObject("Unlock drivetrain", new UnlockDriveTrain());
        autoChooser.addDefault("Drive With Joystick", new DriveWithJoystick());
        shooterTester.addDefault("Manual", new ManualShooterControl());
        shooterTester.addObject(".1", new Speed1());
        shooterTester.addObject(".2", new Speed2());
        shooterTester.addObject(".3", new Speed3());
        shooterTester.addObject(".4", new Speed4());
        shooterTester.addObject(".5", new Speed5());
        shooterTester.addObject(".6", new Speed6());
        shooterTester.addObject(".7", new Speed7());
        shooterTester.addObject(".8", new Speed8());
        shooterTester.addObject(".9", new Speed9());
        shooterTester.addObject(".10", new Speed10());
        SmartDashboard.putData("autoChooser", autoChooser);
        SmartDashboard.putData("Shooter Testing",shooterTester);
        SmartDashboard.putData("Scheduler", Scheduler.getInstance());
        
        CommandBase.init();
    }
    
    public void autonomousInit() {
        // schedule the autonomous command (example)
       

    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		
                CommandBase.init();
                //initializing commandbase is crucial.  Without this small class, nothing would happen.  
                autonomousCommand = (Command) autoChooser.getSelected();
                autonomousCommand.start();
                shooterCommand = (Command) shooterTester.getSelected();
                shooterCommand.start();

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {

                    autonomousCommand = (Command) autoChooser.getSelected();
       
                    Scheduler.getInstance().run();
                
        
    }
}
