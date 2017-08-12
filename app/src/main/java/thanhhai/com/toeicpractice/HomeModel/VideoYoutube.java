package thanhhai.com.toeicpractice.HomeModel;

public class VideoYoutube {
    private String Title;
    private String Thumbnail;
    private String IdVideo;
    private String channel;

    public VideoYoutube() {
    }

    public VideoYoutube(String title, String thumbnail, String idVideo, String channel) {
        Title = title;
        Thumbnail = thumbnail;
        IdVideo = idVideo;
        this.channel = channel;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        Thumbnail = thumbnail;
    }

    public String getIdVideo() {
        return IdVideo;
    }

    public void setIdVideo(String idVideo) {
        IdVideo = idVideo;
    }
}
