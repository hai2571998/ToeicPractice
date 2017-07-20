package thanhhai.com.toeicpractice.Fragment;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;

import thanhhai.com.toeicpractice.Database.Feedback;
import thanhhai.com.toeicpractice.FCM.FirebaseIDTask;
import thanhhai.com.toeicpractice.R;

import static android.content.Context.MODE_PRIVATE;

public class SettingFragment extends Fragment {

    private CardView cw_version,cw_notification,cw_rate, cw_about;
    private Switch mySwitch;
    private Dialog rankDialog;
    private RatingBar ratingBar;
    private EditText txtFeedback, txtEmail;

    private String token;
    private SharedPreferences sharedPreferences;
    private DatabaseReference databaseReference;

    public static SettingFragment newInstance() {
        SettingFragment settingFragment = new SettingFragment();
        return settingFragment;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cw_version = (CardView) getActivity().findViewById(R.id.cw_version);
        cw_version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String version = "✔ <b>Version</b>"+"<br>Application Version: 1.0.0<br><br>✔ <b>Author Information</b><br>In the process of processing the data can not avoid lack of mercy.<br>Hope you guys be able to share and comment with the development team to make application better and more useful for the community.";
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setIcon(R.drawable.version);
                builder.setTitle("Version Update");
                builder.setMessage(Html.fromHtml(version));
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });

        cw_notification = (CardView) getActivity().findViewById(R.id.cw_notification);
        mySwitch = (Switch) getActivity().findViewById(R.id.mySwitch);
        sharedPreferences = getActivity().getSharedPreferences("dataNotification", MODE_PRIVATE);

        mySwitch.setChecked(sharedPreferences.getBoolean("checked", true));
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("checked", true);
                    editor.commit();
                    FirebaseMessaging.getInstance().subscribeToTopic("testfcm");
                    token = FirebaseInstanceId.getInstance().getToken();
                    new FirebaseIDTask().execute(token);
                } else {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("checked", false);
                    editor.commit();
                    new DeleteTokenTask().execute();
                }

            }
        });
        cw_rate = (CardView) getActivity().findViewById(R.id.cw_rate);
        cw_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rankDialog = new Dialog(getContext());
                rankDialog.setContentView(R.layout.rank_dialog);
                rankDialog.setCancelable(true);
                ratingBar = (RatingBar)rankDialog.findViewById(R.id.dialog_ratingbar);
                txtFeedback = (EditText) rankDialog.findViewById(R.id.txtFeedback);
                txtEmail = (EditText) rankDialog.findViewById(R.id.txtEmail);
                ratingBar.setRating(ratingBar.getRating());
                Button updateButton = (Button) rankDialog.findViewById(R.id.rank_dialog_button);
                updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String valEmail = txtEmail.getText().toString();
                        String valRating = String.valueOf(ratingBar.getRating());
                        String valFeedback = txtFeedback.getText().toString();
                        databaseReference = FirebaseDatabase.getInstance().getReference();
                        Feedback feedback = new Feedback(valFeedback, valRating, valEmail);
                        databaseReference.child("feedback").push().setValue(feedback, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                if(databaseError == null){
                                    Toast.makeText(getContext(),"Feedback & Rate đã chuyển đến Admin. Cảm ơn sự đống góp của bạn", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(),"Vui lòng kiểm tra lại Internet", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        rankDialog.dismiss();
                    }
                });
                rankDialog.show();
            }
        });

        cw_about = (CardView) getActivity().findViewById(R.id.cw_about);
        cw_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String about = "✔ <b>Deverloper:</b> Nguyễn Thanh Hải<br>✔ <b>Designer</b>: Nguyễn Mạnh Hùng<br>✔<b>Support</b>: Quân Mạc Tiếu <br>Một số thành viên khác";
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setIcon(R.drawable.about);
                builder.setTitle("About Us");
                builder.setMessage(Html.fromHtml(about));
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
    }

    private class DeleteTokenTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                FirebaseInstanceId.getInstance().deleteInstanceId();
            } catch (IOException e) {
                Log.e("LOI DeleteTokenTask", e.toString());
            }
            return null;
        }
    }
}
