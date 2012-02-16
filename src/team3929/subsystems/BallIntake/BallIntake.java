/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team3929.subsystems.BallIntake;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import team3929.commands.BallIntake.CheckBalls;
import team3929.templates.SensorsControl.RobotMap;

/**
 *
 * @author Carter
 */
public class BallIntake extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    Victor frontConveyor = new Victor(RobotMap.DPWM_ballInVictor1); //New victor for the front of the conveyor belt and sets the Victor the corresponding port on RobotMap
    Victor backConveyor = new Victor(RobotMap.DPWM_ballInVictor2); //New victor for the back of the Conveyor belt
    AnalogChannel enterSensor = new AnalogChannel(RobotMap.DIO_ballDetLightSensor1); //new light sensor on entrance to count how many balls are in
    AnalogChannel shooterSensor = new AnalogChannel(RobotMap.DIO_ballDetLightSensor2); //new light sensor on the shooter to count how many balls are shot and subtract it from the total balls entered
    public int ballCounter = 0; //New int ballCounter counts for the number of balls in the robot

    public void initDefaultCommand() {
        // Set the default command to CheckBalls
        setDefaultCommand(new CheckBalls());
    }

    public void turnOnConveyorBelts() { //New method that sets both conveyor belts on and at the same speed
        frontConveyor.set(.5);
        backConveyor.set(.5);
    }

    public void turnOffConveyorBelts() { //New method that turns off both conveyor belts
        frontConveyor.set(0.0);
        backConveyor.set(0.0);
    }

    public void checkEnterLightSensor() { 


        if (enterSensor.getValue() <= 350) { //If the light sensor sees values less that 350 then there is a ball
            if (frontConveyor.getSpeed() > 0 && backConveyor.getSpeed() > 0) { // if both front and back victors are gbaoing forward 
                ballCounter++; //then ballCounter gets one added to it
                System.out.println("Enter value: " + enterSensor.getValue()); //prints the value of the sensor
            } 
            else {
                ballCounter--; //if the Victors are going backward and the light sensor sees it, subtract one from ballCounter
            }
        }
        else{
        
        }
    }

    public void checkShooterSensor() {
        if (shooterSensor.getValue() <= 350) { // If the light sensor on the shooter sees a ball
            ballCounter--; //Subtract one from ballCounter
            System.out.println("Shooter value: " + shooterSensor.getValue()); //prints out Shooter sensor value
        } 
        else {
        
        }
    }
}
