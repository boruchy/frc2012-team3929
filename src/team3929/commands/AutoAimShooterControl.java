/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team3929.commands;

import team3929.subsystems.Vision;

/**
 *
 * @author Carter
 */
public class AutoAimShooterControl extends CommandBase {

    public boolean isManual;
    private boolean onTarget;    
    private Vision vision = null;

    public AutoAimShooterControl() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(shooter);
        requires(ballIntake);
        vision = Vision.getInstance();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if (vision.seesTarget()) {
            shooter.spinUpToPowerLevel(vision.getTargetRPMs());
            shooter.rotateTurretToAngle((int)vision.getAzimuth());
            // TODO:  DO WE NEED TO PUT A WAIT HERE AND THEN A STATUS LIGHT
            // TO TELL THE DRIVER THAT THE BOT IS READY TO FIRE?
            // DRIVER HOLDS DOWN BUTTON UNTIL BALL FIRED
             if (oi.checkAttackButton(8) == true) { 
                ballIntake.turnOnVerticalConveyor();
            }
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
