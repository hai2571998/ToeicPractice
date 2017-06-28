package thanhhai.com.toeicpractice.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import thanhhai.com.toeicpractice.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ToeicCFragment extends Fragment {


    public ToeicCFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_toeic_c, container, false);
    }

}
