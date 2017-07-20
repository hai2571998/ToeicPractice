package thanhhai.com.toeicpractice.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;

public class VocabularyHelper extends SQLiteOpenHelper {

    private String db_path;
    private static final String DATABASE_NAME = "toeic.db";//

    public static final String TABLE_NAME_LESSON = "lesson";
    public static final String COL_ID = "_id";
    public static final String COL_LESSON = "_lesson";
    public static final String COL_NAME_LESSON = "_namelesson";

    public static final String TABLE_NAME_VOC = "vocmean";
    //public static final String COL_VOCLESSON = "_lesson";
    public static final String COL_VOC = "_voc";
    public static final String COL_MEAN = "_mean";
    public static final String COL_PHIENAM = "_phienAm";
    public static final String COL_AVT = "_avt";
    public static final String COL_TYPE = "_type";

    public static final String TABLE_NAME_MUSIC = "musics";
    public static final String COL_ID1 = "_id1";
    public static final String COL_SONG = "_song";
    public static final String COL_URL = "_url";
    public static final String COL_SOUND = "_sound";
    public static final String COL_AUTHOR = "_author";

    public VocabularyHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        db_path = context.getFilesDir().getParent()+ File.separator+"database" +File.separator + DATABASE_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public SQLiteDatabase openDatabase() {
        return SQLiteDatabase.openDatabase(db_path, null, SQLiteDatabase.OPEN_READWRITE);
    }
}
