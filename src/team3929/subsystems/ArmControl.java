/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team3929.subsystems;


import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.buttons.AnalogIOButton;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Timer;
import team3929.commands.ArmOn;
import team3929.templates.RobotMap;



/**
 *
 * @author Bruce-laptop
 */
public class ArmControl extends Subsystem {
Relay rsSpike; //New Relay called Rspike
    
    public ArmControl() {
        rsSpike = new Relay(RobotMap.DRelay_armSpike); //Sets the location for the Relay at the given point on RobotMap so that there are no numbers in the actual code
    }

   public void On (){
       rsSpike.set(Relay.Value.kOn); //Creates a method called On that sets the Spike to on state
   }

   public void Off(){
       rsSpike.set(Relay.Value.kOff); // Creates a method called Off that sets the relayb state to off

   }
   public void Reverse(){
       rsSpike.set(Relay.Value.kReverse); //Creates a method that sets the spike into reverse so the motor can move in reverse
   }
   public void Forward(){
       rsSpike.set(Relay.Value.kForward);//Creates a method that sets the spike forward so the motor can move forward
   }
  


    protected void initDefaultCommand() {
        setDefaultCommand(new ArmOn()); //Default command is set to ArmOn
    }
    public void fall(){
        
        //Method called fall that times the spike instead of using limit switches to make it not go past its limits. The fall method starts the 
        //Spike and after .15 seconds moves the motor in reverse and then off.
        Timer.delay(.15);
        Reverse();
        Timer.delay(.15);
        Off();
        
    }

    }

    


