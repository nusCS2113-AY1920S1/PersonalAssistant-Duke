<<<<<<< HEAD:src/main/java/Task/Todo.java
package Task;
=======
package Tasks;

import Tasks.Task;
>>>>>>> efbdafcf5b660fc8e346a56460c10dcdbcf6a342:src/main/java/Tasks/Todo.java

public class Todo extends Task {
    public Todo(String description) {
        super(description);
    }
    @Override
    public String toString() {
        return "T"+ "|" + super.getStatusIcon() + "| " + super.description;
    }
    public String listformat(){
        return "[T]" + "[" + super.getStatusIcon() + "]" + super.description ;
    }
}