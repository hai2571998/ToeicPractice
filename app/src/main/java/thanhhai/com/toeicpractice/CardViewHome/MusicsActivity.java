package thanhhai.com.toeicpractice.CardViewHome;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

import thanhhai.com.toeicpractice.Database.Musics;
import thanhhai.com.toeicpractice.Database.SQLiteDatasource;
import thanhhai.com.toeicpractice.R;

public class MusicsActivity extends AppCompatActivity {
    ListView list_music;
    private SQLiteDatasource datasource;
    private ArrayList<Musics> listMusics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musics);
        datasource = new SQLiteDatasource(this);
        list_music = (ListView) findViewById(R.id.list_music);
        listMusics = datasource.getSong();
        MusicAdapter adapter = new MusicAdapter(MusicsActivity.this, R.layout.item_list_music, listMusics);
        list_music.setAdapter(adapter);
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
