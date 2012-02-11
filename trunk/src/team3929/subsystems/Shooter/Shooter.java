/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team3929.subsystems.Shooter;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;
import team3929.templates.SensorsControl.RobotMap;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.DigitalInput;
import team3929.commands.Shooter.CheckAngle;
/**
 *
 * @author Carter
 */
public class Shooter extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    Jaguar jag1 = new Jaguar(RobotMap.DPWM_shooterJag1);
    Jaguar jag2 = new Jaguar(RobotMap.DPWM_shooterJag2);
    Relay relay1 = new Relay(RobotMap.DRelay_shooterSpike1);
    Relay relay2 = new Relay(RobotMap.DRelay_shooterSpike2);
    DigitalInput limSwitch = new DigitalInput(RobotMap.DIO_shooterLimSwitch);
    Encoder encoder = new Encoder(RobotMap.DIO_shooterEncoderChannel1,RobotMap.DIO_shooterEncoderChannel2,false);
    AnalogChannel potential = new AnalogChannel(RobotMap.A_Potential1);
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new CheckAngle());
    }
    public void turnTurretLeft(){
        jag1.set(-.25);
    }
    public void turnTurretRight(){
        jag1.set(.25);
    }
    public void startWheel(){
        jag2.set(0.5);
    }
    public void stopWheel(){
        jag2.set(0.0);
    }
    public long checkPotentiometer(){
        long value = potential.getAccumulatorValue();
        return value;
    }
    public boolean checkLimit(){
        return limSwitch.get();
    }
    
}