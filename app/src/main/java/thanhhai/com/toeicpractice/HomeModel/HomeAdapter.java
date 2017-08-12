package thanhhai.com.toeicpractice.HomeModel;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import thanhhai.com.toeicpractice.R;
import thanhhai.com.toeicpractice.RoomChat.SplashLoginActivity;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
    private Context mContext;
    private List<Home> homeList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        }
    }


    public HomeAdapter(Context mContext, List<Home> homeList) {
        this.mContext = mContext;
        this.homeList = homeList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_cardview, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Home home = homeList.get(position);
        holder.title.setText(home.getName());

        Glide.with(mContext).load(home.getThumbnail()).into(holder.thumbnail);
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (position) {
                    case 0:
                        Intent intent = new Intent(view.getContext(), QuestionActivity.class);
                        view.getContext().startActivity(intent);
                        break;
                    case 1:
                        break;
                    case 2:
                        Intent intent2 = new Intent(view.getContext(), VideoYoutubeActivity.class);
                        view.getContext().startActivity(intent2);
                        break;
                    case 3:
                        Intent intent3 = new Intent(view.getContext(), TestVocabularyActivity.class);
                        view.getContext().startActivity(intent3);
                        break;
                    case 4:
                        Intent intent4 = new Intent(view.getContext(), MusicsActivity.class);
                        view.getContext().startActivity(intent4);
                        break;
                    case 5:
                        Intent intent5 = new Intent(view.getContext(), SplashLoginActivity.class);
                        view.getContext().startActivity(intent5);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return homeList.size();
    }
}
