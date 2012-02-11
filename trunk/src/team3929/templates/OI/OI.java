
package team3929.templates.OI;

import team3929.commands.DriveCommands.DriveInASquare;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import team3929.templates.HIDControl.MadCatzControl;

    public class OI {
       
        public static final int JOYSTICK_PORT = 1;

        private MadCatzControl madcatz;
        
        public OI() {
        
                madcatz = new MadCatzControl(JOYSTICK_PORT);    //Definition
                 

                
                }

            public boolean checkButton(int buttonNumber){//easier to check if a button is pressed
                return getJoystick().getButton(buttonNumber);

            }
            public double getTriggerValue(){
                return getJoystick().getTrigger();
            }

            public double getLeftY(){//get left joy Y
                return getJoystick().getLeftY();
            }

            public double getRightY(){//get right joystick Y
                return getJoystick().getRightY();
            }
        public MadCatzControl getJoystick() { //Returns a madcatz
                
                return madcatz;
                }
        //public Joystick getRightJoystick() { //Returns a joystick

                //return stickRight;
                //}
}