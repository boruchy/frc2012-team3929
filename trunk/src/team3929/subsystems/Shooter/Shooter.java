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

    Victor rotater = new Victor(RobotMap.DPWM_shooterVic1);
    Victor angleChanger = new Victor(RobotMap.DPWM_shooterVic2);
    Victor shooterVictor1 = new Victor(RobotMap.DPWM_shooterVic3);
    Victor shooterVictor2 = new Victor(RobotMap.DPWM_shooterVic4);
    Relay relay1 = new Relay(RobotMap.DRelay_shooterSpike1);
    Relay relay2 = new Relay(RobotMap.DRelay_shooterSpike2);
    DigitalInput limSwitch = new DigitalInput(RobotMap.DIO_shooterLimSwitch);//for the hood or turret to not go too far
    Encoder encoder = new Encoder(RobotMap.DIO_shooterEncoderChannel1, RobotMap.DIO_shooterEncoderChannel2, false);
    AnalogChannel potential = new AnalogChannel(RobotMap.A_Potential1);

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new ShooterControl());
    }

    public void rotateTurret(double speed) {
        rotater.set(speed);
    }

    public void changeAngle(double speed) {
        angleChanger.set(speed);
    }

    public void startWheels() {
        shooterVictor1.set(0.5);
        shooterVictor2.set(0.5);
    }

    public void stopWheels() {
        shooterVictor1.set(0.0);
        shooterVictor2.set(0.0);
    }

    public long checkPotentiometer() {
        long value = potential.getAccumulatorValue();
        return value;
    }

    public boolean checkLimit() {
        return limSwitch.get();
    }
}