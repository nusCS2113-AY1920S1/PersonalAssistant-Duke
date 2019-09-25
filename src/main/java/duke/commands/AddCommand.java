package duke.commands;

import duke.TaskList;
import duke.Ui;
import duke.Storage;
import duke.items.DateTime;
import duke.exceptions.BadInputException;
import duke.enums.CommandType;
import duke.enums.TaskType;

/**
 * Command objects for adding Todos, Events, and Deadlines.
 */
public class AddCommand extends Command {

    private String description;
    private String details;
    private DateTime[] dateTimes = new DateTime[2];

    /**
     * Why does this require a javadoc comment.
     */
    public AddCommand(CommandType type, String description, String details) throws BadInputException {
        super(type);
        this.description = description;
        this.details = details;

        switch (type) {
            case TODO:
                if (!details.equals("")) {
                    dateTimes[0] = new DateTime(details);
                }
            case DEADLINE:
                dateTimes[0] = new DateTime(details);
                break;

            case EVENT:
                try {
                    String[] startEndTime = details.split(" to ", 2);
                    dateTimes[0] = new DateTime(startEndTime[0]);
                    dateTimes[1] = new DateTime(startEndTime[1]);
                } catch (Exception e) {
                    throw new BadInputException("Improper datetime. "
                            + "Correct format: event <event name> /at <event start time> to <event end time>");
                }

                break;

        }

    }

    @Override
    public void execute(TaskList list, Ui ui, Storage storage) throws BadInputException {

        int index = list.getListIndex(); //To assign the next task's index.

        if (super.type == CommandType.TODO) {
            if (!details.equals("")) {
                list.addItem(TaskType.TODO, description, Integer.parseInt(details));
            } else {
                list.addItem(TaskType.TODO,description);
            }
        } else if (super.type == CommandType.DEADLINE) {
            list.addItem(TaskType.DEADLINE, description, dateTimes[0]);
        } else { //Type is event
            list.addItem(TaskType.EVENT, description, dateTimes[1], dateTimes[1]);
        }

    }

    public void execute(TaskList list) throws BadInputException {

        int index = list.getListIndex(); //To assign the next task's index.

        if (super.type == CommandType.TODO) {
            if (!details.equals("")) {
                list.addItem(TaskType.TODO, description, Integer.parseInt(details));
            } else {
                list.addItem(TaskType.TODO,description);
            }
        } else if (super.type == CommandType.DEADLINE) {
            list.addItem(TaskType.DEADLINE, description, dateTimes[0]);
        } else { //Type is event
            list.addItem(TaskType.EVENT, description, dateTimes[1], dateTimes[1]);
        }
    }
}
