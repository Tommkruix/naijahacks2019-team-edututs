package com.codebrainz.campuschat;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.appolica.flubber.Flubber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class OnlineTestEdePolyQ1 extends AppCompatActivity {
    Dialog dialogThankYou;
    private TextView textView_thankyou;
    private TextView thankyou_score;
    private TextView grade_text;

    public static final String EXTRA_SCORE = "extraScore";
    private static final long COUNTDOWN_IN_MILLIS = 30000;

    private static final String KEY_SCORE = "keyScore";
    private static final String KEY_QUESTION_COUNT = "keyQuestionCount";
    private static final String KEY_MILLIS_LEFT = "keyMillisLeft";
    private static final String KEY_ANSWERED = "keyAnswered";
    private static final String KEY_QUESTION_LIST = "keyQuestionlist";

    private TextView textViewQuestion;
    private TextView textViewScore;
    private TextView textViewQuestionCount;
    private TextView textViewDifficulty;
    private TextView textViewCountDown;
    private RadioGroup rbGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private Button buttonConfirmNext;

    private ColorStateList textColorDefaultRb;
    private ColorStateList textColorDefaultCd;

    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;

    private ArrayList<EdePolyQuestion> edePolyQuestionList;
    private int questionCounter;
    private int questionCountTotal;
    private EdePolyQuestion currentQuestion;

    private int score;
    private boolean answered;

    private long backPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_q1_online_test_edepoly);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //changing the status bar color
        if(android.os.Build.VERSION.SDK_INT >= 21){
            Window window= this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDarkStatusQuiz));
        }
        //end changing the status bar color

        textViewQuestion = findViewById(R.id.textView_question);
        textViewScore = findViewById(R.id.textView_score);
        textViewQuestionCount = findViewById(R.id.textView_question_count);
        textViewDifficulty = findViewById(R.id.textView_difficulty);
        textViewCountDown = findViewById(R.id.textView_countdown);
        rbGroup = findViewById(R.id.radio_group);
        rb1 = findViewById(R.id.radio_button1);
        rb2 = findViewById(R.id.radio_button2);
        rb3 = findViewById(R.id.radio_button3);
        buttonConfirmNext = findViewById(R.id.button_confirm_next);
        textColorDefaultRb = rb1.getTextColors();
        textColorDefaultCd = textViewCountDown.getTextColors();

        Intent intent = getIntent();
        String difficulty = intent.getStringExtra(OnlineTestEdePoly.EXTRA_DIFFICULTY);

        textViewDifficulty.setText("Test Mode: " + difficulty);

        if(savedInstanceState == null) {
            OnlineTestDbHelper onlineTestDbHelper = new OnlineTestDbHelper(this);
            edePolyQuestionList = onlineTestDbHelper.getEdePolyQuestions(difficulty);
            questionCountTotal = edePolyQuestionList.size();
            Collections.shuffle(edePolyQuestionList);

            showNextQuestion();
        }else {
            edePolyQuestionList = savedInstanceState.getParcelableArrayList(KEY_QUESTION_LIST);
            /*if (unilorinQuestionList == null){
                finish();
            }*/
            questionCountTotal = edePolyQuestionList.size();
            questionCounter = savedInstanceState.getInt(KEY_QUESTION_COUNT);
            currentQuestion = edePolyQuestionList.get(questionCounter - 1);
            score = savedInstanceState.getInt(KEY_SCORE);
            timeLeftInMillis = savedInstanceState.getLong(KEY_MILLIS_LEFT);
            answered = savedInstanceState.getBoolean(KEY_ANSWERED);

            if (!answered){
                startCountDown();
            }else{
                updateCountDownText();
                showSolution();
            }
        }
        //OnClickListener
        buttonConfirmNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Flubber.with()
                        .animation(Flubber.AnimationPreset.SHAKE)
                        .repeatCount(0)
                        .duration(500)
                        .createFor(buttonConfirmNext)
                        .start();
                if(!answered) {
                    if(rb1.isChecked() || rb2.isChecked() || rb3.isChecked()){
                        checkAnswer();
                    } else {
                        Toast.makeText(OnlineTestEdePolyQ1.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                    }
                } else {
                   showNextQuestion();
                }

            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void showThankYouDialog() {
        dialogThankYou = new Dialog(this);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogThankYou.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogThankYou.getWindow().setAttributes(lp);
        dialogThankYou.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogThankYou.setContentView(R.layout.dialog_finish_online_test);
        dialogThankYou.setTitle("Online Test - Thank You");
        textView_thankyou = dialogThankYou.findViewById(R.id.textView_thankyou);
        thankyou_score = dialogThankYou.findViewById(R.id.thankyou_score);
        grade_text = dialogThankYou.findViewById(R.id.grade_text);
        double ave = questionCountTotal >> 1;
        int pg;
        if(ave <= score ){
            pg = 91;
        }else{
            pg = 60;
        }

        grade_text.setText("You've successfully attempt the CampusChat Online Test. We assured you " + Math.round((score*pg)/questionCountTotal) + "% of getting this score in your EdePoly Realtime Examination.");
        thankyou_score.setText("Your Grade Score is: " + Math.round((score*100)/questionCountTotal) + "%");
        textView_thankyou.setEnabled(true);
        textView_thankyou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext() , "Thank you for participating.", Toast.LENGTH_LONG).show();
                dialogThankYou.cancel();
                Intent resultIntent = new Intent();
                resultIntent.putExtra(EXTRA_SCORE, score);
                setResult(RESULT_OK, resultIntent);
                finish();
                //Intent i = new Intent(OnlineTestUnilorinQ1.this, OnlineTestUnilorin.class);
                //startActivity(i);
            }
        });
        dialogThankYou.setCancelable(false);
        dialogThankYou.show();
    }
    private void showNextQuestion() {
        rb1.setTextColor(textColorDefaultRb);
        rb2.setTextColor(textColorDefaultRb);
        rb3.setTextColor(textColorDefaultRb);
        rbGroup.clearCheck();

        if(questionCounter < questionCountTotal){
            currentQuestion = edePolyQuestionList.get(questionCounter);

            textViewQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());

            questionCounter++;
            textViewQuestionCount.setText("Question: " + questionCounter + "/" + questionCountTotal);
            answered = false;
            buttonConfirmNext.setText("Confirm");

            timeLeftInMillis = COUNTDOWN_IN_MILLIS;
            startCountDown();
        } else {
            finishTest();
        }
    }

    private void startCountDown() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                updateCountDownText();
                checkAnswer();
            }
        }.start();
    }
    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        textViewCountDown.setText(timeFormatted);

        if(timeLeftInMillis < 10000){
            textViewCountDown.setTextColor(Color.RED);
        }else{
            textViewCountDown.setTextColor(textColorDefaultCd);
        }
    }
    private void checkAnswer() {
        answered = true;

        countDownTimer.cancel();

        RadioButton rbSelected = findViewById(rbGroup.getCheckedRadioButtonId());
        int answerNr = rbGroup.indexOfChild(rbSelected) + 1;

        if (answerNr == currentQuestion.getAnswerNr()){
            score++;
            textViewScore.setText("Score: " + score);
        }

        showSolution();
    }
    private void showSolution(){
        rb1.setTextColor(Color.RED);
        rb2.setTextColor(Color.RED);
        rb3.setTextColor(Color.RED);

        switch (currentQuestion.getAnswerNr()){
            case 1:
                rb1.setTextColor(Color.GREEN);
                textViewQuestion.setText("Option 1 is correct!");
                break;
            case 2:
                rb2.setTextColor(Color.GREEN);
                textViewQuestion.setText("Option 2 is correct!");
                break;
            case 3:
                rb3.setTextColor(Color.GREEN);
                textViewQuestion.setText("Option 3 is correct!");
                break;
        }
        if(questionCounter < questionCountTotal){
            buttonConfirmNext.setText("Next");
        }else{
            buttonConfirmNext.setText("Finish");
        }
    }

    private void finishTest() {

        /*Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_SCORE, score);
        setResult(RESULT_OK, resultIntent);
        finish();*/
        showThankYouDialog();
    }

   /* boolean doubleBackToExitPressedOnce;
    @Override
    public void onBackPressed(){
        if(doubleBackToExitPressedOnce){
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = false;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000); //Change time interval here if you want
    }*/
   @Override
   public void onBackPressed() {
       if(backPressedTime + 2000 > System.currentTimeMillis()){
           finishTest();
       }else{
           Toast.makeText(this, "Press again to finish", Toast.LENGTH_SHORT).show();
       }
       backPressedTime = System.currentTimeMillis();
   }
    @Override
    protected void onDestroy(){
       super.onDestroy();
       if(countDownTimer != null){
           countDownTimer.cancel();
       }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_SCORE, score);
        outState.putInt(KEY_QUESTION_COUNT, questionCounter);
        outState.putLong(KEY_MILLIS_LEFT, timeLeftInMillis);
        outState.putBoolean(KEY_ANSWERED, answered);
        outState.putParcelableArrayList(KEY_QUESTION_LIST, edePolyQuestionList);
    }
}
