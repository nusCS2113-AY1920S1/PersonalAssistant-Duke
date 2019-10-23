package duke.task;

public class ToDo extends Tasks {

    public ToDo(String description, String type) {
        super(description, type);

    }

    public String toMessage() {
        return description;
    }
}

