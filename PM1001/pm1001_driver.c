#include <stdio.h>
#include <stdlib.h>
#include "/MinT/usr/BBBio/BBBiolib.h"
#include "MinTDriver_portpin.h"
#include "pm1001.h"

int uartNum = 0;

void initPM1001(int uartNumber){
    printf("PM1001 init...\n");
    iolib_init();
    setUART(uartNumber);
    uartNum = uartNumber;
}

void freePM1001(){
    iolib_free();
}

float calcDustValue(){
    char send[] = {0x11,0x01,0x01,0xED};
    char response[16];
    float dustPCS;
    float dustValue;
    
    writeUART(uartNum,send);
    readUARTBytes(uartNum,response,16);
    
    if(response[0] == 0x16){
        printf("success to receive...\n");
        dustPCS = response[3]*256*256*256 + response[4]*256*256 + response[5]*256 + response[6];
        dustValue = ((float)(dustPCS*3526))/100000;
        return dustValue;
    }
    else{
        printf("fail to receive...\n");
        return 0;
    }
    return 0;
}
/*
 * Class:     MinTFramework_ExternalDevice_Sensing_Dust_MinTDriver_pm1001_java
 * Method:    initSensor
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_MinTFramework_ExternalDevice_Sensing_Dust_MinTDriver_1pm1001_1java_initSensor
  (JNIEnv *env, jobject obj, jint uartNum){
    initPM1001(uartNum);
}

/*
 * Class:     MinTFramework_ExternalDevice_Sensing_Dust_MinTDriver_pm1001_java
 * Method:    getDustValue
 * Signature: ()F
 */
JNIEXPORT jfloat JNICALL Java_MinTFramework_ExternalDevice_Sensing_Dust_MinTDriver_1pm1001_1java_getDustValue
  (JNIEnv *env, jobject obj){
    float dustValue;
    
    dustValue = calcDustValue();
    
    return dustValue;
}

/*
 * Class:     MinTFramework_ExternalDevice_Sensing_Dust_MinTDriver_pm1001_java
 * Method:    freeSensor
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_MinTFramework_ExternalDevice_Sensing_Dust_MinTDriver_1pm1001_1java_freeSensor
  (JNIEnv *env, jobject obj){
    freePM1001();
}