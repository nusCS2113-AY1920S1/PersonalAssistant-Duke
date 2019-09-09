package commons;

import task.Task;
import task.TaskList;

public class Message {
    private static final String BEGIN_DIVIDER = "----------------------------------------------";
    private static final String END_DIVIDER = BEGIN_DIVIDER + System.lineSeparator();

    private static final String MESSAGE_ERROR = "\u2639 OOPS!!! %s";
    private static final String MESSAGE_HELLO = "Hello! I'm logic.Duke";
    private static final String MESSAGE_HELLO_2 = "What can I do for you?";
    private static final String MESSAGE_ADDED = "Got it. I've added this task:\n  %s\n" +
            "Now you have %d tasks in the list.";
    public static final String MESSAGE_LIST_ITEM = "%1$d. %2$s %3$s";
    private static final String MESSAGE_BYE = "Bye. Hope to see you again soon!";
    private static final String MESSAGE_FINISH_DONE = "Nice! I've marked this task as done:\n  ✅ %s";
    private static final String MESSAGE_DELETION = "Noted. I've removed this task: \n" +
            " %s \n" +
            "Now you have %d tasks in the list.";
    private static final String MESSAGE_SEARCH = "Here are the matching tasks in your list:";
    public static final String MESSAGE_UNKNOWN_COMMAND = "This is an unknown command.";

    public static String getMessageError(String message) {
        return buildMessage(BEGIN_DIVIDER, String.format(MESSAGE_ERROR, message), END_DIVIDER);
    }

    public static String getWelcome() {
        return buildMessage(BEGIN_DIVIDER, MESSAGE_HELLO, MESSAGE_HELLO_2, END_DIVIDER);
    }

    public static String getAddition(Task t, TaskList tasks) {
        return buildMessage(BEGIN_DIVIDER, String.format(MESSAGE_ADDED, t, tasks.size()), END_DIVIDER);
    }

    public static String getDeletion(Task t, TaskList tasks) {
        return buildMessage(BEGIN_DIVIDER, String.format(MESSAGE_DELETION,
                t.toString(),
                tasks.size() - 1));
    }

    public static String getList(TaskList tasks) {
        StringBuilder message = new StringBuilder();
        message.append(BEGIN_DIVIDER + "\n");
        for (int i = 0; i < tasks.size(); i++) {
            Task s = tasks.get(i);
            message.append(String.format("%d. %s", i + 1, s.toString()));
            message.append("\n");
        }
        message.append(END_DIVIDER);
        return message.toString();
    }

    public static String getSearch(TaskList tasks) {
        StringBuilder message = new StringBuilder();
        message.append(BEGIN_DIVIDER + "\n");
        message.append(MESSAGE_SEARCH + "\n");
        for (int i = 0; i < tasks.size(); i++) {
            Task s = tasks.get(i);
            message.append(String.format("  %d. %s", i + 1, s.toString()));
            message.append("\n");
        }
        message.append(END_DIVIDER);
        return message.toString();
    }

    public static String getDone(Task task) {
        return buildMessage(BEGIN_DIVIDER,
                String.format(MESSAGE_FINISH_DONE, task.getDescription()),
                END_DIVIDER);
    }

    public static String getExit() {
        return buildMessage(BEGIN_DIVIDER, MESSAGE_BYE, END_DIVIDER);
    }

    private static String buildMessage(String... message) {
        StringBuilder res = new StringBuilder();
        for (String m : message) {
            res.append(m);
            res.append("\n");
        }
        return res.toString().strip();
    }

}
