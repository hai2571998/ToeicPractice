package thanhhai.com.toeicpractice.CheckAnswer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import thanhhai.com.toeicpractice.DataVocabulary.Vocabulaies;
import thanhhai.com.toeicpractice.R;

public class CheckVocabularyAdapter extends BaseAdapter {

    ArrayList lsData;
    LayoutInflater inflater;

    public CheckVocabularyAdapter(ArrayList lsData, Context context) {
        this.lsData = lsData;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return lsData.size();
    }

    @Override
    public Object getItem(int position) {
        return lsData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Vocabulaies data = (Vocabulaies) getItem(position);
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.item_vocabulary_question, null);
            holder.txtAnswerVocabulary = (TextView) view.findViewById(R.id.txtAnswerVocabulary);
            holder.txtQuestionNumber = (TextView) view.findViewById(R.id.txtQuestionNumber);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        int i = position + 1;
        holder.txtQuestionNumber.setText("Câu " + i + ": ");
        holder.txtAnswerVocabulary.setText(data.getAnswer()); // lấy cau tra loi hien thi len
        return view;
    }

    private static class ViewHolder {
        TextView txtQuestionNumber, txtAnswerVocabulary;
    }
}
