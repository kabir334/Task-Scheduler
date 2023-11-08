package com.codegama.Taskscheduler.activity;

public class Task_count {
    public  int total_count;
    public  String email;

    public Task_count(int total_count, String email) {
        this.total_count = total_count;
        this.email = email;
    }

    public  Task_count(){

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }
}
