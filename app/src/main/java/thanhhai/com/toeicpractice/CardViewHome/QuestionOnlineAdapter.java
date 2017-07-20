package thanhhai.com.toeicpractice.CardViewHome;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import thanhhai.com.toeicpractice.R;

public class QuestionOnlineAdapter extends RecyclerView.Adapter<QuestionOnlineAdapter.ViewHolder> {
    private ArrayList<PushQuestion> listQuestions;
    private Context context;

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
    public void onBindViewHolder(ViewHolder holder, int position) {
        PushQuestion question = listQuestions.get(position);
        holder.txtQuestion.setText(question.getQuestion());
        holder.rad_answer_a.setText(question.getAnswer_A());
        holder.rad_answer_b.setText(question.getAnswer_B());
        holder.rad_answer_c.setText(question.getAnswer_C());
        holder.rad_answer_d.setText(question.getAnswer_D());
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
