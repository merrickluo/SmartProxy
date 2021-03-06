package me.smartproxy.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class Utils {

    public static final String CURRENT_PROFILE_KEY = "CURRENT_PROFILE_KEY";

    public static final String PROFILE_LIST_KEY = "PROFILE_LIST_KEY";

    public static final String CONFIG_URL_KEY = "CONFIG_URL_KEY";

    public static final String AUTO_START_CONFIG_KEY = "AUTO_START_CONFIG_KEY";

    private static final String LOCAL_IP_ADDRESS = "(127[.]0[.]0[.]1)|" + "(localhost)|" +
            "(10[.]\\d{1,3}[.]\\d{1,3}[.]\\d{1,3})|" +
            "(172[.]((1[6-9])|(2\\d)|(3[01]))[.]\\d{1,3}[.]\\d{1,3})|" +
            "(192[.]168[.]\\d{1,3}[.]\\d{1,3})";

    private static Pattern localIpPattern = Pattern.compile(LOCAL_IP_ADDRESS);

    public static void setAutoStartConfig(Context context, boolean isChecked) {
        SharedPreferences preferences = context.getSharedPreferences("SmartProxy",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(AUTO_START_CONFIG_KEY, isChecked);
        editor.apply();
    }

    public static String readConfigUrl(Context context, String profile) {
        SharedPreferences preferences = context.getSharedPreferences("SmartProxy",
                Context.MODE_PRIVATE);
        return preferences.getString(profile, "");
    }

    public static void setConfigUrl(Context context, String profile, String configUrl) {
        SharedPreferences preferences = context.getSharedPreferences("SmartProxy",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(profile, configUrl);
        editor.apply();
    }

    public static boolean readAutoStartConfig(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("SmartProxy",
                Context.MODE_PRIVATE);
        return preferences.getBoolean(AUTO_START_CONFIG_KEY, false);
    }

    public static boolean isLocalIp(String ip) {
        return localIpPattern.matcher(ip).find();
    }

    public static boolean isValidUrl(String url) {
        try {
            if (url == null || url.isEmpty()) {
                return false;
            }

            if (url.startsWith("/")) {//file path
                File file = new File(url);
                if (!file.exists()) {
                    return false;
                }
                if (!file.canRead()) {
                    return false;
                }
            } else if (url.startsWith("ss")) {//shadowsocks
                return true;
            } else { //url
                Uri uri = Uri.parse(url);
                if (!"http".equals(uri.getScheme()) && !"https".equals(uri.getScheme())) {
                    return false;
                }
                if (uri.getHost() == null) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void addProfile(Context context, String profileName) {
        SharedPreferences preferences = context.getSharedPreferences("SmartProxy",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Set<String> profiles = preferences.getStringSet(PROFILE_LIST_KEY, null);
        if (profiles == null) {
            profiles = new HashSet<>();
        }
        profiles.add(profileName);
        editor.putStringSet(PROFILE_LIST_KEY, profiles);
        editor.apply();
    }

    public static void setCurrentProfile(Context context, String profileName) {
        SharedPreferences preferences = context.getSharedPreferences("SmartProxy",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(CURRENT_PROFILE_KEY, profileName);
        editor.apply();
    }

    public static String readCurrentProfile(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("SmartProxy",
                Context.MODE_PRIVATE);
        return preferences.getString(CURRENT_PROFILE_KEY, null);
    }

    public static Set<String> readProfileList(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("SmartProxy",
                Context.MODE_PRIVATE);
        return preferences.getStringSet(PROFILE_LIST_KEY, null);
    }
}
