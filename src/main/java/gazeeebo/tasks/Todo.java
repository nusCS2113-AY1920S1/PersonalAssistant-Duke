package gazeeebo.tasks;

public class Todo extends Task {
    public Todo(String description) {
        super(description);
    }
    @Override
    public String toString() {
        return "T"+ "|" + super.getStatusIcon() + "| " + super.description;
    }
    public String listFormat(){
        return "[T]" + "[" + super.getStatusIcon() + "] " + super.description ;
    }

}