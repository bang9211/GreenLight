/*
 * Copyright (C) 2015 Software&System Lab. Kangwon National University.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package MinTFramework.ExternalDevice.Control.RGBLED;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author soobin Jeon <j.soobin@gmail.com>, chungsan Lee <dj.zlee@gmail.com>,
 * youngtak Han <gksdudxkr@gmail.com>
 */
public class PWMManager {
    private PWMPIN red;
    private PWMPIN green;
    private PWMPIN blue;
    private boolean loaded = false;
    private String name;
    
    //PWM list : red, green, blue
    private ArrayList<PWMPIN> pwmlist = new ArrayList();
    
//must enable the PWM number
    private ArrayList<Integer> enabled_PWM_Number = new ArrayList();
    
    public PWMManager(PWMPIN _red, PWMPIN _green, PWMPIN _blue){
        if(_red != null && _green != null & _blue != null){
            loaded = true;
            red = _red;
            green = _green;
            blue = _blue;
            
            addPWM(red);
            addPWM(green);
            addPWM(blue);
        }
    }
    
    /**
     * add PWM
     * @param pin 
     */
    private void addPWM(PWMPIN pin){
        if(pin == null){
            System.err.println("pin is not PWM pin");
        }else{
            if(pwmlist != null){
                System.out.println("Add PWM : "+pin.toString() + " - "+pin.getPWMnumber());
                pwmlist.add(pin);
                addPWNNumber(pin);
            }
        }
    }
    
    /**
     * Add PWM Number
     * @param pin 
     */
    private void addPWNNumber(PWMPIN pin) {
        boolean isnumber = false;
        for(int k :enabled_PWM_Number){
            if(pin.getPWMnumber() == k) {
                isnumber = true;
            }
        }
        
        if(!isnumber){
            System.out.println("Add PWM NUmber : "+pin.getPWMnumber());
            enabled_PWM_Number.add(pin.getPWMnumber());
        }
    }

    /**
     * PWM setting check for RGB
     * @return boolean
     */
    public boolean isloaded() {
        return loaded;
    }

    /**
     * set RGB color
     * @param _red
     * @param _green
     * @param _blue 
     */
    public void setRGB(int _red, int _green, int _blue) {
        red.setRGBvalue(_red);
        green.setRGBvalue(_green);
        blue.setRGBvalue(_blue);
    }
    
    /**
     * get enabled PWM Number
     * @return 
     */
    public ArrayList<Integer> getEnablePWM_Number(){
        return enabled_PWM_Number;
    }
    
    /**
     * get PWM by PWM number and code("A" or "B")
     * @param number PWM Number (0, 1, 2)
     * @param code ("A" or "B")
     * @return 
     */
    public PWMPIN getPWMPIN(int number, char code){
        for(PWMPIN p : pwmlist){
            if(p.getPWMnumber() == number && p.getCode() == code)
                return p;
        }
        return null;
    }
}
