/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team3929.commands.Arms;

import edu.wpi.first.wpilibj.buttons.AnalogIOButton;
import edu.wpi.first.wpilibj.buttons.Button;
import team3929.commands.CommandBase;
import team3929.templates.SensorsControl.RobotMap;


/**
 *
 * @author Bruce-laptop
 */
public class ArmOn extends CommandBase {

int a;

    public ArmOn() {
        requires (armControl);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
            
    }
    protected void execute() {
        armControl.fall();
    }
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }

}