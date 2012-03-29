/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team3929.utilities;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import team3929.subsystems.Shooter;

/**
 *
 * @author bhgray
 */
public class ShooterSpeedController extends PIDController {

    private static final double Kp = 0.001;
    private static final double Ki = 0.0;
    private static final double Kd = 0.001;

    private double output = 0.0;
    private double kOnTargetTolerancePercent = 0.01;
    private double kBallFiredRPMDrop = 300.0;
    private boolean readyToFire = false;

    private Encoder shooterRPMEncoder = null;

    public ShooterSpeedController(Shooter shooter, PIDOutput source) {
        super(Kp, Ki, Kd, shooter.getRPMPIDSource(), source);
        super.setTolerance(kOnTargetTolerancePercent);
    }
    
    public void setGoal(double rpm) {
        super.setSetpoint(rpm);
    }

    @Override
    public void reset() {
        this.output = 0.0;
        super.setSetpoint(0.0);
        readyToFire = false;

        // Use the built-in SynchronousPID reset method
        super.reset();
    }
}
