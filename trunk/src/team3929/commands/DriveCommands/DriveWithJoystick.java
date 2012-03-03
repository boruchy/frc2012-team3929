/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team3929.commands.DriveCommands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import team3929.commands.CommandBase;
import team3929.templates.RobotMap;
import edu.wpi.first.wpilibj.RobotDrive;
/**
 *
 * @author Carter
 */
public class DriveWithJoystick extends CommandBase {

    double JoyLeftY; //Declare Joysticks
    double JoyRightY;
    int reverser = 1; //This variable will allow for a button to control direction of motors
    public DriveWithJoystick() {
        requires(chassis); // reserve the chassis subsystem
    }
        protected void initialize() { // called once each time the command starts running
            
        
     
        }
        protected void execute() { // called repeatedly while the command is running
            
            JoyLeftY = -oi.getLeftY();
            JoyRightY = oi.getRightY();
            //JoyLeftY = -oi.getAttackSecondaryY();//These are continuously called so that they can wait for changes.
            //JoyRightY = oi.getAttackY();
            chassis.driveWithJoystick(JoyLeftY,JoyRightY);
            SmartDashboard.putDouble("Left Joystick: ", JoyLeftY);
            SmartDashboard.putDouble("Right Joystick: ", JoyRightY);
            
            }
        protected boolean isFinished() { // called repeatedly and determines if the
            
            return false; // command is finished executing
    }
    // Called once after isFinished returns true
        protected void end() { // called after the command ends for clean up
    }
    protected void interrupted() { // called if the command is preempted or canceled
}
}
