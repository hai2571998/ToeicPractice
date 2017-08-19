package thanhhai.com.toeicpractice.HomeModel;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import thanhhai.com.toeicpractice.Database.Musics;
import thanhhai.com.toeicpractice.Database.SQLiteDatasource;
import thanhhai.com.toeicpractice.R;

public class MusicsActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener {
    private SQLiteDatasource datasource;
    private ArrayList<Musics> listMusics;
    private int mediaFileLengthInMilliseconds; // this value contains the song duration in milliseconds. Look at getDuration() method in MediaPlayer class

    private final Handler handler = new Handler();

    TextView txtTitle, txtTimeSong, txtTimeTotal;
    ImageButton btnPlaySong, btnPreSong, btnStopSong, btnNextSong;
    SeekBar seekBarSong;
    ImageView imgDisc;
    int position = 0;
    MediaPlayer mediaPlayer;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musics);
        addControls();
        addSong();
        addEvents();
        animation = AnimationUtils.loadAnimation(this, R.anim.disc_rotate);
    }

    private void addEvents() {
        mediaPlayer = new MediaPlayer();
        initializeMedia();
        btnPlaySong.setOnClickListener(this);
        seekBarSong.setMax(99);
        seekBarSong.setOnTouchListener(this);
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnCompletionListener(this);

        btnStopSong.setOnClickListener(this);
        btnNextSong.setOnClickListener(this);
    }

    private void addSong() {
        listMusics = new ArrayList<Musics>();
        datasource = new SQLiteDatasource(this);
        listMusics = datasource.getSong();
    }

    private void addControls() {
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtTimeSong = (TextView) findViewById(R.id.txtTimeSong);
        txtTimeTotal = (TextView) findViewById(R.id.txtTimeTotal);
        btnPlaySong = (ImageButton) findViewById(R.id.btnPlaySong);
        btnNextSong = (ImageButton) findViewById(R.id.btnNextSong);
        btnPreSong = (ImageButton) findViewById(R.id.btnPreSong);
        btnStopSong = (ImageButton) findViewById(R.id.btnStopSong);
        seekBarSong = (SeekBar) findViewById(R.id.seekBarSong);
        imgDisc = (ImageView) findViewById(R.id.imgDisc);
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int i) {
        seekBarSong.setSecondaryProgress(i);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        btnPlaySong.setImageResource(android.R.drawable.ic_media_play);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnPlaySong){
            //mediaFileLengthInMilliseconds = mediaPlayer.getDuration(); // gets the song length in milliseconds from URL

            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                btnPlaySong.setImageResource(android.R.drawable.ic_media_play);
            } else {
                mediaPlayer.start();
                btnPlaySong.setImageResource(android.R.drawable.ic_media_pause);
            }

            //primarySeekBarProgressUpdater();
        }
        if(view.getId() == R.id.btnNextSong){
            position++;
            if (position > listMusics.size() - 1) {
                position = 0;
            }
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            initializeMedia();
            mediaPlayer.start();
            btnPlaySong.setImageResource(android.R.drawable.ic_media_pause);
        }
    }

    private void initializeMedia() {
        try {
            mediaPlayer.setDataSource(listMusics.get(position).get_urlsong().toString());
            mediaPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(view.getId() == R.id.seekBarSong){
            /** Seekbar onTouch event handler. Method which seeks MediaPlayer to seekBar primary progress position*/
            if(mediaPlayer.isPlaying()){
                SeekBar sb = (SeekBar)view;
                int playPositionInMillisecconds = (mediaFileLengthInMilliseconds / 100) * sb.getProgress();
                mediaPlayer.seekTo(playPositionInMillisecconds);
            }
        }
        return false;
    }

    private void primarySeekBarProgressUpdater() {
        seekBarSong.setProgress((int)(((float)mediaPlayer.getCurrentPosition()/mediaFileLengthInMilliseconds)*100)); // This math construction give a percentage of "was playing"/"song length"
        if (mediaPlayer.isPlaying()) {
            Runnable notification = new Runnable() {
                public void run() {
                    primarySeekBarProgressUpdater();
                }
            };
            handler.postDelayed(notification,1000);
        }
    }
}
