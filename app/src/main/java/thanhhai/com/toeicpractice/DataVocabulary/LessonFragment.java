package thanhhai.com.toeicpractice.DataVocabulary;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import thanhhai.com.toeicpractice.Database.SQLiteDatasource;
import thanhhai.com.toeicpractice.R;


public class LessonFragment extends Fragment {
    ListView lvLesson;
    private SQLiteDatasource datasource;

    public LessonFragment() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_lesson, container, false);
        lvLesson = (ListView)rootView.findViewById(R.id.lvLesson);

        datasource = new SQLiteDatasource(getContext());

        ArrayList<Lessons> lessons = datasource.getAllLesson();
        lessons.get(0).setAvatar(R.drawable.contract);
        lessons.get(1).setAvatar(R.drawable.marketing);
        lessons.get(2).setAvatar(R.drawable.warranties);
        lessons.get(3).setAvatar(R.drawable.businessplanning);
        lessons.get(4).setAvatar(R.drawable.conferrence);
        lessons.get(5).setAvatar(R.drawable.computerandinternet);
        lessons.get(6).setAvatar(R.drawable.officetechnology);
        lessons.get(7).setAvatar(R.drawable.officeprocedure);
        lessons.get(8).setAvatar(R.drawable.electronic);
        lessons.get(9).setAvatar(R.drawable.correspondence);
        lessons.get(10).setAvatar(R.drawable.jobartrecruitment);
        lessons.get(11).setAvatar(R.drawable.appleinterview);
        lessons.get(12).setAvatar(R.drawable.hiretrainning);
        lessons.get(13).setAvatar(R.drawable.salariesandbenifit);
        lessons.get(14).setAvatar(R.drawable.promoteaward);
        lessons.get(15).setAvatar(R.drawable.shopping);
        lessons.get(16).setAvatar(R.drawable.ordersupplies);
        lessons.get(17).setAvatar(R.drawable.shipping);
        lessons.get(18).setAvatar(R.drawable.invoice);
        lessons.get(19).setAvatar(R.drawable.inventory);
        lessons.get(20).setAvatar(R.drawable.banking);
        lessons.get(21).setAvatar(R.drawable.acounting);
        lessons.get(22).setAvatar(R.drawable.investment);
        lessons.get(23).setAvatar(R.drawable.taxes);
        lessons.get(24).setAvatar(R.drawable.financialstatement);
        lessons.get(25).setAvatar(R.drawable.propertyanddepartments);
        lessons.get(26).setAvatar(R.drawable.boardmeetings);
        lessons.get(27).setAvatar(R.drawable.qualitycontrol);
        lessons.get(28).setAvatar(R.drawable.productdvelopment);
        lessons.get(29).setAvatar(R.drawable.rentingleasing);
        lessons.get(30).setAvatar(R.drawable.restaurant);
        lessons.get(31).setAvatar(R.drawable.eatingout);
        lessons.get(32).setAvatar(R.drawable.orderinglunh);
        lessons.get(33).setAvatar(R.drawable.cooking);
        lessons.get(34).setAvatar(R.drawable.event);
        lessons.get(35).setAvatar(R.drawable.generaltravel);
        lessons.get(36).setAvatar(R.drawable.airline);
        lessons.get(37).setAvatar(R.drawable.train);
        lessons.get(38).setAvatar(R.drawable.hotel);
        lessons.get(39).setAvatar(R.drawable.carrental);
        lessons.get(40).setAvatar(R.drawable.movie);
        lessons.get(41).setAvatar(R.drawable.theater);
        lessons.get(42).setAvatar(R.drawable.music);
        lessons.get(43).setAvatar(R.drawable.museum);
        lessons.get(44).setAvatar(R.drawable.media);
        lessons.get(45).setAvatar(R.drawable.doctoroffice);
        lessons.get(46).setAvatar(R.drawable.dentist);
        lessons.get(47).setAvatar(R.drawable.health);
        lessons.get(48).setAvatar(R.drawable.hospital);
        lessons.get(49).setAvatar(R.drawable.pharmacy);

        AdapterListLesson customAdapter = new AdapterListLesson(rootView.getContext(),R.layout.item_lesson, lessons);
        lvLesson.setAdapter(customAdapter);
        return rootView;
    }
}
