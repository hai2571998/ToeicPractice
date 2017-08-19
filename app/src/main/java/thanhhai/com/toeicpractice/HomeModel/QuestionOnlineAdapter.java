package thanhhai.com.toeicpractice.HomeModel;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import thanhhai.com.toeicpractice.R;

import static android.content.ContentValues.TAG;
import static thanhhai.com.toeicpractice.R.id.chart;

public class QuestionOnlineAdapter extends RecyclerView.Adapter<QuestionOnlineAdapter.ViewHolder> {
    private ArrayList<PushQuestion> listQuestions;
    private Context context;
    private DatabaseReference databaseReference;
    final private String QUESTION = "question";
    private List<String> mKeys = new ArrayList<>();

    private int totalA, totalB, totalC, totalD;

    public QuestionOnlineAdapter(ArrayList<PushQuestion> listQuestions, Context context) {
        this.listQuestions = listQuestions;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_question, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final PushQuestion question = listQuestions.get(position);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        holder.txtQuestion.setText("Câu " + (position + 1) + ": " + question.getQuestion());
        holder.rad_answer_a.setText(question.getAnswer_A());
        holder.rad_answer_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                totalA = question.getTotalA() + 1;
                databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child(QUESTION).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            String key = childSnapshot.getKey();
                            mKeys.add(key);
                        }
                        String path = "/" + dataSnapshot.getKey() + "/" + mKeys.get(position);
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
                showDialog(position, view);

            }
        });

        holder.rad_answer_b.setText(question.getAnswer_B());
        holder.rad_answer_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                totalB = question.getTotalB() + 1;
                databaseReference.child(QUESTION).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            String key = childSnapshot.getKey();
                            mKeys.add(key);
                        }
                        String path = "/" + dataSnapshot.getKey() + "/" + mKeys.get(position);
                        Log.e("path", path);
                        HashMap<String, Object> result = new HashMap<>();
                        result.put("totalB", totalB);
                        databaseReference.child(path).updateChildren(result);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, ">>> Error:" + "find onCancelled:" + databaseError);
                    }
                });
                showDialog(position, view);
            }
        });

        holder.rad_answer_c.setText("C. " + question.getAnswer_C());
        holder.rad_answer_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                totalC = question.getTotalC() + 1;
                databaseReference.child(QUESTION).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            String key = childSnapshot.getKey();
                            mKeys.add(key);
                        }
                        String path = "/" + dataSnapshot.getKey() + "/" + mKeys.get(position);
                        Log.e("path", path);
                        HashMap<String, Object> result = new HashMap<>();
                        result.put("totalC", totalC);
                        databaseReference.child(path).updateChildren(result);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, ">>> Error:" + "find onCancelled:" + databaseError);
                    }
                });
                showDialog(position, view);
            }
        });

        holder.rad_answer_d.setText(question.getAnswer_D());
        holder.rad_answer_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                totalD = question.getTotalD() + 1;
                databaseReference.child(QUESTION).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            String key = childSnapshot.getKey();
                            mKeys.add(key);
                        }
                        String path = "/" + dataSnapshot.getKey() + "/" + mKeys.get(position);
                        Log.e("path", path);
                        HashMap<String, Object> result = new HashMap<>();
                        result.put("totalD", totalD);
                        databaseReference.child(path).updateChildren(result);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, ">>> Error:" + "find onCancelled:" + databaseError);
                    }
                });
                showDialog(position, view);
            }
        });
    }


    @Override
    public int getItemCount() {
        return listQuestions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtQuestion;
        RadioButton rad_answer_a, rad_answer_b, rad_answer_c, rad_answer_d;

        public ViewHolder(final View itemView) {
            super(itemView);
            txtQuestion = (TextView) itemView.findViewById(R.id.txtQuestion);
            rad_answer_a = (RadioButton) itemView.findViewById(R.id.rad_ans_a);
            rad_answer_b = (RadioButton) itemView.findViewById(R.id.rad_ans_b);
            rad_answer_c = (RadioButton) itemView.findViewById(R.id.rad_ans_c);
            rad_answer_d = (RadioButton) itemView.findViewById(R.id.rad_ans_d);
        }
    }

    private ArrayList getDataSet(int position) {
        final PushQuestion question = listQuestions.get(position);
        ArrayList dataSets = null;
        ArrayList valueSet1 = new ArrayList<>();
        BarEntry v1e1 = new BarEntry(question.getTotalA(), 3);
        valueSet1.add(v1e1);
        BarEntry v1e2 = new BarEntry(question.getTotalB(), 2);
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry(question.getTotalC(), 1);
        valueSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry(question.getTotalD(), 0);
        valueSet1.add(v1e4);

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Answer");
        barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        return dataSets;
    }

    private ArrayList getXAxisValues() {
        ArrayList xAxis = new ArrayList<>();
        xAxis.add("Câu D");
        xAxis.add("Câu C");
        xAxis.add("Câu B");
        xAxis.add("Câu A");
        return xAxis;
    }

    public class MyValueFormatter implements ValueFormatter {
        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            return Math.round(value) + ""; // e.g. append a dollar-sign
        }
    }

    private void showDialog(int vitri, View view) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(view.getContext());
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.dialog_chart_result, null);
        dialogBuilder.setView(dialogView);

        final AlertDialog alertDialog = dialogBuilder.create();
        Window window = alertDialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);

        HorizontalBarChart barChart = (HorizontalBarChart) dialogView.findViewById(chart);
        BarData data = new BarData(getXAxisValues(), getDataSet(vitri));
        data.setValueFormatter(new MyValueFormatter());
        barChart.setData(data);
        data.setValueTextSize(14);
        barChart.getXAxis().setEnabled(true);// hides horizontal grid lines inside chart
        barChart.getXAxis().setTextSize(14);
        YAxis leftAxis = barChart.getAxisLeft();
        barChart.getAxisRight().setEnabled(false); // hides horizontal grid lines with below line
        leftAxis.setEnabled(false); // hides vertical grid lines inside chart
        barChart.animateXY(2000, 2000); // for animating reviews display
        barChart.invalidate();
        barChart.setClickable(false);
        barChart.setDescription(""); // Hide the description
        barChart.getLegend().setEnabled(false);
        barChart.setDoubleTapToZoomEnabled(false);
        barChart.setPinchZoom(false);
        leftAxis.setDrawLabels(true);
        barChart.getXAxis().setDrawGridLines(false);
        alertDialog.show();
    }
}