/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package greenlight;

import MinTFramework.*;
import MinTFramework.ExternalDevice.Control.RGBLED.MinTDriver_RGB_LED_java;
import MinTFramework.ExternalDevice.Sensing.Dust.MinTDriver_cm1101_java;
import MinTFramework.ExternalDevice.Sensing.Dust.MinTDriver_pm1001_java;
import MinT.ExternalDevice.Sensing.TempHumi.MinTDriver_ht01sv;
import MinTFramework.Network.UDP.UDP;

/** * * @author soobin Jeon , chungsan Lee , * youngtak Han  */
public class MinTTest extends MinT {

    public MinTTest() {
        super();
        this.addDevice(new MinTDriver_pm1001_java(1), "PM1001");
        this.addDevice(new MinTDriver_cm1101_java(2), "CM1101");
        this.addDevice(new MinTDriver_RGB_LED_java(9,16,1,8,13,2,9,14,1,"PWM"),"RGB_LED1");
        this.addDevice(new MinTDriver_RGB_LED_java(8,8,67,8,9,69,8,7,66,"GPIO"), "RGB_LED2");
        this.addDevice(new MinTDriver_RGB_LED_java(8,10,68,8,11,45,8,12,44,"GPIO"), "RGB_LED3");
        this.addDevice(new MinTDriver_RGB_LED_java(8,15,47,8,16,46,8,17,27,"GPIO"), "RGB_LED4");
        this.addDevice(new MinTDriver_RGB_LED_java(8,13,23,8,14,26,8,18,65,"GPIO"), "RGB_LED5");
        this.addDevice(new MinTDriver_ht01sv(4,6), "HT01SV");
        RequestSensing re = new RequestSensing(this);
        this.putRequest(re);
        
        this.setNetworkHandler(new RecHandler(re));
        this.setNodeName("192.168.0.149:5010");
        this.setNetwork(new UDP(5010,this));
        
    }
}
