package thanhhai.com.toeicpractice.ViewPager;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import thanhhai.com.toeicpractice.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestVocabularyPageFragment extends Fragment {


    public TestVocabularyPageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test_vocabulary_page, container, false);
    }

}
