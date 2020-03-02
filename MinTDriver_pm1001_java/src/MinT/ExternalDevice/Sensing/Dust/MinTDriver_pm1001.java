package MinT.ExternalDevice.Sensing.Dust;

import MinTFramework.ExternalDevice.Device;
/**
 *
 * @author hanyoungtak
 */
public class MinTDriver_pm1001 extends Device{
    private native void initSensor();
    private native void freeSensor();
    private native float getDustData();
    
    public MinTDriver_pm1001(){
        super("MinTDriver_pm1001");
    }
    
    @Override
    public void initDevice(){
        initSensor();
    }
    
    @Override
    public void freeDevice(){
        freeSensor();
    }
    
    public float getData(){
        return getDustData();
    }
}
