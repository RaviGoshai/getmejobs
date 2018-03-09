package rubberducks.getmejob.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("deprecation")
public class PreferenceConnector {
    public static final String PREF_NAME = "getmejob";
    public static final int MODE = Context.MODE_PRIVATE;


    public static void writeBoolean(Context context, String key, boolean value) {
        getEditor(context).putBoolean(key, value).commit();
    }

    public static boolean readBoolean(Context context, String key,
                                      boolean defValue) {
        return getPreferences(context).getBoolean(key, defValue);
    }

    public static void writeInteger(Context context, String key, int value) {
        getEditor(context).putInt(key, value).commit();

    }

    public static int readInteger(Context context, String key, int defValue) {
        return getPreferences(context).getInt(key, defValue);
    }

    public static void writeString(Context context, String key, String value) {
        getEditor(context).putString(key, value).commit();

    }

    public static String readString(Context context, String key, String defValue) {
        return getPreferences(context).getString(key, defValue);
    }

    public static void writeFloat(Context context, String key, float value) {
        getEditor(context).putFloat(key, value).commit();
    }

    public static float readFloat(Context context, String key, float defValue) {
        return getPreferences(context).getFloat(key, defValue);
    }

    public static void writeLong(Context context, String key, long value) {
        getEditor(context).putLong(key, value).commit();

    }

    public static void writeArraylist(Context context, String key,
                                      List<String> arryid) {
        Set<String> set = new HashSet<String>(arryid);
        getEditor(context).putStringSet(key, set).commit();

    }

    public static List<String> ReadArraylist(Context context, String key) {
        Set<String> stock_Set = getPreferences(context).getStringSet(key,
                new HashSet<String>());
        List<String> demo = new ArrayList<String>(stock_Set);

        return demo;
    }

    public static long readLong(Context context, String key, long defValue) {
        return getPreferences(context).getLong(key, defValue);

    }
    public static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, MODE);
    }

    public static Editor getEditor(Context context) {
        return getPreferences(context).edit();
    }

    public static void remove(Context context, String key) {
        getEditor(context).remove(key);

    }


    public static void clearAllSharedPreferences(Context context) {
        Editor editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();
    }

}
