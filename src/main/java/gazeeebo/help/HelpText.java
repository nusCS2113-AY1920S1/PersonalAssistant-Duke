//@@author yueyuu
package gazeeebo.help;


public class HelpText {
    public static final String commandSeparator = System.lineSeparator() +
            "-----------------------------------------------------------------------------------------------------------\n" +
            System.lineSeparator();

    private static final String commandSeparatorShort = System.lineSeparator() +
            "\t--------------------------------------------------------------------------------------------------------\n" +
            System.lineSeparator();

    public static final String pageSeparator = System.lineSeparator() +
            "___________________________________________________________________________________________________________\n" +
            System.lineSeparator();

    public static final String commandFormat =
            "# Command Format #\n"+
            "Words in UPPER_CASE are the parameters to be supplied by the\n" +
            "user e.g. in todo TASK_DESCRIPTION, TASK_DESCRIPTION is a\n" +
            "parameter which can be used as todo eat.\n";

    public static final String commandsHeader = "# Commands #";

    public static final String tasksPageHeader = "********** Tasks page: tasks **********\n"+
            "\n" +
            "Brings you to your tasks page from the main menu page.\n"+
            "In your tasks page, you can add different kinds of tasks to your schedule and view your upcoming tasks.\n" +
            "The following commands in this section can only be used in the tasks page.\n"+
            "\n"+
            "How to get to the tasks page: \n"+
            "\t- Type in the command tasks in the main menu page and press ENTER as shown in Figure 3.2.1 above.\n"+
            "\t- You can only go to the tasks page from the main menu.\n";

    public static final String todo =
            "\t[ Adding a todo: todo ]\n" +
            "\tAdds a todo task to the task list.\n" +
            "\tFormat: todo TASK_DESCRIPTION\n" +
            "\n" +
            "\tExamples:\n" +
            "\t\t- todo eat\n" +
            "\t\t- todo watch TV\n";

    public static final String deadline =
            "\t[ Adding a deadline: deadline ]\n" +
            "\tAdds a deadline task to the task list.\n" +
            "\tFormat: deadline TASK_DESCRIPTION /by YYYY-MM-DD HH:MM:SS\n" +
            "\n" +
            "\tExamples:\n" +
            "\t\t- deadline assignment /by 2019-12-04 12:07:08\n" +
            "\t\t- deadline watch TV /by 1988-06-27 08:46:37\n";

    public static final String event =
            "\t[ Adding an event: event ]\n" +
            "\tAdds an event task to the task list.\n" +
            "\tFormat: event TASK_DESCRIPTION /at YYYY-MM-DD HH:MM:SS-HH:SS:MM\n" +
            "\n" +
            "\tExamples: \n" +
            "\t\t- event party /at 2019-12-04 12:07:08-12:50:00\n" +
            "\t\t- event project meeting /at 1988-06-27 08:46:37-09:50:10\n";

    public static final String list =
            "\t[ Listing out the task list: list ]\n" +
            "\tList out all the tasks in the task list.\n" +
            "\tFormat: list\n";

    public static final String delete =
            "\t[ Delete task(s): delete ]\n" +
            "\tDeletes n number of tasks at once from the list or delete all tasks at once.\n" +
            "\tFormat: delete TASK_NUM1 and TASK_NUM2...OR delete all\n" +
            "\n" +
            "\tExamples:\n" +
            "\t\t- delete 3 and 5\n" +
            "\t\t- delete 6\n" +
            "\t\t- delete all\n";

    public static final String done =
            "\t[ Set a task as done: done]\n" +
            "\tMarks a task as done.\n" +
            "\tFormat: done TASK_NUM\n" +
            "\n" +
            "\tExamples:\n" +
            "\t\t- done 1\n" +
            "\t\t- done 6\n";

    public static final String tasksFind  =
            "\t[ Find tasks based on a keyword: find ]\n" +
                    "\tSearches for tasks based on a specified keyword.\n" +
                    "\tFormat: find KEYWORD\n" +
                    "\n" +
                    "\tExamples:\n" +
                    "\t\t- find read\n" +
                    "\t\t- find eat\n";

    public static final String scheduleDaily =
            "\t[ Viewing the schedule for a day: scheduleDaily ]\n" +
                    "\tLists out your schedule and your notes for the specified date. \n" +
                    "\tFormat: scheduleDaily YYYY-MM-DD\n" +
                    "\n" +
                    "\tExamples:\n" +
                    "\t\t- scheduleDaily 2019-08-09\n" +
                    "\t\t- scheduleDaily 2022-10-26\n";

    public static final String scheduleWeekly =
            "\t[ Viewing the schedule for a week: scheduleWeekly ]\n" +
                    "\tLists out your schedule and your notes for the specified week. \n" +
                    "\tFormat: scheduleWeekly YYYY-MM-DD YYYY-MM-DD\n" +
                    "\n" +
                    "\tFormat details:\n" +
                    "\t\t- The first date in the format above must be a Monday and the second date must be a Sunday.\n" +
                    "\t\t- The first date must be before the second date.\n" +
                    "\t\t- The number of days between the two dates must be 7.\n" +
                    "\n" +
                    "\tExamples: \n" +
                    "\t\t- scheduleWeekly 2019-10-07 2019-10-13\n" +
                    "\t\t- scheduleWeekly 2019-09-02 2019-09-08\n";

    public static final String scheduleMonthly =
            "\t[ Viewing the schedule for a month: scheduleMonthly ]\n" +
                    "\tLists out your schedule and your notes for the specified month. \n" +
                    "\tFormat: scheduleMonthly YYYY-MM\n" +
                    "\n" +
                    "\tExamples: \n" +
                    "\t\t- scheduleMonthly 2020-10\n" +
                    "\t\t- scheduleMonthly 2019-09\n";

    public static final String doWithinPeriod =
            "\t[ Adding a 'do-within-a-period' task: /between ]\n" +
                    "\tAdding tasks that need to be done within a certain period to the list.\n" +
                    "\tFormat: TASK_DESCRIPTION /between YYYY-MM-DD and YYYY-MM-DD\n" +
                    "\n" +
                    "\tExamples: \n" +
                    "\t\t- collect book /between 2019-12-04 and 2019-12-05\n" +
                    "\t\t- order food /between 2017-12-04 and 2017-12-05 \n";

    public static final String addTentativeEvent =
            "\t[ Adding a Tentative Event base on a keyword: tentative ]\n" +
                    "\tCreating a tentative event with description of the event.\n" +
                    "\tFormat: tentative TASK_DESCRIPTION\n" +
                    "\n" +
                    "\tExamples:\n" +
                    "\t\t- Tentative return book\n" +
                    "\n" +
                    "\tKey in all possible time slots, ends input with a keyword: /\n" +
                    "\tFormat: YYYY-MM-DD HH:MM:SS-HH:MM:SS\n" +
                    "\t        YYYY-MM-DD HH:MM:SS-HH:MM:SS\n" +
                    "\t        /\n" +
                    "\n" +
                    "\tExamples:\n" +
                    "\t\t- 2019-08-04 05:04:03-01:01:01\n" +
                    "\t\t  2019-09-18 07:11:44-10:10:10\n" +
                    "\t\t  /\n" +
                    "\n" +
                    "\tEnsure your creation with a keyword: yes\n" +
                    "\tExample: yes";

    public static final String confirmTentativeEvent =
            "\t[ Confirming a Tentative Event base on a keyword: confirm ]\n" +
                    "\tConfirming one if the timeslots of the exact tentative event with its INDEX.\n" +
                    "\tFormat: confirm INDEX\n" +
                    "\n" +
                    "\tExample: \n" +
                    "\t\t- confirm \n" +
                    "\n" +
                    "\tChoosing which time slot to confirm with its SEQUENCE_NUMBER\n" +
                    "\tFormat: SEQUENCE_NUMBER\n" +
                    "\n" +
                    "\tExample:\n" +
                    "\t\t- 1\n" +
                    "\n" +
                    "\tEnsure your confirmation with a keyword: yes\n" +
                    "\tExample: yes";

    public static final String tasksEdit =
            "\t[ Editing a task: edit ]\n" +
                    "\tEdit task with task’s INDEX.\n" +
                    "\tFormat: edit INDEX\n" +
                    "\n" +
                    "\tExample:\n" +
                    "\t\t- edit 1    \n" +
                    "\n" +
                    "\tChoose time/description/both to edit\n" +
                    "\tFormat: time\n" +
                    "\n" +
                    "\tExample:\n" +
                    "\t\t- 2019-10-10 10:10:10\n";

    public static final String fixedDurationTask =
            "\t[ Adding a task with a fixed duration: /require ]\n" +
                    "\tAdding a task takes a fixed amount of time but does not have a fixed start/end time to the list.\n" +
                    "\tFormat: TASK_DESCRIPTION /require FIXED_AMOUNT_OF_TIME\n" +
                    "\n" +
                    "\tExample: \n" +
                    "\t\t- reading the sales report /require 2 hours\n" +
                    "\t\t- project meeting /require 4 hours\n";

    public static final String categorizeTasks =
            "\t[ Categorize tasks: CATEGORY_NAME ]\n" +
                    "\tList out all the categorized tasks in the category task list.\n" +
                    "\tFormat: CATEGORY_NAME list\n" +
                    "\n" +
                    "\tExample:\n" +
                    "\t\t- event list\n" +
                    "\t\t- deadline list\n" +
                    "\t\t- todo list\n";

    public static final String monthlyCalendarView =
            "\t[ View current month in a calendar view: calendar monthly view ]\n" +
                    "\tShows the dates and current month in a calendar view.\n" +
                    "\tDates with tasks will be demarcated with a ‘ * ’.\n" +
                    "\tCurrent date will be demarcated between ‘ | ’.\n" +
                    "\tFormat: calendar monthly view";

    public static final String annualCalendarView =
            "\t[ View current year in a calendar view: calendar annual view ]\n" +
                    "\tShows the dates and months in a calendar view.\n" +
                    "\tDates with tasks will be demarcated with a ‘ * ’.\n" +
                    "\tCurrent date will be demarcated between ‘ | ’.\n" +
                    "\tFormat: calendar annual view";

    public static final String undone =
            "\t[ Set a ‘done’ task as undone: undone ]\n" +
                    "\tMarks a task as undone.\n" +
                    "\tFormat: undone TASK_NUM\n" +
                    "\n" +
                    "\tExamples:\n" +
                    "\t\t- undone 1\n" +
                    "\t\t- undone 6\n";

    public static final String doneList =
            "\t[ See all the tasks that are marked as done: done list ]\n" +
                    "\tList out all the tasks that are done in a list\n" +
                    "\tFormat: done list";

    public static final String doAfterTask =
            "\t[ Adding a do-after task: /after ]\n" +
                    "\tAdd a follow-up task when a task is marked as done.\n" +
                    "\tFormat: FOLLOW_UP_TASK /after COMPLETED_TASK\n" +
                    "\t\n" +
                    "\tExamples:\n" +
                    "\t\t- return book /after read book\n";

    public static final String snooze =
            "\t[ Snooze a task based on a keyword: reschedule ]\n" +
                    "\tSnooze a task’s timeline with task’s INDEX\n" +
                    "\tFormat: reschedule INDEX\n" +
                    "\n" +
                    "\tExample: \n" +
                    "\t\t- reschedule 1\n" +
                    "\n" +
                    "\tKey in your new timeline with the DATA\n" +
                    "\tFormat: YYYY-MM-DD HH:MM:SS\n" +
                    "\n" +
                    "\tExample: \n" +
                    "\t\t- 1998-04-05 08:09:14\n" +
                    "\n" +
                    "\tEnsure your rescheduling with a keyword: yes\n" +
                    "\t\n" +
                    "\tExample: \n" +
                    "\t\t- Yes\n";

    public static final String reschedule =
            "\t[ Reschedule a task based on a keyword: snooze ]\n" +
                    "\tReschedule a task’s timeline with task’s INDEX\n" +
                    "\tFormat: snooze INDEX\n" +
                    "\n" +
                    "\tExample:\n" +
                    "\t\t- snooze 1    \n" +
                    "\n" +
                    "\tKey in how much time you want to prolong\n" +
                    "\tFormat:  Y M D H\n" +
                    "\n" +
                    "\tExample: \n" +
                    "\t\t- 1 2 3 4\n";

    public static final String recurringTask =
            "\t[ Create recurring tasks based on a keyword: weekly/monthly/yearly ]\n" +
                    "\tA recurring task has any of the keywords.\n" +
                    "\tFormat: TASK_DESCRIPTION\n" +
                    "\n" +
                    "\tExample:\n" +
                    "\t\t- event weekly seminar /at 2019-10-10 10:10:10-11:11:11\n" +
                    "\n" +
                    "\tFormat: done INDEX\n" +
                    "\tExample:\n" +
                    "\t\t- done 1\n";

    public static final String undoneList =
            "\t[ See all the tasks that are marked as undone: undone list ]\n" +
                    "\tList out all the tasks that are undone in a list\n" +
                    "\tFormat: undone list";

    public static final String tagging =
            "\t[ Tagging tasks: #TAG_NAME ]\n" +
                    "\tTag tasks using the hashtag, and see the same tagged tasks in a list\n" +
                    "\tFormat: #TAG_NAME\n" +
                    "\n" +
                    "\tExample:\n" +
                    "\t\tTo tag:\n" +
                    "\t\t\t- todo running #fit\n" +
                    "\t\t\t- event basketball match #fit /at 2019-04-05 12:00:00-14:00:00\n" +
                    "\t\t\t- deadline claim voucher #food /by 2019-04-03 13:00:00\n" +
                    "\n" +
                    "\t\tTo see the tasks with the same tags:\n" +
                    "\t\t\t- #fit\n" +
                    "\t\t\t- #food\n";

    public static final String undoCommands =
            "\t[ Undo commands: undo ]\n" +
                    "\tUndo previous tasks commands\n" +
                    "\tFormat: undo\n" +
                    "\n" +
                    "\tPrevious commands that you can undo:\n" +
                    "\t\t- EventCommand\n" +
                    "\t\t- TodoCommand\n" +
                    "\t\t- DeadlineCommand\n" +
                    "\t\t- TentativeEventCommand\n" +
                    "\t\t- DoneCommand\n" +
                    "\t\t- DeleteCommand\n" +
                    "\t\t- DoAfterCommand\n" +
                    "\t\t- TimeBoundCommand\n" +
                    "\t\t- FixDurationCommand\n" +
                    "\t\t- ConfirmTentativeCommand\n" +
                    "\t\t- UndoneCommand\n";
}


