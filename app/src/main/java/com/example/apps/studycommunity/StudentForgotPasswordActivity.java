package com.example.apps.studycommunity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apps.studycommunity.Adapter.DataAccess;

public class StudentForgotPasswordActivity extends AppCompatActivity {
    EditText etEmail, etRecovery, etNewPassword, etConfirmNewPassword;
    Button btnSubmit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_forgot_password);
        final DataAccess dataAccess = DataAccess.getInstance(this);

        etEmail = findViewById(R.id.et_email);
        etRecovery = findViewById(R.id.et_recovery);
        etNewPassword = findViewById(R.id.et_newPassword);
        etConfirmNewPassword = findViewById(R.id.et_confirmNewPassword);

        btnSubmit = findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                //check email & current password
                String email = etEmail.getText().toString();
                String password = etRecovery.getText().toString();
                String newPassword = etNewPassword.getText().toString();
                String confirmNewPassword = etConfirmNewPassword.getText().toString();
                if(email.equals("")||password.equals("")||newPassword.equals("")||confirmNewPassword.equals(""))
                    Toast.makeText(StudentForgotPasswordActivity.this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
                else{
                    String validateResult = dataAccess.studentForgotPasswordValidation(email,password);
                    if(validateResult.equals("false cursor"))
                        Toast.makeText(StudentForgotPasswordActivity.this, "Failed to load data!", Toast.LENGTH_LONG).show();
                    else if(validateResult.equals("false email"))
                        Toast.makeText(StudentForgotPasswordActivity.this, "Invalid email!", Toast.LENGTH_SHORT).show();
                    else if(validateResult.equals("false code"))
                        Toast.makeText(StudentForgotPasswordActivity.this, "Invalid recovery code!", Toast.LENGTH_SHORT).show();
                    else{
                        String updateResult = dataAccess.updateNewPassword(email,newPassword,confirmNewPassword);
                        if(updateResult.equals("false update"))
                            Toast.makeText(StudentForgotPasswordActivity.this, "Failed to update!", Toast.LENGTH_LONG).show();
                        else if(updateResult.equals("false confirm"))
                            Toast.makeText(StudentForgotPasswordActivity.this, "Confirm Password doesn't match!", Toast.LENGTH_SHORT).show();
                        else{
                            AlertDialog.Builder msgBox = new AlertDialog.Builder(StudentForgotPasswordActivity.this);
                            msgBox.setTitle("Succesfully changed!");
                            msgBox.setMessage("Your password has succesfully changed!\n\nNow, your password is :\n"+newPassword);
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

            }
        });
    }

}
