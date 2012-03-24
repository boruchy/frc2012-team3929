/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package team3929.utilities;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;

/**
 *
 * @author bhgray
 */
public class ShooterSpeedController extends PIDController {

    private static final double Kp = 0.0;
    private static final double Ki = 0.0;
    private static final double Kd = 0.0;

    public ShooterSpeedController(PIDSource input, PIDOutput output)
    {
        super(Kp, Ki, Kd, input, output);
    }
}
