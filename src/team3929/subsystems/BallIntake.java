/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team3929.subsystems;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import team3929.commands.CheckBalls;
import team3929.templates.RobotMap;

/**
 *
 * @author Carter
 */
public class BallIntake extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    Victor verticalConveyor;
    Victor backConveyor;
    AnalogChannel enterSensor;
    AnalogChannel shooterSensor;
    public int ballCounter; //New int ballCounter counts for the number of balls in the robot

   public BallIntake()
    {
        verticalConveyor = new Victor(RobotMap.DPWM_ballInVictor1); //New victor for the front of the conveyor belt and sets the Victor the corresponding port on RobotMap
        backConveyor = new Victor(RobotMap.DPWM_ballInVictor2); //New victor for the back of the Conveyor belt
        enterSensor = new AnalogChannel(RobotMap.A_ballDetLightSensor1); //new light sensor on entrance to count how many balls are in
        shooterSensor = new AnalogChannel(RobotMap.A_ballDetLightSensor2); //new light sensor on the shooter to count how many balls are shot and subtract it from the total balls entered
        ballCounter = 0; //New int ballCounter counts for the number of balls in the robot
    }

    public void initDefaultCommand() {
        // Set the default command to CheckBalls
        setDefaultCommand(new CheckBalls());
    }

    public void turnOnHorizontalConveyor() { //New method that sets both conveyor belts on and at the same speed
        backConveyor.set(.20);
    }
    public void turnOnVerticalConveyor(){
        verticalConveyor.set(.2);
    }
    public void turnOffVerticalConveyor(){
        verticalConveyor.set(0);
    }

    public void reverseHorizontalConveyor() { //New method that turns off both conveyor belts
        backConveyor.set(-.25);
    }
    public void turnOffHorizontalConveyor(){
        backConveyor.set(0.0);
    }

    public void checkEnterLightSensor() {
        
        if (enterSensor.getValue() <= 550) { //If the light sensor sees values less that 350 then there is a ball
            ballCounter++;    
        }
        else{
        }
    }

    public void checkShooterSensor() {
        if (shooterSensor.getValue() <= 550) { // If the light sensor on the shooter sees a ball
            ballCounter--; //Subtract one from ballCounter
            System.out.println("Shooter value: " + shooterSensor.getValue()); //prints out Shooter sensor value
        } 
        else {
        
        }
    }
}
