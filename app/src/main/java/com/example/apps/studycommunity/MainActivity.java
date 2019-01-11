package com.example.apps.studycommunity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnStudent, btnExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStudent = findViewById(R.id.btn_student);
        btnExit = findViewById(R.id.btn_exit);

        btnStudent.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
            Intent studentIntent = new Intent(getApplicationContext(),StudentLoginActivity.class);
            startActivity(studentIntent);
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finishAffinity();
    }
}
