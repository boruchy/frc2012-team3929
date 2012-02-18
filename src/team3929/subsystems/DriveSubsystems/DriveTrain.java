/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team399.y2012.robot.mechanisms;

import edu.wpi.first.wpilibj.CANJaguar;
import team3929.subsystems.MechanismBase;
import team3929.subsystems.MechanismState;

/**
 * Drivetrain class. Wraps all drive base related functions.
 * @author Jeremy Germita
 */
public class DriveTrain extends MechanismBase {

    private static class DRIVETRAIN_STATES {
        public static MechanismState DRIVING = new MechanismState("DRIVING");                           //Regular open field driving
        public static MechanismState BUMP_CROSS = new MechanismState("BUMP_CROSS");                     //Bump traversal state
        public static MechanismState BRIDGE_CROSS = new MechanismState("BRIDGE_CROSS");                 //Bridge crossing state
        public static MechanismState BRIDGE_BALANCE_ONE = new MechanismState("BRIDGE_BALANCE_ONE");     //one robot bridge balancing state
        public static MechanismState BRIDGE_BALANCE_MULTI = new MechanismState("BRIDGE_BALANCE_MULTI"); //Multiple robot bridge balancing state
    }
    private CANJaguar m_leftA = null;
    private CANJaguar m_leftB = null;
    private CANJaguar m_rightA = null;
    private CANJaguar m_rightB = null;

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
            m_leftA = new CANJaguar(leftAID);
            m_leftA.changeControlMode(CANJaguar.ControlMode.kVoltage);
            m_leftA.setVoltageRampRate(24);
        } catch (Exception e) {
            System.err.println("[DRIVE-LEFT-A]Error initializing");
            e.printStackTrace();
        }
        try {
            m_leftB = new CANJaguar(leftBID);
            m_leftB.changeControlMode(CANJaguar.ControlMode.kVoltage);
            m_leftB.setVoltageRampRate(24);
        } catch (Exception e) {
            System.err.println("[DRIVE-LEFT-B]Error initializing");
            e.printStackTrace();
        }
        try {
            m_rightA = new CANJaguar(rightAID);
            m_rightA.changeControlMode(CANJaguar.ControlMode.kVoltage);
            m_rightA.setVoltageRampRate(24);
        } catch (Exception e) {
            System.err.println("[DRIVE-RIGHT-A]Error initializing");
            e.printStackTrace();
        }
        try {
            m_rightB = new CANJaguar(rightBID);
            m_rightB.changeControlMode(CANJaguar.ControlMode.kVoltage);
            m_rightB.setVoltageRampRate(24);
        } catch (Exception e) {
            System.err.println("[DRIVE-RIGHT-B]Error initializing");
            e.printStackTrace();
        }
    }

    /**
     * Basic tank drive method
     * @param leftPower left power to set
     * @param rightPower right power to set
     */
    public void tankDrive(double leftPower, double rightPower) {
        try {
            m_leftA.setX(leftPower);
        } catch (Exception e) {
            System.err.println("[DRIVE-LEFT-A]Error sending power");
            e.printStackTrace();
        }
        try {
            m_leftB.setX(leftPower);
        } catch (Exception e) {
            System.err.println("[DRIVE-LEFT-B]Error sending power");
            e.printStackTrace();
        }
        try {
            m_rightA.setX(rightPower);
        } catch (Exception e) {
            System.err.println("[DRIVE-RIGHT-A]Error sending power");
            e.printStackTrace();
        }
        try {
            m_rightB.setX(rightPower);
   
        } catch (Exception e) {
            System.err.println("[DRIVE-RIGHT-B]Error sending power");
            e.printStackTrace();
        }
    }
}
