package com.example.miniwhatsapp;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.miniwhatsapp.Models.User;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    User user;
    Intent intent;
    Button signUp , googleSignUp;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    EditText signUpEmail;
    EditText signUpPassword;
    EditText signUpName;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setTitle("Account Creating....");
        progressDialog.setMessage("we are working on your account creation");
        setIds();


        signUp.setOnClickListener(new SignUpFunctionality());



    }



    public void setIds(){
        signUp = findViewById(R.id.SignUpButton);
        signUpEmail = findViewById(R.id.SignUpEMailEditText);
        signUpPassword = findViewById(R.id.SignUpPasswordEditText);
        signUpName = findViewById(R.id.SignUpNameEditText);
        googleSignUp = findViewById(R.id.SignUpWithGoogleButton);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    private class SignUpFunctionality implements View.OnClickListener{
        @Override
        public void onClick(View v){
            progressDialog.show();

            firebaseAuth.createUserWithEmailAndPassword(signUpEmail.getText().toString() , signUpPassword.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if(task.isSuccessful()){
                                userAlreadyLogIn(true);
                                user = new User(signUpName.getText().toString() , signUpPassword.getText().toString() , signUpEmail.getText().toString());
                                String uId = task.getResult().getUser().getUid();
                                intent = new Intent(SignUpActivity.this , MainActivity.class);
                                firebaseDatabase.getReference().child("Users").child(uId).setValue(user);
                            }
                            else{
                                Toast.makeText(SignUpActivity.this, "You already have an account...", Toast.LENGTH_SHORT).show();
                                intent = new Intent(SignUpActivity.this , SignInActivity.class);
                            }
                            startActivity(intent);
                            finish();
                        }
                    });
        }
    }

    public void userAlreadyLogIn(boolean trueOrFalse){
        SharedPreferences preferences = getSharedPreferences("logIn" , MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isDone",trueOrFalse);
        editor.apply();
    }

}