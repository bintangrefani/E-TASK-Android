package com.example.lenovo.e_task.model.ToEnroll;

import com.example.lenovo.e_task.model.EnrollClass.User;
import com.example.lenovo.e_task.model.MyClass.Kelas;

public class ToEnroll {
    private Kelas kelas;
    private User user;
    private Test test;

    public ToEnroll(Kelas kelas, User user, Test test) {
        this.kelas = kelas;
        this.user = user;
        this.test = test;
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

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }
}
