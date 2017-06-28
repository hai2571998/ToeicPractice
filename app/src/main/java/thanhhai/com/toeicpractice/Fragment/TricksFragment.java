package thanhhai.com.toeicpractice.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.barteksc.pdfviewer.PDFView;

import thanhhai.com.toeicpractice.R;

public class TricksFragment extends Fragment {
    public static final String SAMPLE_FILE = "trickstoeic.pdf";
    PDFView pdfView;


    public static TricksFragment newInstance() {
        TricksFragment tricksFragment = new TricksFragment();
        return tricksFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tricks, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        pdfView= (PDFView)getActivity().findViewById(R.id.pdfView);
        pdfView.fromAsset(SAMPLE_FILE)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(false)
                .defaultPage(0)
                .enableAnnotationRendering(false)
                .password(null)
                .scrollHandle(null)
                .enableAntialiasing(true)
                .spacing(16)
                .load();
    }
}
