package thanhhai.com.toeicpractice.HomeModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import thanhhai.com.toeicpractice.R;

public class VideoYoutubeAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<VideoYoutube> videoYoutubeList;

    public VideoYoutubeAdapter(Context context, int layout, List<VideoYoutube> videoYoutubeList) {
        this.context = context;
        this.layout = layout;
        this.videoYoutubeList = videoYoutubeList;
    }

    @Override
    public int getCount() {
        return videoYoutubeList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder {
        TextView title, channel;
        ImageView list_image;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            viewHolder.title = (TextView) view.findViewById(R.id.title);
            viewHolder.list_image = (ImageView) view.findViewById(R.id.list_image);
            viewHolder.channel = (TextView) view.findViewById(R.id.channel);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        VideoYoutube videoYoutube = videoYoutubeList.get(i);
        viewHolder.title.setText(videoYoutube.getTitle());
        Picasso.with(context).load(videoYoutube.getThumbnail()).into(viewHolder.list_image);
        viewHolder.channel.setText(videoYoutube.getChannel());
        return view;
    }
}
