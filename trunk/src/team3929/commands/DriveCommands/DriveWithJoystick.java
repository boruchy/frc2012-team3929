/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team3929.commands.DriveCommands;

import team3929.commands.CommandBase;
import team3929.templates.SensorsControl.RobotMap;

/**
 *
 * @author Carter
 */
public class DriveWithJoystick extends CommandBase {

    double JoyLeftY; //Declare Joysticks
    double JoyRightY;
    double ZValue;
    int reverser = 1; //This variable will allow for a button to control direction of motors
    public DriveWithJoystick() {
        requires(chassis); // reserve the chassis subsystem
    }
        protected void initialize() { // called once each time the command starts running
            
     JoyLeftY = oi.getLeftY();//initialize the joysticks by grabbing their info from OI
     JoyRightY = oi.getRightY();
     ZValue = oi.getTriggerValue();
     
        }
        protected void execute() { // called repeatedly while the command is running
            chassis.driveWithJoystick(JoyLeftY,JoyRightY);
            
            JoyLeftY = oi.getLeftY();//These are continuously called so that they can wait for changes.
            JoyRightY = oi.getRightY();
            ZValue = oi.getTriggerValue();
            
                if(oi.checkButton(RobotMap.MC_A) == true){//boolean for is button is pressed
                    reverser *= -1; //make reverser the opposite of what it was priorly
                }
                if(ZValue > 0 && JoyLeftY > -.01){
                    JoyLeftY += ZValue;
                    
                }
                
                if(ZValue > 0 && JoyRightY > 0){
                    
                    JoyRightY -= ZValue;
                }
               


            
            JoyLeftY *= reverser;//multiplies joyY's by either -1, or 1
            JoyRightY *= reverser;
            
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
