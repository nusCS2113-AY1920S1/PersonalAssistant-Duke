package duke.commands;

import duke.TaskList;
import duke.Ui;
import duke.Storage;
import duke.exceptions.BadInputException;

/**
 * Command objects for adding Todos, Events, and Deadlines.
 */
public class AddCommand extends Command {

    private String description;
    private String details;
    private int afterIndex;

    /**
     * Why does this require a javadoc comment.
     */
    public AddCommand(CommandType type, String description, String details, int afterIndex) {
        super(type);
        this.description = description;
        this.details = details;
        this.afterIndex = afterIndex;
    }


    @Override
    public void execute(TaskList list, Ui ui, Storage storage) throws BadInputException {

        int index = list.getListIndex(); //To assign the next task's index.
        int currentIndex = -1;
        //Section for handling doAfters.
        //Using the indexes presented to the user.
        if (afterIndex > 0 && afterIndex > list.getSize()) { //Referring to a nonexistent task
            throw new BadInputException("This is not a valid task. (to do after)");
        } else if (afterIndex > 0) { //afterIndex is -1 if there is nothing to do before.
            currentIndex = list.getTaskList().get(afterIndex - 1).getTaskIndex(); //0-indexed list!
            //Permanent index of the task to doAfter.
        }

        if (super.type == CommandType.TODO) {
            if (!details.equals("")) {
                list.addTodoItem(index, description, currentIndex, Integer.parseInt(details));
            } else {
                list.addTodoItem(index, description, currentIndex);
            }
        } else if (super.type == CommandType.DEADLINE) {
            list.addDeadlineItem(index, description, details, currentIndex);
        } else { //Type is event
            list.addEventItem(index, description, details, currentIndex);
        }
    }
}
