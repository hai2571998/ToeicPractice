package thanhhai.com.toeicpractice.CardViewHome;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import thanhhai.com.toeicpractice.R;

import static android.content.ContentValues.TAG;

public class QuestionOnlineAdapter extends RecyclerView.Adapter<QuestionOnlineAdapter.ViewHolder> {
    private ArrayList<PushQuestion> listQuestions;
    private Context context;
    private DatabaseReference databaseReference;
    final private String QUESTION = "question";

    public QuestionOnlineAdapter(ArrayList<PushQuestion> listQuestions, Context context) {
        this.listQuestions = listQuestions;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_question,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final PushQuestion question = listQuestions.get(position);
        holder.txtQuestion.setText(question.getQuestion());
        holder.rad_answer_a.setText("A. "+question.getAnswer_A());
        holder.rad_answer_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int totalA = question.getTotalA()+1;
                databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child(QUESTION).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        DataSnapshot nodeDataSnapshot = dataSnapshot.getChildren().iterator().next();
                        String key = nodeDataSnapshot.getKey();
                        Log.e("key", key);
                        String path = "/" + dataSnapshot.getKey() + "/" + key;
                        Log.e("path", path);
                        HashMap<String, Object> result = new HashMap<>();
                        result.put("totalA", totalA);
                        databaseReference.child(path).updateChildren(result);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, ">>> Error:" + "find onCancelled:" + databaseError);
                    }
                });


                //String postId = databaseReference.getKey();
                //databaseReference.child("question").getKey();
                //Log.e("getKey",postId.toString());
                //databaseReference.child("question").child("-KppZMkMSkp8TRcbWIN0").child("totalA").setValue(totalA);

            }
        });

        holder.rad_answer_b.setText("B. "+question.getAnswer_B());
        holder.rad_answer_c.setText("C. "+question.getAnswer_C());
        holder.rad_answer_d.setText("D. "+question.getAnswer_D());
    }

    @Override
    public int getItemCount() {
        return listQuestions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtQuestion;
        RadioButton rad_answer_a, rad_answer_b, rad_answer_c, rad_answer_d;
        public ViewHolder(View itemView) {
            super(itemView);
            txtQuestion = (TextView) itemView.findViewById(R.id.txtQuestion);
            rad_answer_a = (RadioButton) itemView.findViewById(R.id.rad_ans_a);
            rad_answer_b = (RadioButton) itemView.findViewById(R.id.rad_ans_b);
            rad_answer_c = (RadioButton) itemView.findViewById(R.id.rad_ans_c);
            rad_answer_d = (RadioButton) itemView.findViewById(R.id.rad_ans_d);
        }
    }
}