/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team3929.commands.Shooter;

import edu.wpi.first.wpilibj.Joystick;
import team3929.commands.CommandBase;

/**
 *
 * @author Carter
 */
public class ManualShooterControl extends CommandBase {

    public boolean isManual;

    public ManualShooterControl() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(shooter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        isManual = true;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        //rotate shooter based on the axes of the attack3 controller
        
            shooter.rotateTurret(oi.getAttackX() / 2);
            shooter.changeAngle(oi.getAttackY() / 2);
            if (oi.checkAttackButton(3)) {
                shooter.startWheels();
            } else if (oi.checkAttackButton(2)) {
                shooter.stopWheels();
            }
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
