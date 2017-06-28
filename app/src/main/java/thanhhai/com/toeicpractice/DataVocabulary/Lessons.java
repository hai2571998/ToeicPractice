package thanhhai.com.toeicpractice.DataVocabulary;

public class Lessons {
    private long _id;
    private long numLesson;
    private String titleLesson;
    private int avatar;

    public Lessons() {
    }

    public Lessons(int avatar, long _id, long numLesson, String titleLesson) {
        this.avatar = avatar;
        this._id = _id;
        this.numLesson = numLesson;
        this.titleLesson = titleLesson;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public long getNumLesson() {
        return numLesson;
    }

    public void setNumLesson(long numLesson) {
        this.numLesson = numLesson;
    }

    public String getTitleLesson() {
        return titleLesson;
    }

    public void setTitleLesson(String titleLesson) {
        this.titleLesson = titleLesson;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }
}