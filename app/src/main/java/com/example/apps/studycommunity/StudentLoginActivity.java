package com.example.apps.studycommunity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apps.studycommunity.Adapter.DataAccess;

public class StudentLoginActivity extends AppCompatActivity {
    Button btnRegister, btnLogin;
    TextView tvForgotPassword;
    EditText etEmail, etPassword;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
        final DataAccess dataAccess = DataAccess.getInstance(this);

        btnRegister = findViewById(R.id.btn_register);
        tvForgotPassword = findViewById(R.id.tv_forgotPassword);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);

        btnRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent studentRegisterIntent = new Intent(getApplicationContext(),StudentRegisterActivity.class);
                startActivity(studentRegisterIntent);
            }
        });

        tvForgotPassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent studentForgotPasswordIntent = new Intent(getApplicationContext(),StudentForgotPasswordActivity.class);
                startActivity(studentForgotPasswordIntent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                if(email.equals("")||password.equals(("")))
                    Toast.makeText(StudentLoginActivity.this,"Please fill all fields!", Toast.LENGTH_SHORT).show();
                else{
                    String auth = dataAccess.studentLoginValidation(email,password);
                    if(auth.equals("false cursor")){
                        Toast.makeText(StudentLoginActivity.this, "Failed to load data!", Toast.LENGTH_LONG).show();
                    }
                    else if(auth.equals("false email")){
                        Toast.makeText(StudentLoginActivity.this, "Invalid email!", Toast.LENGTH_SHORT).show();
                    }
                    else if(auth.equals("false password")){
                        Toast.makeText(StudentLoginActivity.this, "Invalid password!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Intent studentMenuIntent = new Intent(getApplicationContext(),StudentMenuActivity.class);
                        studentMenuIntent.putExtra("studentId",auth);
                        startActivity(studentMenuIntent);
                    }
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent homeIntent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(homeIntent);
    }
}
