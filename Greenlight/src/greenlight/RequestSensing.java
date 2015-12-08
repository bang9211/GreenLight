/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package greenlight;
import MinTFramework.ExternalDevice.Device;
import MinTFramework.ExternalDevice.Sensing.Dust.MinTDriver_cm1101_java;
import MinTFramework.ExternalDevice.Sensing.Dust.MinTDriver_pm1001_java;
import MinTFramework.ExternalDevice.Control.RGBLED.MinTDriver_RGB_LED_java;
import MinTFramework.Request;
import MinTFramework.MinT;
import MinT.ExternalDevice.Sensing.TempHumi.MinTDriver_ht01sv;
import com.github.fedy2.weather.YahooWeatherService;
import com.github.fedy2.weather.data.Channel;
import com.github.fedy2.weather.data.unit.DegreeUnit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
/**
 *
 * @author admin
 */
public class RequestSensing extends Request{

    private final Device pm1001;
    private final Device cm1101;
    private final Device RGB_LED1;
    private final Device RGB_LED2;
    private final Device RGB_LED3;
    private final Device RGB_LED4;
    private final Device RGB_LED5;
    private final Device ht01sv;
    
    
    public RequestSensing(MinT frame){
        super(frame);
        pm1001 = frame.getDevice("PM1001");
        cm1101 = frame.getDevice("CM1101");
        RGB_LED1 = frame.getDevice("RGB_LED1");
        RGB_LED2 = frame.getDevice("RGB_LED2");
        RGB_LED3 = frame.getDevice("RGB_LED3");
        RGB_LED4 = frame.getDevice("RGB_LED4");
        RGB_LED5 = frame.getDevice("RGB_LED5");
        ht01sv = frame.getDevice("HT01SV");
    }
    public int getOutdoorDust(String strURL) throws IOException{
        String value = null;
        URL url = new URL(strURL);
        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        
            InputStreamReader isr = new InputStreamReader(conn.getInputStream(),"UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String str;
        int count = 0;
        while((str = br.readLine()) != null){
            StringBuilder sb= new StringBuilder();
            int i;
            for(i=0;i<str.length();i++){
                if(str.charAt(i) == '<'){
                    if(str.charAt(i+1) != 's')
                        break;
                    int j;
                    for(j=i+1;j<str.length();j++){
                        if(str.charAt(j) == '>')
                            break;
                        sb.append(str.charAt(j));
                    }
                    String station = sb.toString();
                    if(station.equals("stationName")){
                        sb = new StringBuilder();
                        for(int k=j+1;k<str.length();k++){
                            if(str.charAt(k) == '<')
                                break;
                            sb.append(str.charAt(k));
                        }
                        station = sb.toString();
                        if(station.equals("석사동")){
                            sb = new StringBuilder();
                            for(int k=0;k<6;k++)
                                str = br.readLine();
                            int k;
                            for(k=0;k<str.length();k++){
                                if(str.charAt(k) == '>'){
                                    for(int m=k+1;m<str.length();m++){
                                        if(str.charAt(m) == '<')
                                            break;
                                        sb.append(str.charAt(m));
                                    }
                                    break;
                                }
                            }
                            value = sb.toString();
                        }
                    }
                }
            }
        }
        return Integer.valueOf(value);
    }
    public double getDiscomfortIndex(){
        double discomfortIndex = 0;
        
        float temp = ((MinTDriver_ht01sv)ht01sv).getTemperature();
        float humi = ((MinTDriver_ht01sv)ht01sv).getHumidity();
       
        discomfortIndex = (double)(9/5*18.0) - 0.55*(1.0-humi)*(9/5*18.0-26.0) + 32.0;
        return discomfortIndex;
    }
    public int getWeather(){
        int weather_code = 0;
         try {
            YahooWeatherService service = new YahooWeatherService();
            Channel channel = service.getForecast("1132463", DegreeUnit.CELSIUS);
            
            weather_code = channel.getItem().getCondition().getCode();
            System.out.println(weather_code);
            String weather = channel.getItem().getCondition().getText();
            System.out.println(weather);
            
        } catch (JAXBException ex) {    
            Logger.getLogger(RequestSensing.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RequestSensing.class.getName()).log(Level.SEVERE, null, ex);
        }
         return weather_code;
    }
    
    public void free(){
        ((MinTDriver_pm1001_java)pm1001).freeDevice();
        ((MinTDriver_cm1101_java)cm1101).freeDevice();
        ((MinTDriver_RGB_LED_java)RGB_LED1).freeDevice();
        ((MinTDriver_RGB_LED_java)RGB_LED2).freeDevice();
        ((MinTDriver_ht01sv)ht01sv).freeDevice();
        ((MinTDriver_RGB_LED_java)RGB_LED3).freeDevice();
        ((MinTDriver_RGB_LED_java)RGB_LED4).freeDevice();
        ((MinTDriver_RGB_LED_java)RGB_LED5).freeDevice();
    }
    
    @Override
    public void execute() {
        float dustValue = 0;
        float CO2 = 0;
        double discomfortIndex = 0;
        int weather_code = 0;
        int outdoorDust = 0;
        
        ((MinTDriver_RGB_LED_java)RGB_LED2).start_GPIO();
        ((MinTDriver_RGB_LED_java)RGB_LED3).start_GPIO();
        ((MinTDriver_RGB_LED_java)RGB_LED4).start_GPIO();
        ((MinTDriver_RGB_LED_java)RGB_LED5).start_GPIO();
        
        while(!Thread.currentThread().isInterrupted()){
            try {
                outdoorDust = getOutdoorDust("http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty?sidoName=강원&numOfRows=10&pageNo=1&ServiceKey=CSCtClt84KbBaEcSoOGWjgEENDe%2FrvcW5Qcu2tw9F6UkFr16rP2vkoRxnxB3KP4NnG7r%2F2ho%2BY4N3OTqvyhArA%3D%3D");
            } catch (IOException ex) {
                Logger.getLogger(RequestSensing.class.getName()).log(Level.SEVERE, null, ex);
            }
            dustValue = ((MinTDriver_pm1001_java) pm1001).getDust()-270;
            CO2 = ((MinTDriver_cm1101_java) cm1101).getCO2();
            discomfortIndex = getDiscomfortIndex();
            weather_code = getWeather();
            
            System.out.println("discomfortIndex : "+discomfortIndex);
            System.out.println("dustValue : "+dustValue);
            System.out.println("CO2 : "+CO2);
            System.out.println("Weather Code : " + weather_code);
            System.out.println("Outdoor DustValue : " + outdoorDust);
            
            
            if(dustValue <= 30)
                ((MinTDriver_RGB_LED_java)RGB_LED1).coloring_RGB_PWM(0, 100, 255);
            else if(30<dustValue && dustValue <= 80)
                ((MinTDriver_RGB_LED_java)RGB_LED1).coloring_RGB_PWM(0, 255, 0);
            else if(80<dustValue && dustValue<=120)
                ((MinTDriver_RGB_LED_java)RGB_LED1).coloring_RGB_PWM(255, 255, 0);
            else if(120<dustValue && dustValue<=200)
                ((MinTDriver_RGB_LED_java)RGB_LED1).coloring_RGB_PWM(255,128,0);
            else if(200<dustValue)
                ((MinTDriver_RGB_LED_java)RGB_LED1).coloring_RGB_PWM(255, 0, 0);
            
            
            if(outdoorDust <= 30)
                ((MinTDriver_RGB_LED_java)RGB_LED5).setColor_RGB_GPIO(0, 100, 255);
            else if(30<outdoorDust && outdoorDust <= 80)
                ((MinTDriver_RGB_LED_java)RGB_LED5).setColor_RGB_GPIO(0, 255, 0);
             else if(80<outdoorDust && outdoorDust<=120)
                ((MinTDriver_RGB_LED_java)RGB_LED5).setColor_RGB_GPIO(255, 255, 0);
            else if(120<outdoorDust && outdoorDust<=200)
                ((MinTDriver_RGB_LED_java)RGB_LED5).setColor_RGB_GPIO(255,128,0);
            else if(200<outdoorDust)
                ((MinTDriver_RGB_LED_java)RGB_LED5).setColor_RGB_GPIO(255, 0, 0);
            
          
            if(250<CO2 && CO2<=350)
                ((MinTDriver_RGB_LED_java)RGB_LED2).setColor_RGB_GPIO(0, 255, 255);
            else if(350<CO2 && CO2<=1000)
                ((MinTDriver_RGB_LED_java)RGB_LED2).setColor_RGB_GPIO(0, 255, 0);
            else if(1000<CO2 && CO2<=2000)
                ((MinTDriver_RGB_LED_java)RGB_LED2).setColor_RGB_GPIO(255, 255, 0);
            else if(2000<CO2 && CO2<5000)
                ((MinTDriver_RGB_LED_java)RGB_LED2).setColor_RGB_GPIO(255, 128, 0);
            else if(5000<CO2)
                ((MinTDriver_RGB_LED_java)RGB_LED2).setColor_RGB_GPIO(255, 0, 0);
          
            if(discomfortIndex < 68)
                ((MinTDriver_RGB_LED_java)RGB_LED3).setColor_RGB_GPIO(0, 100, 255);
            else if(discomfortIndex < 75)
                ((MinTDriver_RGB_LED_java)RGB_LED3).setColor_RGB_GPIO(255, 255, 0);
            else if(discomfortIndex < 80)
                ((MinTDriver_RGB_LED_java)RGB_LED3).setColor_RGB_GPIO(255, 128, 0);
            else
                ((MinTDriver_RGB_LED_java)RGB_LED3).setColor_RGB_GPIO(255, 0, 0);
           
            if((5<=weather_code && weather_code<=18) || weather_code==35 || (40<=weather_code && weather_code<=43) || (45<=weather_code&&weather_code<=47))
                ((MinTDriver_RGB_LED_java)RGB_LED4).setColor_RGB_GPIO(244, 0, 100);
            else if(19<=weather_code && weather_code<=22)
                ((MinTDriver_RGB_LED_java)RGB_LED4).setColor_RGB_GPIO(255, 128, 0);
            else if(23<=weather_code && weather_code<=25)
                ((MinTDriver_RGB_LED_java)RGB_LED4).setColor_RGB_GPIO(0, 0, 255);
            else if(26<=weather_code && weather_code<=30 || weather_code==44)
                ((MinTDriver_RGB_LED_java)RGB_LED4).setColor_RGB_GPIO(0, 255, 255);
            else if(31==weather_code || weather_code==32)
                ((MinTDriver_RGB_LED_java)RGB_LED4).setColor_RGB_GPIO(0, 255, 0);
            
            try {
                Thread.currentThread().sleep(1000*60*30);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Interrupt Occcured");
            }
        }
        
        
    }
    
}
