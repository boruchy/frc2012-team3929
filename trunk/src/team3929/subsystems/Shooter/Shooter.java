/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team3929.subsystems.Shooter;


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
//We are gonna use JAGUARS
    Victor rotater = new Victor(RobotMap.DPWM_shooterVic1);
    Victor angleChanger = new Victor(RobotMap.DPWM_shooterVic2);
    Jaguar shooterJaguar1 = new Jaguar(RobotMap.DPWM_shooterJag3);
    Jaguar shooterJaguar2 = new Jaguar(RobotMap.DPWM_shooterJag4);
    DigitalInput limSwitch = new DigitalInput(RobotMap.DIO_shooterLimSwitch);//for the hood or turret to not go too far
    Encoder encoder = new Encoder(RobotMap.DIO_shooterEncoderChannel1, RobotMap.DIO_shooterEncoderChannel2, false);
    AnalogChannel potentialAngle = new AnalogChannel(RobotMap.A_Potential1);
    AnalogChannel potentialRotater = new AnalogChannel(RobotMap.A_Potential2);

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
        shooterJaguar1.set(0.5);
        shooterJaguar2.set(0.5);
    }

    public void stopWheels() {
        shooterJaguar1.set(0.0);
        shooterJaguar2.set(0.0);
    }

    public long checkAnglePotentiometer() {
        long value = potentialAngle.getAccumulatorValue();
        return value;
    }
    public long checkRotaterPotentiometer() {
        long value = potentialRotater.getAccumulatorValue();
        return value;
    }

    public boolean checkLimit() {
        return limSwitch.get();
    }
}