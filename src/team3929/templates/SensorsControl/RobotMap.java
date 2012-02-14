package team3929.templates.SensorsControl;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    /** For example to map the left and right motors, you could define the
     following variables to use with your drivetrain subsystem.
     public static final int leftMotor = 1;
     public static final int rightMotor = 2;
    
     If you are using multiple modules, make sure to define both the port
     number and the module. For example you with a rangefinder:
     public static final int rangefinderPort = 1;
     public static final int rangefinderModule = 1;
    */
    /**
            The instructions above are pretty much general purpose.  Below you will
     * find our actual integers devoted to different ports on the CRio.  OK, the
     * way this works is really quite trivial...
     * 
                            *Mr. Phillips Voice*
     *      Everything
     * before the underscore is related to the group of sensors it is in.  For
     * example, A = Analog, DPWM = Digital PWM, DRelay = Digital Relays,
     * DIO = Digital I/O(Pretty self-explanatory)
     *
     *       As for the actual sensors,
     * it is easy to read them, but you should probably memorize them.
     * Potential = Potentiometer, LimSwitch = Limit Switch,
     * LightSensor = Light Sensor, Encoder = Encoder, Victor = Victor,
     * Jag = Jaguar, Spike = Spike
     *
     *      (There are numbers after each to say which version of sensor we are
     * referring to; ie. Jaguar1; That said, we must keep this code commented
     * so we know which sensors perform what function)
     * 
     *      Finally, the mechanism that the sensor is devoted to is given in 
     * its name.  This should be memorized as well.  arm = Arm, 
     * ballDet = Ball Detection, drive = Drive, shooter = Shooter, 
     * ballIn = Ball Intake
     *      
     *       (Potentiometers do not have mechanisms in their code name.)
     * To conclude, the order for naming a sensor is
     * (Type of sensor group in caps)_(mechanism name)(Sensor name)(# of sensor)
     *
     */

    //Analog only contains two potentiometers
    //which return angles to the CRio

    public static final int A_Potential1 = 1;
    public static final int A_Potential2 = 2;

    //Digital PWM has 4 Jaguars devoted to drive, 2 Jaguars devoted to shooting,
    //and 2 Victors devoted to Ball Intake

    public static final int DPWM_driveJag1 = 1;
    public static final int DPWM_driveJag2 = 2;
    public static final int DPWM_driveJag3 = 3;
    public static final int DPWM_driveJag4 = 4;

    public static final int DPWM_shooterVic1 = 5;
    public static final int DPWM_shooterVic2 = 6;
    public static final int DPWM_shooterJag3 = 7;
    public static final int DPWM_shooterJag4 = 8;

    public static final int DPWM_ballInVictor1 = 9;
    public static final int DPWM_ballInVictor2 = 10;

    //Digital relays have 1 Spike devoted to the arm, and 2 Spikes devoted to
    //the shooter

    public static final int DRelay_armSpike = 1;

    public static final int DRelay_shooterSpike1 = 2;
    public static final int DRelay_shooterSpike2 = 3;

    //Digital I/O has 2 Encoders devoted to driving, 2 Limit Switches devoted to
    //the arm, 3 Light Sensors devoted to Ball Detection, 1 Encoder devoted to
    //the shooter, and 1 Limit Switch devoted to the shooter.

    public static final int DIO_driveEncoder1 = 1;
    public static final int DIO_driveEncoder2 = 2;

    public static final int DIO_armLimSwitch1 = 3;
    public static final int DIO_armLimSwitch2 = 4;

    public static final int DIO_ballDetLightSensor1 = 5;
    public static final int DIO_ballDetLightSensor2 = 6;
    public static final int DIO_ballDetLightSensor3 = 7;

    public static final int DIO_shooterEncoderChannel1 = 8;
    public static final int DIO_shooterEncoderChannel2 = 9;

    public static final int DIO_shooterLimSwitch = 10;

    public static final int MC_A = 1;
    public static final int MC_B = 2;
    public static final int MC_Y = 3;
    public static final int MC_X = 4;
    

}
