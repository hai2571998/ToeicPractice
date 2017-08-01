package thanhhai.com.toeicpractice.CardViewHome;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_question);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<PushQuestion>();
        questionOnlineAdapter = new QuestionOnlineAdapter(arrayList, getApplicationContext());
        recyclerView.setAdapter(questionOnlineAdapter);
        updateData();
        questionOnlineAdapter.notifyDataSetChanged();
    }

    private void updateData() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
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
}