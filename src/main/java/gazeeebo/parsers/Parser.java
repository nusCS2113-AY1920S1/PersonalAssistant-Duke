package gazeeebo.parsers;

import gazeeebo.commands.Command;
import gazeeebo.commands.specialization.SpecializationCommand;

import gazeeebo.UI.Ui;
import gazeeebo.commands.note.GeneralNoteCommand;
import gazeeebo.commands.studyassist.studyassistCommand;
import gazeeebo.commands.tasks.ByeCommand;
import gazeeebo.commands.expenses.ExpenseCommand;

import gazeeebo.commands.capCalculator.CAPCommand;
import gazeeebo.commands.tasks.TaskCommand;
import gazeeebo.exception.DukeException;
import gazeeebo.commands.Contact.ContactCommand;
import gazeeebo.commands.help.HelpCommand;
import gazeeebo.commands.places.PlacesCommand;


public class Parser {
    public static Command parse(final String command,
                                final Ui ui)
            throws DukeException {
        String[] splitCommand = command.split(" ");
        if (splitCommand[0].equals("help") || command.equals("1")) {
            return new HelpCommand();
        } else if (command.equals("contacts") || command.equals("2")) {
            return new ContactCommand();
        } else if (command.equals("expenses") || command.equals("3")) {
            return new ExpenseCommand();
        } else if (command.equals("places") || command.equals("4")) {
            return new PlacesCommand();
        } else if (splitCommand[0].equals("bye") || command.equals("10")) {
            return new ByeCommand();
        } else if (command.equals("spec") || command.equals("7")) {
            return new SpecializationCommand();
        } else if (splitCommand[0].equals("tasks") || command.equals("5")) {
            return new TaskCommand();
        } else if (splitCommand[0].equals("moduleplanner") || command.equals("8")) {
            return new studyassistCommand();
        } else if (splitCommand[0].equals("cap") || command.equals("6")) {
            String moduleCode = "";
            int moduleCredit = 0;
            String grade = "";
            return new CAPCommand(moduleCode, moduleCredit, grade);
        } else if (splitCommand[0].equals("notes") || command.equals("9")) {
            return new GeneralNoteCommand();
        } else {
            ui.showDontKnowErrorMessage();
            return null;
        }
    }
}