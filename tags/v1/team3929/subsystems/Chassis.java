/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team3929.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import team3929.commands.DriveCommands.DriveWithJoystick;
import team3929.subsystems.DriveTrain;
import team3929.templates.RobotMap;

/**
 *
 * @author Carter
 * @version 2012-03-02-1756 bhgray
 */
public class Chassis extends Subsystem {

    DriveTrain drive;
    Encoder leftEncoder;
    Encoder rightEncoder;
    boolean isLocked;

     public Chassis() {
        isLocked = false;
        drive = new DriveTrain(RobotMap.DPWM_driveJag1, RobotMap.DPWM_driveJag2, RobotMap.DPWM_driveJag3, RobotMap.DPWM_driveJag4);//instantiate RobotDrive on ports 1,2,3,4 for left and right motors
        leftEncoder = new Encoder(RobotMap.DIO_driveEncoder1Channel1, RobotMap.DIO_driveEncoder1Channel2, false);
        rightEncoder = new Encoder(RobotMap.DIO_driveEncoder2Channel1, RobotMap.DIO_driveEncoder2Channel2, false);
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

    public void driveWithJoystick(double stickLeft, double stickRight) {
        if (!isLocked) {
            drive.tankDrive(stickRight, stickLeft);
        }
    }

    public void lock() {
        isLocked = true;
        drive.tankDrive(0, 0);
    }
}
