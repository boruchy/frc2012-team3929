/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team3929.commands.DriveCommands;


import team3929.commands.CommandBase;
import team3929.templates.DriverTalk;

/**
 *
 * @author Carter
 */

    public class DriveStraight extends CommandBase {
        
        private double m_timeout; //Time spent doin
        private DriverTalk ds = new DriverTalk();
        public DriveStraight(double timeout) {
            
            m_timeout = timeout;
            requires(chassis);
    
        }
        /**Called when the command sets it all up*/
        protected void initialize() {
         
            setTimeout(m_timeout);
    }
       /**Called when the command actually does something...It never actually does anything.  It's an illusion. */
        protected void execute() {
           /**Is called from instance of chassis.  Go check it out if you are that curious to see my amateur coding*/
            chassis.straight();
    }
       /**Tells me if it's done...I think*/
        protected boolean isFinished() {
            
            return isTimedOut();
    }
        /**Called when the command finishes*/
        protected void end() {
    }
        /**Called when the command is ....INTERRUPTED?!?!?!*/
        protected void interrupted() {
            ds.say("Something went wrong , and the DriveStraight command was interrupted.");
    }
}