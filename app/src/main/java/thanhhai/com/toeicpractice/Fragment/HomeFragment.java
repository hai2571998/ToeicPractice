package thanhhai.com.toeicpractice.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.HashMap;
import java.util.Random;

import thanhhai.com.toeicpractice.HomeModel.HomeAdapter;
import thanhhai.com.toeicpractice.HomeModel.QuestionActivity;
import thanhhai.com.toeicpractice.HomeModel.TestVocabularyActivity;
import thanhhai.com.toeicpractice.HomeModel.VideoYoutubeActivity;
import thanhhai.com.toeicpractice.R;
import thanhhai.com.toeicpractice.RoomChat.SplashLoginActivity;

public class HomeFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private SliderLayout imageSlider;
    private int max = 12;

    private String webUrl = "https://toeicpractice.000webhostapp.com/image/";
    private String[] url1 = {
            "abide_by", "agreement", "assurance", "cancellation", "determine",
            "engage", "establish", "obligate", "party", "provision", "resolve",
            "specific"
    };

    private String[] url2 = {
            "attract", "compare", "competition", "consume", "convince", "currently",
            "fad", "inspiration", "market", "persuasion", "productive", "satisfaction"
    };

    private String[] url3 = {
            "characteristic", "consequence", "consider", "cover", "expiration",
            "frequently", "imply", "promise", "protect", "reputation", "require",
            "variety"
    };

    private String[] url4 = {
            "address", "avoid", "demonstrate", "develop", "evaluate", "gather",
            "offer", "primarily", "risk", "strategy", "strong", "substitution"
    };

    private String[] url5 = {
            "accommodate", "arrangement", "association", "attend", "get_in_touch",
            "hold", "location", "overcrowded", "register", "select", "session", "take_part_in"
    };

    private String[] mean1 = {
            "tuân theo", "hợp đồng", "sự đảm bảo", "sự hủy bỏ", "xác định, định rõ", "tham gia",
            "thành lập", "bắt buộc", "bữa tiệc", "điều khoản", "giải quyết", "định rỏ"
    };

    private String[] mean2 = {
            "thu hút, hấp dẫn", "so sánh", "cạnh tranh", "tiêu thụ, hấp thụ", "thuyết phục",
            "hiện tại", "mốt nhất thời", "cảm hứng", "thị trường", "sự thuyết phục", "có năng suất",
            "sự thỏa mãn"
    };

    private String[] mean3 = {
            "đặc điểm", "hậu quả, kết quả", "xem xét", "bảo vệ", "hết hạn", "thường xuyên",
            "ngụ ý", "hứa hẹn", "bảo vệ", "danh tiếng", "yêu cầu", "nhiều loại khác nhau"
    };

    private String[] mean4 = {
            "diển thuyết", "tránh", "chứng minh", "phát triển", "đánh giá", "tập hợp, thu thập",
            "đề nghị", "chủ yếu, chính", "rủi ro", "chiến lược", "mạnh mẽ", "thay thế"
    };

    private String[] mean5 = {
            "cung cấp", "sắp xếp", "hiệp hội", "tham dự", "giữ liên lạc", "tổ chức, chủ trì",
            "vị trí, địa điểm", "quá đông", "đăng ký", "chọn", "kỳ họp, phiên họp", "tham gia vào"
    };

    private String dinhdangfile = ".jpg";

    GridView gvMenu;
    private String[] tenLogo = {
            "Question Online", "Video Toeic", "Room Chat", "Test Vocabulary"
    };

    int[] imgLogo = {
            R.drawable.question,
            R.drawable.videotoiec,
            R.drawable.roomchat,
            R.drawable.test_vocabulary
    };

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageSlider = (SliderLayout) getActivity().findViewById(R.id.sliderMenu);
        Random random = new Random();
        int index  = random.nextInt(max);
        HashMap<String, String> url_maps = new HashMap<String, String>();
        url_maps.put(url1[index]+": "+mean1[index], webUrl+url1[index]+dinhdangfile);
        url_maps.put(url2[index]+": "+mean2[index], webUrl+url2[index]+dinhdangfile);
        url_maps.put(url3[index]+": "+mean3[index], webUrl+url3[index]+dinhdangfile);
        url_maps.put(url4[index]+": "+mean4[index], webUrl+url4[index]+dinhdangfile);
        url_maps.put(url5[index]+": "+mean5[index], webUrl+url5[index]+dinhdangfile);

        for (String name : url_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(getContext());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra", name);
            imageSlider.addSlider(textSliderView);
        }
        imageSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        imageSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        imageSlider.setCustomAnimation(new DescriptionAnimation());
        imageSlider.setDuration(3000);
        imageSlider.addOnPageChangeListener(this);

        gvMenu = (GridView) getActivity().findViewById(R.id.gvMenu);
        HomeAdapter homeAdapter = new HomeAdapter(getContext(), tenLogo, imgLogo);
        gvMenu.setAdapter(homeAdapter);
        gvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position) {
                    case 0:
                        Intent intent = new Intent(getContext(), QuestionActivity.class);
                        getActivity().startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(getContext(), VideoYoutubeActivity.class);
                        getActivity().startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2 = new Intent(getContext(), SplashLoginActivity.class);
                        getActivity().startActivity(intent2);
                        break;
                    case 3:
                        Intent intent3 = new Intent(getContext(), TestVocabularyActivity.class);
                        getActivity().startActivity(intent3);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void onStop() {
        imageSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
