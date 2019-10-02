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
     * Initialises all the attributes of the details of the task to be added.
     * @param type TaskType enum of what task class to add.
     * @param description User input description of the task to add.
     * @param details String describing todo duration (optional), deadline date, or event period.
     * @throws BadInputException Thrown if user input is wrongly formatted.
     */
    public AddCommand(CommandType type, String description, String details) throws BadInputException {
        super(type);
        this.description = description;
        this.details = details;

        switch (type) {
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

        default:
            break;
        }
    }

    /**
     * Executes the actual adding of task to the TaskList.
     * @param list TaskList to add the item to.
     * @param ui Ui object to display output to.
     * @param storage Storage object to handle saving and loading of any data.
     * @throws BadInputException Thrown if user input is wrongly formatted.
     */
    @Override
    public void execute(TaskList list, Ui ui, Storage storage) throws BadInputException {
        switch (super.type) {
        case TODO:
            if (!details.equals("")) {
                list.addItem(TaskType.TODO, description, Integer.parseInt(details));
            } else {
                list.addItem(TaskType.TODO,description);
            }
            break;
        case DEADLINE:
            list.addItem(TaskType.DEADLINE, description, dateTimes[0]);
            break;
        case EVENT:
            list.addItem(TaskType.EVENT, description, dateTimes[0], dateTimes[1]);
            break;
        default:
            break;
        }
    }

    /**
     * Executes the actual adding of task to the TaskList. Only to be used Storage.load().
     * @param list TaskList to add the item to.
     * @throws BadInputException If a record is badly formatted, it should be ignored.
     */
    public void execute(TaskList list) throws BadInputException {
        if (super.type == CommandType.TODO) {
            if (!details.equals("")) {
                list.addItem(TaskType.TODO, description, Integer.parseInt(details));
            } else {
                list.addItem(TaskType.TODO,description);
            }
        } else if (super.type == CommandType.DEADLINE) {
            list.addItem(TaskType.DEADLINE, description, dateTimes[0]);
        } else { //Type is event
            list.addItem(TaskType.EVENT, description, dateTimes[0], dateTimes[1]);
        }
    }
}
