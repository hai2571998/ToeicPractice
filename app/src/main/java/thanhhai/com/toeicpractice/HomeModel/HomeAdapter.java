package thanhhai.com.toeicpractice.HomeModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import thanhhai.com.toeicpractice.R;

public class HomeAdapter extends BaseAdapter{

    private Context context;
    private String[] tenLogo;
    private int[] imgLogo;

    public HomeAdapter(Context context, String[] tenLogo, int[] imgLogo) {
        this.context = context;
        this.tenLogo = tenLogo;
        this.imgLogo = imgLogo;
    }

    @Override
    public int getCount() {
        return tenLogo.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.home_item, null);
        TextView txtMenuHome = (TextView) view.findViewById(R.id.txtMenuHome);
        ImageView imgMenuHome = (ImageView) view.findViewById(R.id.imgMenuHome);

        txtMenuHome.setText(tenLogo[position]);
        imgMenuHome.setImageResource(imgLogo[position]);

        return view;
    }
}
