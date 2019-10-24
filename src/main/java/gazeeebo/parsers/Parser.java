package gazeeebo.parsers;
import gazeeebo.commands.Edit.EditCommand;
import gazeeebo.commands.note.AddNoteCommand;
import gazeeebo.commands.note.DeleteNoteCommand;
import gazeeebo.commands.note.EditNoteCommand;
import gazeeebo.commands.note.ListNoteCommand;
import gazeeebo.commands.schedule.ScheduleDailyCommand;
import gazeeebo.commands.schedule.ScheduleMonthlyCommand;
import gazeeebo.commands.schedule.ScheduleWeeklyCommand;
//import gazeeebo.commands.specialization.SpecializationCommand;
//import gazeeebo.commands.specialization.SpecializationCommand;
import gazeeebo.commands.specialization.SpecializationCommand;
import gazeeebo.commands.tasks.*;

import gazeeebo.UI.Ui;
import gazeeebo.commands.tasks.ByeCommand;
import gazeeebo.commands.expenses.ExpenseCommand;
import gazeeebo.commands.gpacalculator.GPACommand;
import gazeeebo.commands.studyassist.studyassistCommand;
import gazeeebo.commands.note.GeneralNoteCommand;
import gazeeebo.commands.tasks.taskCommand;
import gazeeebo.exception.DukeException;
import gazeeebo.commands.*;
import gazeeebo.commands.Contact.ContactsCommand;
import gazeeebo.commands.help.HelpCommand;
import gazeeebo.commands.places.PlacesCommand;

public class Parser {
    public static Command parse(final String command, Ui ui) throws DukeException {
        String[] splitCommand = command.split(" ");
        if (splitCommand[0].equals("help")) {
            return new HelpCommand();
        } else if (command.equals("contacts")) {
            return new ContactsCommand();
        } else if (command.equals("expenses")) {
            return new ExpenseCommand();
        } else if (command.equals("places")) {
            return new PlacesCommand();
        } else if (splitCommand[0].equals("bye")) {
            return new ByeCommand();
        }
        //-------
        else if (command.equals("spec")) {
            return new SpecializationCommand();
        }

        else if (command.contains("/require")) {
            return new FixDurationCommand();
        } else if (splitCommand[0].equals("reschedule")) {
            return new RescheduleCommand();
        } else if (splitCommand[0].equals("sort")) {
            return new SortCommand();
        } else if (splitCommand[0].equals("scheduleDaily")) {
            return new ScheduleDailyCommand();
        } else if (splitCommand[0].equals("scheduleWeekly")) {
            return new ScheduleWeeklyCommand();
        } else if (splitCommand[0].equals("scheduleMonthly")) {
            return new ScheduleMonthlyCommand();
        } else if (splitCommand[0].equals("snooze")) {
            return new SnoozeCommand();
        } else if (splitCommand[0].equals("tentative")) {
            return new TentativeEventCommand();
        } else if (splitCommand[0].equals("confirm")) {
            return new ConfirmTentativeCommand();
        } else if (splitCommand[0].contains("undone")) {
            return new UndoneCommand();
        } else if (splitCommand[0].equals("undo")) {
            return new UndoCommand();
        } else if (splitCommand[0].equals("edit")) {
            return new EditCommand();
        } else if (splitCommand[0].equals("addNote")) {
            return new AddNoteCommand();
        } else if (splitCommand[0].equals("editNote")) {
            return new EditNoteCommand();
        } else if (splitCommand[0].equals("deleteNote")) {
            return new DeleteNoteCommand();
        } else if (splitCommand[0].equals("listNote")) {
            return new ListNoteCommand();
        } else if (splitCommand[0].equals("help")) {
                return new HelpCommand();
       } else if (command.equals("change password")) {
            return new ChangePasswordCommand();
        } else if (command.contains("#")) {
            return new TagCommand();
        }

        else if(splitCommand[0].equals("tasks")) {

            return new taskCommand();
        } else if (splitCommand[0].equals("moduleplanner")){
            return new studyassistCommand();
        } else if(splitCommand[0].equals("gpa")) {
            String moduleCode = "";
            int moduleCredit = 0;
            String grade = "";
            return new GPACommand(moduleCode,moduleCredit,grade);
        } else if (splitCommand[0].equals("notes")) {
            return new GeneralNoteCommand();
        } else {

            ui.showDontKnowErrorMessage();
            return null;
        }
    }
}