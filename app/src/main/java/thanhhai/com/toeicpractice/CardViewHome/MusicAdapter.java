package thanhhai.com.toeicpractice.CardViewHome;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.io.IOException;
import java.util.List;

import thanhhai.com.toeicpractice.Database.Musics;
import thanhhai.com.toeicpractice.R;

public class MusicAdapter extends ArrayAdapter<Musics> {

    private MediaPlayer mediaPlayer = null;
    private int selected = -1;

    public MusicAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public MusicAdapter(Context context, int resource, List<Musics> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View v = convertView;

        final double[] startTime = {0};
        final double finalTime = 0;
        final SeekBar seekBar = null;
        final Handler myHandler = new Handler();

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.item_list_music, null);
        }
        final Musics p = getItem(position);
        if (p != null) {
            TextView title = (TextView) v.findViewById(R.id.titlemusic);
            title.setText(p.get_author());
            TextView name = (TextView) v.findViewById(R.id.displayname);
            name.setText(p.get_song());

            final ToggleButton im1 = (ToggleButton) v.findViewById(R.id.btnSongPlay);
            im1.setChecked(position == this.selected);
            im1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (im1.isChecked()) {
                        if(mediaPlayer != null && mediaPlayer.isPlaying()) try {
                            mediaPlayer.stop();
                            mediaPlayer.release();
                        } catch (Exception e) {}

                        try {
                            mediaPlayer = new MediaPlayer();
                            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                            mediaPlayer.setDataSource(p.get_urlsong().toString());
                            mediaPlayer.prepare();
                            mediaPlayer.start();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    if(selected != -1 && selected != position) {
                        notifyDataSetChanged();
                    }
                    selected = im1.isChecked() ? position : -1;
                }
            });
        }
        return v;
    }
}