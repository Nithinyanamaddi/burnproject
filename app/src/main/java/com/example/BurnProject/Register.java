package com.example.BurnProject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {

    TextInputEditText editTextEmail, editTextPassword, editTextReenterpassword;
    Button buttonReg;
    ProgressBar progressBar;
    FirebaseAuth mauth;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editTextEmail = findViewById(R.id.email);
        editTextPassword=findViewById(R.id.password);
        editTextReenterpassword=findViewById(R.id.Reenterpassword);
        buttonReg=findViewById(R.id.btn_register);
        progressBar=findViewById(R.id.progressbar);
        textView=findViewById(R.id.registernow);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email,password, reenterpasssword;
                progressBar.setVisibility(View.VISIBLE);
                email=String.valueOf(editTextEmail);
                password=String.valueOf(editTextPassword);
                reenterpasssword=String.valueOf(editTextReenterpassword);
                mauth=FirebaseAuth.getInstance();
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(Register.this,"Enter Email",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(Register.this,"Enter Password",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(reenterpasssword)){
                    Toast.makeText(Register.this,"Enter Re-Enter Password",Toast.LENGTH_SHORT).show();
                    return;
                }

                mauth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if(password!=reenterpasssword){
                                    Toast.makeText(Register.this,"Passwords mismatch",Toast.LENGTH_SHORT).show();
                                }
                                if (task.isSuccessful()) {
                                    Toast.makeText(Register.this,"Account Registered",Toast.LENGTH_SHORT).show();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Register.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}