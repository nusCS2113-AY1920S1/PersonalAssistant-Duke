package duke.commands;

import duke.TaskList;
import duke.Ui;
import duke.Storage;
import duke.exceptions.BadInputException;
import duke.enums.CommandType;

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
        String doAfterWhat = "";
        //Section for handling doAfters.
        //Using the indexes presented to the user.
        if (afterIndex > 0 && afterIndex > list.getSize()) { //Referring to a nonexistent task
            throw new BadInputException("This is not a valid task. (to do after)");
        } else if (afterIndex > 0) { //afterIndex is -1 if there is nothing to do before.
            //Get description of task to doAfter.
            doAfterWhat = list.getTaskList().get(afterIndex - 1).getDescription(); //0-indexed list!
        }

        if (super.type == CommandType.TODO) {
            if (!details.equals("")) {
                list.addTodoItem(description, doAfterWhat, Integer.parseInt(details));
            } else {
                list.addTodoItem(description, doAfterWhat);
            }
        } else if (super.type == CommandType.DEADLINE) {
            list.addDeadlineItem(description, details, doAfterWhat);
        } else { //Type is event
            list.addEventItem(description, details, doAfterWhat);
        }
    }
}
