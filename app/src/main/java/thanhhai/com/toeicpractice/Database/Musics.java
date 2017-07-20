package thanhhai.com.toeicpractice.Database;

public class Musics {
    private int _id;
    private String _song;
    private String _urlsong;
    private String _author;

    public Musics() {
    }

    public Musics(int _id, String _song, String _urlsong, String _author, String _duration) {
        this._id = _id;
        this._song = _song;
        this._urlsong = _urlsong;
        this._author = _author;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_song() {
        return _song;
    }

    public void set_song(String _song) {
        this._song = _song;
    }

    public String get_urlsong() {
        return _urlsong;
    }

    public void set_urlsong(String _urlsong) {
        this._urlsong = _urlsong;
    }

    public String get_author() {
        return _author;
    }

    public void set_author(String _author) {
        this._author = _author;
    }
}
