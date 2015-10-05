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
public class MinTDriver_cm1101_java extends Device{

    /**
     * @param args the command line arguments
     */
    
    private int uartNumber;
    
    private native void initSensor(int uartNumber);
    private native void freeSensor();
    private native int getCO2Value();
    
    public MinTDriver_cm1101_java(int uartNum){
        super("cm1101");
        uartNumber = uartNum;
    }
    public void initDevice(){
        initSensor(uartNumber);
    }
    public void freeDevice(){
        freeSensor();
    }
    public int getCO2(){
        return getCO2Value();
    }
}
