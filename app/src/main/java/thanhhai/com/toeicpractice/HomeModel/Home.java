package thanhhai.com.toeicpractice.HomeModel;

public class Home {
    private String name;
    private String content_main;
    private int thumbnail;

    public Home() {
    }

    public Home(String name, String content_main, int thumbnail) {
        this.name = name;
        this.content_main = content_main;
        this.thumbnail = thumbnail;
    }

    public String getContent_main() {
        return content_main;
    }

    public void setContent_main(String content_main) {
        this.content_main = content_main;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
