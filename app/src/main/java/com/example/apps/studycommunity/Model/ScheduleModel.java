package com.example.apps.studycommunity.Model;

public class ScheduleModel {
    private String jam;
    private String kelas;
    private String guru;
    private String materi;
    private boolean isLoaded;

    public void setJam(String jam) { this.jam = jam; }
    public String getJam() { return jam; }

    public void setKelas(String kelas) { this.kelas = kelas; }
    public String getKelas() { return kelas; }

    public void setGuru(String guru) { this.guru = guru; }
    public String getGuru() { return guru; }

    public void setMateri(String materi) { this.materi = materi; }
    public String getMateri() { return materi; }

    public void setIsLoaded(boolean isLoaded) { this.isLoaded = isLoaded; }
    public boolean getIsLoaded() { return isLoaded; }
}
