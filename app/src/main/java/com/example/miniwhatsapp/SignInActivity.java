package com.example.miniwhatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miniwhatsapp.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignInActivity extends AppCompatActivity {

    Button signIn;
    User user;
    TextView switchToSignUp;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    EditText signInEmailText;
    EditText signInPasswordText;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        progressDialog = new ProgressDialog(SignInActivity.this);
        progressDialog.setTitle("Logging");
        progressDialog.setMessage("Please be patience..");
        setIds();
        signIn.setOnClickListener(new SignInFunctionality());
        switchToSignUp.setOnClickListener(new TextViewForTransferToSignInActivity());
    }



    public void setIds(){
        signIn = findViewById(R.id.SignInButton);
        switchToSignUp = findViewById(R.id.SignInTextViewForSignUp);
        signInEmailText = findViewById(R.id.SignInEMailEditText);
        signInPasswordText = findViewById(R.id.SignInPasswordEditText);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    private class SignInFunctionality implements  View.OnClickListener {

        @Override
        public void onClick(View v) {
            progressDialog.show();

            firebaseAuth.signInWithEmailAndPassword(signInEmailText.getText().toString() , signInPasswordText.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if(task.isSuccessful()){
                                userAlreadyLogIn(true);
                                Intent intent = new Intent(SignInActivity.this , MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                Toast.makeText(SignInActivity.this, "Please first create an account", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignInActivity.this , SignUpActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
        }
    }

    private class TextViewForTransferToSignInActivity implements View.OnClickListener{

        @Override
        public void onClick(View v){
            Intent intent = new Intent(SignInActivity.this , SignUpActivity.class);
            startActivity(intent);
            finish();
        }

    }

    public void userAlreadyLogIn(boolean trueOrFalse){
        SharedPreferences preferences = getSharedPreferences("logIn" , MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isDone",trueOrFalse);
        editor.apply();
    }
}