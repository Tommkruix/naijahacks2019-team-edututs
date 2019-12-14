package com.codebrainz.campuschat;

import android.provider.BaseColumns;

/**
 * Created by Tommkruix on 12/11/2019.
 */

public final class EdePolyTestContract {

    private EdePolyTestContract() {}

    public static class EdePolyQuestionsTable implements BaseColumns{
        public static final String TABLE_NAME = "edepoly_test_questions";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_OPTION1 = "option1";
        public static final String COLUMN_OPTION2 = "option2";
        public static final String COLUMN_OPTION3 = "option3";
        public static final String COLUMN_ANSWER_NR = "answer_nr";
        public static final String COLUMN_DIFFICULTY = "difficulty";
    }
}
