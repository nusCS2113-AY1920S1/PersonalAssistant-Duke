package duke.task;

import java.util.regex.Pattern;

public abstract class Tasks {
    protected String description;
    protected String type;
    protected boolean done;

    /**
     * Represents a duke.task in a todolist.
     * A duke.task can have description, type and whether is it done or not.
     */
    public Tasks(String description, String type) {
        this.description = description;
        this.type = type;
        this.done = false;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getStatusIcon() {
        return (done ? "\u2713" : "\u2718"); //return tick or X symbols
    }

    public String parseDescription(){
        if (type.equals("E")){
            String[] tokens = description.split(Pattern.quote("(at: "));
            return tokens[0];
        } else if (type.equals("D")){
            String[] tokens = description.split(Pattern.quote("(by: "));
            return tokens[0];
        } else{
            return description;
        }
    }
}

