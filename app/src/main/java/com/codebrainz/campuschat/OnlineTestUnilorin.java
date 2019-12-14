package com.codebrainz.campuschat ;

/**
 * Created by Tommkruix on 12/11/2019.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.*;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.appolica.flubber.Flubber;

import com.codebrainz.campuschat.OnlineTestUnilorinQ1.*;

public class OnlineTestUnilorin extends AppCompatActivity  {
    private static final int REQUEST_CODE_TEST = 1;
    public static final String EXTRA_DIFFICULTY = "extraDifficulty";

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_HIGHSCORE = "keyHighscore";

    private TextView textViewHighscore;
    private Spinner spinnerDifficulty;

    private int highscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_online_test_unilorin);

        textViewHighscore = findViewById(R.id.textView_highscore);
        spinnerDifficulty = findViewById(R.id.spinner_difficulty);

        String[] difficultyLevels = UnilorinQuestion.getAllDifficultyLevels();

        ArrayAdapter<String> adapterDifficulty = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item , difficultyLevels);
        adapterDifficulty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDifficulty.setAdapter(adapterDifficulty);
        loadHighscore();

        final Button btn_start = findViewById(R.id.btn_start);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Flubber.with()
                        .animation(Flubber.AnimationPreset.ZOOM_OUT)
                        .repeatCount(1)
                        .duration(3000)
                        .createFor(btn_start)
                        .start();
                startTest();
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

        //changing the status bar color
        if(android.os.Build.VERSION.SDK_INT >= 21){
            Window window= this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDarkStatusQuiz));
        }
        //end changing the status bar color
    }
    private void startTest() {
        String difficulty = spinnerDifficulty.getSelectedItem().toString();
        Intent i = new Intent(OnlineTestUnilorin.this, OnlineTestUnilorinQ1.class);
        i.putExtra(EXTRA_DIFFICULTY, difficulty);
        startActivityForResult(i, REQUEST_CODE_TEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_TEST){
            if(resultCode == RESULT_OK){
                int score = data.getIntExtra(OnlineTestUnilorinQ1.EXTRA_SCORE, 0);
                if(score > highscore){
                    updateHighScore(score);
                }
            }
        }
    }
    private void loadHighscore(){
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        highscore = prefs.getInt(KEY_HIGHSCORE, 0);
            textViewHighscore.setText("Highscore: " + highscore);
    }
    private void updateHighScore(int highscoreNew){
        highscore = highscoreNew;
        textViewHighscore.setText("Highscore: " + highscore);

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_HIGHSCORE, highscore);
        editor.apply();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_online_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_help) {
            // To Do
        }
        if (id == R.id.action_dashboard) {
            Intent i = new Intent(OnlineTestUnilorin.this, Bg_OnlineTest.class);
            startActivity(i);

        }

        return super.onOptionsItemSelected(item);
    }
    boolean doubleBackToExitPressedOnce;
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
    }

}
