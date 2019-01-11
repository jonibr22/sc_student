package com.example.apps.studycommunity.Model;

public class StudentModel {

    private int id;
    private String name;
    private String email;
    private String password;
    private String gender;
    private String phone;
    private String school;
    private String level;
    private String kelas;
    private String recovery;

    public void setId(int id) { this.id = id; }
    public int getId() { return id; }

    public void setName(String name) { this.name = name; }
    public String getName() { return name; }

    public void setEmail(String email) { this.email = email; }
    public String getEmail() { return email; }

    public void setPassword(String password) { this.password = password; }
    public String getPassword() { return password; }

    public void setGender(String gender) { this.gender = gender; }
    public String getGender() { return gender; }

    public void setPhone(String phone) { this.phone = phone; }
    public String getPhone() { return phone; }

    public void setSchool(String school) { this.school = school; }
    public String getSchool() { return school; }

    public void setLevel(String level) { this.level = level; }
    public String getLevel() { return level; }

    public void setKelas(String kelas) { this.kelas = kelas; }
    public String getKelas() { return kelas; }

    public void setRecovery(String recovery) { this.recovery = recovery; }
    public String getRecovery() { return recovery; }
}
