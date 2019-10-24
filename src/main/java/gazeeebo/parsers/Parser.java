package gazeeebo.parsers;

import gazeeebo.UI.Ui;
import gazeeebo.commands.note.GeneralNoteCommand;
import gazeeebo.commands.studyassist.studyassistCommand;
import gazeeebo.commands.tasks.ByeCommand;
import gazeeebo.commands.expenses.ExpenseCommand;

import gazeeebo.commands.capCalculator.CAPCommand;
import gazeeebo.commands.tasks.taskCommand;
import gazeeebo.exception.DukeException;
import gazeeebo.commands.*;
import gazeeebo.commands.contact.ContactCommand;
import gazeeebo.commands.help.HelpCommand;
import gazeeebo.commands.places.PlacesCommand;


public class Parser {
    public static Command parse(final String command, Ui ui) throws DukeException {
        String[] splitCommand = command.split(" ");
        if (splitCommand[0].equals("help")) {
            return new HelpCommand();
        } else if (command.equals("contacts")) {
            return new ContactCommand();
        } else if (command.equals("expenses")) {
            return new ExpenseCommand();
        } else if (command.equals("places")) {
            return new PlacesCommand();
        } else if (splitCommand[0].equals("bye")) {
            return new ByeCommand();
        } else if (splitCommand[0].equals("tasks")) {
            return new taskCommand();
        } else if (splitCommand[0].equals("moduleplanner")){
            return new studyassistCommand();
        } else if(splitCommand[0].equals("cap")) {
            String moduleCode = "";
            int moduleCredit = 0;
            String grade = "";
            return new CAPCommand(moduleCode,moduleCredit,grade);
        } else if (splitCommand[0].equals("notes")) {
            return new GeneralNoteCommand();
        } else {
            ui.showDontKnowErrorMessage();
            return null;
        }
    }
}