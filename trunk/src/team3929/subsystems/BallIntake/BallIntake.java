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

    Victor frontConveyor = new Victor(RobotMap.DPWM_ballInVictor1);
    Victor backConveyor = new Victor(RobotMap.DPWM_ballInVictor2);
    AnalogChannel enterSensor = new AnalogChannel(RobotMap.DIO_ballDetLightSensor1);
    AnalogChannel shooterSensor = new AnalogChannel(RobotMap.DIO_ballDetLightSensor2);
    public int ballCounter = 0;

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new CheckBalls());
    }

    public void turnOnConveyorBelts() {
        frontConveyor.set(.5);
        backConveyor.set(.5);
    }

    public void turnOffConveyorBelts() {
        frontConveyor.set(0.0);
        backConveyor.set(0.0);
    }

    public void checkEnterLightSensor() {


        if (enterSensor.getValue() <= 350) {
            if (frontConveyor.getSpeed() > 0 && backConveyor.getSpeed() > 0) {
                ballCounter++;
                System.out.println("Enter value: " + enterSensor.getValue());
            } else {
                ballCounter--;
            }
        } else {
        }
    }

    public void checkShooterSensor() {
        if (shooterSensor.getValue() <= 350) {
            ballCounter--;
            System.out.println("Shooter value: " + shooterSensor.getValue());
        } else {
        }
    }
}
