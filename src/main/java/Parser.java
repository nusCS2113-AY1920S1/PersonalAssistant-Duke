import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Parser that parses input from the user.
 */
public class Parser {

    private static SimpleDateFormat dataformat = new SimpleDateFormat("dd/MM/yyyy HHmm");

    /**
     * Method that parses input from the user and executes processes based on the input.
     * @param input Input from the user.
     * @param tasklist Tasklist of the user.
     * @param ui Ui that interacts with the user.
     * @param storage Storage for the Tasklist.
     * @return Returns boolean variable to indicate when to stop parsing for input.
     * @throws DukeException if input is not valid.
     */
    public static boolean parse(String input, TaskList tasklist, Ui ui, Storage storage) {
        try {
            if (isBye(input)) {
                //print bye message
                ui.byeMessage();
                ui.in.close();
                return true;

            } else if (isList(input)) {
                //print out current list
                ui.printList(tasklist, "list");

            } else if (isDone(input)) {
                processDone(input, tasklist, ui);

            } else if (isDeadline(input)) {
                processDeadline(input, tasklist, ui);
                storage.save(tasklist.returnArrayList());

            } else if (isTodo(input)) {
                processTodo(input, tasklist, ui);
                storage.save(tasklist.returnArrayList());

            } else if (isEvent(input)) {
                processEvent(input, tasklist, ui);
                storage.save(tasklist.returnArrayList());

            } else if (isDelete(input)) {
                processDelete(input, tasklist, ui);
                storage.save(tasklist.returnArrayList());

            } else if (isFind(input)) {
                processFind(input, tasklist, ui);

            } else {
                throw new DukeException("     ☹ OOPS!!! I'm sorry, but I don't know what that means :-(");

            }

        } catch (DukeException e) {
            ui.exceptionMessage(e.getMessage());
            return true;
        }

        return false;
    }

    /**
     * Processes the find command and outputs a list of tasks containing the word.
     * @param input Input from the user.
     * @param tasklist Tasklist of the user.
     * @param ui Ui that interacts with the user.
     */
    private static void processFind(String input, TaskList tasklist, Ui ui) {
        try {
            TaskList findlist = new TaskList();
            String[] splitspace = input.split(" ", 2);
            for (Task tasks : tasklist.returnArrayList()) {
                if (tasks.description.contains(splitspace[1])) {
                    findlist.addTask(tasks);
                }
            }
            ui.printList(findlist, "find");
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     ☹ OOPS!!! The content to find cannot be empty.");
        }
    }

    /**
     * Processes the delete command.
     * @param input Input from the user.
     * @param tasklist Tasklist of the user.
     * @param ui Ui that interacts with the user.
     */
    private static void processDelete(String input, TaskList tasklist, Ui ui) {
        try {
            String[] arr = input.split(" ", 2);
            int numdelete = Integer.parseInt(arr[1]) - 1;
            String task = tasklist.get(numdelete).giveTask();
            tasklist.deleteTask(numdelete);
            ui.printDeleteMessage(task, tasklist);

        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     ☹ OOPS!!! Please input the list number to delete.");
        }
    }

    /**
     * Processes the done command and sets the task specified as done.
     * @param input Input from the user.
     * @param tasklist Tasklist of the user.
     * @param ui Ui that interacts with the user.
     */
    private static void processDone(String input, TaskList tasklist, Ui ui) {
        try {
            String[] arr = input.split(" ", 2);
            int numdone = Integer.parseInt(arr[1]) - 1;
            tasklist.get(numdone).setDone();
            ui.printDoneMessage(numdone, tasklist);

        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     ☹ OOPS!!! Please input the list number to indicate as done.");
        }
    }

    /**
     * Processes the deadline command and adds a deadline to the user's Tasklist.
     * @param input Input from the user.
     * @param tasklist Tasklist of the user.
     * @param ui Ui that interacts with the user.
     */
    private static void processDeadline(String input, TaskList tasklist, Ui ui) {
        try {
            String[] splitspace = input.split(" ", 2);
            String[] splitslash = splitspace[1].split("/", 2);
            String taskDescription = splitslash[0];
            String[] splittime = splitslash[1].split(" ", 2);
            String taskTime = splittime[1];
            Date formattedtime = dataformat.parse(taskTime);
            Deadline deadline = new Deadline(taskDescription, dataformat.format(formattedtime));
            tasklist.addTask(deadline);
            ui.printAddedMessage(deadline, tasklist);
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     ☹ OOPS!!! The description of a deadline cannot be empty.");
        } catch (ParseException e) {
            ui.exceptionMessage("     ☹ OOPS!!! Format of time is wrong.");
        }
    }

    /**
     * Processes the todo command and adds a todo to the user's Tasklist.
     * @param input Input from the user.
     * @param tasklist Tasklist of the user.
     * @param ui Ui that interacts with the user.
     */
    private static void processTodo(String input, TaskList tasklist, Ui ui) {
        try {
            String[] splitspace = input.split(" ", 2);
            Todo todotoadd = new Todo(splitspace[1]);
            tasklist.addTask(todotoadd);
            ui.printAddedMessage(todotoadd, tasklist);
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     ☹ OOPS!!! The description of a todo cannot be empty.");
        }
    }

    /**
     * Processes the event command and adds an event to the user's Tasklist.
     * @param input Input from the user.
     * @param tasklist Tasklist of the user.
     * @param ui Ui that interacts with the user.
     */
    private static void processEvent(String input, TaskList tasklist, Ui ui) {
        try {
            String[] splitspace = input.split(" ", 2);
            String[] splitslash = splitspace[1].split("/", 2);
            String taskDescription = splitslash[0];
            String[] splittime = splitslash[1].split(" ", 2);
            String taskTime = splittime[1];
            Date formattedtime = dataformat.parse(taskTime);
            Event event = new Event(taskDescription, dataformat.format(formattedtime));
            tasklist.addTask(event);
            ui.printAddedMessage(event, tasklist);
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.exceptionMessage("     ☹ OOPS!!! The description of a event cannot be empty.");
        } catch (ParseException e) {
            ui.exceptionMessage("     ☹ OOPS!!! Format of time is wrong.");
        }
    }


    private static boolean isBye(String input) {
        return input.equals("bye");
    }

    private static boolean isList(String input) {
        return input.equals("list");
    }

    private static boolean isDone(String input) {
        return input.startsWith("done");
    }

    private static boolean isDeadline(String input) {
        return input.startsWith("deadline");
    }

    private static boolean isTodo(String input) {
        return input.startsWith("todo");
    }

    private static boolean isEvent(String input) {
        return input.startsWith("event");
    }

    private static boolean isDelete(String input) {
        return input.startsWith("delete");
    }

    private static boolean isFind(String input) {
        return input.startsWith("find");
    }
}
