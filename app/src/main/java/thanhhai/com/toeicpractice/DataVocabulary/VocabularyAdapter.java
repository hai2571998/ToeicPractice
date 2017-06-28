package thanhhai.com.toeicpractice.DataVocabulary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import thanhhai.com.toeicpractice.R;

public class VocabularyAdapter  extends ArrayAdapter<Vocabulaies> {
    private File cacheDir;
    private File tempMp3;

    public VocabularyAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public VocabularyAdapter(Context context, int resource, List<Vocabulaies> items) {
        super(context, resource, items);
    }


    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.item_vocabulary, null);
        }

        final Vocabulaies p = getItem(position);

        if (p != null) {
            TextView txtVocabulary = (TextView) v.findViewById(R.id.title_vocabulary);
            txtVocabulary.setText(p.get_voc());

            TextView txtPhienAm = (TextView) v.findViewById(R.id.txtPhienAm);
            txtPhienAm.setText(p.get_phienAm());

            TextView txtType = (TextView) v.findViewById(R.id.txtType);
            txtType.setText(p.get_type());

            TextView txtMean = (TextView) v.findViewById(R.id.txtMean);
            txtMean.setText(p.get_mean());

            ImageView im1 = (ImageView) v.findViewById(R.id.image_vocabulary);
            Bitmap bitmap = BitmapFactory.decodeByteArray(p.get_avt(),0,p.get_avt().length);
            im1.setImageBitmap(bitmap);

            ImageButton audio = (ImageButton)v.findViewById(R.id.audio);
            audio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playMp3FromByte(p.getMediaPlayer());
                }
            });
        }
        return v;
    }
    private void playMp3FromByte(byte[] mp3SoundByteArray) {
        try {
            tempMp3 = File.createTempFile("kurchina", "mp3",getCacheDir());
            tempMp3.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(tempMp3);
            fos.write(mp3SoundByteArray);
            fos.close();

            MediaPlayer mediaPlayer = new MediaPlayer();

            FileInputStream fis = new FileInputStream(tempMp3);
            mediaPlayer.setDataSource(fis.getFD());

            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException ex) {
            String s = ex.toString();
            ex.printStackTrace();
        }
    }

    public File getCacheDir() {
        return cacheDir;
    }
}