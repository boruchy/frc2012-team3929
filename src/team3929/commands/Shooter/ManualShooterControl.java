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

    public ManualShooterControl() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(shooter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        //rotate shooter based on the axes of the attack3 controller
        shooter.rotateTurret(oi.getAttackX());
        shooter.changeAngle(oi.getAttackY());
        //if the top button on the controller is pressed start the shooter wheels
        if (oi.checkAttackButton(Joystick.ButtonType.kTop) == true) {
            shooter.startWheels();

        } else if (oi.checkAttackButton(Joystick.ButtonType.kTrigger) == true) {//if attack3 trigger is pressed, stop shooter wheels
            shooter.stopWheels();
        } else if (oi.checkAttackButton(Joystick.ButtonType.kNumButton)) {
            System.out.println("Encoder: " + shooter.checkEncoder());
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
