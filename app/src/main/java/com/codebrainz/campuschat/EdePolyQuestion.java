package com.codebrainz.campuschat;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Tommkruix on 12/11/2019.
 */

public class EdePolyQuestion implements Parcelable {
    public static final String DIFFICULTY_EASY = "Easy";
    public static final String DIFFICULTY_MEDIUM = "Medium";
    public static final String DIFFICULTY_HARD = "Hard";

    private String question;
    private String option1;
    private String option2;
    private String option3;
    private int answerNr;
    private String difficulty;

    public EdePolyQuestion(){}

    public EdePolyQuestion(String question, String option1, String option2,
                           String option3, int answerNr, String difficulty) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.answerNr = answerNr;
        this.difficulty = difficulty;
    }

    protected EdePolyQuestion(Parcel in) {
        question = in.readString();
        option1 = in.readString();
        option2 = in.readString();
        option3 = in.readString();
        answerNr = in.readInt();
        difficulty = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeString(option1);
        dest.writeString(option2);
        dest.writeString(option3);
        dest.writeInt(answerNr);
        dest.writeString(difficulty);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<EdePolyQuestion> CREATOR = new Creator<EdePolyQuestion>() {
        @Override
        public EdePolyQuestion createFromParcel(Parcel in) {
            return new EdePolyQuestion(in);
        }

        @Override
        public EdePolyQuestion[] newArray(int size) {
            return new EdePolyQuestion[size];
        }
    };

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public int getAnswerNr() {
        return answerNr;
    }

    public void setAnswerNr(int answerNr) {
        this.answerNr = answerNr;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
    public static String[] getAllDifficultyLevels() {
        return new String[]{
                DIFFICULTY_EASY,
                DIFFICULTY_MEDIUM,
                DIFFICULTY_HARD
        };
    }
}
