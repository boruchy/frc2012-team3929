/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team3929.commands.BallIntake;

import team3929.commands.CommandBase;

/**
 *
 * @author Carter
 */
public class CheckBalls extends CommandBase {

    public CheckBalls() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(ballIntake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if (oi.checkButton(9) == true) {
            ballIntake.turnOnConveyorBelts();
        } else if (oi.checkButton(10) == true) {
            ballIntake.turnOffConveyorBelts();
        }
        ballIntake.checkEnterLightSensor();
        ballIntake.checkShooterSensor();
        System.out.println(ballIntake.ballCounter);           // System.out.println(ballIntake.ballCounter);

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
