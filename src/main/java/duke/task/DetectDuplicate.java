package duke.task;

/**
 * Finds duplicated tasks and alerts the user.
 */
public class DetectDuplicate {
    protected String command;
    protected String description;
    protected TaskList items;

    public DetectDuplicate(TaskList items) {
        this.items = items;
    }

    public String getDescription() {
        return description;
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

    public void showList(){
        for(int i=0; i<items.size(); i++){
            System.out.println(items.get(i));
        }
    }
}