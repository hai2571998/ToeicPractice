package thanhhai.com.toeicpractice.ArrayPagerAdapter;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

public class PageItem implements Parcelable {
    @NonNull
    private PageInfo pageInfo;
    private Fragment.SavedState state = null;


    public PageItem(@NonNull PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    @NonNull
    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public PageItem(Parcel in) {
        this.state = in.readParcelable(getClass().getClassLoader());
        this.pageInfo = in.readParcelable(getClass().getClassLoader());
    }

    public static final Creator<PageItem> CREATOR = new Creator<PageItem>() {
        @Override
        public PageItem createFromParcel(Parcel in) {
            return new PageItem(in);
        }

        @Override
        public PageItem[] newArray(int size) {
            return new PageItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(state, 0);
        parcel.writeParcelable(pageInfo, 0);
    }
}
