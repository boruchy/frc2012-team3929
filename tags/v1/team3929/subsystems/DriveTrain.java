/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team3929.subsystems;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.Jaguar;
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
    private Jaguar m_leftA = null;
    private Jaguar m_leftB = null;
    private Jaguar m_rightA = null;
    private Jaguar m_rightB = null;

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
}
