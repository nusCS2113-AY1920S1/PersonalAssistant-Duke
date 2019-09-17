package duke;

import duke.commands.*;
import duke.tasks.*;
import com.joestelmach.natty.DateGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Parser {

    /**
     * Allows the user input to be parsed before running 'execute'.
     * @param input String inputted by user, which needs to be parsed
     *              to identify the intent
     * @return a subclass of the Command Class along
     *         with their respective intent
     * @throws DukeException Shows error when unknown command is inputted
     */
    public static Command parse(String input) throws DukeException {
        if (input.equals("bye")) {
            return new ExitCommand();
        } else if (input.equals("list")) {
            return new ListCommand();
        } else if (input.equals("reminder")) {
            return new ReminderCommand();
        } else if (input.length() >= 12 && input.substring(0, 12).equals("viewschedule")) {
            return new ViewScheduleCommand(input);
        } else if (input.length() >= 8 && input.substring(0, 8).equals("freetime")) {
            return new FreeTimeCommand(input);
        } else if (input.length() > 4 && input.substring(0, 4).equals("find")) {
            return new FindCommand(input);
        } else if (input.length() > 4 && input.substring(0, 4).equals("done")) {
            return new DoneCommand(input);
        } else if (input.length() > 6 && input.substring(0,6).equals("delete")) {
            return new DeleteCommand(input);
        } else if (input.length() >= 4 && input.substring(0, 4).equals("todo")) {
            if (input.contains("/daily")) {
                return new AddCommand(Command.CmdType.DAILY, input);
            } else if (input.contains("/weekly")) {
                return new AddCommand(Command.CmdType.WEEKLY, input);
            } else if (input.contains("/monthly")) {
                return new AddCommand(Command.CmdType.MONTHLY, input);
            }
            return new AddCommand(Command.CmdType.TODO, input);
        } else if (input.length() >= 5 && input.substring(0, 5).equals("event")) {
            return new AddCommand(Command.CmdType.EVENT, input);
        } else if (input.length() >= 8 && input.substring(0, 8).equals("deadline")) {
            return new AddCommand(Command.CmdType.DEADLINE, input);
        } else if (input.length() >= 15 && input.substring(0,15).equals("tentative event")) {
            return new TentativeCommand(input, true);
        } else if (input.equals("tentative list")) {
            return new TentativeCommand(input, false);
        } else if (input.length() >= 7 && input.substring(0,7).equals("confirm")) {
            return new ConfirmCommand(input);
        } else {
            throw new DukeException("☹ OOPS!!! I'm sorry, but I don't know what that means :-( [Unknown COMMAND TYPE]");
        }
    }

    private static String getString(ArrayList<Task> data, int state, Task tempTask) {
        StringBuilder stringBuilder = new StringBuilder();

        if (state == 2) {
            tempTask.markAsDone();
        }
        data.add(tempTask);
        if (state == 0) {
            stringBuilder.append("Got it. I've added this task: ").append("\n   ");
            stringBuilder.append(tempTask.getFullString()).append("\n");
            stringBuilder.append("Now you have ").append(data.size()).append(" tasks in the list.");

        }
        return stringBuilder.toString();
    }

    private static StringBuilder computeTaskDetail(String task, StringBuilder stringBuilder) {
        String[] tokens = task.split(" ");
        for (String token : tokens) {
            if (token.charAt(0) != '/') stringBuilder.append(token);
            else break;
        }
        return stringBuilder;
    }

    /**
     * Creates a new 'toBeDone' task, before adding it to current list,
     * then returning the output by Duke.
     * @param data ArrayList of Tasks that's currently being stored
     * @param input Command input by user
     * @param state The type of output needed:
     *              0 : Needs to return a string
     *              1 : Returns null string with unchecked task
     *              2 : Returns null string with checked task
     * @return String which highlights what Duke processed
     */
    public static String runTodo(ArrayList<Task> data, String input, int state) {

        StringBuilder taskDetail = new StringBuilder();
        StringBuilder taskReq = new StringBuilder();
        boolean hasReq = false;
        boolean hasDoAfter = false;
        boolean hasWithinPeriod = false;
        boolean hasFixedDuration = false;
        Task tempTask;

        input = input.substring(5);
        String[] tokens = input.split("\\s+"); //splitting the string when number of spaces >= 1
        for (String token : tokens) {
            if (token.charAt(0) == '/' && token.equals("/after")) {
                hasReq = true;
                hasDoAfter = true;
            } else if (token.charAt(0) == '/' && token.equals("/within")) {
                hasReq = true;
                hasWithinPeriod = true;
            } else if (token.charAt(0) == '/' && token.equals("/needs")) {
                hasReq = true;
                hasFixedDuration = true;
            }
            else if (!hasReq) taskDetail.append(token + " ");
            else if (hasReq) taskReq.append(token + " ");
        }
        String finalTaskDetail = taskDetail.toString();
        finalTaskDetail = finalTaskDetail.substring(0, finalTaskDetail.length() - 1);
        String finalTaskReq = taskReq.toString();

        if (hasReq) finalTaskReq = finalTaskReq.substring(0, finalTaskReq.length() - 1);

        if (hasDoAfter) {
            tempTask = new DoAfterTask(finalTaskDetail, finalTaskReq);
        } else if (hasWithinPeriod) {
            tempTask = new WithinPeriodTask(finalTaskDetail, finalTaskReq);
        } else if (hasFixedDuration) {
            tempTask = new FixedDurationTask(finalTaskDetail, finalTaskReq);
        }
        else {
            tempTask = new ToDo(input);
        }
        return getString(data, state, tempTask);
    }

    /**
     * Creates a new 'Deadline' task, before adding it to current list,
     * then returning the output by Duke.
     * @param data ArrayList of Tasks that's currently being stored
     * @param input Command input by user
     * @param state The type of output needed:
     *              0 : Needs to return a string
     *              1 : Returns null string with unchecked task
     *              2 : Returns null string with checked task
     * @return String which highlights what Duke processed
     */


    public static String runDeadline(ArrayList<Task> data, String input, int state) throws DukeException {
        input = input.substring(9);
        int startOfBy = input.indexOf("/");
        String tt1 = input.substring(0, startOfBy - 1);
        String tt2 = input.substring(startOfBy + 4);
        Task tempTask = new Deadline(tt1, tt2);
        return getString(data, state, tempTask);
    }

    /**
     * Creates a new 'Event' task, before adding it to current list,
     * then returning the output by Duke.
     * @param data ArrayList of Tasks that's currently being stored
     * @param input Command input by user
     * @param state The type of output needed:
     *              0 : Needs to return a string
     *              1 : Returns null string with unchecked task
     *              2 : Returns null string with checked task
     * @return String which highlights what Duke processed
     */
    public static String runEvent(ArrayList<Task> data, String input, int state) throws DukeException {
        input = input.substring(6);
        int startOfAt = input.indexOf("/");
        String tt1 = input.substring(0, startOfAt - 1);
        String tt2 = input.substring(startOfAt + 4);
        Task tempTask = new Event(tt1, tt2);
        if (noClash(data, tempTask)) {
            return getString(data, state, tempTask);
        } else {
            throw new DukeException("Please change date of new event to be added or delete current event");
        }
    }

    /**
     * Checks if new event clash of with existing event.
     * Clash only checked against task of EVENT type
     * @param newTask new task that use wants to add
     * @return false if no clash is found
     */
    public static boolean noClash(ArrayList<Task> data, Task newTask) throws DukeException {
        for (Task task : data) {
            if (task instanceof Event) {
                if (task.getExtra().equals(newTask.getExtra().toString())) {
                    throw new DukeException("     ☹ OOPS!!! This new event clashes with\n " + task.getFullString());
                }
            }
        }
        return true;
    }

    /**
     * Confirm slot for tentative event, and add that event to the task list.
     * @param data ArrayList of Tasks that's currently being stored
     * @param input Command input by user
     * @param state The type of output needed:
     *              0 : Needs to return a string
     *              1 : Returns null string with unchecked task
     *              2 : Returns null string with checked task
     * @return String which highlights what Duke processed
     * @throws DukeException Shows error when unknown slot or no valid slot
     */
    public static String runConfirm(ArrayList<Task> data, String input, int state) throws DukeException {
        input = input.substring(8);
        int num;
        try {
            num = Integer.parseInt(input);
            if (num > TentativeEvent.dates.size()) {
                throw new DukeException("No corresponding slot!");
            }
        } catch (NumberFormatException e) {
            throw new DukeException("Not a valid Slot Number!");
        }

        // construct new Event object to add to the list.
        Task tempTask = new Event(TentativeEvent.description, TentativeEvent.dates.get(num - 1));
        return getString(data, state, tempTask);
    }

    public static String runRecurring(ArrayList<Task> data, String input, int state, String freq) throws DukeException {
        input = input.substring(5).trim();
        String tt1, tt2;
        int token;
        token = input.indexOf("/");
        tt1 = input.substring(0, token - 1);
        if (freq.equals("daily")) {
            tt2 = input.substring(token + 7);
        } else if (freq.equals("weekly")) {
            tt2 = input.substring(token + 8);
        } else {
            tt2 = input.substring(token + 9);
        }

        // parse date here
        Date startDate = parseDate(tt2);
        Task tempTask = new RecurringTask(tt1, startDate, freq);
        return getString(data, state, tempTask);
    }

    public static Date parseDate(String tt2) throws DukeException{
        try {
            com.joestelmach.natty.Parser parser = new com.joestelmach.natty.Parser();
            List<DateGroup> groups = parser.parse(tt2);
            return groups.get(0).getDates().get(0);
        } catch (Exception e) {
            throw new DukeException("   Date cannot be parsed: " + tt2);
        }
    }
}