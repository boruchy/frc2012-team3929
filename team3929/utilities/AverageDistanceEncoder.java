/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package team3929.utilities;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSource;

/**
 *
 * @author bhgray
 */
public class AverageDistanceEncoder implements PIDSource {

    Encoder leftEncoder;
    Encoder rightEncoder;

    public AverageDistanceEncoder(Encoder leftEncoder, Encoder rightEncoder)
    {
        this.leftEncoder = leftEncoder;
        this.rightEncoder = rightEncoder;
    }

    public double pidGet() {
        return ((leftEncoder.getDistance() + rightEncoder.getDistance())/2.0);
    }

}
