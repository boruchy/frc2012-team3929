/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team3929.subsystems.Arm;


import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.buttons.AnalogIOButton;
import edu.wpi.first.wpilibj.command.Subsystem;
import team3929.commands.Arms.ArmOn;



/**
 *
 * @author Bruce-laptop
 */
public class Rspike extends Subsystem {
Relay RSpike;
AnalogIOButton Button = new AnalogIOButton(2);
    public Rspike(int port) {
    RSpike = new Relay(port);
        
    }

   public void On (){
       RSpike.set(Relay.Value.kOn);
   }

   public void Off(){
       RSpike.set(Relay.Value.kOff);

   }

  public void reverse(){
       RSpike.set(Relay.Value.kReverse);

  }

  public void forward(){
      RSpike.set(Relay.Value.kForward);
  }


    protected void initDefaultCommand() {
        setDefaultCommand(new ArmOn());
    }


    }

    


