package com.example.apps.studycommunity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.apps.studycommunity.Adapter.DataAccess;

public class StudentMenuActivity extends AppCompatActivity {
    TextView tvGreet;
    Button btnSchedule, btnPayment, btnLogout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_menu);
        DataAccess dataAccess = DataAccess.getInstance(this);
        final String id = getIncomingIntent(dataAccess);
        btnSchedule = findViewById(R.id.btn_schedule);
        btnPayment = findViewById(R.id.btn_payment);
        btnLogout = findViewById(R.id.btn_logout);
        btnSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent scheduleIntent = new Intent(getApplicationContext(), StudentScheduleActivity.class);
                scheduleIntent.putExtra("studentId",id);
                startActivity(scheduleIntent);
            }
        });
        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent paymentIntent = new Intent(getApplicationContext(), StudentPaymentActivity.class);
                paymentIntent.putExtra("studentId",id);
                startActivity(paymentIntent);
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity();
                Intent logoutIntent = new Intent(getApplicationContext(), StudentLoginActivity.class);
                startActivity(logoutIntent);
            }
        });

    }
    private String getIncomingIntent(DataAccess dataAccess){
        String id = getIntent().getStringExtra("studentId");
        String name = dataAccess.getStudentName(id);
        tvGreet = findViewById(R.id.tv_greet);
        tvGreet.setText("Welcome, " + name + "!");
        return id;
    }
}
