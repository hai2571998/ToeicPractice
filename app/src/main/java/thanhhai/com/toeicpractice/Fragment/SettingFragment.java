package thanhhai.com.toeicpractice.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import thanhhai.com.toeicpractice.R;

public class SettingFragment extends Fragment {


    public static SettingFragment newInstance() {
        SettingFragment settingFragment = new SettingFragment();
        return settingFragment;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

}
