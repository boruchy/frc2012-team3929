/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team3929.subsystems;

import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;
import team3929.commands.DriveCommands.DriveWithJoystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import team3929.commands.DriveCommands.DriveWithJoystick;
import team3929.subsystems.DriveTrain;
import team3929.templates.MadCatzControl;
import team3929.templates.RobotMap;

/**
 *
 * @author Carter
 */
public class Chassis extends Subsystem {

    DriveTrain drive = new DriveTrain(RobotMap.DPWM_driveJag1, RobotMap.DPWM_driveJag2, RobotMap.DPWM_driveJag3, RobotMap.DPWM_driveJag4);//instantiate RobotDrive on ports 1,2,3,4 for left and right motors
    Encoder leftEncoder = new Encoder(RobotMap.DIO_driveEncoder1Channel1, RobotMap.DIO_driveEncoder1Channel2, false);
    Encoder rightEncoder = new Encoder(RobotMap.DIO_driveEncoder2Channel1, RobotMap.DIO_driveEncoder2Channel2, false);
    //create the left and right corresponding encoders that are counting rotations 
    boolean isLocked;

    public Chassis() {
        isLocked = false;
    }

    public void initDefaultCommand() {
        setDefaultCommand(new DriveWithJoystick()); // set default command
    }

    public void straight() { // sets the motor speeds to drive straight (no turn)
        if (!isLocked) {
            drive.tankDrive(.25, 0.0);
        }
    }

    public void turnLeft() { // sets the motor speeds to start a left turn
        if (!isLocked) {
            drive.tankDrive(0.0, .45);
        }
    }

    public void unlock() {
        isLocked = false;
    }

    public void driveWithJoystick(double stickLeft, double stickRight) {//a method the drives with joystick given to doubles
        if (!isLocked) {
            drive.tankDrive(stickLeft, stickRight);
        }
    }

    public void lock() {
        
        drive.tankDrive(0, 0);
        
    }
}
