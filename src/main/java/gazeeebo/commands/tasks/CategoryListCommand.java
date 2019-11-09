//@@author e0323290

package gazeeebo.commands.tasks;

import gazeeebo.commands.Command;


import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.storage.Storage;
import gazeeebo.exception.DukeException;
import gazeeebo.tasks.Deadline;
import gazeeebo.tasks.Event;
import gazeeebo.tasks.Task;
import gazeeebo.tasks.Todo;
import gazeeebo.tasks.FixedDuration;
import gazeeebo.tasks.Timebound;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Shows the categorized list when called.
 */
public class CategoryListCommand extends Command {
    /**
     * Index of parameter duration on the command is 3.
     */
    static final int INDEX_OF_DURATION = 3;

    /**
     * Sorts the different types of tasks into their respective categories.
     * It also shows the specified category list.
     *
     * @param list          List of all tasks
     * @param ui            the object that deals with
     *                      printing things to the user
     * @param storage       The object that deals with storing data
     * @param commandStack
     * @param deletedTask
     * @param triviaManager
     * @throws DukeException  Throws a custom exception if
     *                        module index does not exist.
     * @throws ParseException
     * @throws IOException    Catch error if the read file fails
     */
    @Override
    public void execute(final ArrayList<Task> list,
                        final Ui ui,
                        final Storage storage,
                        final Stack<ArrayList<Task>> commandStack,
                        final ArrayList<Task> deletedTask,
                        final TriviaManager triviaManager)
            throws DukeException, ParseException, IOException {
        ArrayList<Deadline> deadlineList = new ArrayList<Deadline>();
        ArrayList<Event> eventList = new ArrayList<Event>();
        ArrayList<Todo> todoList = new ArrayList<>();
        ArrayList<FixedDuration> fdList = new ArrayList<>();
        ArrayList<Timebound> tbList = new ArrayList<>();

        for (Task task : list) {
            if (task.getClass().getName().equals("gazeeebo.tasks.Deadline")) {
                Deadline deadline = new Deadline(task.description,
                        task.toString().split("by:")[1].trim());
                deadlineList.add(deadline);
            } else if (task.getClass().getName().equals("gazeeebo."
                    + "tasks.Event")) {
                Event event = new Event(task.description,
                        task.toString().split("at:")[1].trim());
                eventList.add(event);
            } else if (task.getClass().getName().equals("gazeeebo."
                    + "tasks.Todo")) {
                Todo todo = new Todo(task.description);
                todoList.add(todo);
            } else if (task.getClass().getName().equals("gazeeebo."
                    + "tasks.FixedDuration")) {
                FixedDuration fixedDuration
                        = new FixedDuration(task.description,
                        task.toString().split("\\|")[INDEX_OF_DURATION].trim());
                fdList.add(fixedDuration);
            } else if (task.getClass().getName().equals("gazeeebo."
                    + "tasks.Timebound")) {
                Timebound timebound = new Timebound(task.description,
                        task.toString().split("\\|")[INDEX_OF_DURATION].trim());
                tbList.add(timebound);
            }
        }

        if (ui.fullCommand.equals("deadline list")) {
            System.out.println("List of deadlines tasks:");
            for (int i = 0; i < deadlineList.size(); i++) {
                System.out.println(i + 1 + "."
                        + deadlineList.get(i).listFormat());
            }
        } else if (ui.fullCommand.equals("event list")) {
            System.out.println("List of events tasks:");
            for (int i = 0; i < eventList.size(); i++) {
                System.out.println(i + 1 + "." + eventList.get(i).listFormat());
            }
        } else if (ui.fullCommand.equals("todo list")) {
            System.out.println("List of todo tasks:");
            for (int i = 0; i < todoList.size(); i++) {
                System.out.println(i + 1 + "." + todoList.get(i).listFormat());
            }
        } else if (ui.fullCommand.equals("fixed duration list")) {
            System.out.println("List of fixed duration tasks:");
            for (int i = 0; i < fdList.size(); i++) {
                System.out.println(i + 1 + "." + fdList.get(i).listFormat());
            }
        } else if (ui.fullCommand.equals("timebound list")) {
            System.out.println("List of timebounded tasks:");
            for (int i = 0; i < tbList.size(); i++) {
                System.out.println(i + 1 + "." + tbList.get(i).listFormat());
            }
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
