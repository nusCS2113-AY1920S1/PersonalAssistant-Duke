package gazeeebo.commands.tasks;

import gazeeebo.commands.Command;
import gazeeebo.tasks.Event;
import gazeeebo.tasks.Task;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.storage.Storage;
import gazeeebo.exception.DukeException;

import java.io.IOException;

import java.text.ParseException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Stack;

/**
 * This class creates and adds a new event task.
 */
public class EventCommand extends Command {
    /**
     * The string "event" has 5 characters.
     */
    static final int EVENT_CHAR_COUNT = 5;
    /**
     * The string "event " has 6 characters.
     */
    static final int EVENT_AND_SPACE_CHAR_COUNT = 6;
    /**
     * Calls in the Event object.
     */
    static final String EVENT = "gazeeebo.tasks.Event";

    /**
     * This class adds event tasks to the list of tasks when called.
     *
     * @param list          List of all tasks
     * @param ui            the object that deals with
     *                      printing things to the user
     * @param storage       The object that deals with storing data
     * @param commandStack
     * @param deletedTask
     * @param triviaManager
     * @throws DukeException  Throws custom exception when
     *                        format of event command is wrong
     * @throws ParseException Catch error if parsing of command fails
     * @throws IOException    Catch error if the read file fails
     */
    @Override
    public void execute(final ArrayList<Task> list,
                        final Ui ui, final Storage storage,
                        final Stack<ArrayList<Task>> commandStack,
                        final ArrayList<Task> deletedTask,
                        final TriviaManager triviaManager)
            throws DukeException, ParseException, IOException {

        String description;
        try {
            if (ui.fullCommand.length() == EVENT_CHAR_COUNT) {
                throw new DukeException("OOPS!!! The description of"
                        + "an event cannot be empty.");
            } else {
                description = ui.fullCommand.split("/at ")[0]
                        .substring(EVENT_AND_SPACE_CHAR_COUNT);
                triviaManager.learnInput(ui.fullCommand, storage);
            }
            String at = ui.fullCommand.split("/at ")[1];
            Event ev = new Event(description, at);

            //CHECKING FOR SCHEDULE ANOMALIES-----------------------
            ArrayList<Event> clash = new ArrayList<Event>();
            //to store events that clash with the incoming event
            for (Task t : list) {
                if (t.getClass().getName().equals(EVENT)
                        && ((Event) t).date.equals(ev.date)
                        && ((ev.start.isBefore(((Event) t).start)
                        && ev.end.isAfter(((Event) t).start))
                        || ev.start.equals(((Event) t).start)
                        || (ev.start.isAfter(((Event) t).start)
                        && ev.start.isBefore(((Event) t).end)))) {
                    clash.add((Event) t);
                }
            }
            if (!clash.isEmpty()) {
                System.out.println("The following event(s) "
                        + "clash with your current event:");
                for (int i = 0; i < clash.size(); i++) {
                    System.out.println((i + 1) + "."
                            + clash.get(i).listFormat());
                }
                System.out.println("");
            }
            list.add(ev);
            System.out.println("Got it. I've added this task:");
            System.out.println(ev.listFormat());
            System.out.println("Now you have " + list.size()
                    + " tasks in the list.");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                sb.append(list.get(i).toString() + "\n");
            }
            storage.writeToSaveFile(sb.toString());
        } catch (DukeException e) {
            System.out.println(e.getMessage());
            triviaManager.showPossibleInputs("event");
        } catch (ArrayIndexOutOfBoundsException | DateTimeParseException a) {
            Ui.showEventDateFormatError();
        }
    }

    /**
     * Program does not exit and continues running
     * since command "bye" is not called.
     *
     * @return false
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
