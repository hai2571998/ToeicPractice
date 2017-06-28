package thanhhai.com.toeicpractice.ArrayPagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

public interface FragmentListener {
    public void attach(Fragment fragment, FragmentTransaction fragmentTransaction);

    public void detach(Fragment fragment, FragmentTransaction fragmentTransaction);
}
