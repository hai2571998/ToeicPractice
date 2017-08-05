package thanhhai.com.toeicpractice.RoomChat;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import thanhhai.com.toeicpractice.R;

public class SplashLoginFragment extends Fragment {

    private FirebaseAuth mAuth;

    public SplashLoginFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        if (isCheckRememberPassword()) {
            handleLogin(getUserEmailPref(), getUserPasswordPref());
        } else {
            LoginFragment loginFragment = new LoginFragment();
            FragmentManager manager = getActivity().getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_main, loginFragment).addToBackStack(null).commit();
        }
    }

    public boolean isCheckRememberPassword() {
        return getActivity().getSharedPreferences(Config.KEY_USER_INFO, Context.MODE_PRIVATE)
                .getBoolean(Config.KEY_CHECK_REMEMBER_PASS, false);
    }

    public void handleLogin(String userEmail, String userPassword) {
        mAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
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
        return getActivity().getSharedPreferences(Config.KEY_USER_INFO, Context.MODE_PRIVATE)
                .getString(Config.KEY_EMAIL, "");
    }

    private String getUserPasswordPref() {
        return getActivity().getSharedPreferences(Config.KEY_USER_INFO, Context.MODE_PRIVATE)
                .getString(Config.KEY_PASSWORD, "");
    }

    public void onLoginSuccess() {
        ChatRoomFragment chatRoomFragment = new ChatRoomFragment();
        FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_main, chatRoomFragment).addToBackStack(null).commit();
    }

    public void onLoginFailed() {
        LoginFragment loginFragment = new LoginFragment();
        FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_main, loginFragment).addToBackStack(null).commit();
    }
}
