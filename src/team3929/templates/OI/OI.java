package team3929.templates.OI;

import team3929.commands.DriveCommands.DriveInASquare;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.ButtonType;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import team3929.templates.HIDControl.MadCatzControl;

public class OI {

    public static final int JOYSTICK_PORT = 1;
    public static final int ATTACK3_PORT = 2;
    private MadCatzControl madcatz;
    private Joystick attack3;

    public OI() {

        madcatz = new MadCatzControl(JOYSTICK_PORT);    //Definition
        attack3 = new Joystick(ATTACK3_PORT);


    }

    public boolean checkButton(int buttonNumber) {//easier to check if a button is pressed
        return getJoystick().getButton(buttonNumber);

    }

    public double getTriggerValue() {
        return getJoystick().getTrigger();
    }

    public double getLeftY() {//get left joy Y
        return getJoystick().getLeftY();
    }

    public double getRightY() {//get right joystick Y
        return getJoystick().getRightY();
    }

    public double getAttackY() {
        return getAttack().getY();
    }

    public double getAttackX() {
        return getAttack().getX();
    }

    public boolean checkAttackButton(ButtonType button) {
        return getAttack().getButton(button);
    }

    public MadCatzControl getJoystick() { //Returns a madcatz

        return madcatz;
    }

    public Joystick getAttack() {
        return attack3;
    }
    //public Joystick getRightJoystick() { //Returns a joystick
    //return stickRight;
    //}
}