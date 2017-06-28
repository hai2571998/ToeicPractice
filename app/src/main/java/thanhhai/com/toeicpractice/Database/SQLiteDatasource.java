package thanhhai.com.toeicpractice.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import thanhhai.com.toeicpractice.DataVocabulary.Vocabulaies;
import thanhhai.com.toeicpractice.DataVocabulary.Lessons;

public class SQLiteDatasource {

    private VocabularyHelper helper;
    private SQLiteDatabase database;

    public SQLiteDatasource(Context context) {
        helper = new VocabularyHelper(context);
        database = helper.openDatabase();
    }


    public ArrayList<Vocabulaies> getVocInLesson(int _lesson) {
        String selection = "_lesson = " + _lesson;
        Cursor cursor = database.query(VocabularyHelper.TABLE_NAME_VOC, null, selection, null, null, null, null);
        return parseCursorVocInLesson(cursor);
    }

    private ArrayList<Vocabulaies> parseCursorVocInLesson(Cursor cursor) {
        ArrayList<Vocabulaies> list = null;
        if (cursor.moveToFirst()) {
            list = new ArrayList<>();
            while (!cursor.isAfterLast()) {
                Vocabulaies vocabulary = new Vocabulaies();
                vocabulary.set_id(cursor.getLong(cursor.getColumnIndex(VocabularyHelper.COL_ID)));
                vocabulary.set_lesson(cursor.getLong(cursor.getColumnIndex(VocabularyHelper.COL_LESSON)));
                vocabulary.set_voc(cursor.getString(cursor.getColumnIndex(VocabularyHelper.COL_VOC)));
                vocabulary.set_mean(cursor.getString(cursor.getColumnIndex(VocabularyHelper.COL_MEAN)));
                vocabulary.set_phienAm(cursor.getString(cursor.getColumnIndex(VocabularyHelper.COL_PHIENAM)));
                vocabulary.set_type(cursor.getString(cursor.getColumnIndex(VocabularyHelper.COL_TYPE)));
                vocabulary.set_avt(cursor.getBlob(cursor.getColumnIndex(VocabularyHelper.COL_AVT)));
                vocabulary.setMediaPlayer(cursor.getBlob(cursor.getColumnIndex(VocabularyHelper.COL_SOUND)));
                list.add(vocabulary);
                cursor.moveToNext();
            }
        }
        return list;
    }

    public ArrayList<Vocabulaies> getVocAndMean(int _lesson) {
        String selection = "_lesson = " + _lesson;
        Cursor cursor = database.query(VocabularyHelper.TABLE_NAME_VOC, null, selection, null, null, null, null);
        return parseCursorVocInLesson(cursor);
    }

    private ArrayList<Vocabulaies> parseCursorVocAndMean(Cursor cursor) {
        ArrayList<Vocabulaies> list = null;
        if (cursor.moveToFirst()) {
            list = new ArrayList<>();
            while (!cursor.isAfterLast()) {
                Vocabulaies vocabulary = new Vocabulaies();
                vocabulary.set_id(cursor.getLong(cursor.getColumnIndex(VocabularyHelper.COL_ID)));
                vocabulary.set_lesson(cursor.getLong(cursor.getColumnIndex(VocabularyHelper.COL_LESSON)));
                vocabulary.set_voc(cursor.getString(cursor.getColumnIndex(VocabularyHelper.COL_VOC)));
                vocabulary.set_mean(cursor.getString(cursor.getColumnIndex(VocabularyHelper.COL_MEAN)));
                list.add(vocabulary);
                cursor.moveToNext();
            }
        }
        return list;
    }

    public ArrayList<Lessons> getAllLesson() {
        Cursor cursor = database.query(VocabularyHelper.TABLE_NAME_LESSON, null, null, null, null, null, null);
        return parseCursorLesson(cursor);
    }

    private ArrayList<Lessons> parseCursorLesson(Cursor cursor) {
        ArrayList<Lessons> list = null;
        if (cursor.moveToFirst()) {
            list = new ArrayList<>();
            while (!cursor.isAfterLast()) {
                Lessons lessons = new Lessons();
                lessons.set_id(cursor.getInt(cursor.getColumnIndex(VocabularyHelper.COL_ID)));
                lessons.setNumLesson(cursor.getInt(cursor.getColumnIndex(VocabularyHelper.COL_LESSON)));
                lessons.setTitleLesson(cursor.getString(cursor.getColumnIndex(VocabularyHelper.COL_NAME_LESSON)));
                list.add(lessons);
                cursor.moveToNext();
            }
        }
        return list;
    }
}