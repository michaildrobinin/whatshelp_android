package sos.rock.sosapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class MPreferenceManager {
    public static final String TOKEN = "token";
    public static final String REMEMBER_ME = "remember";
    public static final String IS_SOS = "is_sos";
    public static final String ACTIONWAY = "action_way"; //0: distance_way, 1: contactList_way
    public static final String DISTANCE_PROGRESS = "distance_progress";
    public static final String DISTANCE = "distance";
    public static final String RESTRICTION = "restriction";
    public static final String TOPADS_SHOW = "top_ads_show";
    public static final String BOTTOMADS_SHOW = "bottom_ads_show";

    public static void saveStringInformation(Context context, String key, String value){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString(key, value);
        ed.apply();
    }

    public static String readStringInformation(Context context, String key){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(key, "");
    }

    public static void saveBoolInformation(Context context, String key, boolean value){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor ed = sp.edit();
        ed.putBoolean(key, value);
        ed.apply();
    }

    public static boolean readBoolInformation(Context context, String key){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(key, false);
    }

    public static void saveIntInformation(Context context, String key, int value){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor ed = sp.edit();
        ed.putInt(key, value);
        ed.apply();
    }

    public static int readIntInformation(Context context, String key){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getInt(key, 0);
    }

}
