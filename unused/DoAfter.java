package duke.task;

//@@author maxxyx96-unused
/**
 * Code is not used as we are narrowing down our feature and this code is done to satisfy B-DoAfter requirements
 * for Week 6.
 *
 * Represents a do After that stores description only if task is Done.
 */
public class DoAfter extends Task {

    protected String after;

    /**
     * Creates a DoAfter Task with the specific description and prerequisite task.
     *
     * @param description Description of the Task.
     * @param after Prerequisite task that is to be done beforehand.
     */
    public DoAfter(String description, String after) {
        super(description);
        this.after = after;
    }

    /**
     * Extracting a task content into readable string.
     *
     * @return String to be displayed.
     */
    @Override
    public String toString() {
        return "[A]" + super.toString() + " (Do after: " + after + ")";
    }

    /**
     * Extracting a task content into readable string (GUI).
     *
     * @return String to be displayed.
     */
    @Override
    public String toStringGui() {
        return "[A]" + super.toStringGui() + " (Do after: " + after + ")";
    }

    /**
     * Extracting a task content into string that is suitable for text file.
     *
     * @return String to be written into text file.
     */
    @Override
    public String toFile() {
        return "A|" + super.toFile() + "|" + after;
    }
}

/**
 * Code snippet that calls DoAfter from Parser
 */
/*
else if (arr.length > ZERO && (arr[ZERO].equals("doafter") || arr[ZERO].equals("da"))){
    //doafter <task> /after <pre-requisite task>
    String afterTaskDesc="";
    boolean detectBackSlash=false;
    for(int i=ONE;i<arr.length;i++){
        if((arr[i].trim().isEmpty()||!arr[i].substring(ZERO,ONE).equals("/"))&&!detectBackSlash){
            taskDesc+=arr[i]+" ";
        } else {
            if(!detectBackSlash){
                detectBackSlash=true;
            } else {
                afterTaskDesc+=arr[i]+" ";
            }
        }
    }
    taskDesc=taskDesc.trim();
    afterTaskDesc=afterTaskDesc.trim();
    if(taskDesc.isEmpty()){
        throw new DukeException("     (>_<) OOPS!!! The description of a "+arr[ZERO]+" cannot be empty.");
    } else if(afterTaskDesc.isEmpty()){
        throw new DukeException("     (>_<) OOPS!!! The description of Task for "
        +arr[ZERO]+" cannot be empty.");
    } else {
        String currentTasks=items.getList();
        if (currentTasks.contains(afterTaskDesc)){
            Task taskObj;
            taskObj = new DoAfter(taskDesc,afterTaskDesc);
            return new AddCommand(taskObj);
        } else {
            throw new DukeException("(>_<) OOPS!!! You cant set a "
            +arr[ZERO]+" task for a task that is not in the list!");
        }
    }
}
*/
//@@author