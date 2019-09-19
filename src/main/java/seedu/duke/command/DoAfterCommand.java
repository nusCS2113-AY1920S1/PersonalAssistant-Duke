package seedu.duke.command;

import seedu.duke.Duke;
import seedu.duke.TaskList;

public class DoAfterCommand extends Command{

    private TaskList taskList;
    private int itemNumber;
    private String doAfterDescription;

    public DoAfterCommand(TaskList taskList, int itemNumber, String doAfterDescription) {
        this.taskList = taskList;
        this.itemNumber = itemNumber;
        this.doAfterDescription = doAfterDescription;
    }

    @Override
    public boolean execute() {
        taskList.get(itemNumber-1).setDoAfterDescription(doAfterDescription);
        String msg = "Do after task " + doAfterDescription + " has been added to task " + itemNumber;
        Duke.getUI().showResponse(msg);
        return true;
    }
}
