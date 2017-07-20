package thanhhai.com.toeicpractice.DataVocabulary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

import thanhhai.com.toeicpractice.Database.SQLiteDatasource;
import thanhhai.com.toeicpractice.R;

public class VocabularyActivity extends AppCompatActivity {
    ListView listViewVoc;
    private SQLiteDatasource datasource;
    private ArrayList<Vocabulaies> listVocs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary);
        datasource = new SQLiteDatasource(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listViewVoc = (ListView) findViewById(R.id.list_vocabulary);

        Intent intent = getIntent();

        if (intent != null) {
            int ps = intent.getIntExtra("ps", -1);
            Log.d("Yasuo", ps + "");
            if (ps != -1) {
                listVocs = datasource.getVocInLesson(ps);
                this.setTitle("Lesson " + ps);
            }
        }
        VocabularyAdapter adapter = new VocabularyAdapter(getApplicationContext(), R.layout.item_vocabulary, listVocs);
        listViewVoc.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}