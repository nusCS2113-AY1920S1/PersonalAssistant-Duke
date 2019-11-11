package gazeeebo.parser;

import gazeeebo.commands.Command;
import gazeeebo.commands.tasks.CalendarView;
import gazeeebo.commands.tasks.TodoCommand;
import gazeeebo.commands.tasks.UndoTaskCommand;
import gazeeebo.commands.tasks.CategoryListCommand;
import gazeeebo.commands.tasks.ChangePriority;
import gazeeebo.commands.tasks.DeadlineCommand;
import gazeeebo.commands.tasks.ConfirmTentativeCommand;
import gazeeebo.commands.tasks.DeleteCommand;
import gazeeebo.commands.tasks.DoneCommand;
import gazeeebo.commands.tasks.DoAfterCommand;
import gazeeebo.commands.tasks.DoneListCommand;
import gazeeebo.commands.tasks.EventCommand;
import gazeeebo.commands.tasks.FindCommand;
import gazeeebo.commands.tasks.FixDurationCommand;
import gazeeebo.commands.tasks.RescheduleCommand;
import gazeeebo.commands.tasks.ListCommand;
import gazeeebo.commands.tasks.UndoneListCommand;
import gazeeebo.commands.tasks.UndoneCommand;
import gazeeebo.commands.tasks.TimeboundCommand;
import gazeeebo.commands.tasks.TentativeEventCommand;
import gazeeebo.commands.tasks.SnoozeCommand;
import gazeeebo.commands.tasks.SortCommand;
import gazeeebo.commands.tasks.TagCommand;
import gazeeebo.commands.tasks.edit.EditCommand;
import gazeeebo.commands.help.HelpCommand;
import gazeeebo.commands.note.AddNoteCommand;
import gazeeebo.commands.note.DeleteNoteCommand;
import gazeeebo.commands.note.EditNoteCommand;
import gazeeebo.commands.note.ListNoteCommand;
import gazeeebo.commands.schedule.ScheduleDailyCommand;
import gazeeebo.commands.schedule.ScheduleMonthlyCommand;
import gazeeebo.commands.schedule.ScheduleWeeklyCommand;
import gazeeebo.exception.DukeException;
import gazeeebo.triviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.storage.Storage;
import gazeeebo.tasks.Deadline;
import gazeeebo.tasks.Event;
import gazeeebo.tasks.Task;
import gazeeebo.tasks.Todo;
import gazeeebo.tasks.FixedDuration;
import gazeeebo.tasks.Timebound;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Stack;

public class TaskCommandParser extends Command {
    private static final String COMMAND_LIST = "_______________________________________________________________"
            + "____________________\n"
            + "Adding a todo: todo task_description\n"
            + "Adding a deadline: deadline task_description /by YYYY-MM-DD HH:MM:SS\n"
            + "Adding an event: event task_description /at YYYY-MM-DD HH:MM:SS-HH:SS:MM\n"
            + "See all your task list: list\n"
            + "See your task based on category: category_name list"
            + "Delete task(s): delete task_number1 and task_number2 and...\n"
            + "Delete all tasks: delete all\n"
            + "Mark task as done: done task_number\n"
            + "Search for a task: find keyword\n"
            + "View daily schedule: scheduleDaily YYYY-MM-DD\n"
            + "View weekly schedule: scheduleWeekly YYYY-MM-DD YYYY-MM-DD\n"
            + "View monthly schedule: scheduleMonthly YYYY-MM\n"
            + "Adding a 'do-within-a-period' task: task_description /between YYYY-MM-DD and YYYY-MM-DD\n"
            + "Adding a tentative event: tentative task_description\n"
            + "Confirming a tentative event: confirm task_number\n"
            + "Editing a task: edit task_number\n"
            + "Adding a task with fixed duration: task_description /require amount_of_time\n"
            + "Ranking your task: task_number rank rank_number\n"
            + "View monthly calendar: calendar monthly view\n"
            + "View annual calendar: calendar annual view\n"
            + "Marking a task as undone: undone task_number\n"
            + "Adding a do-after task: follow_up_task /after completed_task\n"
            + "Reschedule a task: reschedule task_number\n"
            + "Prolong a task: snooze task_number\n"
            + "Create recurring tasks: task_description weekly/monthly/yearly\n"
            + "Tagging a task: #tagname\n"
            + "Undo previous commands: undo\n"
            + "Adding a note to a day/week/month: addNote day/week/month YYYY-MM-DD/YYYY-MM-DD/YYYY-MM\n"
            + "Editing a note: editNote day/week/month YYYY-MM-DD/YYYY-MM-DD/YYYY-MM note_number\n"
            + "Deleting a note: deleteNote day/week/month YYY-MM-DD/YYYY-MM-DD/YYYY-MM note_number\n"
            + "Listing all notes: listNote day/week/month YYY-MM-DD/YYYY-MM-DD/YYYY-MM note_number\n"
            + "Show all commands: commands\n"
            + "Return to main page: esc\n"
            + "________________________________________________________________________________________________\n";
    private static final String MAIN_MENU_PAGE = "Going back to Main Menu...\n"
            + "Content Page:\n"
            + "------------------ \n"
            + "1. help\n"
            + "2. contacts\n"
            + "3. expenses\n"
            + "4. places\n"
            + "5. tasks\n"
            + "6. cap\n"
            + "7. spec\n"
            + "8. moduleplanner\n"
            + "9. notes\n"
            + "10. change password\n"
            + "To exit: bye\n";
    /**
     * Parses the user input and return a command object.
     *
     * @param list the list of all tasks.
     * @param ui the object that deals with printing things to the user.
     * @param storage the object that deals with storing data.
     * @param commandStack the stack of previous commands.
     * @param deletedTask the list of deleted task.
     * @param triviaManager the object for triviaManager
     * @throws DukeException exception thrown when there is an input error.
     * @throws ParseException parse exception from help command
     * @throws IOException input or output error when interacting with user.
     */

    @Override
    public void execute(ArrayList<Task> list,
                        final Ui ui, final Storage storage,
                        final Stack<ArrayList<Task>> commandStack,
                        final ArrayList<Task> deletedTask,
                        final TriviaManager triviaManager)
            throws IOException, DukeException, ParseException {
        System.out.println("Welcome to your Tasks page!"
                + "What would you like to do?\n");
        Calendar now = Calendar.getInstance();
        int month = (now.get(Calendar.MONTH) + 1);
        int year = now.get(Calendar.YEAR);
        int date = now.get(Calendar.DATE);
        CalendarView calendarView = new CalendarView();
        calendarView.monthlyView(list,month,year,date);

        System.out.println(COMMAND_LIST);
        ArrayList<Task> oldList;
        while (!ui.fullCommand.equals("esc")) {
            ui.readCommand();
            String command = ui.fullCommand;
            String[] splitCommand = command.split(" ");
            if (splitCommand[0].equals("help")) {
                new HelpCommand().execute(list, ui, storage,
                        commandStack, deletedTask, triviaManager);
            } else if (command.equals("event list")) {
                new CategoryListCommand().execute(list, ui, storage, commandStack, deletedTask, triviaManager);
            } else if (command.equals("commands")) {
                System.out.println(COMMAND_LIST);
            } else if (command.equals("deadline list")) {
                new CategoryListCommand().execute(list, ui, storage,
                        commandStack, deletedTask, triviaManager);
            } else if (command.equals("todo list")) {
                new CategoryListCommand().execute(list, ui, storage,
                        commandStack, deletedTask, triviaManager);
            } else if (command.equals("fixed list")) {
                new CategoryListCommand().execute(list, ui, storage,
                        commandStack, deletedTask, triviaManager);
            } else if (command.equals("timebound list")) {
                new CategoryListCommand().execute(list, ui, storage,
                        commandStack, deletedTask, triviaManager);
            } else if (command.equals("list")) {
                new ListCommand().execute(list, ui, storage,
                        commandStack, deletedTask, triviaManager);
            } else if (command.equals("done list")) {
                new DoneListCommand().execute(list, ui, storage,
                        commandStack, deletedTask, triviaManager);
            } else if (command.equals("undone list")) {
                new UndoneListCommand().execute(list, ui, storage,
                        commandStack, deletedTask, triviaManager);
            } else if (splitCommand[0].equals("done")) {
                oldList = new ArrayList<>();
                copyOldList(oldList, list);
                new DoneCommand().execute(list, ui, storage,
                        commandStack, deletedTask, triviaManager);
                commandStack.push(oldList);
            } else if (splitCommand[0].equals("delete")) {
                oldList = new ArrayList<>();
                copyOldList(oldList, list);
                new DeleteCommand().execute(list, ui, storage,
                        commandStack, deletedTask, triviaManager);
                commandStack.push(oldList);
            } else if (splitCommand[0].equals("deadline")) {
                oldList = new ArrayList<>();
                copyOldList(oldList, list);
                new DeadlineCommand().execute(list, ui, storage,
                        commandStack, deletedTask, triviaManager);
                commandStack.push(oldList);
            } else if (command.contains("/after")) {
                oldList = new ArrayList<>();
                copyOldList(oldList, list);
                new DoAfterCommand().execute(list, ui, storage,
                        commandStack, deletedTask, triviaManager);
                commandStack.push(oldList);
            } else if (splitCommand[0].equals("event")) {
                oldList = new ArrayList<>();
                copyOldList(oldList, list);
                new EventCommand().execute(list, ui, storage,
                        commandStack, deletedTask, triviaManager);
                commandStack.push(oldList);
            } else if (splitCommand[0].equals("todo")) {
                oldList = new ArrayList<>();
                copyOldList(oldList, list);
                new TodoCommand().execute(list, ui, storage,
                        commandStack, deletedTask, triviaManager);
                commandStack.push(oldList);
            } else if (command.contains("/between")) {
                oldList = new ArrayList<>();
                copyOldList(oldList, list);
                new TimeboundCommand().execute(list, ui, storage,
                        commandStack, deletedTask, triviaManager);
                commandStack.push(oldList);
            } else if (splitCommand[0].equals("find")) {
                new FindCommand().execute(list, ui, storage,
                        commandStack, deletedTask, triviaManager);
            } else if (command.contains("/require")) {
                oldList = new ArrayList<>();
                copyOldList(oldList, list);
                new FixDurationCommand().execute(list, ui, storage,
                        commandStack, deletedTask, triviaManager);
                commandStack.push(oldList);
            } else if (splitCommand[0].equals("reschedule")) {
                oldList = new ArrayList<>();
                copyOldList(oldList, list);
                new RescheduleCommand().execute(list, ui, storage,
                        commandStack, deletedTask, triviaManager);
                commandStack.push(oldList);
            } else if (splitCommand[0].equals("sort")) {
                new SortCommand().execute(list, ui, storage,
                        commandStack, deletedTask, triviaManager);
            } else if (splitCommand[0].equals("scheduleDaily")) {
                new ScheduleDailyCommand().execute(list, ui, storage,
                        commandStack, deletedTask, triviaManager);
            } else if (splitCommand[0].equals("scheduleWeekly")) {
                new ScheduleWeeklyCommand().execute(list, ui, storage,
                        commandStack, deletedTask, triviaManager);
            } else if (splitCommand[0].equals("scheduleMonthly")) {
                new ScheduleMonthlyCommand().execute(list, ui, storage,
                        commandStack, deletedTask, triviaManager);
            } else if (splitCommand[0].equals("snooze")) {
                oldList = new ArrayList<>();
                copyOldList(oldList, list);
                new SnoozeCommand().execute(list, ui, storage,
                        commandStack, deletedTask, triviaManager);
                commandStack.push(oldList);
            } else if (splitCommand[0].equals("tentative")) {
                oldList = new ArrayList<>();
                copyOldList(oldList, list);
                new TentativeEventCommand().execute(list, ui, storage,
                        commandStack, deletedTask, triviaManager);
                commandStack.push(oldList);
            } else if (splitCommand[0].equals("confirm")) {
                oldList = new ArrayList<>();
                copyOldList(oldList, list);
                new ConfirmTentativeCommand().execute(list, ui, storage,
                        commandStack, deletedTask, triviaManager);
                commandStack.push(oldList);
            } else if (splitCommand[0].contains("undone")) {
                oldList = new ArrayList<>();
                copyOldList(oldList, list);
                new UndoneCommand().execute(list, ui, storage,
                        commandStack, deletedTask, triviaManager);
                commandStack.push(oldList);
            } else if (splitCommand[0].equals("undo")) {
                list = UndoTaskCommand.undo(commandStack, list, storage);
            } else if (splitCommand[0].equals("edit")) {
                oldList = new ArrayList<>();
                copyOldList(oldList, list);
                new EditCommand().execute(list, ui, storage,
                        commandStack, deletedTask, triviaManager);
                commandStack.push(oldList);
            } else if (splitCommand[0].equals("addNote")) {
                new AddNoteCommand().execute(list, ui, storage,
                        commandStack, deletedTask, triviaManager);
            } else if (splitCommand[0].equals("editNote")) {
                new EditNoteCommand().execute(list, ui, storage,
                        commandStack, deletedTask, triviaManager);
            } else if (splitCommand[0].equals("deleteNote")) {
                new DeleteNoteCommand().execute(list, ui, storage,
                        commandStack, deletedTask, triviaManager);
            } else if (splitCommand[0].equals("listNote")) {
                new ListNoteCommand().execute(list, ui, storage,
                        commandStack, deletedTask, triviaManager);
            } else if (splitCommand[0].equals("priority")) {
                new ChangePriority().execute(list, ui, storage,
                        commandStack, deletedTask, triviaManager);
            } else if (command.contains("#")) {
                new TagCommand().execute(list, ui, storage,
                        commandStack, deletedTask, triviaManager);
            } else if (command.equals("calendar monthly view")) {
                new CalendarView().monthlyView(list, month, year, date);
            } else if (command.equals("calendar annual view")) {
                new CalendarView().annualView(list, month, year, date);
            } else if (command.equals("esc")) {
                System.out.println(MAIN_MENU_PAGE);
            } else {
                ui.showDontKnowErrorMessage();
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


    /**
     * Copy an Arraylist of task.
     *
     * @param oldList previous list of task before new command executed
     * @param list current list of tasks
     */
    public static void copyOldList(final ArrayList<Task> oldList,
                                   ArrayList<Task> list) {
        for (Task task : list) {
            if (task.getClass().getName().equals("gazeeebo.tasks.Deadline")) {
                Deadline deadline = new Deadline(task.description,
                        task.toString().split("by:")[1].trim());
                deadline.isDone = task.isDone;
                oldList.add(deadline);
            } else if (task.getClass().getName().equals(
                    "gazeeebo.tasks.Event")) {
                Event event = new Event(task.description,
                        task.toString().split("at:")[1].trim());
                event.isDone = task.isDone;
                oldList.add(event);
            } else if (task.getClass().getName().equals(
                    "gazeeebo.tasks.Todo")) {
                Todo todo = new Todo(task.description);
                todo.isDone = task.isDone;
                oldList.add(todo);
            } else if (task.getClass().getName().equals(
                    "gazeeebo.tasks.FixedDuration")) {
                FixedDuration fixedDuration
                        = new FixedDuration(task.description,
                        task.toString().split("\\|")[3].trim());
                fixedDuration.isDone = task.isDone;
                oldList.add(fixedDuration);
            } else if (task.getClass().getName().equals(
                    "gazeeebo.tasks.Timebound")) {
                Timebound timebound
                        = new Timebound(task.description,
                        task.toString().split("\\|")[3].trim());
                timebound.isDone = task.isDone;
                oldList.add(timebound);
            }
        }
    }
}
