package thanhhai.com.toeicpractice.ViewPager;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import thanhhai.com.toeicpractice.DataVocabulary.Vocabulaies;
import thanhhai.com.toeicpractice.R;

public class TestVocabularyFragment extends Fragment {

    private ArrayList<Vocabulaies> arr_vocabulary;
    public static final String ARG_PAPE = "PageVocabulary";
    public static final String ARG_CHECKANSWER = "checkAnswer";
    private int mPageNumber;
    private int checkAns;
    private TextView txtNumQuestion, txtMean, txtTypeWord, txtKQ, txtGiaiThich_Vocabulary, txtMyAnswer_Vocabulary;
    private ImageButton btnPlayWord;
    private EditText txtNhapDapAn;
    private ImageView imgVocabulary;
    private File cacheDir;
    private File tempMp3;
    private String DapAn = "";
    private ImageView imgRightorWrong;

    public TestVocabularyFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_test_vocabulary_page, container, false);
        txtMean = (TextView) rootView.findViewById(R.id.txtMean);
        txtNumQuestion = (TextView) rootView.findViewById(R.id.txtNumQuestion);
        txtTypeWord = (TextView) rootView.findViewById(R.id.txtTypeWord);
        btnPlayWord = (ImageButton) rootView.findViewById(R.id.btnPlayWord);
        imgVocabulary = (ImageView) rootView.findViewById(R.id.imgVocabulary);
        txtNhapDapAn = (EditText) rootView.findViewById(R.id.txtNhapDapAn);
        txtKQ = (TextView) rootView.findViewById(R.id.txtKQ);
        txtGiaiThich_Vocabulary = (TextView) rootView.findViewById(R.id.txtGiaiThich_Vocabulary);
        txtMyAnswer_Vocabulary = (TextView) rootView.findViewById(R.id.txtMyAnswer_Vocabulary);
        imgRightorWrong = (ImageView) rootView.findViewById(R.id.imgRightorWrong);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        arr_vocabulary = new ArrayList<Vocabulaies>();
        VocabularySlideActivity vocabularySlideActivity = (VocabularySlideActivity) getActivity();
        arr_vocabulary = vocabularySlideActivity.getDataVocabulary();
        mPageNumber = getArguments().getInt(ARG_PAPE);
        checkAns = getArguments().getInt(ARG_CHECKANSWER);
    }

    public static TestVocabularyFragment createPage(int papeNumber, int checkAnswer) {
        TestVocabularyFragment fragment = new TestVocabularyFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_PAPE, papeNumber);
        bundle.putInt(ARG_CHECKANSWER, checkAnswer);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        txtNumQuestion.setText("Question " + (mPageNumber + 1));
        txtMean.setText(getItem(mPageNumber).get_mean());
        txtTypeWord.setText(getItem(mPageNumber).get_type());
        Bitmap bitmap = BitmapFactory.decodeByteArray(getItem(mPageNumber).get_avt(), 0, getItem(mPageNumber).get_avt().length);
        imgVocabulary.setImageBitmap(bitmap);
        btnPlayWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playMp3FromByte(getItem(mPageNumber).getMediaPlayer());
            }
        });

        if (checkAns != 0) {
            checkAnswer(getItem(mPageNumber).get_voc().toString());
        }

        txtNhapDapAn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                DapAn = txtNhapDapAn.getText().toString();
                getItem(mPageNumber).setAnswer(DapAn);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public Vocabulaies getItem(int position) {
        return arr_vocabulary.get(position);
    }

    private void playMp3FromByte(byte[] mp3SoundByteArray) {
        try {
            tempMp3 = File.createTempFile("kurchina", "mp3", getCacheDir());
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

    private void checkAnswer(String result) {
        DapAn = getItem(mPageNumber).getAnswer();
        txtMyAnswer_Vocabulary.setVisibility(View.GONE);
        txtNhapDapAn.setVisibility(View.GONE);
        txtKQ.setVisibility(View.VISIBLE);
        txtGiaiThich_Vocabulary.setVisibility(View.VISIBLE);
        imgRightorWrong.setVisibility(View.VISIBLE);
        if (result.equals(DapAn)) {
            imgRightorWrong.setImageResource(R.drawable.right);
            txtKQ.setText("Đán án của đúng " + result + " trùng khời của đán án của bạn " + DapAn);
        } else {
            imgRightorWrong.setImageResource(R.drawable.wrong);
            if (DapAn.equals("")) {
                txtKQ.setText("Đán án của đúng " + result + ". Bạn không nên để trống");
            } else {
                txtKQ.setText("Đán án của đúng " + result + " không trùng khời của đán án của bạn " + DapAn);
            }
        }
    }
}
