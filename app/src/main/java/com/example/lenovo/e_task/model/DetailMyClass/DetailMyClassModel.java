package com.example.lenovo.e_task.model.DetailMyClass;

import com.example.lenovo.e_task.model.MyClass.Kelas;

import java.util.ArrayList;

public class DetailMyClassModel {
    private String user;
    private Kelas kelas;
    private ArrayList<Mentor> mentor;
    private ArrayList<Task> task;

    public DetailMyClassModel(String user, Kelas kelas, ArrayList<Mentor> mentor, ArrayList<Task> task) {
        this.user = user;
        this.kelas = kelas;
        this.mentor = mentor;
        this.task = task;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Kelas getKelas() {
        return kelas;
    }

    public void setKelas(Kelas kelas) {
        this.kelas = kelas;
    }

    public ArrayList<Mentor> getMentor() {
        return mentor;
    }

    public void setMentor(ArrayList<Mentor> mentor) {
        this.mentor = mentor;
    }

    public ArrayList<Task> getTask() {
        return task;
    }

    public void setTask(ArrayList<Task> task) {
        this.task = task;
    }
}
