package com.example.apps.studycommunity.Adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.apps.studycommunity.Model.ScheduleModel;
import com.example.apps.studycommunity.Model.StudentModel;

public class DataAccess {

    private SQLiteOpenHelper openHelper;
    private static DataAccess instance;

    private DataAccess(Context context){
        this.openHelper = new DataOpenHelper(context);
    }

    public static DataAccess getInstance(Context context){
        if(instance == null){
            instance = new DataAccess(context);
        }
        return instance;
    }

    public void addStudent(StudentModel studentModel){

        SQLiteDatabase db  = openHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Name", studentModel.getName());
        values.put("Email", studentModel.getEmail());
        values.put("Password", studentModel.getPassword());
        values.put("Gender", studentModel.getGender());
        values.put("Phone", studentModel.getPhone());
        values.put("School", studentModel.getSchool());
        values.put("Level", studentModel.getLevel());
        values.put("Class", studentModel.getKelas());
        values.put("RecoveryCode", studentModel.getRecovery());

        db.insert("msStudent", null, values);
        db.close();
    }
    public String studentForgotPasswordValidation(String email, String code) {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        //check if email is correct
        Cursor cursor = db.query("msStudent", new String[] { "IDStudent", "RecoveryCode" }, "Email" + "=?",
                new String[] { email }, null, null, null, null);
        if (cursor != null) {
            boolean cond = cursor.moveToFirst();
            if(!cond)
                return "false email";
            else{
                // check if code is correct
                if(cursor.getString(1).equals(code))
                    return "true";
                else
                    return "false code";
            }
        }
        else return "false cursor";

    }

    public String studentLoginValidation(String email, String password){
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.query("msStudent", new String[]{"IDStudent", "Password"}, "Email" + "=?",
                new String[]{ email },null,null,null,null);
        if(cursor != null){
            boolean cond = cursor.moveToFirst();
            if(!cond)
                return "false email";
            else{
                if(cursor.getString(1).equals(password))
                    return cursor.getString(0);
                else
                    return "false password";
            }
        }
        else return "false cursor";
    }

    public String getStudentName(String id){
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.query("msStudent", new String[]{"Name"}, "IDStudent" + "=?",
                new String[]{ id },null,null,null,null);
        if(cursor != null) {
            cursor.moveToFirst();
            return cursor.getString(0);
        }
        else return "";
    }

    public String updateNewPassword(String email, String newPassword, String confirmNewPassword) {
        if(newPassword.equals(confirmNewPassword)){
            SQLiteDatabase db = openHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("Password", newPassword);
            // updating row
            int rowAffected = db.update("msStudent", values, "Email" + " = ?",
                    new String[] { email });
            if(rowAffected > 0)
                return "true";
            else
                return "false update";
        }
        else
            return "false confirm";
    }

    public ScheduleModel getSchedule(String id, String tanggal){
        SQLiteDatabase db = openHelper.getReadableDatabase();
        ScheduleModel schedule = new ScheduleModel();
        Cursor cursor = db.query("detailScheduleStudent a JOIN msScheduleStudent b ON b.IDStudent = a.IDStudent AND b.IDTeacher = a.IDTeacher JOIN msTeacher c ON a.IDTeacher = c.IDTeacher AND b.IDMateri = a.IDMateri JOIN msMateri d ON a.IDMateri = d.IDMateri",
                new String[]{"Jam", "Kelas", "c.Name", "d.Name"}, "a.IDStudent=? AND Tanggal LIKE ?", new String[]{id,"%"+tanggal+"%"}, null, null, null, null);
        if(cursor!=null){
            boolean cond = cursor.moveToFirst();
            if(!cond){
                schedule.setJam("-");
                schedule.setKelas("-");
                schedule.setGuru("-");
                schedule.setMateri("-");
                schedule.setIsLoaded(true);
                return schedule;
            }
            else{
                schedule.setJam(cursor.getString(0));
                schedule.setKelas(cursor.getString(1));
                schedule.setGuru(cursor.getString(2));
                schedule.setMateri(cursor.getString(3));
                schedule.setIsLoaded(true);
                return schedule;
            }
        }
        else{
            schedule.setIsLoaded(false);
            return schedule;
        }
    }

    public String getPaymentAmount(String id, String tanggal){
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.query("detailPembayaran a JOIN msPembayaran b ON a.IDDetailPembayaran = b.IDDetailPembayaran",
                new String[]{"TotalPembayaran"},"a.IDStudent=? AND b.TanggalPembayaran LIKE ?",new String[]{id,"%"+tanggal+"%"},null,null,null,null);
        if(cursor!=null){
            boolean cond = cursor.moveToFirst();
            if(!cond){
                return "-";
            }
            else{
                return cursor.getString(0);
            }
        }
        else{
            return "false cursor";
        }
    }

    public boolean isEmailTaken(String email){
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.query("msStudent",new String[]{"Email"},"Email=?",new String[]{email},null,null,null,null);
        boolean isTaken = cursor.moveToFirst();
        if(isTaken) return true;
        else return false;
    }
    public boolean isPhoneTaken(String phone){
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.query("msStudent",new String[]{"Phone"},"Phone=?",new String[]{phone},null,null,null,null);
        boolean isTaken = cursor.moveToFirst();
        if(isTaken) return true;
        else return false;
    }

}
