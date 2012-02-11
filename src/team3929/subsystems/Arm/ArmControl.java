/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team3929.subsystems.Arm;


import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.buttons.AnalogIOButton;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Timer;
import team3929.commands.Arms.ArmOn;
import team3929.templates.SensorsControl.RobotMap;



/**
 *
 * @author Bruce-laptop
 */
public class ArmControl extends Subsystem {
Relay RSpike;

    public ArmControl() {
        RSpike = new Relay(RobotMap.DRelay_armSpike);
    }

   public void On (){
       RSpike.set(Relay.Value.kOn);
   }

   public void Off(){
       RSpike.set(Relay.Value.kOff);

   }
   public void Reverse(){
       RSpike.set(Relay.Value.kReverse);
   }
   public void Forward(){
       RSpike.set(Relay.Value.kForward);
   }
  


    protected void initDefaultCommand() {
        setDefaultCommand(new ArmOn());
    }
    public void fall(){
        
        
        Timer.delay(.15);
        Reverse();
        Timer.delay(.15);
        Off();
        
    }

    }

    


