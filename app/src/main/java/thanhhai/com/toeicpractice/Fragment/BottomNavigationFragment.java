package thanhhai.com.toeicpractice.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import thanhhai.com.toeicpractice.MainActivity;
import thanhhai.com.toeicpractice.R;
import thanhhai.com.toeicpractice.ViewPager.ViewPagerAdapter;

public class BottomNavigationFragment extends Fragment implements
        BottomNavigationView.OnNavigationItemSelectedListener,ViewPager.OnPageChangeListener{
    BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;

    MenuItem prevMenuItem;

    public BottomNavigationFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Toeic Practice");
        return inflater.inflate(R.layout.fragment_bottom_navigation, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewPager = (ViewPager) getActivity().findViewById(R.id.viewpager);
        bottomNavigationView = (BottomNavigationView) getActivity().findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        viewPager.addOnPageChangeListener(this);
        setupViewPager(viewPager);
    }


    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_test:
                viewPager.setCurrentItem(0);
                break;
            case R.id.action_tricks:
                viewPager.setCurrentItem(1);
                break;
            case R.id.action_vocabulary:
                viewPager.setCurrentItem(2);
                break;
            case R.id.action_conatct:
                viewPager.setCurrentItem(3);
                break;
            case R.id.action_settings:
                viewPager.setCurrentItem(4);
                break;
        }
        return false;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }
    @Override
    public void onPageSelected(int position) {
        if (prevMenuItem != null) {
            prevMenuItem.setChecked(false);
        }
        else
        {
            bottomNavigationView.getMenu().getItem(0).setChecked(false);
        }
        Log.d("page", "onPageSelected: "+position);
        bottomNavigationView.getMenu().getItem(position).setChecked(true);
        prevMenuItem = bottomNavigationView.getMenu().getItem(position);

    }
    @Override
    public void onPageScrollStateChanged(int state) {
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
    }
}

