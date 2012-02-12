/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team3929.subsystems.Shooter;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import team3929.templates.SensorsControl.RobotMap;
import team3929.commands.Shooter.ShooterControl;

/**
 *
 * @author Carter
 */
public class Shooter extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    Jaguar jag1 = new Jaguar(RobotMap.DPWM_shooterJag1);
    Jaguar jag2 = new Jaguar(RobotMap.DPWM_shooterJag2);
    Victor vic1 = new Victor(9);
    Relay relay1 = new Relay(RobotMap.DRelay_shooterSpike1);
    Relay relay2 = new Relay(RobotMap.DRelay_shooterSpike2);
    DigitalInput limSwitch = new DigitalInput(RobotMap.DIO_shooterLimSwitch);
    Encoder encoder = new Encoder(RobotMap.DIO_shooterEncoderChannel1, RobotMap.DIO_shooterEncoderChannel2, false);
    AnalogChannel potential = new AnalogChannel(RobotMap.A_Potential1);

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new ShooterControl());
    }

    public void rotateTurret(double speed) {
        jag1.set(speed);
    }

    public void changeAngle(double speed) {
        jag1.set(speed);
    }

    public void startWheel() {
        jag2.set(0.5);
    }

    public void stopWheel() {
        jag2.set(0.0);
    }

    public long checkPotentiometer() {
        long value = potential.getAccumulatorValue();
        return value;
    }

    public boolean checkLimit() {
        return limSwitch.get();
    }
}