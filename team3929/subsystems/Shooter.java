/*
 * The class represents the shooter, hood and the turret subsystems.
 */
package team3929.subsystems;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Timer;
import team3929.commands.ManualShooterControl;
import team3929.templates.RobotMap;
import team3929.utilities.MovingAverageFilter;
import team3929.utilities.ShooterSpeedController;

/**
 *
 * @author Carter Henderson
 * @author Brent Gray
 * @version 2012-03-03-1308
 */
public class Shooter extends Subsystem {

    private static final double SHOOTER_MOTOR_STARTUP_POWER = 0.2;
    private static final double SHOOTER_MOTOR_SPINDOWN_POWER = 0.0;

    private boolean atSpeed = false;
    private boolean atAngle = false;
    
    /***********************  SHOOTER SUBSYSTEM *********************** */
    // the shooter has two Jags being controlled off one PWM
    // therefore this Jaguar acutally represents both
    private Jaguar shooterMotors;
    private Counter rpmEncoder;
    private PIDController rpmSpeedController;
    private MovingAverageFilter rpmFilter;
    public static final int kCountsPerRev = 2;


    /***********************  HOOD SUBSYSTEM *********************** */

    /***********************  TURRET SUBSYSTEM *********************** */
//    private AnalogChannel turretRotationPot;
    private Victor turretRotationMotor;
    private PIDController turretRotationController;

    public Shooter()
    {
        shooterMotors = new Jaguar(RobotMap.DPWM_shooterJag3);

        // at k4X, 1440 cycles per revolution (360 pulses per revolution)  this allows for a max of 10,000 rpm
        // see specs for details
        // ChiefDelphi advises to use 1x encoder type to minimize phase errors.

        // now using just a simple counter.
        rpmEncoder = new Counter(RobotMap.DIO_shooterEncoderChannel1);
        rpmEncoder.start();
        rpmFilter = new MovingAverageFilter(6);
        rpmSpeedController = new ShooterSpeedController(this, shooterMotors);
        rpmSpeedController.enable();

        
        // this PIDController will need to be tuned
        // the PIDSource is the vision system (the azimuth)
        // the PIDOutput is the motor.
        
        turretRotationController = new PIDController(0.1, 0.001, 0.0, Vision.getInstance(), turretRotationMotor);
        turretRotationController.enable();
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(null);
    }

    /*********************** TURRET SUBSYSTEM METHODS *********************** */

      
    // mechanical angle on the pot is 0 (90 degrees LEFT) to 180 (90 degrees RIGHT)
    // input is in the drivetrain coordinate system (0 is straight ahead)

    /* IF there is a pot on the turrent, we can go directly to an angle by setting
     * the setpoint to an explicit angle.
     * ELSE we are simply comparing the current turrent angle against the aiming
     * line in the ByrdsEye (i.e., the azimuth reading) and trying to get the
     * difference to 0.
     */

    public void rotateTurretToAngle(int angle) {
       // turretRotationController.setSetpoint(convertDriverAngleToPotAngle(angle));
        turretRotationController.setSetpoint(angle);
    }

    public void alignTurretWithTargetingAzimuth()
    {
        atAngle = false;
        turretRotationController.setSetpoint(0);
        while (!turretRotationController.onTarget())
        {

        }
        atAngle = true;
        System.out.println("On target angle.");
    }

    public void rotateTurretByJoy(double speed) {
        turretRotationMotor.set(speed);
    }


    // Joystick is [-1..1]
    // TODO:  calibrate turret to joystick on manual
    public void rotateTurretToAngleByJoystick(double d) {
        this.rotateTurretToAngle((int) (d + 1 * 2.5));
    }

    public void setTurretToZero() {
        this.rotateTurretToAngle(0);
    }

    /*********************** SHOOTER SUBSYSTEM METHODS *********************** */

    // spins up the shooter to a present minimum level
    public void spinUpToMinimum() {
        shooterMotors.set(SHOOTER_MOTOR_STARTUP_POWER);
    }

    public synchronized void runInputFilters()
    {
        double rpm = 60.0/(rpmEncoder.getPeriod()*(double)kCountsPerRev);
        if( rpm < 7500.0 )
        {
            // This check ensures that erratic readings are rejected
            rpmFilter.addPoint(rpm);
            rpmFilter.run();
        }
    }

    public double getShooterSpeedRPM()
    {
        return rpmFilter.getAverage();
    }

    /*
     * this is raw power level.  useful only in the joystick (manual)
     * context
     */

    public void spinUpToPowerLevel(double power) {
        shooterMotors.set(power);
    }

    public void spinUpToRPM(double rpm)
    {
        atSpeed = false;
        rpmSpeedController.setSetpoint(rpm);
        while (!rpmSpeedController.onTarget())
        {
        }
        atSpeed = true;
        System.out.println("Setpoint: " + this.rpmSpeedController.getSetpoint());
        System.out.println("At speed: " + this.getShooterSpeedRPM());
    }

    // spins down the shooter
    public void spinDown() {//stops shooting wheels
        shooterMotors.set(SHOOTER_MOTOR_SPINDOWN_POWER);
    }

    /*********************** HOOD SUBSYSTEM METHODS *********************** */

    public PIDSource getRPMPIDSource() {
        return new RPMPIDSource();
    }

    public PIDOutput getRPMPIDOutput() {
        return new RPMPIDOutput();
    }

    private class RPMPIDSource implements PIDSource {

        @Override
        public double pidGet() {
            return getShooterSpeedRPM();
        }
    }

    private class RPMPIDOutput implements PIDOutput {

        @Override
        public void pidWrite(double output) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
    }

}
