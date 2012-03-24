/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package team3929.utilities;

import edu.wpi.first.wpilibj.Encoder;

/**
 *
 * @author bhgray
 */
public class EqualizedDriveEncoder {
    Encoder leftEncoder;
    Encoder rightEncoder;

    public EqualizedDriveEncoder(Encoder leftEncoder, Encoder rightEncoder)
    {
        this.leftEncoder = leftEncoder;
        this.rightEncoder = rightEncoder;
    }

    /*
     * This gives the error between the two encoders
     * positive values = drift left (right encoder is faster
     * negative values = drift right (right encoder is slower
     */
    
    public double pidGet() {
        return rightEncoder.getDistance() - leftEncoder.getDistance();
    }
}
