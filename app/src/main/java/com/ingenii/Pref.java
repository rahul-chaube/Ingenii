package com.ingenii;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by rahul on 15/10/17.
 */

public class Pref {
    private final static String Name = "name";
    private final static String Company = "company";
    private final static String Logo = "logo";
    private final static String Url = "url";
    private final static String Designation = "Designation";
    private static SharedPreferences getSharedPreferenceInstanced(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static String getName(Context context) {

        return  getSharedPreferenceInstanced(context).getString(Name, "");
    }
    public static void setName(Context context, String status) {
        SharedPreferences.Editor editor = getSharedPreferenceInstanced(context).edit();
        editor.putString(Name,status );
        editor.apply();
    }
    public static String getCompany(Context context) {

        return  getSharedPreferenceInstanced(context).getString(Company, "");
    }
    public static void setCompany(Context context, String status) {
        SharedPreferences.Editor editor = getSharedPreferenceInstanced(context).edit();
        editor.putString(Company,status);
        editor.apply();
    }
    public static String getLogo(Context context) {

        return  getSharedPreferenceInstanced(context).getString(Logo, "");
    }
    public static void setLogo(Context context, String status) {
        SharedPreferences.Editor editor = getSharedPreferenceInstanced(context).edit();
        editor.putString(Logo,status );
        editor.apply();
    }

    public static String getUrl(Context context) {

        return  getSharedPreferenceInstanced(context).getString(Url, "");
    }
    public static void setUrl(Context context, String status) {
        SharedPreferences.Editor editor = getSharedPreferenceInstanced(context).edit();
        editor.putString(Url,status );
        editor.apply();
    }
    public static String getDesignation(Context context) {

        return  getSharedPreferenceInstanced(context).getString(Designation, "");
    }
    public static void setDesignation(Context context, String status) {
        SharedPreferences.Editor editor = getSharedPreferenceInstanced(context).edit();
        editor.putString(Designation,status );
        editor.apply();
    }

}
