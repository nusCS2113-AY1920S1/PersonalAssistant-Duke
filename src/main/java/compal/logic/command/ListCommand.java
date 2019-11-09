package compal.logic.command;

import compal.commons.LogUtils;
import compal.logic.parser.exceptions.ParserException;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

//@@author SholihinK

/**
 * The ListCommand Class handles all list commands.
 */
public class ListCommand extends Command {
    public static final String MESSAGE_USAGE = "list\n\t"
        + "Format: list [/type deadline|event] [/status done|ongoing|due]\n\n\t"
        + "Note: content in \"[]\": optional\n\t"
        + "content separated by \"|\": must choose exactly one from them\n\n"
        + "This command will list all the deadline stored in COMPal that not due and incomplete.\n"
        + "Examples:\n\t"
        + "list /type deadline /status n\n\t\t"
        + "list all tasks with type deadline";

    private static final String LIST_PREFIX = "Here are the tasks in your list sorted by chronological order: \n";
    public static final String LIST_EMPTY = "Looks like there is nothing to list for this command!\n";
    public static final String MESSAGE_INVALID_INPUT = "Error: Invalid status input!";

    private String type = "";
    private String listPrefixTwo;
    private String status = "";
    boolean isDue = false;
    boolean isOngoing = false;

    private static final Logger logger = LogUtils.getLogger(ListCommand.class);

    /**
     * Construct the ListCommand class.
     *
     * @param type the type to be display if valid.
     */
    public ListCommand(String type) {
        listPrefixTwo = "Here are the stored " + type + " in your list:\n\n";
        if ("deadline".equals(type)) {
            this.type = "D";
        } else if ("event".equals(type)) {
            this.type = "E";
        }
    }

    /**
     * Construct the  overload ListCommand class.
     *
     * @param type   type to show
     * @param status if completed or not
     * @throws ParserException if status is not y or no
     */
    public ListCommand(String type, String status) throws ParserException {
        listPrefixTwo = "Here are the stored " + type + " in your list";
        if ("deadline".equals(type)) {
            this.type = "D";
        } else if ("event".equals(type)) {
            this.type = "E";
        } else {
            listPrefixTwo = "Here are the stored tasks in your list";
        }

        if ("done".equalsIgnoreCase(status)) {
            this.status = "true";
            listPrefixTwo += " which are completed:\n\n";
        } else if ("ongoing".equalsIgnoreCase(status)) {
            this.status = "false";
            isOngoing = true;
            listPrefixTwo += " which are on going:\n\n";
        } else if ("due".equalsIgnoreCase(status)) {
            this.status = "false";
            isDue = true;
            listPrefixTwo += " which are due:\n\n";
        } else {
            throw new ParserException(MESSAGE_INVALID_INPUT);
        }
    }


    @Override
    public CommandResult commandExecute(TaskList taskList) {
        logger.info("Executing list command");
        ArrayList<Task> toList = taskList.getArrList();


        StringBuilder finalList;
        if (type.isEmpty() && status.isEmpty()) {
            finalList = new StringBuilder(LIST_PREFIX);
        } else {
            finalList = new StringBuilder(listPrefixTwo);
        }

        Date currentDate = Calendar.getInstance().getTime();
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(currentDate);
        Date dateToday = calendar.getTime();


        for (Task t : toList) {
            if (type.isEmpty() && status.isEmpty()) {
                String taskString = t.toString() + "\n";
                finalList.append(taskString);
            } else if (!type.isEmpty() && status.isEmpty()) {
                if (t.getSymbol().equals(type)) {
                    String taskString = t.toString() + "\n";
                    finalList.append(taskString);
                }
            } else if (type.isEmpty() && !status.isEmpty()) {
                if (isDue) {
                    if (t.getStringisDone().equals(status) && t.getMainDate().before(currentDate)) {
                        String taskString = t.toString() + "\n";
                        finalList.append(taskString);
                    }
                } else if (isOngoing) {
                    if (t.getStringisDone().equals(status) && t.getMainDate().after(currentDate)) {
                        String taskString = t.toString() + "\n";
                        finalList.append(taskString);
                    }
                } else {
                    if (t.getStringisDone().equals(status)) {
                        String taskString = t.toString() + "\n";
                        finalList.append(taskString);
                    }
                }

            } else if (!status.isEmpty() && !type.isEmpty()) {
                if (isDue) {
                    if (t.getSymbol().equals(type) && t.getStringisDone().equals(status)
                        && t.getMainDate().before(currentDate)) {
                        String taskString = t.toString() + "\n";
                        finalList.append(taskString);
                    }
                } else if (isOngoing) {
                    if (t.getSymbol().equals(type) && t.getStringisDone().equals(status)
                        && t.getMainDate().after(currentDate)) {
                        String taskString = t.toString() + "\n";
                        finalList.append(taskString);
                    }
                } else {
                    if (t.getSymbol().equals(type) && t.getStringisDone().equals(status)) {
                        String taskString = t.toString() + "\n";
                        finalList.append(taskString);
                    }
                }

            }
        }

        if (finalList.toString().equals(LIST_PREFIX) || finalList.toString().equals(listPrefixTwo)) {
            finalList = new StringBuilder(LIST_EMPTY);
        }

        return new CommandResult(finalList.toString(), false);
    }
}
