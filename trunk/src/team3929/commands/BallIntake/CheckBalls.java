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
        requires(ballIntake); //Requires the ballIntake Subsystem
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if (oi.checkButton(9) == true) { //Method from the oi class that checks if the button is pressed and if it is pressed
            ballIntake.turnOnConveyorBelts();//then uses the turnOnConveyorBelts method from ballIntake
        } else if (oi.checkButton(10) == true) { //Method from the oi class that checks if the button is pressed and if it is pressed
            ballIntake.turnOffConveyorBelts();//then turn off the conveyor belts
        }
        ballIntake.checkEnterLightSensor(); //calls the method to check theentrance light sensor
        ballIntake.checkShooterSensor();//calls the method to check the shooter light sensor
        System.out.println(ballIntake.ballCounter);//Prints out the value for the ballCounter int in ballIntake class

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;//Never returns true
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
