package com.codebrainz.campuschat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.appolica.flubber.Flubber;
import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;
//import com.codebrainz.campuschat.ChatMainActivity;

public class MainMenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    CircleImageView profile_image;
    TextView username;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;

    private ShimmerFrameLayout mShimmerViewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        RootRef = FirebaseDatabase.getInstance().getReference();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);

        /*hel = findViewById(R.id.hel);
        //OnClickListener
        hel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Flubber.with()
                        .animation(Flubber.AnimationPreset.SQUEEZE)
                        .repeatCount(3)
                        .duration(3000)
                        .createFor(hel)
                        .start();
            }
        });*/

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);

       // View navHeaderView = navigationView.getHeaderView(0);
      /*  profile_image = navigationView.getHeaderView(0).findViewById(R.id.ref_profile_image);
        username = navigationView.getHeaderView(0).findViewById(R.id.ref_username);
       // profile_image = findViewById(R.id.ref_profile_image);
       // username = findViewById(R.id.ref_username);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                username.setText(user.getUsername());
               // username.setText(dataSnapshot.child("username").getValue().toString());
                if (user.getImageURL().equals("default")){
                    profile_image.setImageResource(R.drawable.a);
                }else{
                    Glide.with(MainMenuActivity.this).load(user.getImageURL()).into(profile_image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
    }
    @Override
    protected void onStart() {
        super.onStart();

        if(currentUser == null){
            SendUserToLoginActivity();
        }else{
            VerifyUserExistence();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmer();
    }

    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmer();
        super.onPause();
    }

    private void VerifyUserExistence() {
        String currentUserID = mAuth.getCurrentUser().getUid();

        RootRef.child("Users").child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if ((dataSnapshot.child("name").exists())){
                    //Toast.makeText(MainMenuActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                    Snackbar.make(findViewById(R.id.main_menu), "Welcome", Snackbar.LENGTH_LONG)
                            .setAction("John Doe", null).show();
                }else{
                    SendUserToAccountActivity();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void SendUserToAccountActivity() {
        Intent intent = new Intent(MainMenuActivity.this, MainAccountEditActivity.class);
        startActivity(intent);
    }

    private void SendUserToLoginActivity() {
        Intent intent = new Intent(MainMenuActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
    boolean doubleBackToExitPressedOnce;
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        if(doubleBackToExitPressedOnce){
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = false;
       /* Intent i = new Intent(MainMenuActivity.this, MainMenuActivity.class);
        startActivity(i);*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 1000); //Change time interval here if you want
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_account) {
            Intent intent = new Intent(MainMenuActivity.this, MainAccountActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_logout) {
            mAuth.signOut();
            SendUserToLoginActivity();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {
            Intent i = new Intent(MainMenuActivity.this, MainMenuActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_chat) {
           Intent i = new Intent(MainMenuActivity.this, ChatMainActivity.class);
           startActivity(i);
        } else if (id == R.id.nav_online_test) {
            Intent i = new Intent(MainMenuActivity.this, Bg_OnlineTest.class);
            startActivity(i);
        } else if (id == R.id.nav_tour_schools) {
            Intent i = new Intent(MainMenuActivity.this, Bg_TourSchool.class);
            startActivity(i);

        }  else if (id == R.id.nav_blog) {

        }
          else if (id == R.id.nav_share) {

        }
          else if (id == R.id.nav_about) {

        }
          else if (id == R.id.nav_help) {

        }
          else if (id == R.id.nav_feedback) {

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
