/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team3929.subsystems;


import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.buttons.AnalogIOButton;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import team3929.commands.ArmOn;
import team3929.templates.RobotMap;



/**
 *
 * @author Bruce-laptop
 */
public class ArmControl extends Subsystem {
Victor vic; //New Relay called Rspike
    
    public ArmControl() {
        vic = new Victor(RobotMap.DRelay_armSpike); //Sets the location for the Relay at the given point on RobotMap so that there are no numbers in the actual code
    }

   public void Off(){
       vic.set(0); // Creates a method called Off that sets the relayb state to off

   }
   public void Reverse(){
       vic.set(-.4); //Creates a method that sets the spike into reverse so the motor can move in reverse
       
   }
   public void Forward(){
       vic.set(.5); //Creates a method that sets the spike into reverse so the motor can move in reverse
       
         }
  


    protected void initDefaultCommand() {
        setDefaultCommand(new ArmOn()); //Default command is set to ArmOn
    }
   
    

    }

    


