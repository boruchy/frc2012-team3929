/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team3929.commands.DriveCommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
/**
 *
 * @author Carter
 */
public class DriveInASquare extends CommandGroup {

    public DriveInASquare() {
    

        addParallel(new Turn(4)); //Basically juse gives the group of commands a CoastTest...Don't know why it's a "Parallel." Parameter of 1 second
            }
}