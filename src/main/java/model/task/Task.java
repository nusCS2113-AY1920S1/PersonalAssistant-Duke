package model.task;

import utils.DukeException;

import java.util.ArrayList;
import java.util.Date;

/**
 * a general Task class, to be extended
 */
public class Task {
    //Basic description fields
    private Name name;
    private Description description;

    public Task(Name name){
        this.name = name;
    }

    public Name getName(){
        return name;
    }

}