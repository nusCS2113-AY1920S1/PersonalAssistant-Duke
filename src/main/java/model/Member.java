package model;

import model.task.Task;

import java.util.ArrayList;

public class Member {
    String name;
    ArrayList<Task> taskList;
    String biography;
    String email;
    String phone;

    public Member(String name) {
        this.name = name;
        this.taskList = new ArrayList<>();
    }
}
