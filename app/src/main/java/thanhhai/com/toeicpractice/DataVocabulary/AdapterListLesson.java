package thanhhai.com.toeicpractice.DataVocabulary;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import thanhhai.com.toeicpractice.R;

import static android.support.v4.content.ContextCompat.startActivities;

public class AdapterListLesson extends ArrayAdapter<Lessons> {
    public AdapterListLesson(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public AdapterListLesson(Context context, int resource, List<Lessons> items) {
        super(context, resource, items);
    }


    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.item_lesson, null);
        }

        Lessons p = getItem(position);

        if (p != null) {
            TextView txtLesson = (TextView) v.findViewById(R.id.txtLesson);
            txtLesson.setText("Lesson " + p.getNumLesson() + ": ");
            TextView txtTitle = (TextView) v.findViewById(R.id.title);
            txtTitle.setText(p.getTitleLesson());
            ImageView imgHinh = (ImageView) v.findViewById(R.id.list_image);
            imgHinh.setImageResource(p.getAvatar());
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int ps = position + 1;
                    Intent intent0 = new Intent(v.getContext(), VocabularyActivity.class);
                    intent0.putExtra("ps",ps);
                    startActivities(v.getContext(), new Intent[]{intent0});
                }
            });
        }
        return v;
    }
}
