package thanhhai.com.toeicpractice.RoomChat;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import thanhhai.com.toeicpractice.R;

public class SplashLoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_login);
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();
        if (isCheckRememberPassword()) {
            handleLogin(getUserEmailPref(), getUserPasswordPref());
        } else {
            LoginFragment loginFragment = new LoginFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.main, loginFragment).commit();
        }
    }

    public boolean isCheckRememberPassword() {
        return getSharedPreferences(Config.KEY_USER_INFO, Context.MODE_PRIVATE)
                .getBoolean(Config.KEY_CHECK_REMEMBER_PASS, false);
    }

    public void handleLogin(String userEmail, String userPassword) {
        mAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    onLoginSuccess();
                } else {
                    onLoginFailed();
                }
            }
        });
    }

    private String getUserEmailPref() {
        return getSharedPreferences(Config.KEY_USER_INFO, Context.MODE_PRIVATE)
                .getString(Config.KEY_EMAIL, "");
    }

    private String getUserPasswordPref() {
        return getSharedPreferences(Config.KEY_USER_INFO, Context.MODE_PRIVATE)
                .getString(Config.KEY_PASSWORD, "");
    }

    public void onLoginSuccess() {
        ChatRoomFragment chatRoomFragment = new ChatRoomFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.main, chatRoomFragment).commit();
    }

    public void onLoginFailed() {
        LoginFragment loginFragment = new LoginFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.main, loginFragment).commit();
    }
}
