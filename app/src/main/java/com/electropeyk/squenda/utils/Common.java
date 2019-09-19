package com.electropeyk.squenda.utils;


import java.util.ArrayList;
import java.util.List;

public class Common {
    public static final String ABSOLUTE_PATH_NAMES_VIDEO = "absolute_path_name_video";
    public static final String DATABASE = "squenda_db";
    public static List<String> ABSOLUTE_PATH_NAMES_VIDEO_LIST = new ArrayList<>();
    public static String ABSOLUTE_PATH_NAMES_PHOTO = "absolute_path_name_photo";
    public static List<String> ABSOLUTE_PATH_NAMES_PHOTO_LIST = new ArrayList<>();
    public static List<Integer> VIDEO_NUM_SELECCTED = new ArrayList<>();
    public static List<Integer> PHOTO_NUM_SELECCTED = new ArrayList<>();
    public static String[] days = new String[]{"Sunday", "Monday", "Tuseday",
            "Wednesday", "Thursday", "Friday", "Saturday"};
    public static String[] months = {"January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"};

    public static boolean compatreString(String str1, String str2) {
        return str1.equals(str2) ? true : false;
    }

}
