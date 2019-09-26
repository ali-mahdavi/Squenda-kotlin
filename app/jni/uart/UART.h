
#ifndef UART_H_
#define UART_H_

#include <stdio.h>
#include <android/log.h>
#include <fcntl.h>
#include "../../../../../../../Library/Android/sdk/ndk-bundle/toolchains/llvm/prebuilt/darwin-x86_64/sysroot/usr/include/jni.h"


extern "C" {

JNIEXPORT jint JNICALL Java_com_electropeyk_squenda_jni_UART_serialOpen(JNIEnv *, jclass type, jstring, jint);
JNIEXPORT jint JNICALL Java_com_electropeyk_squenda_jni_UART_serialClose(JNIEnv *, jclass type, jint);
JNIEXPORT jint JNICALL Java_com_electropeyk_squenda_jni_UART_serialWrite(JNIEnv *, jclass type, jint, jstring);
JNIEXPORT jint JNICALL Java_com_electropeyk_squenda_jni_UART_serialRead(JNIEnv *, jclass type, jint, jcharArray);
};


#endif /* UART_H_ */