package com.codebrainz.campuschat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainAccountActivity extends AppCompatActivity {
    private Button btn_edit;
    private TextView userName, userStatus;
    private CircleImageView userProfileImage;

    private String currentUserID;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;

    private StorageReference UserProfileImagesRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_account);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        RootRef = FirebaseDatabase.getInstance().getReference();

        UserProfileImagesRef = FirebaseStorage.getInstance().getReference().child("Profile Images");

        InitializeFields();

        RetrieveUserInfo();


        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendUserToMainAccountEditActivity();
            }
        });
    }

    private void InitializeFields() {
        btn_edit = findViewById(R.id.btn_edit);
        userName = findViewById(R.id.userName);
        userStatus = findViewById(R.id.userStatus);
        userProfileImage = findViewById(R.id.set_profile_image);

    }

    private void RetrieveUserInfo() {

        RootRef.child("Users").child(currentUserID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if((dataSnapshot).exists() && (dataSnapshot.hasChild("image"))){
                            String retrieveUserName = dataSnapshot.child("name").getValue().toString();
                            String retrieveUserStatus = dataSnapshot.child("status").getValue().toString();
                            String retrieveUserProfileImage = dataSnapshot.child("image").getValue().toString();

                            Picasso.get().load(retrieveUserProfileImage).placeholder(R.drawable.a).into(userProfileImage);
                            userName.setText(retrieveUserName);
                            userStatus.setText(retrieveUserStatus);



                        }else{
                            String retrieveUserName = dataSnapshot.child("name").getValue().toString();
                            String retrieveUserStatus = dataSnapshot.child("status").getValue().toString();

                            userName.setText(retrieveUserName);
                            userStatus.setText(retrieveUserStatus);


                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) { }
                });
    }
    private void SendUserToMainAccountEditActivity(){
        Intent intent = new Intent(MainAccountActivity.this, MainAccountEditActivity.class);
       startActivity(intent);
    }
}
