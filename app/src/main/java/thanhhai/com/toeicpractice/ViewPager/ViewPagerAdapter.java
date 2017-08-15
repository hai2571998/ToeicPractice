package thanhhai.com.toeicpractice.ViewPager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import thanhhai.com.toeicpractice.Fragment.QuestionOnlineFragment;
import thanhhai.com.toeicpractice.Fragment.HomeFragment;
import thanhhai.com.toeicpractice.Fragment.SettingFragment;
import thanhhai.com.toeicpractice.Fragment.GrammarFragment;
import thanhhai.com.toeicpractice.DataVocabulary.LessonFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new GrammarFragment();
            case 2:
                return new LessonFragment();
            case 3:
                return new QuestionOnlineFragment();
            case 4:
                return new SettingFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 5;
    }


}