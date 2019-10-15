package help;

public class HelpText {
    public final String commandSeparator = System.lineSeparator() +
            "---------------------------------------------------------\n" +
            System.lineSeparator();

    public final String commandFormat =
            "# Command Format #\n"+
            "Words in UPPER_CASE are the parameters to be supplied by the\n" +
            "user e.g. in todo TASK_DESCRIPTION, TASK_DESCRIPTION is a\n" +
            "parameter which can be used as todo eat.\n";

    public final String commandsHeader = "# Commands #";

    public final String todo =
            "[ todo ]\n" +
            "Adds a todo task to the task list.\n" +
            "Format: todo TASK_DESCRIPTION\n" +
            "Examples:\n" +
            "\t- todo eat\n" +
            "\t- todo watch TV\n";

    public final String deadline =
            "[ deadline ]\n" +
            "Adds a deadline task to the task list.\n" +
            "Format: deadline TASK_DESCRIPTION /by YYYY-MM-DD HH:MM:SS\n" +
            "Examples:\n" +
            "\t- deadline assignment /by 2019-12-04 12:07:08\n" +
            "\t- deadline watch TV /by 1988-06-27 08:46:37\n";

    public final String event =
            "[ event ]\n" +
            "Adds an event task to the task list.\n" +
            "Format: event TASK_DESCRIPTION /at YYYY-MM-DD HH:MM:SS\n" +
            "Examples: \n" +
            "\t- event party /at 2019-12-04 12:07:08\n" +
            "\t- event project meeting /at 1988-06-27 08:46:37\n";

    public final String list =
            "[ list ]\n" +
            "List out all the tasks in the task list.\n" +
            "Format: list\n";

    public final String delete =
            "[ delete ]\n" +
            "Deletes n number of tasks at once from the list or delete all tasks at once.\n" +
            "Format: delete TASK_NUM1 and TASK_NUM2...OR delete all\n" +
            "Examples:\n" +
            "\t- delete 3 and 5\n" +
            "\t- delete 6\n" +
            "\t- delete all\n";

    public final String done =
            "[ done ]\n" +
            "Marks a task as done.\n" +
            "Format: done TASK_NUM\n" +
            "Examples:\n" +
            "\t- done 1\n" +
            "\t- done 6\n";
}
