package duke.task;

/**
 * Finds duplicated tasks and alerts the user.
 */
public class DetectDuplicate {
    protected TaskList items;

    public DetectDuplicate(TaskList items) {
        this.items = items;
    }

    public boolean isDuplicate(String command, String description){
        switch (command){
            case "todo":
                for(int i=0; i<items.size(); i++){
                    if(items.get(i).contains(description)) //contains, is implemented in Task.java
                        return true;
                }
                break;
            default:
                return false;
        }

        return false;
    }

    @Override
    public String toString() {
        return "     The same task is already in the list!";
    }
}