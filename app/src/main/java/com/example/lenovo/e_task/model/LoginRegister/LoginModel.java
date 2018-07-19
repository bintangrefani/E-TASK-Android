package com.example.lenovo.e_task.model.LoginRegister;

public class LoginModel {
    private String code;
    private String description;
    private String message;
    private Data data;

    public LoginModel(String code, String description, String message, Data data) {
        this.code = code;
        this.description = description;
        this.message = message;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
