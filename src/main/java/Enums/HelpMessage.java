package Enums;

public enum HelpMessage {
    MENU(Constants.HELP_MENU),
    ADD(Constants.HELP_ADD),
    DELETE(Constants.HELP_DELETE),
    LIST(Constants.HELP_LIST),
    DONE(Constants.HELP_DONE),
    RESTORE(Constants.HELP_RESTORE),
    FIND(Constants.HELP_FIND),
    SNOOZE(Constants.HELP_SNOOZE),
    SUBTASK(Constants.HELP_SUBTASK),
    UPDATE(Constants.HELP_UPDATE),
    SORT(Constants.HELP_SORT),
    SHOW(Constants.HELP_SHOW),
    LOG(Constants.HELP_LOG),
    REMOVE_OVERDUE(Constants.HELP_REMOVE_OVERDUE),
    BYE(Constants.HELP_BYE),
    OVERDUE(Constants.HELP_OVERDUE),
    RESCHEDULE(Constants.HELP_RESCHEDULE);


    private String message;
    HelpMessage (String message) {
        this.message = message;
    }
    @Override
    public String toString() {
        return Color.YELLOW + message + Color.RESET;
    }

    private static class Constants {
        public static final String HELP_ADD = "Before you proceed please familiarize yourself with these notation:\n\n" +
                "(must) Use \"#______#\" to specify the type of a task. Example: #assignment#\n\n" +
                "(must) Use \"(______)\" to specify the description of a task. Example: (finish report)\n\n" +
                "(must) Use \"&______&\" to specify the time of a task. Example: &next monday 14:00&\n\n" +
                "Use \"*______*\" to specify the priority of a task. Example: *high*\n\n" +
                "Use \"^______^\" to specify the duration of a task. Example: ^2 hours^\n\n" +
                "Use \"@______@\" to specify the responsible person of a task. Example: @Harry@\n\n" +
                "Now that you've familiar with the syntax. Here's an example on how to add an assignment:\n" +
                "add #assignment# &12/2/2020 15:00& (finish report)\n\n" +
                "Or you can key in the information in any different order and write date in short form:\n" +
                "add &next tues 16:00& (product evaluation) #meeting# ^2 hours^";
        public static final String HELP_MENU = "Here are a list of commands you can input:\n" +
                "add\t\t\t\t\tdelete\t\t\t\tupdate\n" +
                "restore\t\t\t\tsnooze\t\t\t\tdone\n" +
                "list\t\t\t\tfind\t\t\t\tsort\n" +
                "show\t\t\t\toverdue\t\t\t\tsubtask\n" +
                "removeoverdue\t\treschedule\t\t\tbye\n" +
                "bye\n" +
                "To know more about the commands, type help \"command\". Example: help add";
        public static final String HELP_DELETE = "This command deletes the tasks at the specified order number.\n" +
                "Example: delete 1\n" +
                "         delete 3 - 5";
        public static final String HELP_LIST = "This command shows the list of task that are currently in the Task list\n" +
                "Example: list";
        public static final String HELP_DONE = "This command marks the specified task as completed. Example:\n" +
                "Example: done 1 \n" +
                "         done 2 - 4";
        public static final String HELP_RESTORE = "This command restores a deleted task back into the task list based on its index\n" +
                "Example: restore 2";
        public static final String HELP_FIND = "This command dinds tasks in the task list based on the keyword specified\n" +
                "Example: find report";
        public static final String HELP_SNOOZE = "This command snoozes a task for a specified amount of time\n"
                + "Different time units include: hours, minutes\n"
                + "Example: snooze 1 2 hours";
        public static final String HELP_SUBTASK = "This command add subtasks to an assignment task type\n" +
                "Example: subtask 3 subtask1, subtask2\n" +
                "This will add 2 subtasks to the task at index 3, subtask1 and subtask2";
        public static final String HELP_UPDATE = "Before you proceed please familiarize yourself with these notation:\n\n" +
                "(must) Use \"#______#\" to specify the type of a task. Example: #assignment#\n\n" +
                "(must) Use \"(______)\" to specify the description of a task. Example: (finish report)\n\n" +
                "(must) Use \"&______&\" to specify the time of a task. Example: &next monday 14:00&\n\n" +
                "Use \"*______*\" to specify the priority of a task. Example: *high*\n\n" +
                "Use \"^______^\" to specify the duration of a task. Example: ^2 hours^\n\n" +
                "Use \"@______@\" to specify the responsible person of a task. Example: @Harry@\n\n" +
                "Now that you've familiar with the syntax. Here's an example on how to update a task:\n" +
                "update 1 *high* &next mon 15:00&";
        public static final String HELP_SORT = "This command sorts the tasks in the task list based on type, deadline, priority and alphabetical order\n" +
                "Example: sort deadline\n" +
                "This will sort the tasks in the task list by their deadlines";
        public static final String HELP_SHOW = "This command shows you the task tagged to each user in the task list\n" +
                "Example 1: show kelly\n" +
                "This will list all the tasks assigned to kelly and everyone\n" +
                "To show deleted tasks, you can type in 'show deleted'\n" +
                "Example 2: show deleted";
        public static final String HELP_LOG = "This command logs the current task list into a saved file";
        public static final String HELP_REMOVE_OVERDUE = "This command removes tasks from the overdue list if you do not want to reschedule it\n" +
                "Example: removeoverdue 2";
        public static final String HELP_BYE = "Typing in 'bye' will exit the program";
        public static final String HELP_OVERDUE = "This command shows the list of overdue tasks";
        public static final String HELP_RESCHEDULE = "This command reschedules an overdue task by index to a later date by inputting a new date\n" +
                "Example reschedule 1 &20/11/2019 10:00&\n" +
                "This will reschedule the tasks specified by their index to the new date";
    }
}
