/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team3929.subsystems.DriveSubsystems;

import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;
import team3929.commands.DriveCommands.DriveWithJoystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import team3929.commands.DriveCommands.DriveWithJoystick;
import team3929.templates.HIDControl.MadCatzControl;
/**
 *
 * @author Carter
 */

public class Chassis extends Subsystem {
    
    RobotDrive drive;
    private DriverStationLCD d=  DriverStationLCD.getInstance();

    public void initDefaultCommand() {
         setDefaultCommand(new DriveWithJoystick()); // set default command
    }
    public Chassis() {
        drive = new RobotDrive(2, 1);
        drive.setSafetyEnabled(false);
    }
    public void straight() { // sets the motor speeds to drive straight (no turn)
        drive.arcadeDrive(.45, 0.0);
    }
    public void turnLeft() { // sets the motor speeds to start a left turn
        drive.arcadeDrive(0.0, .45);
    }
    public void driveWithJoystick(double stickLeft,double stickRight) {
        
        drive.tankDrive(stickLeft, stickRight);
        
    }
    
}
