/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team3929.commands.DriveCommands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;
import team3929.commands.CommandBase;
import team3929.templates.DashboardandStation.DriverTalk;


/**
 *
 * @author Carter
 */
public class Turn extends CommandBase {
    /**Time used*/
    private double m_timeout;
    private DriverTalk ds = new DriverTalk();
        public Turn(double timeout) {
    
            m_timeout = timeout;
            requires(chassis);
            
                       }
            /**Called when the command is given initial statements*/
            protected void initialize() {
                
                setTimeout(m_timeout);
                       }
            /**Called when the command starts*/
            protected void execute() {

                chassis.turnLeft();
                       }
            /**Boolean check for if command has finished*/
            protected boolean isFinished() {
                ds.say("Turn completed");
                return isTimedOut();
                       }
            /**Called when the command finishes*/
            protected void end() {
    
                        }
            /**Called when the command is interrupted somehow*/
            protected void interrupted() {
                ds.say("Something went wrong , and the Turn command was interrupted.");
                       }
            
    }