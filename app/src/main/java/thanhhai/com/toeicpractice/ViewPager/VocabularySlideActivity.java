package thanhhai.com.toeicpractice.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import thanhhai.com.toeicpractice.CheckAnswer.CheckVocabularyAdapter;
import thanhhai.com.toeicpractice.DataVocabulary.Vocabulaies;
import thanhhai.com.toeicpractice.Database.SQLiteDatasource;
import thanhhai.com.toeicpractice.R;

public class VocabularySlideActivity extends FragmentActivity {
    private static final int NUM_PAGES = 12;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    private SQLiteDatasource datasource;
    private ArrayList<Vocabulaies> arr_vocabulary;

    private TextView txtKiemTra, txtTimer, txtScore;
    private Button cancel_btn, finish_btn, btnExit_Vocabulary;
    private int totalTimer;
    private CounterClass timer;
    private int num_lession;
    private int checkAns = 0;

    private CheckVocabularyAdapter vocabularyAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary_slide);
        mPager = (ViewPager) findViewById(R.id.VocabularyPager);
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mPagerAdapter = new VocabularySlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        final Intent intent = getIntent();
        num_lession = intent.getIntExtra("num_lession", 0);

        datasource = new SQLiteDatasource(this);
        arr_vocabulary = new ArrayList<Vocabulaies>();
        arr_vocabulary = datasource.getVocInLesson(num_lession);

        txtTimer = (TextView) findViewById(R.id.txtTimer);
        totalTimer = 5;
        timer = new CounterClass(totalTimer * 60 * 1000, 1000);
        timer.start();

        txtKiemTra = (TextView) findViewById(R.id.txtKiemTra);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(VocabularySlideActivity.this, R.style.DialogTheme);
        LayoutInflater inflater = VocabularySlideActivity.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.customdialogwithlist_layout, null);
        dialogBuilder.setView(dialogView);

        vocabularyAdapter = new CheckVocabularyAdapter(arr_vocabulary, this);
        final ListView lvVocabulary = (ListView) dialogView.findViewById(R.id.lvVocabulary);
        lvVocabulary.setAdapter(vocabularyAdapter);

        final AlertDialog alertDialog = dialogBuilder.create();
        Window window = alertDialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);

        txtKiemTra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vocabularyAdapter.notifyDataSetChanged();
                alertDialog.show();
            }
        });

        lvVocabulary.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                mPager.setCurrentItem(position);
                alertDialog.dismiss();
            }
        });

        cancel_btn = (Button) dialogView.findViewById(R.id.btnCancelList);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.hide();
            }
        });

        finish_btn = (Button) dialogView.findViewById(R.id.btnFinishlList);
        finish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.cancel();
                result();
                alertDialog.dismiss();
            }
        });

        btnExit_Vocabulary = (Button) dialogView.findViewById(R.id.btnExit_Vocabulary);
        btnExit_Vocabulary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(VocabularySlideActivity.this);
                builder.setIcon(R.drawable.exit);
                builder.setTitle("Notification");
                builder.setMessage("Do you want to exit?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                        finish();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();
            }
        });
    }

    public ArrayList<Vocabulaies> getDataVocabulary() {
        return arr_vocabulary;
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            dialogExit();
        } else {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    private class VocabularySlidePagerAdapter extends FragmentStatePagerAdapter {
        public VocabularySlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return TestVocabularyFragment.createPage(position, checkAns);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) {
                view.setAlpha(0);

            } else if (position <= 1) {
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                view.setAlpha(MIN_ALPHA +
                        (scaleFactor - MIN_SCALE) /
                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));
            } else {
                view.setAlpha(0);
            }
        }
    }

    public class CounterClass extends CountDownTimer {
        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            String countTime = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished), TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
            txtTimer.setText(countTime);
        }

        @Override
        public void onFinish() {
            txtTimer.setText("00:00");
            result();
        }
    }

    private void result() {
        checkAns = 1;
        if (mPager.getCurrentItem() >= 4) mPager.setCurrentItem(mPager.getCurrentItem() - 4);
        else if (mPager.getCurrentItem() <= 4) mPager.setCurrentItem(mPager.getCurrentItem() + 4);
        finish_btn.setVisibility(View.GONE);
        btnExit_Vocabulary.setVisibility(View.VISIBLE);
    }

    public void dialogExit() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(VocabularySlideActivity.this);
        builder.setIcon(R.drawable.exit);
        builder.setTitle("Notification");
        builder.setMessage("Do you want to exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                timer.cancel();
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.show();
    }
}