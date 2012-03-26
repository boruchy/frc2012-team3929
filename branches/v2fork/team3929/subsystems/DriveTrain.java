/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team3929.subsystems;

import edu.wpi.first.smartdashboard.gui.elements.Subsystem;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import team3929.templates.RobotMap;

/**
 * Drivetrain class. Wraps all drive base related functions.
 * @author Jeremy Germita
 */
public class DriveTrain extends Subsystem {

    private Jaguar m_leftA = null;
    private Jaguar m_leftB = null;
    private Jaguar m_rightA = null;
    private Jaguar m_rightB = null;
    private Encoder leftEncoder = null;
    private Encoder rightEncoder = null;
    private double leftCount;
    private double rightCount;

    /**
     * Constructor
     * @param leftAID   Left A CAN ID
     * @param leftBID   Left B CAN ID
     * @param rightAID  Right A CAN ID
     * @param rightBID  Right B CAN ID
     */
    public DriveTrain(int leftAID, int leftBID, int rightAID, int rightBID) {
        //Initialize drive motor controllers in their own try-catch statements to catch individual errors
        try {
            m_leftA = new Jaguar(leftAID);
        } catch (Exception e) {
            System.err.println("[DRIVE-LEFT-A]Error initializing");
            e.printStackTrace();
        }
        try {
            m_leftB = new Jaguar(leftBID);
        } catch (Exception e) {
            System.err.println("[DRIVE-LEFT-B]Error initializing");
            e.printStackTrace();
        }
        try {
            m_rightA = new Jaguar(rightAID);
        } catch (Exception e) {
            System.err.println("[DRIVE-RIGHT-A]Error initializing");
            e.printStackTrace();
        }
        try {
            m_rightB = new Jaguar(rightBID);
        } catch (Exception e) {
            System.err.println("[DRIVE-RIGHT-B]Error initializing");
            e.printStackTrace();
        }

        // setDistance per pulse = (Wheel Circumference) / (encoder pulses per rotation)
        /*
         * our wheel diameter is 6" -> cirumference 6\pi
         * 10fps at load calculated with the gear box, means 10feet --> 120 inches per second / 6\pi inches = 6.3662 inches 
         * at k4X, 1440 cycles per revolution (360 pulses per revolution)
         * 
         */
        leftEncoder = new Encoder(RobotMap.DIO_driveEncoder1Channel1, RobotMap.DIO_driveEncoder1Channel2, false, EncodingType.k1X);
        leftEncoder.setDistancePerPulse(0.0);
        rightEncoder = new Encoder(RobotMap.DIO_driveEncoder2Channel1, RobotMap.DIO_driveEncoder2Channel2, false, EncodingType.k1X);
        rightEncoder.setDistancePerPulse(0.0);
    }

    /**
     * Basic tank drive method
     * @param leftPower left power to set
     * @param rightPower right power to set
     */
    public void tankDrive(double leftPower, double rightPower) {
        try {
            m_leftA.set(leftPower);
        } catch (Exception e) {
            System.err.println("[DRIVE-LEFT-A]Error sending power");
            e.printStackTrace();
        }
        try {
            m_leftB.set(leftPower);
        } catch (Exception e) {
            System.err.println("[DRIVE-LEFT-B]Error sending power");
            e.printStackTrace();
        }
        try {
            m_rightA.set(rightPower);
        } catch (Exception e) {
            System.err.println("[DRIVE-RIGHT-A]Error sending power");
            e.printStackTrace();
        }
        try {
            m_rightB.set(rightPower);

        } catch (Exception e) {
            System.err.println("[DRIVE-RIGHT-B]Error sending power");
            e.printStackTrace();
        }
    }

    private int getRightCount() {
        return rightEncoder.getRaw();
    }

    private int getLeftCount() {
        return leftEncoder.getRaw();
    }
    
    private void resetEncoders() {
        rightEncoder.reset();
        leftEncoder.reset();
        leftCount = rightCount = 0;
    }

    private void startEncoders() {
        rightEncoder.start();
        leftEncoder.start();
        this.resetEncoders();
    }

    private int leftDrift()
    {
        return this.getLeftCount() - this.getRightCount();
    }
}
