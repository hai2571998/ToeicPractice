package thanhhai.com.toeicpractice.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;

public class DBHelper extends SQLiteOpenHelper {
    private String db_path ;

    private static final String DATABASE_NAME = "exercise.db";

    public DBHelper(Context context) {
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
