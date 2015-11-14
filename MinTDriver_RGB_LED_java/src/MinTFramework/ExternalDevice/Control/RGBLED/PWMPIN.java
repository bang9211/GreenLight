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

/**
 *
 * @author soobin Jeon <j.soobin@gmail.com>, chungsan Lee <dj.zlee@gmail.com>,
 * youngtak Han <gksdudxkr@gmail.com>
 */
public enum PWMPIN {
    EHRPWM0A(9,22,0,'A',0),
    EHRPWM0B(9,21,0,'B',0),
    EHRPWM1A(9,14,1,'A',0),
    EHRPWM1B(9,16,1,'B',0),
    EHRPWM2A(8,19,2,'A',0),
    EHRPWM2B(8,13,2,'B',0);
    
    private PWMPIN(int _port, int _pin, int _num, char _code, int _rgbvalue){
        port = _port;
        pin = _pin;
        num = _num;
        code = _code;
        rgbvalue = _rgbvalue;
    }
    
    //port number
    private final int port;
    //pin number
    private final int pin;
    //EHRPWM"num""code"
    private final int num;
    //EHRPWM"num""code"
    private final char code;
    //RGB value
    private int rgbvalue;
    
    public static PWMPIN getPWM(int port, int pin){
        for(PWMPIN p : PWMPIN.values()){
            if(p.port == port && p.pin == pin)
                return p;
        }
        System.err.println("Wrong PWM Number Please check your Port and pin");
        return null;
    }
    
    public int getPWMnumber(){
        return num;
    }
    
    public void setRGBvalue(int value){
        rgbvalue = value;
    }

    char getCode() {
        return code;
    }
    
    public int getRGBvalue(){
        return rgbvalue;
    }
}
