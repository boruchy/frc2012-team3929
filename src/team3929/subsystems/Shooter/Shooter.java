/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team3929.subsystems.Shooter;


import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import team3929.templates.SensorsControl.RobotMap;
import team3929.commands.Shooter.ManualShooterControl;

/**
 *
 * @author Carter
 */
public class Shooter extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
//Most of these require just a port number for instantiation.  For this, we created robotmap which is where ports are being called from
    Victor rotater = new Victor(RobotMap.DPWM_shooterVic1);//declaration and instantiation of sensors and speed controllers
    Victor angleChanger = new Victor(RobotMap.DPWM_shooterVic2);
    Jaguar shooterJaguar1 = new Jaguar(RobotMap.DPWM_shooterJag3);
    Jaguar shooterJaguar2 = new Jaguar(RobotMap.DPWM_shooterJag4);
    DigitalInput limSwitch = new DigitalInput(RobotMap.DIO_shooterLimSwitch);//for the hood or turret to not go too far
    Encoder encoder = new Encoder(RobotMap.DIO_shooterEncoderChannel1, RobotMap.DIO_shooterEncoderChannel2, false);
    //this requires two channels(one for forward an one for back)--an encoder will count turns of the motor and gives speed.
    AnalogChannel potentialAngle = new AnalogChannel(RobotMap.A_Potential1);
    AnalogChannel potentialRotater = new AnalogChannel(RobotMap.A_Potential2);
    //we instantiate potentiometers because we need one for the angle of the hood, and one for the rotation of the turret
    /*
     * by the way, AnalogChannels(variable output), are used for potentiometers and lightsensors, and digitalinput is used for limitswitch(boolean output)
     */
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new ManualShooterControl());
    }

    public void rotateTurret(double speed) {//will allow turret to be rotate t a value
        rotater.set(speed);
    }

    public void changeAngle(double speed) {//changes hood angle
        angleChanger.set(speed);
    }

    public void startWheels() {//starts wheels that the shooter will use
        shooterJaguar1.set(0.5);
        shooterJaguar2.set(0.5);
    }

    public void stopWheels() {//stops shooting wheels
        shooterJaguar1.set(0.0);
        shooterJaguar2.set(0.0);
    }

    public long checkAnglePotentiometer() {//checks potentiometer of the hood which can get the angle of the hood
        long value = potentialAngle.getAccumulatorValue();
        //getAccumulatorValue(); returns how far the potentiometer is turned at the given call to it.
        //getAccumulatorCount(); returns a total count since the robot was turned on.  
        return value;
    }
    public long checkRotaterPotentiometer() {//checks potentiometer of the turret which gets angle of rotation
        long value = potentialRotater.getAccumulatorValue();
        return value;
    }
    public int checkEncoder(){//gets encoder value at given moment
        int value = encoder.get();
        return value;
    }

    public boolean checkLimit() {//checks if limit switch is pressed 
        return limSwitch.get();
    }
}