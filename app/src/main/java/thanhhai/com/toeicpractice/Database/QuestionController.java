package thanhhai.com.toeicpractice.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class QuestionController {
    private DBHelper dbHelper;
    private SQLiteDatabase database;

    public QuestionController(Context context) {
        dbHelper = new DBHelper(context);
        database = dbHelper.openDatabase();
    }

    //Lấy danh sách câu hỏi
    public ArrayList<Question> getQuestion(int num_exam, String subject) {
        ArrayList<Question> lsData = new ArrayList<Question>();
        Cursor cursor = database.rawQuery("SELECT * FROM tracnghiem WHERE num_exam = '" + num_exam + "' AND subject='" + subject + "' ORDER BY random()", null);
        cursor.moveToFirst();
        do {
            Question item;
            item = new Question(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                    cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getInt(7), cursor.getString(8), cursor.getString(9), "", cursor.getString(10));
            lsData.add(item);
        } while (cursor.moveToNext());
        return lsData;
    }

    //Lấy danh sách câu hỏi theo câu hỏi...
    public Cursor getSearchQuestion(String subject, String key) {
        Cursor cursor = database.rawQuery("SELECT * FROM tracnghiem WHERE question LIKE '%" + key + "%' AND subject LIKE '%" + subject + "%'", null);
        if (cursor != null) {
            cursor.moveToFirst();
            cursor.close();
        }
        return cursor;
    }

    //Lấy danh sách câu hỏi theo môn học
    public Cursor getQuestionBySubject(String key) {
        Cursor cursor = database.rawQuery("SELECT * FROM tracnghiem WHERE subject LIKE '%" + key + "%'", null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
}