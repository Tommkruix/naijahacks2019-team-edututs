package com.codebrainz.campuschat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.appolica.flubber.Flubber;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText UserEmail, UserPassword;
    Button registered_already, btnRegister;

    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;

    private ProgressDialog loadingBar;
    //Spinner spinner_school;
    //private static final String[]paths = {"Select", "Unilorin", "OAU", "Fedpolyede"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        RootRef = FirebaseDatabase.getInstance().getReference();

        InitializeFields();

        //spinner_school = findViewById(R.id.spinner_school);
      /*  ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, paths);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_school.setAdapter(adapter);
        spinner_school.setOnItemSelectedListener(this);*/
        //OnClickListener
        registered_already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Flubber.with()
                        .animation(Flubber.AnimationPreset.SQUEEZE)
                        .repeatCount(1)
                        .duration(10000)
                        .createFor(btnRegister)
                        .start();
                    Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(i);

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               CreateNewAccount();
            }
        });
    }

    private void CreateNewAccount() {
        String email = UserEmail.getText().toString();
        String password = UserPassword.getText().toString();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter email address...", Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter password...", Toast.LENGTH_SHORT).show();
        }
        else{
            loadingBar.setTitle("Creating New Account");
            loadingBar.setMessage("Please wait, while we are creating new account for you...");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                String deviceToken = FirebaseInstanceId.getInstance().getToken();

                                String currentUserId = mAuth.getCurrentUser().getUid();
                                RootRef.child("Users").child(currentUserId).setValue("");

                                RootRef.child("Users").child(currentUserId).child("device_token")
                                        .setValue(deviceToken);

                                SendUserToMainMenuActivity();
                                Toast.makeText(RegisterActivity.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }else{
                                String message = task.getException().toString();
                                Toast.makeText(RegisterActivity.this, "Error : " + message, Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });
        }
    }

    private void InitializeFields() {
        btnRegister = findViewById(R.id.register_button);
        UserEmail = findViewById(R.id.register_email);
        UserPassword = findViewById(R.id.register_password);
        registered_already = findViewById(R.id.registered_already);

        loadingBar = new ProgressDialog(this);
    }

    private void SendUserToLoginActivity(){
       Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
       startActivity(intent);
    }
    private void SendUserToMainMenuActivity(){
        Intent intent = new Intent(RegisterActivity.this, MainMenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        switch (position) {
            case 0:

                break;
            case 1:
                Toast.makeText(RegisterActivity.this, "OAU", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(RegisterActivity.this, "Fedpolyede", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(RegisterActivity.this, "Fedpolyede", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
