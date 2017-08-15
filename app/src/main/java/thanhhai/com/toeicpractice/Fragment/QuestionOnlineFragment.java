package thanhhai.com.toeicpractice.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import thanhhai.com.toeicpractice.HomeModel.PushQuestion;
import thanhhai.com.toeicpractice.R;


public class QuestionOnlineFragment extends Fragment {
    private EditText txtCauHoi, txtAnswer_A, txtAnswer_B, txtAnswer_C, txtAnswer_D;
    private Button btnPushQuestion;
    private String valCauHoi, valAnswerA, valAnswerB, valAnswerC, valAnswerD;

    public QuestionOnlineFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question_online, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtCauHoi = (EditText) getActivity().findViewById(R.id.txtCauHoi);
        txtAnswer_A = (EditText) getActivity().findViewById(R.id.txtAnswer_A);
        txtAnswer_B = (EditText) getActivity().findViewById(R.id.txtAnswer_B);
        txtAnswer_C = (EditText) getActivity().findViewById(R.id.txtAnswer_C);
        txtAnswer_D = (EditText) getActivity().findViewById(R.id.txtAnswer_D);
        btnPushQuestion = (Button) getActivity().findViewById(R.id.btnPushQuestion);
        btnPushQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valCauHoi = txtCauHoi.getText().toString();
                valAnswerA = txtAnswer_A.getText().toString();
                valAnswerB = txtAnswer_B.getText().toString();
                valAnswerC = txtAnswer_C.getText().toString();
                valAnswerD = txtAnswer_D.getText().toString();

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

                if (!valCauHoi.equals("") && !valAnswerA.equals("") && !valAnswerB.equals("") && !txtAnswer_C.equals("") && !valAnswerD.equals("")) {
                    Log.e("val", valCauHoi+valAnswerA+valAnswerB+valAnswerC+valAnswerD);
                    PushQuestion pushQuestion = new PushQuestion(valCauHoi, valAnswerA, valAnswerB, valAnswerC, valAnswerD, 0, 0, 0, 0);
                    ref.child("question").push().setValue(pushQuestion, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            Toast.makeText(getContext(), "Câu hỏi của bạn đã gửi đến người hỗ trợ.", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "Bạn vui lòng nhập đầy đủ thông tin câu hỏi, không được bỏ trống!!!", Toast.LENGTH_SHORT).show();
                }
                txtCauHoi.setText("");
                txtAnswer_A.setText("");
                txtAnswer_B.setText("");
                txtAnswer_C.setText("");
                txtAnswer_D.setText("");
            }
        });
    }
}
