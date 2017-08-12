package thanhhai.com.toeicpractice.RoomChat;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import thanhhai.com.toeicpractice.R;

public class RegisterFragment extends Fragment implements View.OnClickListener {

    private EditText mDisplayNameRegister, mUserNameRegister, mUserPasswordRegister;
    private Button btnRegister, btnCancel;

    private AlertDialog dialog;
    private FirebaseAuth mAuth;

    public RegisterFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((SplashLoginActivity) getActivity()).getSupportActionBar().setTitle("Register Account");
        ((SplashLoginActivity) getActivity()).getSupportActionBar().show();
        mAuth = FirebaseAuth.getInstance();
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnRegister.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    private void addControls() {
        mDisplayNameRegister = (EditText) getActivity().findViewById(R.id.edit_text_display_name);
        mUserNameRegister = (EditText) getActivity().findViewById(R.id.edit_text_email_register);
        mUserPasswordRegister = (EditText) getActivity().findViewById(R.id.edit_text_password_register);
        btnRegister = (Button) getActivity().findViewById(R.id.btn_register_user);
        btnCancel = (Button) getActivity().findViewById(R.id.btn_cancel_register);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_register_user:
                onRegisterUser();
                break;
            case R.id.btn_cancel_register:
                getActivity().onBackPressed();
                break;
            default:
                break;
        }
    }

    private void onRegisterUser() {
        if(getUserDisplayName().equals("") || getUserEmail().equals("") || getUserPassword().equals("") || getUserDisplayName().equals("Admin") || getUserDisplayName().equals("admin")){
            showFieldsAreRequired();
        }else if(isIncorrectEmail(getUserEmail()) || isIncorrectPassword(getUserPassword())) {
            showIncorrectEmailPassword();
        }else {
            handleSignUp(getUserDisplayName(), getUserEmail(), getUserPassword());
        }
    }

    private void showIncorrectEmailPassword() {
        showAlertDialog(getString(R.string.error_incorrect_email_pass), true);
    }

    private boolean isIncorrectEmail(String userEmail) {
        return !android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail).matches();
    }

    private boolean isIncorrectPassword(String userPassword) {
        return !(userPassword.length() >= 6);
    }

    private void showFieldsAreRequired() {
        showAlertDialog(getString(R.string.error_fields_empty), true);
    }

    public void onSignUpSuccess() {
        LoginFragment loginFragment = new LoginFragment();
        FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.main, loginFragment).commit();
    }

    public void showAlertDialog(String message, boolean isCancelable){
        dialog = Config.buildAlertDialog(getString(R.string.login_error_title),message,isCancelable,getActivity());
        dialog.show();
    }

    public void dismissAlertDialog() {
        dialog.dismiss();
    }

    public void onSignUpFailed() {
        Toast.makeText(getContext(), "Sign Up failed!", Toast.LENGTH_SHORT).show();
    }

    public String getUserDisplayName() {
        return mDisplayNameRegister.getText().toString().trim();
    }

    public String getUserEmail() {
        return mUserNameRegister.getText().toString().trim();
    }

    private String getUserPassword() {
        return mUserPasswordRegister.getText().toString().trim();
    }

    public void handleSignUp(final String userDisplayName, String userEmailRegister, String userPasswordRegister) {
        showAlertDialog("Registering...", true);
        mAuth.createUserWithEmailAndPassword(userEmailRegister, userPasswordRegister).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                dismissAlertDialog();
                if (task.isSuccessful()) {
                    onAuthSuccess(task.getResult().getUser(), userDisplayName);
                } else {
                    showAlertDialog(task.getException().getMessage(), true);
                }
            }
        });
    }

    private void onAuthSuccess(FirebaseUser user, String userDisplayName) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(userDisplayName)
                .build();
        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            onSignUpSuccess();
                        } else {
                            onSignUpFailed();
                        }
                    }
                });
    }
}
