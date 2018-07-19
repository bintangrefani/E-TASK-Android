package com.example.lenovo.e_task.model.ToEnroll;

public class Test {
    private Integer id;
    private Integer classId;
    private Integer userId;

    public Test(Integer id, Integer classId, Integer userId) {
        this.id = id;
        this.classId = classId;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
