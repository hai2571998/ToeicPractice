package thanhhai.com.toeicpractice.Score;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import thanhhai.com.toeicpractice.MainActivity;
import thanhhai.com.toeicpractice.R;

public class ScoreFragment extends Fragment {

    ListView lvScore;
    ScoreController scoreController;
    ScoreAdapter scoreAdapter;

    public static ScoreFragment newInstance() {
        ScoreFragment fragment = new ScoreFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Danh sách điểm");
        return inflater.inflate(R.layout.fragment_score, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        scoreController=new ScoreController(getActivity());
        lvScore=(ListView) getActivity().findViewById(R.id.lvScore);
        Cursor cursor=scoreController.getScore();
        scoreAdapter=new ScoreAdapter(getActivity(),cursor,true);
        lvScore.setAdapter(scoreAdapter);
    }
}
