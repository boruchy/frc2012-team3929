/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team3929.templates.DashboardandStation;

import edu.wpi.first.wpilibj.DriverStationLCD;

/**
 *
 * @author carter
 */
public class DriverTalk {
    //Ok i call on an instance of DriverStationLCD
    DriverStationLCD  ds =  DriverStationLCD.getInstance();
    
    
    public DriverTalk(){
      
    }
    //this makes it easier for people to say things to the driverstation.  well just me and the other coders
    public void say(String said){
        ds.println(DriverStationLCD.Line.kMain6, 1 , said);//prints a line to the column
        ds.updateLCD();//needs to be updated
    }
}
