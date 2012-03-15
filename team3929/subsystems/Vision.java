/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package team3929.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author Jared Russell Team 341 DaisyBot
 * @version 2012-03-02-1712 Brent Gray Team 3929
 */
public class Vision {
    private static Vision instance = null;

    public static Vision getInstance()
    {
        if( instance == null )
            instance = new Vision();
        return instance;
    }

    private Vision()
    {

    }

    public boolean seesTarget()
    {
        return SmartDashboard.getBoolean("found", false);
    }

    public double getTargetRPMs()
    {
        return SmartDashboard.getDouble("rpms", 0.0);
    }

    public double getAzimuth()
    {
        return SmartDashboard.getDouble("azimuth", 0.0);
    }
}
