package thanhhai.com.toeicpractice.HomeModel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.List;

import thanhhai.com.toeicpractice.Database.SQLiteDatasource;
import thanhhai.com.toeicpractice.R;
import thanhhai.com.toeicpractice.ViewPager.VocabularySlideActivity;

public class TestVocabularyActivity extends AppCompatActivity {

    private Button btnKiemTraTu;
    private Spinner spLessionVocabulary;
    private SQLiteDatasource datasource;
    private List<String> lables;
    private ArrayAdapter<String> dataAdapter;
    private int vitri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_vocabulary);
        setTitle("Test Vocabulary");
        addControls();
        addEvents();
    }

    private void addControls() {
        datasource = new SQLiteDatasource(getApplicationContext());
        btnKiemTraTu = (Button) findViewById(R.id.btnKiemTraTu);
        spLessionVocabulary = (Spinner) findViewById(R.id.spLessionVocabulary);
        lables = datasource.getAllLabels();
        dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lables);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLessionVocabulary.setAdapter(dataAdapter);
    }

    private void addEvents() {
        spLessionVocabulary.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                vitri = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnKiemTraTu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TestVocabularyActivity.this, VocabularySlideActivity.class);
                intent.putExtra("num_lession", vitri + 1);
                startActivity(intent);
            }
        });
    }
}
