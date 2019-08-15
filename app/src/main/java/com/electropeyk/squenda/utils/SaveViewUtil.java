package com.electropeyk.squenda.utils;


import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class SaveViewUtil {
    private static final File rootDir = new File(Environment.getExternalStorageDirectory()+File.separator+"squendaImages");

    /** Save picture to file */
    public static boolean saveScreen(Bitmap bitmap){
        //determine if SDCARD is available
        if(!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            return false;
        }
        if(!rootDir.exists()){
            rootDir.mkdir();
        }

        try {
            String PATH=rootDir+File.separator+ System.currentTimeMillis() + ".jpg";
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(new File(PATH)));
            Common.ABSOLUTE_PATH_NAMES_PHOTO_LIST.add(PATH);
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }


}

