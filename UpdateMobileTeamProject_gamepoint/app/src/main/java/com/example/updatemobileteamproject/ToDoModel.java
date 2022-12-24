package com.example.updatemobileteamproject;

public class ToDoModel {

    private int id;
    private String task;
    private int status;
    private String pickerdate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPickerdate(){return pickerdate;}

    public void setPickerdate(String pickerdate) {this.pickerdate = pickerdate;}
}
//설정 상 모델링