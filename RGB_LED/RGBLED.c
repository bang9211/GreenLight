#include <stdio.h>
#include <unistd.h>
#include "/MinT/usr/BBBio/BBBiolib.h"
#include "MinTDriver_portpin.h"
#include "RGBLED.h"
#define HZ 500.0f
#define disable 100.0f

double PWM0A = disable;
double PWM0B = disable;
double PWM1A = disable;
double PWM1B = disable;
double PWM2A = disable;
double PWM2B = disable;


void initRGB_LED_PWM(int portNum_red, int pinNum_red,
        int portNum_green, int pinNum_green,
        int portNum_blue, int pinNum_blue){
    iolib_init();
    setPWM(portNum_red, pinNum_red);
    setPWM(portNum_blue, pinNum_blue);
    setPWM(portNum_green, pinNum_green);
}

void initRGB_LED_GPIO(int r_gpioNum, int r_portNum, int r_pinNum,
        int g_gpioNum, int g_portNum, int g_pinNum,
        int b_gpioNum, int b_portNum, int b_pinNum){
    exportGPIO(b_gpioNum);
    exportGPIO(r_gpioNum);
    exportGPIO(g_gpioNum);
    
    iolib_init();
    
    iolib_setdir(b_portNum,b_pinNum,BBBIO_DIR_OUT);
    iolib_setdir(r_portNum,r_pinNum,BBBIO_DIR_OUT);
    iolib_setdir(g_portNum,g_pinNum,BBBIO_DIR_OUT);
}

void freeRGB_LED(){
    iolib_free();
}

void delay_ms(int ms){
    iolib_delay_ms(ms);
}

void set_BBBIO_PWM(int number, double duty_A, double duty_B){
    BBBIO_PWMSS_Setting(number,HZ,duty_A,duty_B);
    BBBIO_ehrPWM_Enable(number);
}

void setPWMrgb(int number, int _valA, int _valB){
    int valA = _valA == -1 ? disable : _valA;
    int valB = _valB == -1 ? disable : _valB;
    
    printf("Set PWM RGB : number(%d) - valA(%d), -valB(%d)\n",number, valA, valB);
    
	switch(number){
		case 0 :
			PWM0A = _valA == -1 ? PWM0A : 100.0 - (float)(_valA+1)*99.0/256.0;
			PWM0B = _valB == -1 ? PWM0B : 100.0 - (float)(_valB+1)*99.0/256.0;
			set_BBBIO_PWM(number, PWM0A, PWM0B);
			break;
		case 1 :
			PWM1A = _valA == -1 ? PWM1A : 100.0 - (float)(_valA+1)*99.0/256.0;
			PWM1B = _valB == -1 ? PWM1B : 100.0 - (float)(_valB+1)*99.0/256.0;
			set_BBBIO_PWM(number, PWM1A, PWM1B);
			break;
		case 2 :
			PWM2A = _valA == -1 ? PWM2A : 100.0 - (float)(_valA+1)*99.0/256.0;
			PWM2B = _valB == -1 ? PWM2B : 100.0 - (float)(_valB+1)*99.0/256.0;
			set_BBBIO_PWM(number, PWM2A, PWM2B);
			break;
	}
}

void coloring_PWM(int number_red, int number_green, double red, double green, double blue){
    BBBIO_PWMSS_Setting(number_red,HZ,blue,red);
    BBBIO_PWMSS_Setting(number_green,HZ, green, disable);
    
    BBBIO_ehrPWM_Enable(number_red);
    BBBIO_ehrPWM_Enable(number_green);
}

void coloring_RGB_PWM(int number_red, int number_green,int red, int green, int blue){
    float duty_red, duty_green, duty_blue;
    
    duty_red = 100.0 - (float)(red+1)*99.0/256.0;
    duty_green = 100.0 - (float)(green+1)*99.0/256.0;
    duty_blue = 100.0 - (float)(blue+1)*99.0/256.0;
    
    coloring_PWM(number_red,number_green, duty_red, duty_green, duty_blue);
}
void GPIO_pin_low(int portNum, int pinNum){
    pin_low(portNum,pinNum);
}

void GPIO_pin_high(int portNum, int pinNum){
    pin_high(portNum, pinNum);
}
void USleep(long delay){
    usleep(delay);
}

/*
 * Class:     MinTFramework_ExternalDevice_Control_RGBLED_MinTDriver_RGB_LED_java
 * Method:    initSensor_PWM
 * Signature: (IIIIII)V
 */
JNIEXPORT void JNICALL Java_MinTFramework_ExternalDevice_Control_RGBLED_MinTDriver_1RGB_1LED_1java_initSensor_1PWM
  (JNIEnv *env, jobject obj, jint portNum_red, jint pinNum_red, jint portNum_green, jint pinNum_green, jint portNum_blue, jint pinNum_blue){
    initRGB_LED_PWM(portNum_red,pinNum_red,portNum_green,pinNum_green,portNum_blue,pinNum_blue);
}

/*
 * Class:     MinTFramework_ExternalDevice_Control_RGBLED_MinTDriver_RGB_LED_java
 * Method:    initSensor_GPIO
 * Signature: (IIIIIIIII)V
 */
JNIEXPORT void JNICALL Java_MinTFramework_ExternalDevice_Control_RGBLED_MinTDriver_1RGB_1LED_1java_initSensor_1GPIO
  (JNIEnv *env, jobject obj, jint r_gpioNum, jint r_portNum, jint r_pinNum, jint g_gpioNum, jint g_portNum, jint g_pinNum, jint b_gpioNum, jint b_portNum, jint b_pinNum){
    initRGB_LED_GPIO(r_gpioNum,r_portNum,r_pinNum,g_gpioNum,g_portNum,g_pinNum,b_gpioNum,b_portNum,b_pinNum);
}

/*
 * Class:     MinTFramework_ExternalDevice_Control_RGBLED_MinTDriver_RGB_LED_java
 * Method:    freeSensor
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_MinTFramework_ExternalDevice_Control_RGBLED_MinTDriver_1RGB_1LED_1java_freeSensor
  (JNIEnv *env, jobject ob){
    iolib_intit();
}

/*
 * Class:     MinTFramework_ExternalDevice_Control_RGBLED_MinTDriver_RGB_LED_java
 * Method:    delay
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_MinTFramework_ExternalDevice_Control_RGBLED_MinTDriver_1RGB_1LED_1java_delay
  (JNIEnv *env, jobject obj, jint delay){
    delay_ms(delay);
}

/*
 * Class:     MinTFramework_ExternalDevice_Control_RGBLED_MinTDriver_RGB_LED_java
 * Method:    set_pwmRGB
 * Signature: (III)V
 */
JNIEXPORT void JNICALL Java_MinTFramework_ExternalDevice_Control_RGBLED_MinTDriver_1RGB_1LED_1java_set_1pwmRGB
  (JNIEnv *env, jobject obj, jint number, jint valA, jint valB){
    setPWMrgb(number, valA, valB);
}

/*
 * Class:     MinTFramework_ExternalDevice_Control_RGBLED_MinTDriver_RGB_LED_java
 * Method:    color_PWM
 * Signature: (IIIII)V
 */
JNIEXPORT void JNICALL Java_MinTFramework_ExternalDevice_Control_RGBLED_MinTDriver_1RGB_1LED_1java_color_1PWM
  (JNIEnv *env, jobject obj, jint number_red , jint number_green, jdouble red, jdouble green, jdouble blue){
    coloring_PWM(number_red, number_green, red, green, blue);
}

/*
 * Class:     MinTFramework_ExternalDevice_Control_RGBLED_MinTDriver_RGB_LED_java
 * Method:    color_RGB_PWM
 * Signature: (IIIII)V
 */
JNIEXPORT void JNICALL Java_MinTFramework_ExternalDevice_Control_RGBLED_MinTDriver_1RGB_1LED_1java_color_1RGB_1PWM
  (JNIEnv *env, jobject obj, jint number_red, jint number_green, jint red, jint green, jint blue){
    coloring_RGB_PWM(number_red,number_green,red,green,blue);
}

/*
 * Class:     MinTFramework_ExternalDevice_Control_RGBLED_MinTDriver_RGB_LED_java
 * Method:    pin_low
 * Signature: (II)V
 */
JNIEXPORT void JNICALL Java_MinTFramework_ExternalDevice_Control_RGBLED_MinTDriver_1RGB_1LED_1java_pin_1low
  (JNIEnv *env, jobject obj, jint portNum, jint pinNum){
    GPIO_pin_low(portNum, pinNum);
}

/*
 * Class:     MinTFramework_ExternalDevice_Control_RGBLED_MinTDriver_RGB_LED_java
 * Method:    pin_high
 * Signature: (II)V
 */
JNIEXPORT void JNICALL Java_MinTFramework_ExternalDevice_Control_RGBLED_MinTDriver_1RGB_1LED_1java_pin_1high
  (JNIEnv *env, jobject obj, jint portNum, jint pinNum){
    GPIO_pin_high(portNum, pinNum);
}

/*
 * Class:     MinTFramework_ExternalDevice_Control_RGBLED_MinTDriver_RGB_LED_java
 * Method:    uSleep
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_MinTFramework_ExternalDevice_Control_RGBLED_MinTDriver_1RGB_1LED_1java_uSleep
  (JNIEnv *env, jobject obj, jlong delay){
    USleep(delay);
}
