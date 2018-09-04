package ifrs.canoas.ifhelper;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {
    private static Session instance;

    private static String NAME = "IFHELPER_SESSION";
    private static Integer MODE = Context.MODE_PRIVATE;
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    private Session(Context context) {
        sharedPreferences = context.getSharedPreferences(NAME, MODE);
        editor = sharedPreferences.edit();
    }

    public static Session init(Context context) {
        if (instance == null)
            instance = new Session(context);

        return instance;
    }

    public void saveToken(String token) {
        editor.putString("token", token);
        editor.apply();
    }

    public void clear() {
        editor.clear();
        editor.apply();
    }

    public String getToken() {
        return sharedPreferences.getString("token", null);
    }
}
