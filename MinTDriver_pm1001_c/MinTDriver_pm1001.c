#include <wiringPi.h>
#include <wiringSerial.h>
#include <stdio.h>
#include <stdlib.h>
#include "MinTDriver_pm1001.h"

int fd;
int uartNum = 0;

void init_PM1001() {
    printf("PM1001 init...\n");

    if ((fd = serialOpen("/dev/ttyAMA0", 9600)) < 0) {
        printf("Unable to open serial device\n");
        exit(1);
    }

    if (wiringPiSetup() == -1) {
        printf("wiringPiSetup ERROR");
        exit(1);
    }
}

float read_PM1001() {
    int dustPCS;
    float dustValue = -1;
    char send[] = {0x11, 0x01, 0x01, 0xED};
    char response[8];
    fd = serialOpen("/dev/ttyAMA0", 9600);

    serialPuts(fd, send);
    while (serialGetchar(fd) != 0x16);
    response[0] = 0x16;
    response[1] = serialGetchar(fd);
    response[2] = serialGetchar(fd);
    response[3] = serialGetchar(fd);
    response[4] = serialGetchar(fd);
    response[5] = serialGetchar(fd);
    response[6] = serialGetchar(fd);
    response[7] = serialGetchar(fd);
    //printf("DEBUG : 0x%x 0x%x 0x%x 0x%x 0x%x 0x%x 0x%x\n", response[0], response[1], response[2], response[3], response[4], response[5], response[6]);
    if (response[0] == 0x16) {
        //농도(ug/㎥) = ((수량PCS/L값) * 3,528) / 100,000
        dustPCS = response[3] * 256 * 256 * 256 + response[4] * 256 * 256 + response[5] * 256 + response[6];
        dustValue = ((float) (dustPCS * 3528)) / 100000;
    }
    else {
        printf("Data get failed : pm1001\n");
    }

    return dustValue;
}

void free_PM1001() {
    serialClose(fd);
}

JNIEXPORT void JNICALL Java_MinT_ExternalDevice_Sensing_Dust_MinTDriver_1pm1001_initSensor
(JNIEnv *env, jobject obj) {
    init_PM1001();
}

JNIEXPORT jfloat JNICALL Java_MinT_ExternalDevice_Sensing_Dust_MinTDriver_1pm1001_getDustData
(JNIEnv *env, jobject obj) {
    float dustValue;

    dustValue = read_PM1001();

    return dustValue;
}

JNIEXPORT void JNICALL Java_MinT_ExternalDevice_Sensing_Dust_MinTDriver_1pm1001_freeSensor
(JNIEnv *env, jobject obj) {
    free_PM1001();
}