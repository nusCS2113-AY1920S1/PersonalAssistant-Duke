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
        int index = list.getListIndex();

        if (afterIndex > 0 && afterIndex >= list.getSize()) { //Referring to a nonexistent task
            throw new BadInputException("This is not a valid task. (to do after)");
        }

        if (super.type == CommandType.TODO) {
            list.addTodoItem(index, description, afterIndex);
        } else if (super.type == CommandType.DEADLINE) {
            list.addDeadlineItem(index, description, details, afterIndex);
        } else { //Type is event
            list.addEventItem(index, description, details, afterIndex);
        }
    }
}
