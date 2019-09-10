import java.util.*;
import java.io.*;
import java.text.*;

/**
 * Carries out the necessary operations required in the creation of a new Event.
 */

public class EventCommand extends Command {
    protected Event newEvent;

    /**
     * Constructor for an EventCommand. Creates a new Event based on the input
     * provided by the user.
     * @param description The description of the Event.
     * @param at The date of the Event.
     * @throws ParseException if the date does not follow the required format
     */
    public EventCommand(String description, String at) throws ParseException {
        newEvent = new Event(description, at);
    }

    /**
     * This method will add the new Event to the TaskList and print a message
     * to the CLI, with the number of tasks now present.
     * @param ui An instance of the user interface.
     * @param storage An instance of the Storage class.
     * @param tasks The TaskList, containing all the created Tasks thus far.
     */
    public void execute(Ui ui, Storage storage, TaskList tasks) {
        tasks.addItem(newEvent);
        ui.printAddedTask(newEvent.toString(), tasks.getNumberOfItems());
    }
}