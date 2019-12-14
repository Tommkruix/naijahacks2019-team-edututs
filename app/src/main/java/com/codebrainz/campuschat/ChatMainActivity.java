package com.codebrainz.campuschat;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChatMainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ViewPager myViewPager;
    private TabLayout myTabLayout;
    private ChatTabsAccessorAdapter myChatTabsAccessorAdapter;

    private String currentUserID;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_main);

        mToolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("CampusChat");

        myViewPager = findViewById(R.id.main_tabs_pager);
        myChatTabsAccessorAdapter = new ChatTabsAccessorAdapter(getSupportFragmentManager());
        myViewPager.setAdapter(myChatTabsAccessorAdapter);

        myTabLayout = findViewById(R.id.main_tabs);
        myTabLayout.setupWithViewPager(myViewPager);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        RootRef = FirebaseDatabase.getInstance().getReference();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu_chat_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.action_profile){
            SendUserToProfileActivity();
        }
        if (item.getItemId() == R.id.action_find_friends){
            Intent intent = new Intent(ChatMainActivity.this, FindFriendsActivity.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.action_create_group_option){
            RequestNewGroup();
        }
        if (item.getItemId() == R.id.action_dashboard){
            Intent intent = new Intent(ChatMainActivity.this, MainMenuActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        return true;
    }

    private void RequestNewGroup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ChatMainActivity.this, R.style.AlertDialog);
        builder.setTitle("Enter Group Name :");

        final EditText groupNameField = new EditText(ChatMainActivity.this);
        groupNameField.setHint("E.g Computer Scientist");
        builder.setView(groupNameField);

        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
           String groupName = groupNameField.getText().toString();

           if(TextUtils.isEmpty(groupName)){
               Toast.makeText(ChatMainActivity.this, "Please write a Group Name...", Toast.LENGTH_SHORT).show();
           }else{
                CreateNewGroup(groupName);
           }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.show();
    }

    private void CreateNewGroup(final String groupName) {
        RootRef.child("Groups").child(groupName).setValue("")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(ChatMainActivity.this, "Group " + groupName + " is Created Successfully...", Toast.LENGTH_SHORT).show();
                    }
                    }
                });
    }

    private void SendUserToProfileActivity() {
        Intent intent = new Intent(ChatMainActivity.this, MainAccountActivity.class);
        startActivity(intent);
    }
}
