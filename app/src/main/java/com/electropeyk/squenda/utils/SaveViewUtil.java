package com.electropeyk.squenda.utils;


import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.os.Environment;
import com.electropeyk.squenda.models.MetaFile;
import com.electropeyk.squenda.models.TypeStorage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class SaveViewUtil {


    /** Save picture to file */
    public static boolean saveScreen(Context context, Bitmap bitmap, TypeStorage typeStorage) {
        final File directory;
        if (typeStorage == TypeStorage.SDCARD) {


            directory = new File(Environment.getExternalStorageDirectory() +
                    File.separator + "images");
            directory.mkdirs();
        } else {
            String filepath = "images";
            ContextWrapper contextWrapper = new ContextWrapper(context);
             directory = contextWrapper.getDir(filepath, Context.MODE_PRIVATE);
        }
        if (!directory.exists()) {
            directory.mkdirs();
        }
        try {
            String PATH = directory.getAbsolutePath() + File.separator + System.currentTimeMillis() + ".jpg";
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, new FileOutputStream(new File(PATH)));
            MetaFile metaFile=new MetaFile(false,PATH);
            Common.ABSOLUTE_PATH_NAMES_PHOTO_LIST.add(metaFile);
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }


}

