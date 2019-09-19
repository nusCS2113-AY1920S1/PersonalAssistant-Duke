package duke.commands;

import duke.TaskList;
import duke.Ui;
import duke.Storage;
import duke.exceptions.BadInputException;

/**
 * Command objects for adding Todos, Events, and Deadlines.
 */
public class AddCommand extends Command {

    public String description;
    public String details;

    /**
     * Why does this require a javadoc comment.
     */
    public AddCommand(CommandType type, String description, String details) {
        super(type);
        this.description = description;
        this.details = details;
    }


    @Override
    public void execute(TaskList list, Ui ui, Storage storage)  {
        int index = list.getListIndex();
        if (super.type == CommandType.TODO) {
            list.addTodoItem(index, description);
        } else if (super.type == CommandType.DEADLINE) {
            list.addDeadlineItem(index, description, details);
        } else { //Type is event
            list.addEventItem(index, description, details);
        }
    }
}
