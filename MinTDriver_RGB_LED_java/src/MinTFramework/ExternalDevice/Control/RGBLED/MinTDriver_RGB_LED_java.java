/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MinTFramework.ExternalDevice.Control.RGBLED;

import MinTFramework.ExternalDevice.Device;
import java.util.ArrayList;

/**
 *
 * @author admin
 *
 *
 */
public class MinTDriver_RGB_LED_java extends Device {

    /**
     * @param args the command line arguments
     */
    private int portNumber_red;
    private int pinNumber_red;
    private int number_red;
    private int portNumber_green;
    private int pinNumber_green;
    private int number_green;
    private int portNumber_blue;
    private int pinNumber_blue;
    private int number_blue;
    
    /**
     * for PWM
     */
    PWMManager pwmRGB = null;

    private String method;

    class ColorDelay {

        long delay_low;
        long delay_high;

        public void set_low_high(long low, long high) {
            System.out.println("SET LOW HIGH");
            delay_low = low;
            delay_high = high;
        }

        public long get_high() {
            return delay_high;
        }

        public long get_low() {
            return delay_low;
        }
    }

    class RedController extends Thread {

        @Override
        public void run() {
            System.out.println("RED CONTROLLER");
            while (true) {
                gpio_pin_low(portNumber_red, pinNumber_red);
                USleep(red.delay_low);
                gpio_pin_high(portNumber_red, pinNumber_red);
                USleep(red.delay_high);
            }
        }
    }

    class GreenController extends Thread {

        @Override
        public void run() {
            System.out.println("GREEN CONTROLLER");
            while (true) {
                gpio_pin_low(portNumber_green, pinNumber_green);
                USleep(green.delay_low);
                gpio_pin_high(portNumber_green, pinNumber_green);
                USleep(green.delay_high);
            }
        }
    }

    class BlueController extends Thread {

        @Override
        public void run() {
            System.out.println("BLUE CONTROLLER");
            while (true) {
                gpio_pin_low(portNumber_blue, pinNumber_blue);
                USleep(blue.delay_low);
                gpio_pin_high(portNumber_blue, pinNumber_blue);
                USleep(blue.delay_high);
            }
        }
    }

    private native void initSensor_PWM(int portNum_red, int pinNum_red,
            int portNum_green, int pinNum_green,
            int portNum_blue, int pinNum_blue);

    private native void initSensor_GPIO(int gpioNum_red, int portNum_red, int pinNum_red,
            int gpioNum_green, int portNum_green, int pinNum_green,
            int gpioNum_blue, int portNum_blue, int pinNum_blue);

    private native void freeSensor();

    private native void delay(int ms);

    private native void set_pwmRGB(int number, int valA, int valB);
            
    private native void color_PWM(int number_red, int number_green, double red, double green, double blue);

    private native void color_RGB_PWM(int number_red, int number_green, int red, int green, int blue);
    
    private native void pin_low(int portNum, int pinNum);

    private native void pin_high(int portNum, int pinNum);

    private native void uSleep(long delay);

    ColorDelay red = new ColorDelay();
    ColorDelay green = new ColorDelay();
    ColorDelay blue = new ColorDelay();

    RedController redControl = new RedController();
    GreenController greenControl = new GreenController();
    BlueController blueControl = new BlueController();

    public void USleep(long delay) {
        uSleep(delay);
    }

    /**
     * @deprecated 
     * @param portNum_red
     * @param pinNum_red
     * @param num_red
     * @param portNum_green
     * @param pinNum_green
     * @param num_green
     * @param portNum_blue
     * @param pinNum_blue
     * @param num_blue
     * @param m 
     */
    public MinTDriver_RGB_LED_java(int portNum_red, int pinNum_red, int num_red,
            int portNum_green, int pinNum_green, int num_green,
            int portNum_blue, int pinNum_blue, int num_blue, String m) {
        super("RGBLED");
        portNumber_red = portNum_red;
        pinNumber_red = pinNum_red;
        number_red = num_red;
        portNumber_green = portNum_green;
        pinNumber_green = pinNum_green;
        number_green = num_green;
        portNumber_blue = portNum_blue;
        pinNumber_blue = pinNum_blue;
        number_blue = num_blue;
        method = m;
        
        //for PWM
        if(method.equals("PWM")){
            pwmRGB = new PWMManager(PWMPIN.getPWM(portNum_red, pinNum_red),
                                    PWMPIN.getPWM(portNum_green, pinNum_green),
                                    PWMPIN.getPWM(portNum_blue, pinNum_blue));
        }
    }
    
    /**
     * Updated PWM progress
     * @param portNum_red
     * @param pinNum_red
     * @param portNum_green
     * @param pinNum_green
     * @param portNum_blue
     * @param pinNum_blue
     * @param m 
     */
    public MinTDriver_RGB_LED_java(int portNum_red, int pinNum_red,
            int portNum_green, int pinNum_green,
            int portNum_blue, int pinNum_blue, String m) {
        super("RGBLED");
        portNumber_red = portNum_red;
        pinNumber_red = pinNum_red;
        portNumber_green = portNum_green;
        pinNumber_green = pinNum_green;
        portNumber_blue = portNum_blue;
        pinNumber_blue = pinNum_blue;
        method = m;
        
        //for PWM
        if(method.equals("PWM")){
            pwmRGB = new PWMManager(PWMPIN.getPWM(portNum_red, pinNum_red),
                                    PWMPIN.getPWM(portNum_green, pinNum_green),
                                    PWMPIN.getPWM(portNum_blue, pinNum_blue));
        }
    }

    @Override
    public void initDevice() {
        if (method.equals("PWM")) {
            initSensor_PWM(portNumber_red, pinNumber_red,
                    portNumber_green, pinNumber_green,
                    portNumber_blue, pinNumber_blue);
        } else if (method.equals("GPIO")) {
            initSensor_GPIO(number_red, portNumber_red, pinNumber_red,
                    number_green, portNumber_green, pinNumber_green,
                    number_blue, portNumber_blue, pinNumber_blue);
        }
    }

    public void freeDevice() {
        freeSensor();
    }

    public void delay_ms(int ms) {
        delay(ms);
    }

    public void setPWM_RGB(int red, int green, int blue){
        if(pwmRGB == null || !pwmRGB.isloaded())
            return;
        
        pwmRGB.setRGB(red, green, blue);
        
        for(int num : pwmRGB.getEnablePWM_Number()){
            PWMPIN pinA= pwmRGB.getPWMPIN(num, 'A');
            PWMPIN pinB= pwmRGB.getPWMPIN(num, 'B');
            int valA = pinA != null ? pinA.getRGBvalue() : -1;
            int valB = pinB != null ? pinB.getRGBvalue() : -1;
            
            set_pwmRGB(num,valA,valB);
        }
    }
    /**
     * @deprecated 
     * @param red
     * @param green
     * @param blue 
     */
    public void coloring_PWM(double red, double green, double blue) {
        System.out.println("coloring PWM");
        color_PWM(number_red, number_green, red, green, blue);
    }

    /**
     * @deprecated 
     * @param red
     * @param green
     * @param blue 
     */
    public void coloring_RGB_PWM(int red, int green, int blue) {
        System.out.println("coloring RGB PWM");
        /*double duty_red, duty_green, duty_blue;

        duty_red = 100.0 - (double) (red + 1) * 99.0 / 256.0;
        duty_green = 100.0 - (double) (green + 1) * 99.0 / 256.0;
        duty_blue = 100.0 - (double) (blue + 1) * 99.0 / 256.0;
        
        coloring_PWM(duty_red,duty_green,duty_blue);*/
        
        color_RGB_PWM(number_red,number_green, red, green, blue);
    }

    public void gpio_pin_low(int portNum, int pinNum) {
        pin_low(portNum, pinNum);
    }

    public void gpio_pin_high(int portNum, int pinNum) {
        pin_high(portNum, pinNum);
    }

    public void start_GPIO() {
        System.out.println("START GPIO");
        redControl.start();
        greenControl.start();
        blueControl.start();
    }

    public void stop_GPIO() {
        System.out.println("STOP GPIO");
        redControl.stop();
        greenControl.stop();
        blueControl.stop();
    }

    public void setColor_GPIO(double red_duty, double green_duty, double blue_duty) {
        System.out.println("SET COLOR GPIO");
        double cycle_us = (double) 1 / 500.0f * (double) 1000 * 1000;
        double b_duty = blue_duty / (double) 100;
        double r_duty = red_duty / (double) 100;
        double g_duty = green_duty / (double) 100;

        double b_sleep_high = cycle_us * b_duty;
        double r_sleep_high = cycle_us * r_duty;
        double g_sleep_high = cycle_us * g_duty;
        double b_sleep_low = cycle_us * ((double) 1 - b_duty);
        double r_sleep_low = cycle_us * ((double) 1 - r_duty);
        double g_sleep_low = cycle_us * ((double) 1 - g_duty);

        red.delay_low = (long) r_sleep_low;
        red.delay_high = (long) r_sleep_high;
        green.delay_low = (long) g_sleep_low;
        green.delay_high = (long) g_sleep_high;
        blue.delay_low = (long) b_sleep_low;
        blue.delay_high = (long) b_sleep_high;
    }

    public void setColor_RGB_GPIO(int red, int green, int blue) {
        System.out.println("SET COLOT RGB GPIO");
        double duty_red, duty_green, duty_blue;

        duty_red = 100.0 - (double) (red + 1) * 99.0 / 256.0;
        duty_green = 100.0 - (double) (green + 1) * 99.0 / 256.0;
        duty_blue = 100.0 - (double) (blue + 1) * 99.0 / 256.0;

        setColor_GPIO(duty_red, duty_green, duty_blue);

    }
}
