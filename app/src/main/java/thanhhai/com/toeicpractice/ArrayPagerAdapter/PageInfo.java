package thanhhai.com.toeicpractice.ArrayPagerAdapter;

import android.os.Parcel;

public class PageInfo implements IPageInfo {

    private String tag, title;
    private int pageNumber, checkAnswer;

    public PageInfo(String tag, String title, int pageNumber, int checkAnswer) {
        this.tag = tag;
        this.title = title;
        this.pageNumber = pageNumber;
        this.checkAnswer = checkAnswer;
    }

    protected PageInfo(Parcel in) {
        tag = in.readString();
        title = in.readString();
    }

    public static final Creator<PageInfo> CREATOR = new Creator<PageInfo>() {
        @Override
        public PageInfo createFromParcel(Parcel in) {
            return new PageInfo(in);
        }

        @Override
        public PageInfo[] newArray(int size) {
            return new PageInfo[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(tag);
        parcel.writeString(title);
    }

    public int getCheckAnswer() {
        return checkAnswer;
    }

    public void setCheckAnswer(int checkAnswer) {
        this.checkAnswer = checkAnswer;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
}
