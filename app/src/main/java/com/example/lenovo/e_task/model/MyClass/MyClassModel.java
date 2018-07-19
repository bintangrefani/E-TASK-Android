package com.example.lenovo.e_task.model.MyClass;

import java.util.ArrayList;

public class MyClassModel {
    private String user;
    private ArrayList<Kelas> kelas;

    public MyClassModel(String user, ArrayList<Kelas> kelas) {
        this.user = user;
        this.kelas = kelas;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public ArrayList<Kelas> getKelas() {
        return kelas;
    }

    public void setKelas(ArrayList<Kelas> kelas) {
        this.kelas = kelas;
    }
}
