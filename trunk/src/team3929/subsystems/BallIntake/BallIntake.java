/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team3929.subsystems.BallIntake;

import edu.wpi.first.wpilibj.DigitalInput;
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
    Victor vic1 = new Victor(RobotMap.DPWM_ballInVictor1);
    Victor vic2 = new Victor(RobotMap.DPWM_ballInVictor2);
    DigitalInput lsensor1 = new DigitalInput(RobotMap.DIO_ballDetLightSensor1);
    DigitalInput lsensor2 = new DigitalInput(RobotMap.DIO_ballDetLightSensor2);
    DigitalInput lsensor3 = new DigitalInput(RobotMap.DIO_ballDetLightSensor3);
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new CheckBalls());
    }
    public boolean checkBallSlot1(){
        
        return lsensor1.get();

    }

    private void setDefaultCommand(BallIntake ballIntake) {
    }
}