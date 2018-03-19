package thanhhai.com.toeicpractice.HomeModel;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import thanhhai.com.toeicpractice.R;

public class QuestionActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private ArrayList<PushQuestion> arrayList;
    private QuestionOnlineAdapter questionOnlineAdapter;
    private RecyclerView recyclerView;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        setTitle("Question Online");
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_question);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<PushQuestion>();
        questionOnlineAdapter = new QuestionOnlineAdapter(arrayList, getApplicationContext());
        recyclerView.setAdapter(questionOnlineAdapter);
        new QuestionTask().execute();
    }

    private void updateData() {
        databaseReference.child("question").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                PushQuestion pushQuestion = dataSnapshot.getValue(PushQuestion.class);
                arrayList.add(new PushQuestion(pushQuestion.getQuestion(), pushQuestion.getAnswer_A(),pushQuestion.getAnswer_B(), pushQuestion.getAnswer_C(),pushQuestion.getAnswer_D(),pushQuestion.getTotalA(),pushQuestion.getTotalB(),pushQuestion.getTotalC(),pushQuestion.getTotalD()));
                questionOnlineAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private class QuestionTask extends AsyncTask<String,Void,Void>{

        @Override
        protected Void doInBackground(String... strings) {
            try{
                updateData();
            }catch (Exception ex){
                Log.e("LOI", ex.toString());
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}