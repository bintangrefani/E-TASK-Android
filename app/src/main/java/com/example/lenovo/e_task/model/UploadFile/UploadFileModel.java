package com.example.lenovo.e_task.model.UploadFile;

import com.example.lenovo.e_task.model.DetailMyClass.Task;

public class UploadFileModel {
    private Task task;
    private UploadFile uploadFile;

    public UploadFileModel(Task task, UploadFile uploadFile) {
        this.task = task;
        this.uploadFile = uploadFile;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public UploadFile getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(UploadFile uploadFile) {
        this.uploadFile = uploadFile;
    }
}
