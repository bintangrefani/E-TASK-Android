package com.example.lenovo.e_task.model.EnrollClass;

import com.example.lenovo.e_task.model.MyClass.Kelas;

public class EnrollClassModel {
    private Kelas kelas;
    private User user;

    public EnrollClassModel(Kelas kelas, User user) {
        this.kelas = kelas;
        this.user = user;
    }

    public Kelas getKelas() {
        return kelas;
    }

    public void setKelas(Kelas kelas) {
        this.kelas = kelas;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
