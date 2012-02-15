/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team3929.subsystems.DriveSubsystems;

import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;
import team3929.commands.DriveCommands.DriveWithJoystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import team3929.commands.DriveCommands.DriveWithJoystick;
import team3929.templates.HIDControl.MadCatzControl;
import team3929.templates.SensorsControl.RobotMap;
/**
 *
 * @author Carter
 */

public class Chassis extends Subsystem {
    
    
    RobotDrive drive = new RobotDrive(2,1);//instantiate RobotDrive on ports 2,1 for left and right motors
    Encoder leftEncoder = new Encoder(RobotMap.DIO_driveEncoder1Channel1,RobotMap.DIO_driveEncoder1Channel2,false);
    Encoder rightEncoder = new Encoder(RobotMap.DIO_driveEncoder2Channel1,RobotMap.DIO_driveEncoder2Channel2,false);
    //create the left and right corresponding encoders that are counting rotations 

    public Chassis() {
        
        
    }
    public void initDefaultCommand() {
         setDefaultCommand(new DriveWithJoystick()); // set default command
    }
    public void straight() { // sets the motor speeds to drive straight (no turn)
    drive.arcadeDrive(.25,0.0);
    }
    public void turnLeft() { // sets the motor speeds to start a left turn
        drive.arcadeDrive(0.0, .45);
    }
    public void driveWithJoystick(double stickLeft,double stickRight) {//a method the drives with joystick given to doubles 
        
        drive.tankDrive(stickLeft, stickRight);
        
    }
    
}
