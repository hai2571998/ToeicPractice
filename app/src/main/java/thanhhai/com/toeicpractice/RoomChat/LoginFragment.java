package thanhhai.com.toeicpractice.RoomChat;


import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import thanhhai.com.toeicpractice.R;

public class LoginFragment extends Fragment implements View.OnClickListener{

    private EditText mUserEmail, mUserPassWord;
    private Button btnLogin, btnRegister;
    private CheckBox chkRememberPassword;
    private TextView tvForgotPass;

    private AlertDialog dialog;
    private FirebaseAuth mAuth;

    public LoginFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((SplashLoginActivity) getActivity()).getSupportActionBar().setTitle("Login Account");
        setAuthInstance();
        addControls();
        addevents();

    }

    private void addControls() {
        mUserEmail = (EditText) getActivity().findViewById(R.id.edit_text_email_login);
        mUserPassWord = (EditText) getActivity().findViewById(R.id.edit_text_password_log_in);
        btnLogin = (Button) getActivity().findViewById(R.id.btn_login);
        btnRegister = (Button) getActivity().findViewById(R.id.btn_register);
        chkRememberPassword = (CheckBox) getActivity().findViewById(R.id.check_box_remember_pass);
        tvForgotPass = (TextView) getActivity().findViewById(R.id.text_view_forgot_pass);
    }

    private void addevents() {
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        tvForgotPass.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                onLogInUser(getUserEmail(), getUserPassword());
                break;
            case R.id.btn_register:
                goToRegisterFragment();
                break;
            case R.id.text_view_forgot_pass:
                goToForgotPassword();
                break;
            default:
                break;
        }
    }

    @NonNull
    private String getUserPassword() {
        return mUserPassWord.getText().toString().trim();
    }

    @NonNull
    private String getUserEmail() {
        return mUserEmail.getText().toString().trim();
    }

    private void goToForgotPassword() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = inflater.inflate(R.layout.dialog_forgot_pass_layout, null);
        final EditText userEmail = (EditText) view.findViewById(R.id.edit_text_user_email);
        Button btnSend = (Button) view.findViewById(R.id.btn_send_pass_to_email);
        Button btnCancel = (Button) view.findViewById(R.id.btn_cancel_forgot_pass);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleForgotPassword(userEmail.getText().toString());
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }

    private void goToRegisterFragment() {
        RegisterFragment registerFragment = new RegisterFragment();
        FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.main, registerFragment).addToBackStack(null).commit();
    }

    private void onLogInUser(String userEmail, String userPassword) {
        if (userEmail.equals("") || userPassword.equals("")) {
            showFieldsAreRequired();
        } else {
            handleLogin(userEmail, userPassword);
        }
    }

    public void onLoginSuccess(FirebaseUser user) {
        saveSharedPreferencesOfUser(user);
        ChatRoomFragment chatRoomFragment = new ChatRoomFragment();
        FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.main, chatRoomFragment).commit();
    }

    public void showAlertDialog(String message, boolean isCancelable) {
        dialog = Config.buildAlertDialog(getString(R.string.login_error_title), message, isCancelable, getContext());
        dialog.show();
    }

    public void dismissAlertDialog() {
        dialog.dismiss();
    }

    private void showFieldsAreRequired() {
        showAlertDialog(getString(R.string.error_incorrect_email_pass), true);
    }

    private void setAuthInstance() {
        mAuth = FirebaseAuth.getInstance();
    }

    public void handleLogin(String userEmail, String userPassword) {
        showAlertDialog("Log In...", false);
        mAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                dismissAlertDialog();
                if (task.isSuccessful()) {
                    onLoginSuccess(task.getResult().getUser());
                    Toast.makeText(getContext(), "Login Thành Công", Toast.LENGTH_SHORT).show();
                } else {
                    showAlertDialog(task.getException().getMessage(), true);
                }
            }
        });
    }

    public void handleForgotPassword(String userEmail) {
        mAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                dismissAlertDialog();
                if(task.isSuccessful())
                    showAlertDialog("Send password to Email successfully!", true);
                else
                    showAlertDialog("Send password to Email failed!", true);
            }
        });
    }

    private void saveSharedPreferencesOfUser(FirebaseUser user) {
        SharedPreferences pref = getContext().getSharedPreferences(Config.KEY_USER_INFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Config.KEY_USER_ID, user.getUid());
        editor.putString(Config.KEY_PASSWORD, mUserPassWord.getText().toString());
        editor.putString(Config.KEY_DISPLAY_NAME, user.getDisplayName());
        editor.putString(Config.KEY_EMAIL, user.getEmail());
        if (chkRememberPassword.isChecked())
            editor.putBoolean(Config.KEY_CHECK_REMEMBER_PASS, true);
        else
            editor.putBoolean(Config.KEY_CHECK_REMEMBER_PASS, false);
        editor.commit();
    }
}
