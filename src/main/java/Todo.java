public class Todo extends Task {


    protected String by;

    public Todo(String description) {
        super(description);
    }

    public String getType() {
        return "[T]";
    }

    //Override by using the same name of function from parent
    public String getStatusIcon() {
        return "[T]" + "[" + (isDone ? "Y" : "N") + "] " + this.description;
    }
}

