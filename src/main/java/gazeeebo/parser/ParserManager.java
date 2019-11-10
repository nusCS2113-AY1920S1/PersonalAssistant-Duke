package gazeeebo.parser;

import gazeeebo.commands.Command;

import gazeeebo.UI.Ui;
import gazeeebo.commands.tasks.ByeCommand;

import gazeeebo.exception.DukeException;
import gazeeebo.commands.help.HelpCommand;


public class ParserManager {
    public static Command parse(final String command,
                                final Ui ui)
            throws DukeException {
        String[] splitCommand = command.split(" ");
        if (splitCommand[0].equals("help") || command.equals("1")) {
            return new HelpCommand();
        } else if (command.equals("contacts") || command.equals("2")) {
            return new ContactCommandParser();
        } else if (command.equals("expenses") || command.equals("3")) {
            return new ExpenseCommandParser();
        } else if (command.equals("places") || command.equals("4")) {
            return new PlacesCommandParser();
        } else if (splitCommand[0].equals("bye")) {
            return new ByeCommand();
        } else if (command.equals("spec") || command.equals("7")) {
            return new SpecializationCommandParser();
        } else if (splitCommand[0].equals("tasks") || command.equals("5")) {
            return new TaskCommandParser();
        } else if (splitCommand[0].equals("moduleplanner") || command.equals("8")) {
            return new StudyAssistCommandParser();
        } else if (splitCommand[0].equals("cap") || command.equals("6")) {
            String moduleCode = "";
            int moduleCredit = 0;
            String grade = "";
            return new CAPCommandParser(moduleCode, moduleCredit, grade);
        } else if (splitCommand[0].equals("notes") || command.equals("9")) {
            return new GeneralNoteCommandParser();
        } else {
            ui.showDontKnowErrorMessage();
            return null;
        }
    }
}