package com.example.apps.studycommunity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.apps.studycommunity.Model.StudentModel;
import com.example.apps.studycommunity.Adapter.DataAccess;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StudentRegisterActivity extends AppCompatActivity {
    Button btnSubmit;
    EditText etName, etEmail, etPassword, etPhone, etSchool;
    RadioGroup radioGender, radioLevel;
    RadioButton rbGender, rbLevel;
    Spinner spClass;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);

        final DataAccess dataAccess = DataAccess.getInstance(this);

        btnSubmit = findViewById(R.id.btn_submit);
        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etPhone = findViewById(R.id.et_phone);
        etSchool = findViewById(R.id.et_school);

        radioGender = findViewById(R.id.radio_gender);
        radioLevel = findViewById(R.id.radio_level);

        spClass = findViewById(R.id.sp_class);


        radioLevel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                List<String> list = new ArrayList<>();
                switch (checkedId) {
                    case R.id.radio_junior:
                        list.add("SMP 7");
                        list.add("SMP 8");
                        list.add("SMP 9");
                        break;
                    case R.id.radio_high:
                        list.add("SMA 10 IPA");
                        list.add("SMA 10 IPS");
                        list.add("SMA 11 IPA");
                        list.add("SMA 11 IPS");
                        list.add("SMA 12 IPA");
                        list.add("SMA 12 IPS");
                        break;
                }
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(StudentRegisterActivity.this, android.R.layout.simple_spinner_item, list);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spClass.setAdapter(dataAdapter);
            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                rbGender = findViewById(radioGender.getCheckedRadioButtonId());
                rbLevel = findViewById(radioLevel.getCheckedRadioButtonId());
                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String gender;
                if(rbGender != null)
                    gender = rbGender.getText().toString();
                else
                    gender = "";
                String phone = etPhone.getText().toString();
                String school = etSchool.getText().toString();
                String level;
                if(rbLevel != null)
                    level = rbLevel.getText().toString();
                else
                    level = "";

                String kelas = spClass.getSelectedItem().toString();

                if(name.equals("")||email.equals("")||password.equals("")||gender.equals("")||phone.equals("")||school.equals("")||level.equals("")||kelas.equals("")){
                    Toast.makeText(StudentRegisterActivity.this, "Please input all fields!", Toast.LENGTH_SHORT).show();
                }else {
                    if(isValidEmail(email)) {
                        if(dataAccess.isEmailTaken(email))
                            Toast.makeText(StudentRegisterActivity.this, "Email has already taken!", Toast.LENGTH_SHORT).show();
                        else{
                            if(isAlphanumeric(password)) {
                                if(isNumeric(phone)) {
                                    if(dataAccess.isPhoneTaken(phone))
                                        Toast.makeText(StudentRegisterActivity.this, "Phone number has already taken!", Toast.LENGTH_SHORT).show();
                                    else{
                                        Calendar myCalendar = Calendar.getInstance();
                                        StudentModel studentModel = new StudentModel();
                                        studentModel.setName(name);
                                        studentModel.setEmail(email);
                                        studentModel.setPassword(password);
                                        studentModel.setGender(gender);
                                        studentModel.setPhone(phone);
                                        studentModel.setSchool(school);
                                        studentModel.setLevel(level);
                                        studentModel.setKelas(kelas);
                                        studentModel.setRecovery(getRandomString(12));
                                        boolean isSuccess = true;
                                        try {
                                            dataAccess.addStudent(studentModel);
                                        } catch (Exception e) {
                                            isSuccess = false;
                                            Toast.makeText(StudentRegisterActivity.this, "Failed to add data", Toast.LENGTH_SHORT).show();
                                        }

                                        if (isSuccess) {
                                            AlertDialog.Builder msgBox = new AlertDialog.Builder(StudentRegisterActivity.this);
                                            msgBox.setTitle("Succesfully registered!");
                                            msgBox.setMessage("Your recovery code :\n" + studentModel.getRecovery() + "\n\nThis code will be used when you forget your password");
                                            msgBox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int whichButton) {
                                                    Intent intent = new Intent(getApplicationContext(), StudentLoginActivity.class);
                                                    startActivity(intent);
                                                }
                                            });
                                            msgBox.show();
                                        }
                                    }
                                }
                                else{
                                    Toast.makeText(StudentRegisterActivity.this, "Phone must be numeric!",Toast.LENGTH_SHORT).show();

                                }
                            }
                            else{
                                Toast.makeText(StudentRegisterActivity.this, "Password must be alphanumeric!",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    else{
                        Toast.makeText(StudentRegisterActivity.this, "Invalid email pattern",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    private static final String ALLOWED_CHARACTERS ="0123456789qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";

    private static String getRandomString(final int sizeOfRandomString)
    {
        final Random random=new Random();
        final StringBuilder sb=new StringBuilder(sizeOfRandomString);
        for(int i=0;i<sizeOfRandomString;++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }
    private boolean isValidEmail(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
    private boolean isAlphanumeric(String password){
        String passwordRegex = "^[a-zA-Z0-9]+$";

        Pattern pattern = Pattern.compile(passwordRegex);

        Matcher matcher = pattern.matcher(password);
        return matcher.matches();

    }
    private boolean isNumeric(String phone){
        String phoneRegex = "^[0-9]+$";

        Pattern pattern = Pattern.compile(phoneRegex);

        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }


}
