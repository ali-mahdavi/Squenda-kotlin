/*
 * I2C.h
 *
 *  Created on: June 5, 2013
 *      Author: root
 */

#ifndef I2C_H_
#define I2C_H_

#include <android/log.h>
#include <stdio.h>
#include <android/log.h>
#include <fcntl.h>
#include <linux/i2c.h>
#include <sys/ioctl.h>
#include <memory.h>
#include <malloc.h>

#include <sys/stat.h>

#include <unistd.h>


#include <linux/i2c-dev.h>


extern "C" {
JNIEXPORT jint JNICALL Java_com_electropeyk_squenda_jni_I2C_init(JNIEnv *, jobject type, jstring);
JNIEXPORT jint JNICALL Java_com_electropeyk_squenda_jni_I2C_close(JNIEnv *, jobject type, jint);
JNIEXPORT jint JNICALL Java_com_electropeyk_squenda_jni_I2C_open(JNIEnv *, jobject type, jint, jint);
JNIEXPORT jint JNICALL Java_com_electropeyk_squenda_jni_I2C_write(JNIEnv *, jobject type, jint, jintArray, jint);
JNIEXPORT jint JNICALL Java_com_electropeyk_squenda_jni_I2C_read(JNIEnv *, jobject type, jint, jintArray, jint);


};

#endif /* I2C_H_ */