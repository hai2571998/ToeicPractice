package thanhhai.com.toeicpractice.ViewPager;

import android.support.v4.app.FragmentManager;

import java.util.ArrayList;

import thanhhai.com.toeicpractice.ArrayPagerAdapter.ArrayPagerAdapter;
import thanhhai.com.toeicpractice.ArrayPagerAdapter.PageInfo;
import thanhhai.com.toeicpractice.ArrayPagerAdapter.PageItem;

public class CustomPagerAdapter extends ArrayPagerAdapter<ScreenSlidePageFragment> {

    public CustomPagerAdapter(FragmentManager fragmentManager, ArrayList<PageItem> items) {
        super(fragmentManager, items);
    }

    @Override
    public ScreenSlidePageFragment newInstance(PageInfo pageInfo) {
        return ScreenSlidePageFragment.create(pageInfo.getPageNumber(), pageInfo.getCheckAnswer());
    }
}
