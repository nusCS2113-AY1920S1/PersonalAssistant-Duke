package Duke;

import Duke.Commands.*;
import Duke.Tasks.Deadline;
import Duke.Tasks.Event;
import Duke.Tasks.Task;
import Duke.Tasks.ToDo;

import java.util.ArrayList;

public class Parser{
    /**
     * Allows the user input to be parsed before running 'execute'
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
        } else if (input.length() > 4 && input.substring(0, 4).equals("find")) {
            return new FindCommand(input);
        } else if (input.length() > 4 && input.substring(0, 4).equals("done")) {
            return new DoneCommand(input);
        } else if (input.length() > 6 && input.substring(0,6).equals("delete")) {
            return new DeleteCommand(input);
        } else if (input.length() >= 4 && input.substring(0, 4).equals("todo")) {
            return new AddCommand(Command.CmdType.TODO, input);
        } else if (input.length() >= 5 && input.substring(0, 5).equals("event")) {
            return new AddCommand(Command.CmdType.EVENT, input);
        } else if (input.length() >= 8 && input.substring(0, 8).equals("deadline")) {
            return new AddCommand(Command.CmdType.DEADLINE, input);
        } else {
            throw new DukeException("     ☹ OOPS!!! I'm sorry, but I don't know what that means :-( [Unknown COMMAND TYPE]");
        }
    }

    /**
     * Creates a new 'toBeDone' task, before adding it to current list,
     * then returning the output by Duke
     * @param data ArrayList of Tasks that's currently being stored
     * @param input Command input by user
     * @param state The type of output needed:
     *              0 : Needs to return a string
     *              1 : Returns null string with unchecked task
     *              2 : Returns null string with checked task
     * @return String which highlights what Duke processed
     */
    public static String runTodo(ArrayList<Task> data, String input, int state) {
        StringBuilder stringBuilder = new StringBuilder();
        input = input.substring(5);
        Task tempTask = new ToDo(input);
        if (state == 2)
            tempTask.markAsDone();
        data.add(tempTask);
        if (state == 0) {
            stringBuilder.append("Got it. I've added this task: ").append("\n");
            stringBuilder.append("    [T][").append(tempTask.getStatusIcon()).append("] ").append(input).append("\n");
            stringBuilder.append("Now you have ").append(data.size()).append(" tasks in the list.");
        }
        return stringBuilder.toString();
    }

    /**
     * Creates a new 'Deadline' task, before adding it to current list,
     * then returning the output by Duke
     * @param data ArrayList of Tasks that's currently being stored
     * @param input Command input by user
     * @param state The type of output needed:
     *              0 : Needs to return a string
     *              1 : Returns null string with unchecked task
     *              2 : Returns null string with checked task
     * @return String which highlights what Duke processed
     */
    public static String runDeadline(ArrayList<Task> data, String input, int state) {
        StringBuilder stringBuilder = new StringBuilder();
        input = input.substring(9);
        int startOfBy = input.indexOf("/");
        String tt1 = input.substring(0, startOfBy - 1);
        String tt2 = input.substring(startOfBy + 4);
        Task tempTask = new Deadline(tt1, tt2);
        if (state == 2)
            tempTask.markAsDone();
        data.add(tempTask);
        if (state == 0) {
            stringBuilder.append("Got it. I've added this task: ").append("\n");
            stringBuilder.append("    [D][").append(tempTask.getStatusIcon()).append("] ").append(tt1).append(" (by: ").append(tt2).append(")").append("\n");
            stringBuilder.append("Now you have ").append(data.size()).append(" tasks in the list.");
        }
        return stringBuilder.toString();
    }

    /**
     * Creates a new 'Event' task, before adding it to current list,
     * then returning the output by Duke
     * @param data ArrayList of Tasks that's currently being stored
     * @param input Command input by user
     * @param state The type of output needed:
     *              0 : Needs to return a string
     *              1 : Returns null string with unchecked task
     *              2 : Returns null string with checked task
     * @return String which highlights what Duke processed
     */
    public static String runEvent(ArrayList<Task> data, String input, int state) {
        StringBuilder stringBuilder = new StringBuilder();
        input = input.substring(6);
        int startOfAt = input.indexOf("/");
        String tt1 = input.substring(0, startOfAt - 1);
        String tt2 = input.substring(startOfAt + 4);
        Task tempTask = new Event(tt1, tt2);
        if (state == 2)
            tempTask.markAsDone();
        data.add(tempTask);
        if (state == 0) {
            stringBuilder.append("Got it. I've added this task: ").append("\n");
            stringBuilder.append("    [E][").append(tempTask.getStatusIcon()).append("] ").append(tt1).append(" (at: ").append(tt2).append(")").append("\n");
            stringBuilder.append("Now you have ").append(data.size()).append(" tasks in the list.");
        }
        return stringBuilder.toString();
    }
}
