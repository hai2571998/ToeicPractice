package thanhhai.com.toeicpractice.RoomChat;


import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import thanhhai.com.toeicpractice.R;

public class ChangePasswordFragment extends Fragment implements View.OnClickListener {

    private EditText txtCurrentPassword, txtNewPassword, txtVerifyPassword;
    private Button btnChangePassword, btnCancel;

    private AlertDialog dialog;

    public ChangePasswordFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_change_password, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtCurrentPassword = (EditText) getActivity().findViewById(R.id.edit_text_current_password);
        txtNewPassword = (EditText) getActivity().findViewById(R.id.edit_text_new_password);
        txtVerifyPassword = (EditText) getActivity().findViewById(R.id.edit_text_verify_password);
        btnChangePassword = (Button) getActivity().findViewById(R.id.btn_change_password);
        btnCancel = (Button) getActivity().findViewById(R.id.btn_cancel_change_password);

        btnChangePassword.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_change_password:
                onChangePassword();
                break;
            case R.id.btn_cancel_change_password:
                getActivity().onBackPressed();
                break;
            default:
                break;
        }
    }

    private void onChangePassword() {
        if(getCurrentUserPassword().equals("") || getNewUserPassword().equals("") || getVerifyUserPassword().equals("")) {
            showAlertDialog(getString(R.string.error_pass_fields_empty), true);
        } else if(getCurrentUserPassword().length() < 6 || getNewUserPassword().length() < 6 || getVerifyUserPassword().length() < 6) {
            showAlertDialog(getString(R.string.error_incorrect_pass), true);
        } else if(!getCurrentUserPassword().equals(getCurrentPassword())){
            showAlertDialog(getString(R.string.error_incorrect_current_pass), true);
        } else if(!getNewUserPassword().equals(getVerifyUserPassword())) {
            showAlertDialog(getString(R.string.error_incorrect_verify_pass), true);
        } else{
            handleChangePassword(getCurrentUserPassword(), getNewUserPassword());
        }
    }

    public void showAlertDialog(String message, boolean isCancelable) {
        dialog = Config.buildAlertDialog(getString(R.string.login_error_title), message, isCancelable, getActivity());
        dialog.show();
    }

    public void onChangePasswordSuccess() {
        dialog.setMessage("Change password successfully!");
        txtCurrentPassword.setText("");
        txtNewPassword.setText("");
        txtVerifyPassword.setText("");
    }

    public void onChangePasswordFailed() {
        showAlertDialog(getString(R.string.error_change_password_failed), true);
    }

    @NonNull
    private String getCurrentUserPassword() {
        return txtCurrentPassword.getText().toString().trim();
    }

    @NonNull
    private String getNewUserPassword() {
        return txtNewPassword.getText().toString().trim();
    }

    @NonNull
    private String getVerifyUserPassword() {
        return txtVerifyPassword.getText().toString().trim();
    }

    private String getCurrentPassword() {
        SharedPreferences preferences = getActivity().getSharedPreferences(Config.KEY_USER_INFO, Context.MODE_PRIVATE);
        return preferences.getString(Config.KEY_PASSWORD, "");
    }

    public void handleChangePassword(String currentPassword, String newPassword) {
        showAlertDialog("Registering...", true);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    onChangePasswordSuccess();
                }
                else{
                    onChangePasswordFailed();
                }
            }
        });
    }
}
