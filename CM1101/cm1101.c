#include <stdio.h>
#include <stdlib.h>
#include "/MinT/usr/BBBio/BBBiolib.h"
#include "MinTDriver_portpin.h"
#include "cm1101.h"

int uartNum = 0;

void initCM1101(int uartNumber){
    printf("CM1101 init...\n");
    iolib_init();
    setUART(uartNumber);
    uartNum = uartNumber;
}

void freeCM1101(){
    iolib_free();
}

int calcCO2(){
    char send[] = {0x11, 0x01, 0x01, 0xED};
    char response[8];
    int co2 = 0;
    
    writeUART(uartNum, send);
    readUARTBytes(uartNum,response,8);
    
    if(response[0] == 0x16){
        printf("success to receive...\n");
        co2 = response[3]*256 + response[4];
        return co2;
    }
    else
        printf("fail to receive...\n");
    
    return 0;
}
/*
 * Class:     MinTFramework_ExternalDevice_Sensing_Dust_MinTDriver_cm1101_java
 * Method:    initSensor
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_MinTFramework_ExternalDevice_Sensing_Dust_MinTDriver_1cm1101_1java_initSensor
  (JNIEnv *env, jobject obj, jint uartNumber){
    initCM1101(uartNumber);
}

/*
 * Class:     MinTFramework_ExternalDevice_Sensing_Dust_MinTDriver_cm1101_java
 * Method:    freeSensro
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_MinTFramework_ExternalDevice_Sensing_Dust_MinTDriver_1cm1101_1java_freeSensro
  (JNIEnv *env, jobject obj){
    freeCM1101();
}

/*
 * Class:     MinTFramework_ExternalDevice_Sensing_Dust_MinTDriver_cm1101_java
 * Method:    getCO2Value
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_MinTFramework_ExternalDevice_Sensing_Dust_MinTDriver_1cm1101_1java_getCO2Value
  (JNIEnv *env, jobject obj){
    int co2;
    
    co2 = calcCO2();
    
    return co2;
}