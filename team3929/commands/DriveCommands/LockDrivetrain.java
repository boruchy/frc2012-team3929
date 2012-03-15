/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team3929.commands.DriveCommands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import team3929.commands.CommandBase;
import team3929.templates.RobotMap;
import edu.wpi.first.wpilibj.RobotDrive;
/**
 *
 * @author Carter
 */
public class LockDrivetrain extends CommandBase {

    public LockDrivetrain() {
        requires(chassis); // reserve the chassis subsystem
    }
        protected void initialize() { // called once each time the command starts running

                chassis.lock();

        }
        protected void execute() { // called repeatedly while the command is running

            

            }
        protected boolean isFinished() { // called repeatedly and determines if the

            return false; // command is finished executing
    }
    // Called once after isFinished returns true
        protected void end() { // called after the command ends for clean up
    }
    protected void interrupted() { // called if the command is preempted or canceled
}
}
