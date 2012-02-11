package team3929.commands;

import team3929.subsystems.DriveSubsystems.Chassis;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import team3929.templates.OI.OI;
import team3929.subsystems.ExampleSubsystem;
import team3929.subsystems.Arm.Rspike;
import team3929.templates.SensorsControl.RobotMap;

/**
 * The base for all commands. All atomic commands should subclass CommandBase.
 * CommandBase stores creates and stores each control system. To access a
 * subsystem elsewhere in your code in your code use CommandBase.exampleSubsystem
 * @author Author
 */

public abstract class CommandBase extends Command {

    public static OI oi;
    public static Chassis chassis = new Chassis();
    public static Rspike Rspike = new Rspike(RobotMap.DRelay_armSpike);
    // Create a single static instance of all of your subsystems


    public static void init() {
        // This MUST be here. If the OI creates Commands (which it very likely
        // will), constructing it during the construction of CommandBase (from
        // which commands extend), subsystems are not guaranteed to be
        // yet. Thus, their requires() statements may grab null pointers. Bad
        // news. Don't move it.
        oi = new OI();

        // Show what command your subsystem is running on the SmartDashboard
        SmartDashboard.putData(chassis);
    }

    public CommandBase(String name) {
        super(name);
    }

    public CommandBase() {
        super();
    }
}
