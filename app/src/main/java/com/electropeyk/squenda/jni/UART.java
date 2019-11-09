package com.electropeyk.squenda.jni;

import android.util.Log;

import java.io.IOException;

public class UART {

    public static int fd;

    /* Load shared library */
    static {
        System.loadLibrary("UART");
    }

    /* Native methods */
    public static native int serialOpen(String port, int baud);

    public static native int serialClose(int fd);

    public static native int serialWrite(int fd, String text);

    public static native int serialRead(int fd, char[] buffer);

    public static void getPermission() {
        try {
            Process p = Runtime.getRuntime().exec(
                    new String[]{"su", "-c", "chmod 777 /dev/ttyS*"});
            p.waitFor();
            Log.i("UART", "chmod -> success!");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("UART", "chmod -> fail!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}