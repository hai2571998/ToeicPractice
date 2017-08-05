package thanhhai.com.toeicpractice.RoomChat;

import android.app.AlertDialog;
import android.content.Context;

public class Config {

    // SharedPreference
    public static final String KEY_USER_INFO = "USER_INFO";
    public static final String KEY_USER_ID = "USER_ID";
    public static final String KEY_PASSWORD = "PASSWORD";
    public static final String KEY_DISPLAY_NAME = "DISPLAY_NAME";
    public static final String KEY_EMAIL = "EMAIL";
    public static final String KEY_CHECK_REMEMBER_PASS = "CHECK_REMEMBER_PASS";

    // Firebase
    public static final String KEY_ROOM_ONE = "ROOM_ONE";

    // AlertDialog
    public static AlertDialog buildAlertDialog(String title, String message, boolean isCancelable, Context context){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setTitle(title);

        if(isCancelable){
            builder.setPositiveButton(android.R.string.ok, null);
        }else {
            builder.setCancelable(false);
        }
        return builder.create();
    }
}
