/*
 * The class represents the shooter, hood and the turret subsystems.
 */
package team3929.subsystems.Shooter;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import team3929.templates.SensorsControl.RobotMap;
import team3929.commands.Shooter.ManualShooterControl;

/**
 *
 * @author Carter Henderson
 * @author Brent Gray
 * @version 2012-03-03-1308
 */
public class Shooter extends Subsystem {

    /***********************  SHOOTER SUBSYSTEM *********************** */
    // the shooter has two Jags being controlled off one PWM
    // therefore this Jaguar acutally represents both
    Jaguar shooterMotors;
    // RPM encoder for the shooter -- NOT INCLUDED ON THE BOT CURRENTLY
    //Encoder encoder;
    
    /***********************  HOOD SUBSYSTEM *********************** */
    // safety for hood.
    // TODO:  is this necessary?  there might be mechanical stops at this point.
    //DigitalInput hoodSafetyLimitSwitch;
    Victor hoodAngleMotor;
    // POT IS NOT CURRENTLY INSTALLED ON HOOD
    // AnalogChannel hoodAnglePot;

    /***********************  TURRET SUBSYSTEM *********************** */
    AnalogChannel turretRotationPot;
    Victor turretRotationMotor;
    PIDController turretRotationController;
    // mechanical zero on the pot will be set to 90 degrees left
    // driving coordinate system will be 0 straight ahead, -90 for LEFT;
    // +90 for RIGHT
    int potMechanicalOffset = 90;
    public static Shooter instance = null;

    private Shooter() {
        turretRotationMotor = new Victor(RobotMap.DPWM_shooterVic1);
        hoodAngleMotor = new Victor(RobotMap.DPWM_shooterVic2);
        shooterMotors = new Jaguar(RobotMap.DPWM_shooterJag3);
        // hoodSafetyLimitSwitch = new DigitalInput(RobotMap.DIO_shooterLimSwitch);
        //encoder = new Encoder(RobotMap.DIO_shooterEncoderChannel1, RobotMap.DIO_shooterEncoderChannel2, false);
        // hoodAnglePot = new AnalogChannel(RobotMap.A_Potential1);
        turretRotationPot = new AnalogChannel(RobotMap.A_Potential2);
        turretRotationController = new PIDController(0.1, 0.001, 0.0, turretRotationPot, turretRotationMotor);
    }

    public static Shooter getInstance() {
        if (instance == null) {
            instance = new Shooter();
        }
        return instance;
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new ManualShooterControl());
    }

    /*********************** TURRET SUBSYSTEM METHODS *********************** */


    // mechanical angle on the pot is 0 (90 degrees LEFT) to 180 (90 degrees RIGHT)
    // input is in the drivetrain coordinate system (0 is straight ahead)
    public void rotateTurretToAngle(int angle) {
        turretRotationController.setSetpoint(convertDriverAngleToPotAngle(angle));
    }

    public int convertDriverAngleToPotAngle(int driverAngle) {
        if (driverAngle >= -potMechanicalOffset && driverAngle <= 0)
        {
            return Math.abs(potMechanicalOffset - Math.abs(driverAngle));
        } else if (driverAngle > 0 && driverAngle <= 90){
            return potMechanicalOffset + Math.abs(driverAngle);
        }
        return 0;
    }

    public int convertPotAngleToDriverAngle(int potAngle) {
        if (potAngle >= 0 && potAngle <= 90)
        {
            return (-1) * potAngle;
        } else if (potAngle >= 90 && potAngle <= 180)
        {
            return potAngle - potMechanicalOffset;
        }
        return 0;
    }

     public long checkAnglePotentiometer() {//checks potentiometer of the hood which can get the angle of the hood
        long value = 0;
        // value = hoodAnglePot.getAccumulatorValue();
        //getAccumulatorValue(); returns how far the potentiometer is turned at the given call to it.
        //getAccumulatorCount(); returns a total count since the robot was turned on.
        return value;
    }

    public long checkRotaterPotentiometer() {//checks potentiometer of the turret which gets angle of rotation
        long value = 0;
        value = turretRotationPot.getAccumulatorValue();
        return value;
    }

    public void setTurretToZero() {
        this.rotateTurretToAngle(0);
    }

    /*********************** SHOOTER SUBSYSTEM METHODS *********************** */

    // spins up the shooter to a minimum level
    public void spinUpToMinimum() {
        shooterMotors.set(0.2);
    }

    // daisy code has power set to neg power....
    public void spinUpToPowerLevel(double power) {
        shooterMotors.set(power);
    }

    // spins down the shooter
    public void spinDown() {//stops shooting wheels
        shooterMotors.set(0.0);
    }

//    public int checkEncoder() {//gets encoder value at given moment
//        int value = 0;
//        //value = encoder.get();
//        return value;
//    }

    /*********************** HOOD SUBSYSTEM METHODS *********************** */
    
    //changes hood angle
    public void changeAngle(double speed) {
        hoodAngleMotor.set(speed);
    }

//    //checks if limit switch is pressed
//    public boolean checkLimit() {
//        return hoodSafetyLimitSwitch.get();
//    }
}
