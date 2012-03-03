/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team3929.commands;

import edu.wpi.first.wpilibj.Joystick.ButtonType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import team3929.commands.CommandBase;
import team3929.templates.RobotMap;

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
        if (oi.checkButton(RobotMap.MC_LB) == true) { //Method from the oi class that checks if the button is pressed and if it is pressed
            ballIntake.turnOnHorizontalConveyor();//then uses the turnOnConveyorBelts method from ballIntake
        }
        else if (oi.checkButton(RobotMap.MC_RB) == true) { //Method from the oi class that checks if the button is pressed and if it is pressed
            ballIntake.reverseHorizontalConveyor();//then turn off the conveyor belts
        } else if (oi.checkButton(RobotMap.MC_X) == true){
            ballIntake.reverseHorizontalConveyor();
        }
        /*if(oi.checkAttackRealButton(ButtonType.kTrigger)){

            ballIntake.turnOnVerticalConveyor();
        }
        else{
            ballIntake.turnOffVerticalConveyor();
        }
         * */
         
        ballIntake.checkEnterLightSensor(); //calls the method to check theentrance light sensor
        ballIntake.checkShooterSensor();//calls the method to check the shooter light sensor
        SmartDashboard.putInt("Ball Counter:", ballIntake.ballCounter);//Prints out the value for the ballCounter int in ballIntake class

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
