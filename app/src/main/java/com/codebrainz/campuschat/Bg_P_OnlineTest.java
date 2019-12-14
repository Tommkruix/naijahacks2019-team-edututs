package com.codebrainz.campuschat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.support.v7.widget.SearchView;

import java.util.ArrayList;

public class Bg_P_OnlineTest extends AppCompatActivity {
    //LinearLayout ll1;
    // CircleImageView imageview_unilorin;
    // Dialog dialogPreviewUnilorin;
    ListView listView;
    Bg_P_ListViewAdapter_OnlineTest adapter;
    String[] title;
    String[] description;
    int[] icon;
    ArrayList<Bg_P_Model_OnlineTest> arrayList = new ArrayList<Bg_P_Model_OnlineTest>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bg_p_online_test);
        // ActionBar actionBar = getSupportActionBar();
        // actionBar.setTitle("Choose University");

       /* Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        }); */
        //changing the status bar color
        if(android.os.Build.VERSION.SDK_INT >= 21){
            Window window= this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDarkStatusQuiz));
        }
        //end changing the status bar color

        title = new String[]{"The Federal Polytechnic Ede"};
        description = new String[]{"Ede City, Osun State"};
        icon = new int[]{R.drawable.fedepe_icon};

        listView = findViewById(R.id.listView);

        for (int i=0; i<title.length; i++){
            Bg_P_Model_OnlineTest model = new Bg_P_Model_OnlineTest(title[i], description[i], icon[i]);
            //bind all strings in an array
            arrayList.add(model);
        }

        //pass resulta to listviewadapter class
        adapter = new Bg_P_ListViewAdapter_OnlineTest(this, arrayList);

        //bind the adapter to the listview
        listView.setAdapter(adapter);

       /* ll1 = findViewById(R.id.ll1);
        imageview_unilorin = findViewById(R.id.imageview_unilorin);
        // setOnClickListener
        ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Bg_U_OnlineTest.this, OnlineTestUnilorin.class);
                startActivity(i);
            }
        });
        imageview_unilorin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogPreviewUnilorin();
            }
        });*/
    }
    /* private void showDialogPreviewUnilorin() {
         dialogPreviewUnilorin = new Dialog(this);
         WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
         lp.copyFrom(dialogPreviewUnilorin.getWindow().getAttributes());
         lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
         lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
         dialogPreviewUnilorin.getWindow().setAttributes(lp);
         dialogPreviewUnilorin.requestWindowFeature(Window.FEATURE_SWIPE_TO_DISMISS);
         dialogPreviewUnilorin.setContentView(R.layout.preview_unilorin_online_test);
         dialogPreviewUnilorin.setTitle("Unilorin");
         dialogPreviewUnilorin.show();
     }*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bg_p_online_test, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView)myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (TextUtils.isEmpty(s)){
                    adapter.filter("");
                    listView.clearTextFilter();
                }else{
                    adapter.filter(s);
                }
                return true;
            }
        });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id==R.id.action_settings){
            return true;
        }
        if (id == R.id.action_help) {
            return true;
        }
        if (id == R.id.action_dashboard) {
            Intent i = new Intent(Bg_P_OnlineTest.this, Bg_OnlineTest.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
}