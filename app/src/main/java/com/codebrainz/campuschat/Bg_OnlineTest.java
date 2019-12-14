package com.codebrainz.campuschat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.appolica.flubber.Flubber;

public class Bg_OnlineTest extends AppCompatActivity {
    LinearLayout ll1,ll2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_bg_online_test);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(android.os.Build.VERSION.SDK_INT >= 21){
            Window window= this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDarkStatusQuiz));
        }
        ll1 = findViewById(R.id.ll1);
        //OnClickListener
        ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Bg_OnlineTest.this, Bg_U_OnlineTest.class);
                startActivity(i);

            }
        });
        ll2 = findViewById(R.id.ll2);
        //OnClickListener
        ll2.setOnClickListener(new View.OnClickListener(){
           public void onClick(View view){
               Intent i = new Intent(Bg_OnlineTest.this, Bg_P_OnlineTest.class);
               startActivity(i);
           }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bg_online_test, menu);
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
            return true;
        }
        if (id == R.id.action_dashboard) {
            Intent i = new Intent(Bg_OnlineTest.this, MainMenuActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
    public void onBackPressed() {
        Intent i = new Intent(Bg_OnlineTest.this, MainMenuActivity.class);
        startActivity(i);
    }
}
