package com.example.finalversion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;

    private EditText editName, editAge, editEmail, editPassword;
    private ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mAuth = FirebaseAuth.getInstance();

        TextView banner = (TextView) findViewById(R.id.banner);
        banner.setOnClickListener(this);

        TextView registerUser = (Button) findViewById(R.id.registerUser);
        registerUser.setOnClickListener(this);

        editName = (EditText) findViewById(R.id.Username);
        editAge = (EditText) findViewById(R.id.age);
        editEmail = (EditText) findViewById(R.id.email);
        editPassword = (EditText) findViewById(R.id.password);

        progressbar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.banner:
                startActivity(new Intent(Register.this, MainActivity.class));
                break;
            case R.id.registerUser:
                registerUser();
                break;

        }
    }

    private void registerUser() {
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        String name = editName.getText().toString().trim();
        String age = editAge.getText().toString().trim();

        if(name.isEmpty()){
            editName.setError("Username cannot be empty!");
            editName.requestFocus();
            return;
        }
        if(email.isEmpty()){
            editEmail.setError("Email cannot be empty!");
            editEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editPassword.setError("Password cannot be empty!");
            editPassword.requestFocus();
            return;
        }
        if(age.isEmpty()){
            editAge.setError("Age cannot be empty!");
            editAge.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editEmail.setError("Email has to be valid (ex. xxx@gmail.com)");
            editEmail.requestFocus();
            return;
        }
        if(password.length() < 6){
            editPassword.setError("Password has to be greater than 6 numbers");
            editPassword.requestFocus();
            return;
        }

        progressbar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(name, age, email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(Register.this, "User has been registered", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(Register.this, "Failed to register!", Toast.LENGTH_LONG).show();

                                    }
                                    progressbar.setVisibility(View.GONE);
                                }
                            });
                        }else {
                            Toast.makeText(Register.this, "Failed to register! AAAA", Toast.LENGTH_LONG).show();
                            progressbar.setVisibility(View.GONE);

                        }
                    }
                });

    }
}