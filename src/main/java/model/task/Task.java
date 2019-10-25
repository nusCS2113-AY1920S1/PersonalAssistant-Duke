package model.task;

import utils.DukeException;

import java.util.ArrayList;
import java.util.Date;

/**
 * a general Task class, to be extended
 */
public class Task {
    //Basic description fields
    private String name;
    private String description;

    public Task(String name){
        this.name = name.trim();
    }

    public String getName(){
        return name;
    }

    public String getDescription() { return description; }

}