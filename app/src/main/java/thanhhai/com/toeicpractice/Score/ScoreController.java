package thanhhai.com.toeicpractice.Score;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import thanhhai.com.toeicpractice.Database.DBHelper;

public class ScoreController {
    private DBHelper dbHelper;
    private SQLiteDatabase database;

    public ScoreController(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void insertScore(String name, int score, String room) {
        database = dbHelper.openDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("score", score);
        values.put("room", room);
        database.insert("tbscore", null, values);
        database.close();
    }


    //Lấy danh sách điểm
    public Cursor getScore() {
        database= dbHelper.getWritableDatabase();
        database = dbHelper.openDatabase();
        Cursor cursor = database.query("tbscore", //ten bang
                null, //danh sach cot can lay
                null, //dieu kien where
                null, //doi so dieu kien where
                null, //bieu thuc groupby
                null, //bieu thuc having
                "_id DESC", //bieu thuc orderby
                null
        );
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
}
