package thanhhai.com.toeicpractice.ViewPager;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import thanhhai.com.toeicpractice.ArrayPagerAdapter.PageInfo;
import thanhhai.com.toeicpractice.ArrayPagerAdapter.PageItem;
import thanhhai.com.toeicpractice.CheckAnswer.CheckAnswerAdapter;
import thanhhai.com.toeicpractice.CheckAnswer.TestDoneActivity;
import thanhhai.com.toeicpractice.Database.Question;
import thanhhai.com.toeicpractice.Database.QuestionController;
import thanhhai.com.toeicpractice.R;
import thanhhai.com.toeicpractice.Translate.ApiKeys;
import thanhhai.com.toeicpractice.Translate.Language;
import thanhhai.com.toeicpractice.Translate.Translate;

public class ScreenSlideActivity extends FragmentActivity {
    private static final int NUM_PAGES = 40;
    private ViewPager mPager;
    private CustomPagerAdapter mPagerAdapter;
    private ImageButton imgBtnExit;


    private TextView tvKiemtra, tvTimer, tvXemDiem;
    public int checkAns = 0;

    private QuestionController questionController;
    private ArrayList<Question> arr_Ques;
    private CounterClass timer;
    private String subject;
    private int num_exam;
    private int totalTimer;
    String test = "";

    FloatingActionButton fab, fab_test, fab_exit, fab_translate;
    ImageButton imgbtnPrev, imgbtnNext;
    private boolean anHien = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);

        setupPageView();

        Intent intent = getIntent();
        subject = intent.getStringExtra("subject");
        num_exam = intent.getIntExtra("num_exam", 0);
        test = intent.getStringExtra("test");

        totalTimer = 20;
        timer = new CounterClass(totalTimer * 60 * 1000, 1000);
        questionController = new QuestionController(this);
        arr_Ques = new ArrayList<Question>();

        if (test.equals("yes") == true) {
            arr_Ques = questionController.getQuestion(num_exam, subject);
        } else {
            arr_Ques = (ArrayList<Question>) intent.getExtras().getSerializable("arr_Ques");
        }

        tvKiemtra = (TextView) findViewById(R.id.tvKiemTra);
        tvTimer = (TextView) findViewById(R.id.tvTimer);
        tvXemDiem = (TextView) findViewById(R.id.tvScore);

        tvKiemtra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });
        tvXemDiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent1 = new Intent(ScreenSlideActivity.this, TestDoneActivity.class);
                intent1.putExtra("arr_Ques", arr_Ques);
                startActivity(intent1);
            }
        });
        timer.start();

        imgBtnExit = (ImageButton) findViewById(R.id.imgBtnExit);
        imgBtnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogExit();
            }
        });

        imgbtnPrev = (ImageButton) findViewById(R.id.imgbtnPrev);
        imgbtnNext = (ImageButton) findViewById(R.id.imgbtnNext);
        imgbtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPager.setCurrentItem(mPager.getCurrentItem()+1, true);
            }
        });
        imgbtnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPager.setCurrentItem(mPager.getCurrentItem()-1, true);
            }
        });

        xuLyFloatActionButton();
    }

    public void setupPageView() {
        mPager = (ViewPager) findViewById(R.id.pager);
        ArrayList<PageItem> items = new ArrayList<>();
        for (int i = 0; i < NUM_PAGES; i++) {
            items.add(new PageItem(new PageInfo("Fragment" + i, "", i, checkAns)));
        }
        mPagerAdapter = new CustomPagerAdapter(getSupportFragmentManager(), items);
        mPager.setAdapter(mPagerAdapter);
        mPager.setPageTransformer(true, new DepthPageTransformer());
        mPager.setOffscreenPageLimit(mPagerAdapter.getCount());
    }

    public ArrayList<Question> getData() {
        return arr_Ques;
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            dialogExit();
        } else {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    public void dialogExit() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(ScreenSlideActivity.this);
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

    public class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            if (position < -1) {
                view.setAlpha(0);

            } else if (position <= 0) {
                view.setAlpha(1);
                view.setTranslationX(0);
                view.setScaleX(1);
                view.setScaleY(1);

            } else if (position <= 1) {
                view.setAlpha(1 - position);
                view.setTranslationX(pageWidth * -position);
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else {
                view.setAlpha(0);
            }
        }
    }

    //Dialog hiện thị danh sách những câu trả lời và chưa trả lời
    public void checkAnswer() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.check_answer_dialog);
        dialog.setTitle("Danh sách câu trả lời");

        CheckAnswerAdapter answerAdapter = new CheckAnswerAdapter(arr_Ques, this);
        GridView gvLsQuestion = (GridView) dialog.findViewById(R.id.gvLsQuestion);
        gvLsQuestion.setAdapter(answerAdapter);

        gvLsQuestion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPager.setCurrentItem(position);
                dialog.dismiss();
            }
        });

        Button btnCancle, btnFinish;
        btnCancle = (Button) dialog.findViewById(R.id.btnCancle);
        btnFinish = (Button) dialog.findViewById(R.id.btnFinish);
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                result();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void result() {
        for (int i = 0; i < mPagerAdapter.getCount(); i++) {
            ScreenSlidePageFragment currentFragment = mPagerAdapter.getFragmentInPosition(i);
            currentFragment.showTextView();
            currentFragment.invalidate(true);
        }
        if (mPager.getCurrentItem() >= 4) mPager.setCurrentItem(mPager.getCurrentItem() - 4);
        else if (mPager.getCurrentItem() <= 4) mPager.setCurrentItem(mPager.getCurrentItem() + 4);
        tvXemDiem.setVisibility(View.VISIBLE);
        tvKiemtra.setVisibility(View.GONE);
        HideAllFloatAction();
    }

    public class CounterClass extends CountDownTimer {
        //millisInFuture: 60*1000
        //countDownInterval:  1000
        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            String countTime = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished), TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
            tvTimer.setText(countTime); //SetText cho textview hiện thị thời gian.
        }

        @Override
        public void onFinish() {
            tvTimer.setText("00:00");  //SetText cho textview hiện thị thời gian.
            result();
        }
    }

    private void xuLyFloatActionButton() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab_test = (FloatingActionButton) findViewById(R.id.fab_test);
        fab_exit = (FloatingActionButton) findViewById(R.id.fab_exit);
        fab_translate = (FloatingActionButton) findViewById(R.id.fab_translate);
        fab_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogExit();
            }
        });
        fab_translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Translate();
            }
        });
        fab_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (anHien == false) {
                    HienFloatAction();
                    anHien = true;
                } else {
                    AnFloatAction();
                    anHien = false;
                }
            }
        });
    }
    private void HienFloatAction() {
        fab_test.show();
        fab_exit.show();
        fab_translate.show();
    }
    private void AnFloatAction() {
        fab_test.hide();
        fab_exit.hide();
        fab_translate.hide();
    }
    private void HideAllFloatAction(){
        fab.hide();
        fab_test.hide();
        fab_translate.hide();
        fab_exit.hide();
        imgBtnExit.setVisibility(View.VISIBLE);
    }
    public void Translate() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.translate_dialog);
        dialog.setTitle("Tra từ nhanh");

        Button btnThoat, btnTranslate;
        final TextView txtResultTranslate;
        final EditText txtTranslate;

        btnThoat = (Button) dialog.findViewById(R.id.btnThoat);
        btnTranslate = (Button) dialog.findViewById(R.id.btnTranslate);
        txtResultTranslate = (TextView) dialog.findViewById(R.id.txtResultTranslate);
        txtTranslate = (EditText) dialog.findViewById(R.id.txtTranslate);

        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String InputString;
                String OutputString = null;
                InputString = txtTranslate.getText().toString();

                Translate.setKey(ApiKeys.YANDEX_API_KEY);
                try {
                    OutputString = Translate.execute(InputString, Language.ENGLISH, Language.VIETNAMESE);
                } catch (Exception e) {
                    e.printStackTrace();
                    OutputString = "Error";
                }
                if (OutputString.equals(InputString)) {
                    txtResultTranslate.setText("Bạn nhập từ không đúng!");
                } else {
                    txtResultTranslate.setText(OutputString);
                }
            }
        });
        dialog.show();
    }
}