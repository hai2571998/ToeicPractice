package thanhhai.com.toeicpractice.DataVocabulary;

public class Vocabulaies {
    private long _id;
    private long _lesson;
    private String _voc;
    private String _mean;
    private String _phienAm;
    private String _type;
    private byte[] _avt;
    private byte[] MediaPlayer;
    private String answer = "";

    public Vocabulaies() {

    }

    public Vocabulaies(long _id, long _lesson, String _voc, String _mean, String _phienAm, String _type, byte[] _avt, byte[] mediaPlayer, String answer) {
        this._id = _id;
        this._lesson = _lesson;
        this._voc = _voc;
        this._mean = _mean;
        this._phienAm = _phienAm;
        this._type = _type;
        this._avt = _avt;
        MediaPlayer = mediaPlayer;
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public long get_lesson() {
        return _lesson;
    }

    public void set_lesson(long _lesson) {
        this._lesson = _lesson;
    }

    public String get_voc() {
        return _voc;
    }

    public void set_voc(String _voc) {
        this._voc = _voc;
    }

    public String get_mean() {
        return _mean;
    }

    public void set_mean(String _mean) {
        this._mean = _mean;
    }

    public String get_phienAm() {
        return _phienAm;
    }

    public void set_phienAm(String _phienAm) {
        this._phienAm = _phienAm;
    }

    public String get_type() {
        return _type;
    }

    public void set_type(String _type) {
        this._type = _type;
    }

    public byte[] get_avt() {
        return _avt;
    }

    public void set_avt(byte[] _avt) {
        this._avt = _avt;
    }

    public byte[] getMediaPlayer() {
        return MediaPlayer;
    }

    public void setMediaPlayer(byte[] mediaPlayer) {
        MediaPlayer = mediaPlayer;
    }
}
