
package gazeeebo.parser;

import gazeeebo.commands.Command;

import gazeeebo.UI.Ui;
import gazeeebo.commands.tasks.ByeCommand;
import gazeeebo.exception.DukeException;
import gazeeebo.commands.help.HelpCommand;

import java.io.IOException;
import java.text.ParseException;


public class ParserManager {
    /**
     * Parser command to parse the input into different pages.
     * @param command full command that is input into the command line
     * @param ui the object that deals with printing things to the user.
     * @return Command type
     * @throws DukeException Throws custom exception when
     *                        format of find command is wrong
     * @throws ParseException Catch error if parsing of command fails
     * @throws IOException    Catch error if the read file fails
     */

    public static Command parse(final String command,
                                final Ui ui)
            throws DukeException, IOException, ParseException {
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
        } else if (splitCommand[0].equals("moduleplanner")
                || command.equals("8")) {
            return new StudyAssistCommandParser();
        } else if (splitCommand[0].equals("cap") || command.equals("6")) {
            String moduleCode = "";
            int moduleCredit = 0;
            String grade = "";
            return new CapCommandParser(moduleCode, moduleCredit, grade);
        } else if (splitCommand[0].equals("notes")
                || command.equals("9")) {
            return new GeneralNoteCommandParser();
        } else if (command.equals("change password")
                || command.equals("10")) {
            new ChangePasswordCommandParser().execute(null, ui, null,
                    null, null, null);
            return null;
        } else {
            ui.showDontKnowErrorMessage();
            return null;
        }
    }
}