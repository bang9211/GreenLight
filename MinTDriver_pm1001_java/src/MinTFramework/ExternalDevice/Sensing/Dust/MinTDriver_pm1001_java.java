/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MinTFramework.ExternalDevice.Sensing.Dust;

import MinTFramework.ExternalDevice.Device;

/**
 *
 * @author admin
 */
public class MinTDriver_pm1001_java extends Device{

    /**
     * @param args the command line arguments
     */
    private native void initSensor(int uartNumber);
    private native float getDustValue();
    private native void freeSensor();
   
    private int uartNumber;
    
    public MinTDriver_pm1001_java(int uartNum){
        super("pm1001_driver");
        uartNumber = uartNum;
    }
    
    public void initDevice(){
        initSensor(uartNumber);
    }
    
    public void freeDevice(){
        freeSensor();
    }
    
    public float getDust(){
        return getDustValue();
    }
}
