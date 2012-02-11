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
    
    DriverStationLCD  ds =  DriverStationLCD.getInstance();
    
    
    public DriverTalk(){
      
    }
    public void say(String said){
        ds.println(DriverStationLCD.Line.kMain6, 1 , said);
        ds.updateLCD();
    }
}
