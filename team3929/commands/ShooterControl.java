/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team3929.commands;

import edu.wpi.first.wpilibj.Joystick;

/**
 *
 * @author Carter
 */
public class ShooterControl extends CommandBase {

    public ShooterControl() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(shooter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        // TODO:  need to convert attackX [-1..1] to angle
        shooter.rotateTurretToAngle((int) (oi.getAttackX() + 1 * 2.5));
        shooter.spinUpToPowerLevel(oi.getAttackY());
        if (oi.checkAttackRealButton(Joystick.ButtonType.kTop) == true) {
            shooter.spinUpToPowerLevel(0.5);
        } else if (oi.checkAttackRealButton(Joystick.ButtonType.kTrigger) == true) {
            shooter.spinDown();
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