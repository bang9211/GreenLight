/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package greenlight;
import MinTFramework.ExternalDevice.Device;
import MinTFramework.ExternalDevice.Sensing.Dust.*;
import MinTFramework.ExternalDevice.Control.RGBLED.MinTDriver_RGB_LED_java;
import MinTFramework.Request;
import MinTFramework.MinT;
import MinT.ExternalDevice.Sensing.TempHumi.MinTDriver_ht01sv;
import com.github.fedy2.weather.YahooWeatherService;
import com.github.fedy2.weather.data.Channel;
import com.github.fedy2.weather.data.unit.DegreeUnit;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
/**
 *
 * @author admin
 */
public class RequestSensing extends Request{

    private final Device device1;
    private final Device device2;
    private final Device RGB_LED1;
    //private final Device RGB_LED2;
    //private final Device RGB_LED3;
    //private final Device RGB_LED4;
    private final Device device5;
    
    
    public RequestSensing(MinT frame){
        device1 = frame.getDevice("PM1001");
        device2 = frame.getDevice("CM1101");
        RGB_LED1 = frame.getDevice("RGB_LED1");
        //RGB_LED2 = frame.getDevice("RGB_LED2");
        //RGB_LED3 = frame.getDevice("RGB_LED3");
        //RGB_LED4 = frame.getDevice("RGB_LED4");
        device5 = frame.getDevice("HT01SV");
    }
    public void getWeather(){
         try {
            YahooWeatherService service = new YahooWeatherService();
            Channel channel = service.getForecast("1132463", DegreeUnit.CELSIUS);
            
            int weather_code = channel.getItem().getCondition().getCode();
            System.out.println(weather_code);
            String weather = channel.getItem().getCondition().getText();
            System.out.println(weather);
            
        } catch (JAXBException ex) {    
            Logger.getLogger(RequestSensing.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RequestSensing.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void execute() {
        float dustValue;
        float CO2;
        float humidity;
        
        //((MinTDriver_RGB_LED_java)RGB_LED2).start_GPIO();
        //((MinTDriver_RGB_LED_java)RGB_LED3).start_GPIO();
        //((MinTDriver_RGB_LED_java)RGB_LED4).start_GPIO();
       // getWeather();
        
        while(!Thread.currentThread().isInterrupted()){
            dustValue = ((MinTDriver_pm1001_java) device1).getDust();
            CO2 = ((MinTDriver_cm1101_java)device2).getCO2();
            humidity = ((MinTDriver_ht01sv)device5).getHumidity();
           
            System.out.println("humidity : "+humidity);
            System.out.println("dustValue : "+dustValue);
            
            /*if(dustValue <= 30){
                ((MinTDriver_RGB_LED_java)device3).coloring_RGB(0, 255, 255);
                //((MinTDriver_RGB_LED_java)device4).setColor_RGB_GPIO(0, 255, 255);
            }
            else if(30<dustValue && dustValue <= 80){
                ((MinTDriver_RGB_LED_java)device3).coloring_RGB(128, 255, 0);
                //((MinTDriver_RGB_LED_java)device4).setColor_RGB_GPIO(128, 255, 0);
            }
            else if(80<dustValue && dustValue<=120){
                ((MinTDriver_RGB_LED_java)device3).coloring_RGB(255, 128, 0);
                //((MinTDriver_RGB_LED_java)device4).setColor_RGB_GPIO(255, 128, 0);
            }
            else if(120<dustValue && dustValue<=200){
                ((MinTDriver_RGB_LED_java)device3).coloring_RGB(255, 60, 0);
                //((MinTDriver_RGB_LED_java)device4).setColor_RGB_GPIO(255, 60, 0);
            }
            else if(200<dustValue){
                ((MinTDriver_RGB_LED_java)device3).coloring_RGB(255, 0, 0);
                //((MinTDriver_RGB_LED_java)device4).setColor_RGB_GPIO(255, 0, 0);
            }*/
            //((MinTDriver_RGB_LED_java)device3).delay_ms(900);
            //((MinTDriver_RGB_LED_java)device3).delay_ms(900);
            
            System.out.println("CO2 : "+CO2);
            
            /*if(250<CO2 && CO2<=350)
                ((MinTDriver_RGB_LED_java)device3).coloring_RGB(0, 255, 255);
            else if(350<CO2 && CO2<=1000)
                ((MinTDriver_RGB_LED_java)device3).coloring_RGB(128, 255, 0);
            else if(1000<CO2 && CO2<=2000)
                ((MinTDriver_RGB_LED_java)device3).coloring_RGB(255, 128, 0);
            else if(2000<CO2 && CO2<5000)
                ((MinTDriver_RGB_LED_java)device3).coloring_RGB(255, 60, 0);
            else if(5000<CO2)
                ((MinTDriver_RGB_LED_java)device3).coloring_RGB(255, 0, 0);
            */
            //((MinTDriver_RGB_LED_java)device3).delay_ms(900);
            //((MinTDriver_RGB_LED_java)device3).delay_ms(900);
            
            ((MinTDriver_RGB_LED_java)RGB_LED1).coloring_RGB_PWM(244, 0, 100);
            //((MinTDriver_RGB_LED_java)RGB_LED2).setColor_RGB_GPIO(244, 0, 100);
            //((MinTDriver_RGB_LED_java)RGB_LED3).setColor_RGB_GPIO(244, 0, 100);
            //((MinTDriver_RGB_LED_java)RGB_LED4).setColor_RGB_GPIO(255, 0, 127);
            
            ((MinTDriver_RGB_LED_java)RGB_LED1).delay_ms(900);
            ((MinTDriver_RGB_LED_java)RGB_LED1).delay_ms(900);
            ((MinTDriver_RGB_LED_java)RGB_LED1).delay_ms(900);
            
            //((MinTDriver_RGB_LED_java)RGB_LED4).delay_ms(900);
            //((MinTDriver_RGB_LED_java)RGB_LED4).delay_ms(900);
            //((MinTDriver_RGB_LED_java)RGB_LED4).delay_ms(900);
            
            //((MinTDriver_RGB_LED_java)RGB_LED2).delay_ms(900);
            //((MinTDriver_RGB_LED_java)RGB_LED2).delay_ms(900);
            //((MinTDriver_RGB_LED_java)RGB_LED2).delay_ms(900);
            
            //((MinTDriver_RGB_LED_java)RGB_LED3).delay_ms(900);
            //((MinTDriver_RGB_LED_java)RGB_LED3).delay_ms(900);
            //((MinTDriver_RGB_LED_java)RGB_LED3).delay_ms(900);
            
            try {
                Thread.currentThread().sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Interrupt Occcured");
            }
           //((MinTDriver_RGB_LED_java)device7).stop_GPIO();
        }
        
        ((MinTDriver_pm1001_java)device1).freeDevice();
        ((MinTDriver_cm1101_java)device2).freeDevice();
        ((MinTDriver_RGB_LED_java)RGB_LED1).freeDevice();
        //((MinTDriver_RGB_LED_java)RGB_LED2).freeDevice();
        ((MinTDriver_ht01sv)device5).freeDevice();
        //((MinTDriver_RGB_LED_java)RGB_LED3).freeDevice();
        //((MinTDriver_RGB_LED_java)RGB_LED4).freeDevice();
    }
    
}
