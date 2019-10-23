package gazeeebo.commands.tasks;

import gazeeebo.commands.Command;
import gazeeebo.commands.edit.EditCommand;
import gazeeebo.commands.help.HelpCommand;
import gazeeebo.commands.note.AddNoteCommand;
import gazeeebo.commands.note.DeleteNoteCommand;
import gazeeebo.commands.note.EditNoteCommand;
import gazeeebo.commands.note.ListNoteCommand;
import gazeeebo.commands.schedule.ScheduleDailyCommand;
import gazeeebo.commands.schedule.ScheduleMonthlyCommand;
import gazeeebo.commands.schedule.ScheduleWeeklyCommand;
import gazeeebo.exception.DukeException;
import gazeeebo.tasks.Task;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.storage.Storage;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

public class taskCommand extends Command {
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<String> commandStack, ArrayList<Task> deletedTask, TriviaManager triviaManager) throws IOException, DukeException, ParseException {
        System.out.println("Welcome to your Tasks page! What would you like to do?\n");
        CalendarMonthlyView calendarMonthlyView = new CalendarMonthlyView();
        calendarMonthlyView.MonthlyView(list);
        while(!ui.fullCommand.equals("esc")) {
            ui.readCommand();
            String command = ui.fullCommand;
            String[] splitCommand = command.split(" ");
            if (splitCommand[0].equals("help")) {
                new HelpCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
            } else if (splitCommand[0].equals("list")) {
                if (command.contains("event")) {
                    new CategoryListCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
                } else if (command.contains("deadline")) {
                    new CategoryListCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
                } else if (command.contains("todo")) {
                    new CategoryListCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
                } else if (command.contains("fixed")) {
                    new CategoryListCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
                } else if (command.contains("timebound")) {
                    new CategoryListCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
                } else {
                    new ListCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
                }
            } else if (command.equals("done list")) {
                new DoneListCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
            } else if (command.equals("undo list")) {
                new UndoneListCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
            } else if (splitCommand[0].equals("done")) {
                new DoneCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
            } else if (splitCommand[0].equals("delete")) {
                new DeleteCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
            } else if (splitCommand[0].equals("deadline")) {
                new DeadlineCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
            } else if (command.contains("/after")) {
                new DoAfterCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
            } else if (splitCommand[0].equals("event")) {
                new EventCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
            } else if (splitCommand[0].equals("todo")) {
                new TodoCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
            } else if (command.contains("/between")) {
                new TimeboundCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
            } else if (splitCommand[0].equals("find")) {
                new FindCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
            } else if (command.contains("/require")) {
                new FixDurationCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
            } else if (splitCommand[0].equals("reschedule")) {
                new RescheduleCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
            } else if (splitCommand[0].equals("sort")) {
                new SortCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
            } else if (splitCommand[0].equals("scheduleDaily")) {
                new ScheduleDailyCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
            } else if (splitCommand[0].equals("scheduleWeekly")) {
                new ScheduleWeeklyCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
            } else if (splitCommand[0].equals("scheduleMonthly")) {
                new ScheduleMonthlyCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
            } else if (splitCommand[0].equals("snooze")) {
                new SnoozeCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
            } else if (splitCommand[0].equals("tentative")) {
                new TentativeEventCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
            } else if (splitCommand[0].equals("confirm")) {
                new ConfirmTentativeCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
            } else if (splitCommand[0].contains("undone")) {
                new UndoneCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
            } else if (splitCommand[0].equals("undo")) {
                new UndoCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
            } else if (splitCommand[0].equals("edit")) {
                new EditCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
            } else if (splitCommand[0].equals("addNote")) {
                new AddNoteCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
            } else if (splitCommand[0].equals("editNote")) {
                new EditNoteCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
            } else if (splitCommand[0].equals("deleteNote")) {
                new DeleteNoteCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
            } else if (splitCommand[0].equals("listNote")) {
                new ListNoteCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
            } else if (command.equals("change password")) {
                new ChangePasswordCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
            } else if(splitCommand[0].equals("priority")) {
                new ChangePriority().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
            } else if (command.contains("#")) {
                new TagCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
            } else if (command.equals("calendar monthly view")) {
                new CalendarMonthlyView().MonthlyView(list);
            } else if (command.equals("esc")) {
                System.out.println("Back to the main page!");
            } else {
                System.out.println("OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
        }
    }
    @Override
    public boolean isExit() {
        return false;
    }
}
