package com.example.apps.studycommunity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apps.studycommunity.Adapter.DataAccess;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class StudentPaymentActivity extends AppCompatActivity {
    TextView icCalendar, tvDueDate, tvAmount;
    Button btnBack;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_payment);
        final DataAccess dataAccess = DataAccess.getInstance(this);

        icCalendar = findViewById(R.id.ic_calendar);
        tvDueDate = findViewById(R.id.tv_showDueDate);
        tvAmount = findViewById(R.id.tv_showAmount);
        btnBack = findViewById(R.id.btn_back);

        final String id = getIntent().getStringExtra("studentId");

        final Calendar myCalendar = Calendar.getInstance();
        String selectedDate = dateSetter(myCalendar);
        paymentSetter(id,selectedDate, dataAccess);

        icCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String selectedDate = dateSetter(myCalendar);
                        paymentSetter(id,selectedDate, dataAccess);
                    }

                };
                new DatePickerDialog(StudentPaymentActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backIntent = new Intent(getApplicationContext(),StudentMenuActivity.class);
                backIntent.putExtra("studentId",id);
                startActivity(backIntent);
            }
        });

    }
    private String dateSetter(Calendar myCalendar){
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        String dateText = sdf.format(myCalendar.getTime());
        tvDueDate.setText(dateText);
        return dateText;
    }

    private void paymentSetter(String id, String tanggal, DataAccess dataAccess){
        String amount = dataAccess.getPaymentAmount(id,tanggal);
        if(amount.equals("false cursor")){
            Toast.makeText(StudentPaymentActivity.this, "Failed to load data!", Toast.LENGTH_LONG).show();
        }
        else{
            tvAmount.setText(amount);
        }
    }

}
