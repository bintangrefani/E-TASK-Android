package com.example.lenovo.e_task.model.DaftarSemuaKelas;

public class DaftarSemuaKelas {
    private int id;
    private String name;

    public DaftarSemuaKelas(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
