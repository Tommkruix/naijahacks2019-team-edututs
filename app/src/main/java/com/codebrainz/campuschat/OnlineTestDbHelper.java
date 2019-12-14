package com.codebrainz.campuschat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.codebrainz.campuschat.UnilorinTestContract.*;
import com.codebrainz.campuschat.EdePolyTestContract.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tommkruix on 12/11/2019.
 */

public class OnlineTestDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "CampusChatOnlineTest.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public OnlineTestDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_UNILORIN_QUESTIONS_TABLE = "CREATE TABLE " +
                UnilorinQuestionsTable.TABLE_NAME + " ( " +
                UnilorinQuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                UnilorinQuestionsTable.COLUMN_QUESTION + " TEXT, " +
                UnilorinQuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                UnilorinQuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                UnilorinQuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                UnilorinQuestionsTable.COLUMN_ANSWER_NR + " INTEGER, " +
                UnilorinQuestionsTable.COLUMN_DIFFICULTY + " TEXT" +
                ")";
        db.execSQL(SQL_CREATE_UNILORIN_QUESTIONS_TABLE);
        fillUnilorinQuestionsTable();

        // Ede poly
        final String SQL_CREATE_EDEPOLY_QUESTIONS_TABLE = "CREATE TABLE " +
                EdePolyQuestionsTable.TABLE_NAME + " ( " +
                EdePolyQuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                EdePolyQuestionsTable.COLUMN_QUESTION + " TEXT, " +
                EdePolyQuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                EdePolyQuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                EdePolyQuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                EdePolyQuestionsTable.COLUMN_ANSWER_NR + " INTEGER, " +
                EdePolyQuestionsTable.COLUMN_DIFFICULTY + " TEXT" +
                ")";
        db.execSQL(SQL_CREATE_EDEPOLY_QUESTIONS_TABLE);
        fillEdePolyQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UnilorinQuestionsTable.TABLE_NAME);
        // Ede Poly
        db.execSQL("DROP TABLE IF EXISTS " + EdePolyQuestionsTable.TABLE_NAME);

        onCreate(db);
    }
    private void fillUnilorinQuestionsTable() {
        UnilorinQuestion q1 = new UnilorinQuestion("Describe Naijahacks?",
                "Technology space","A competition",  "An Hackathon", 3, UnilorinQuestion.DIFFICULTY_EASY);
        addUnilorinQuestion(q1);
        UnilorinQuestion q2 = new UnilorinQuestion("Who developed campuschat at Naijahacks2019?",
                "Uchi Organizer","Team EduTuts", "Ream Anonymous", 2, UnilorinQuestion.DIFFICULTY_MEDIUM);
        addUnilorinQuestion(q2);
        UnilorinQuestion q3 = new UnilorinQuestion("Where is Naijahacks located?",  "Oyo",
                "Abuja", "Lagos",3, UnilorinQuestion.DIFFICULTY_MEDIUM);
        addUnilorinQuestion(q3);
        UnilorinQuestion q4 = new UnilorinQuestion("When is the naijahacks2019 demo day?",
                "Dec 17","Dec 12", "Dec 25",  1, UnilorinQuestion.DIFFICULTY_HARD);
        addUnilorinQuestion(q4);
        UnilorinQuestion q5 = new UnilorinQuestion("What is the name of team Edututs mentor",
                "Fab","Abel", "Cain", 2, UnilorinQuestion.DIFFICULTY_HARD);
        addUnilorinQuestion(q5);
        UnilorinQuestion q6 = new UnilorinQuestion("Where do naijahacks communicates?",
                "Slack","Whatsapp", "Facebook", 1, UnilorinQuestion.DIFFICULTY_HARD);
        addUnilorinQuestion(q6);
    }
    // Ede Poly
    private void fillEdePolyQuestionsTable() {
        EdePolyQuestion q1 = new EdePolyQuestion("Which year does naijahacks started?",
                "2019","2018",  "2001", 1, EdePolyQuestion.DIFFICULTY_EASY);
        addEdePolyQuestion(q1);
        EdePolyQuestion q2 = new EdePolyQuestion("Which name is among the Team EduTuts member",
                "Anny","Tommkruix", "AJ", 2, EdePolyQuestion.DIFFICULTY_MEDIUM);
        addEdePolyQuestion(q2);
        EdePolyQuestion q3 = new EdePolyQuestion("Which name is among the Team EduTuts member",  "Mixpeal",
                "Fashtech", "Mich",3, EdePolyQuestion.DIFFICULTY_MEDIUM);
        addEdePolyQuestion(q3);
        EdePolyQuestion q4 = new EdePolyQuestion("Which name is among the Team EduTuts member",
                "Rachael","peal", "Tom",  1, EdePolyQuestion.DIFFICULTY_HARD);
        addEdePolyQuestion(q4);
        EdePolyQuestion q5 = new EdePolyQuestion("Which name is among the Team EduTuts member",
                "Flo","Emir", "Fatai", 2, EdePolyQuestion.DIFFICULTY_HARD);
        addEdePolyQuestion(q5);
        EdePolyQuestion q6 = new EdePolyQuestion("Which number does Team EduTuts falls on the naijahacks2019 google sheet?",
                "63","12", "89", 1, EdePolyQuestion.DIFFICULTY_HARD);
        addEdePolyQuestion(q6);
    }
    private void addUnilorinQuestion(UnilorinQuestion unilorinQuestion){
        ContentValues cv = new ContentValues();
        cv.put(UnilorinQuestionsTable.COLUMN_QUESTION, unilorinQuestion.getQuestion());
        cv.put(UnilorinQuestionsTable.COLUMN_OPTION1, unilorinQuestion.getOption1());
        cv.put(UnilorinQuestionsTable.COLUMN_OPTION2, unilorinQuestion.getOption2());
        cv.put(UnilorinQuestionsTable.COLUMN_OPTION3, unilorinQuestion.getOption3());
        cv.put(UnilorinQuestionsTable.COLUMN_ANSWER_NR, unilorinQuestion.getAnswerNr());
        cv.put(UnilorinQuestionsTable.COLUMN_DIFFICULTY, unilorinQuestion.getDifficulty());
        db.insert(UnilorinQuestionsTable.TABLE_NAME, null, cv);
    }
    // Ede Poly
    private void addEdePolyQuestion(EdePolyQuestion edePolyQuestion){
        ContentValues cv = new ContentValues();
        cv.put(EdePolyQuestionsTable.COLUMN_QUESTION, edePolyQuestion.getQuestion());
        cv.put(EdePolyQuestionsTable.COLUMN_OPTION1, edePolyQuestion.getOption1());
        cv.put(EdePolyQuestionsTable.COLUMN_OPTION2, edePolyQuestion.getOption2());
        cv.put(EdePolyQuestionsTable.COLUMN_OPTION3, edePolyQuestion.getOption3());
        cv.put(EdePolyQuestionsTable.COLUMN_ANSWER_NR, edePolyQuestion.getAnswerNr());
        cv.put(EdePolyQuestionsTable.COLUMN_DIFFICULTY, edePolyQuestion.getDifficulty());
        db.insert(EdePolyQuestionsTable.TABLE_NAME, null, cv);
    }

    public ArrayList<UnilorinQuestion> getAllUnilorinQuestions(){
        ArrayList<UnilorinQuestion> unilorinQuestionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + UnilorinQuestionsTable.TABLE_NAME, null);
        if (c.moveToFirst()){
            do {
                UnilorinQuestion unilorinQuestion = new UnilorinQuestion();
                unilorinQuestion.setQuestion(c.getString(c.getColumnIndex(UnilorinQuestionsTable.COLUMN_QUESTION)));
                unilorinQuestion.setOption1(c.getString(c.getColumnIndex(UnilorinQuestionsTable.COLUMN_OPTION1)));
                unilorinQuestion.setOption2(c.getString(c.getColumnIndex(UnilorinQuestionsTable.COLUMN_OPTION2)));
                unilorinQuestion.setOption3(c.getString(c.getColumnIndex(UnilorinQuestionsTable.COLUMN_OPTION3)));
                unilorinQuestion.setAnswerNr(c.getInt(c.getColumnIndex(UnilorinQuestionsTable.COLUMN_ANSWER_NR)));
                unilorinQuestion.setDifficulty(c.getString(c.getColumnIndex(UnilorinQuestionsTable.COLUMN_DIFFICULTY)));
                unilorinQuestionList.add(unilorinQuestion);
            }while (c.moveToNext());
        }
        c.close();
        return unilorinQuestionList;
    }

    // Ede poly
    public ArrayList<EdePolyQuestion> getAllEdePolyQuestions(){
        ArrayList<EdePolyQuestion> edePolyQuestionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + EdePolyQuestionsTable.TABLE_NAME, null);
        if (c.moveToFirst()){
            do {
                EdePolyQuestion edePolyQuestion = new EdePolyQuestion();
                edePolyQuestion.setQuestion(c.getString(c.getColumnIndex(EdePolyQuestionsTable.COLUMN_QUESTION)));
                edePolyQuestion.setOption1(c.getString(c.getColumnIndex(EdePolyQuestionsTable.COLUMN_OPTION1)));
                edePolyQuestion.setOption2(c.getString(c.getColumnIndex(EdePolyQuestionsTable.COLUMN_OPTION2)));
                edePolyQuestion.setOption3(c.getString(c.getColumnIndex(EdePolyQuestionsTable.COLUMN_OPTION3)));
                edePolyQuestion.setAnswerNr(c.getInt(c.getColumnIndex(EdePolyQuestionsTable.COLUMN_ANSWER_NR)));
                edePolyQuestion.setDifficulty(c.getString(c.getColumnIndex(EdePolyQuestionsTable.COLUMN_DIFFICULTY)));
                edePolyQuestionList.add(edePolyQuestion);
            }while (c.moveToNext());
        }
        c.close();
        return edePolyQuestionList;
    }

    public ArrayList<UnilorinQuestion> getUnilorinQuestions(String difficulty){
        ArrayList<UnilorinQuestion> unilorinQuestionList = new ArrayList<>();
        db = getReadableDatabase();

        String[] selectionArgs = new String[]{difficulty};
        Cursor c = db.rawQuery("SELECT * FROM " + UnilorinQuestionsTable.TABLE_NAME +
                " WHERE " + UnilorinQuestionsTable.COLUMN_DIFFICULTY + " = ?", selectionArgs);
        if (c.moveToFirst()){
            do {
                UnilorinQuestion unilorinQuestion = new UnilorinQuestion();
                unilorinQuestion.setQuestion(c.getString(c.getColumnIndex(UnilorinQuestionsTable.COLUMN_QUESTION)));
                unilorinQuestion.setOption1(c.getString(c.getColumnIndex(UnilorinQuestionsTable.COLUMN_OPTION1)));
                unilorinQuestion.setOption2(c.getString(c.getColumnIndex(UnilorinQuestionsTable.COLUMN_OPTION2)));
                unilorinQuestion.setOption3(c.getString(c.getColumnIndex(UnilorinQuestionsTable.COLUMN_OPTION3)));
                unilorinQuestion.setAnswerNr(c.getInt(c.getColumnIndex(UnilorinQuestionsTable.COLUMN_ANSWER_NR)));
                unilorinQuestion.setDifficulty(c.getString(c.getColumnIndex(UnilorinQuestionsTable.COLUMN_DIFFICULTY)));
                unilorinQuestionList.add(unilorinQuestion);
            }while (c.moveToNext());
        }
        c.close();
        return unilorinQuestionList;
    }

    // Ede Poly

    public ArrayList<EdePolyQuestion> getEdePolyQuestions(String difficulty){
        ArrayList<EdePolyQuestion> edePolyQuestionList = new ArrayList<>();
        db = getReadableDatabase();

        String[] selectionArgs = new String[]{difficulty};
        Cursor c = db.rawQuery("SELECT * FROM " + EdePolyQuestionsTable.TABLE_NAME +
                " WHERE " + EdePolyQuestionsTable.COLUMN_DIFFICULTY + " = ?", selectionArgs);
        if (c.moveToFirst()){
            do {
                EdePolyQuestion edePolyQuestion = new EdePolyQuestion();
                edePolyQuestion.setQuestion(c.getString(c.getColumnIndex(EdePolyQuestionsTable.COLUMN_QUESTION)));
                edePolyQuestion.setOption1(c.getString(c.getColumnIndex(EdePolyQuestionsTable.COLUMN_OPTION1)));
                edePolyQuestion.setOption2(c.getString(c.getColumnIndex(EdePolyQuestionsTable.COLUMN_OPTION2)));
                edePolyQuestion.setOption3(c.getString(c.getColumnIndex(EdePolyQuestionsTable.COLUMN_OPTION3)));
                edePolyQuestion.setAnswerNr(c.getInt(c.getColumnIndex(EdePolyQuestionsTable.COLUMN_ANSWER_NR)));
                edePolyQuestion.setDifficulty(c.getString(c.getColumnIndex(EdePolyQuestionsTable.COLUMN_DIFFICULTY)));
                edePolyQuestionList.add(edePolyQuestion);
            }while (c.moveToNext());
        }
        c.close();
        return edePolyQuestionList;
    }

}
