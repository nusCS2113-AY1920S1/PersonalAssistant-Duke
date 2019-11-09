package javacake;

import javacake.commands.AddCommand;
import javacake.commands.ChangeColorCommand;
import javacake.commands.BackCommand;
import javacake.commands.Command;
import javacake.commands.CreateNoteCommand;
import javacake.commands.DeleteCommand;
import javacake.commands.DeleteNoteCommand;
import javacake.commands.DoneCommand;
import javacake.commands.EditCommand;
import javacake.commands.EditNoteCommand;
import javacake.commands.ExitCommand;
import javacake.commands.GoToCommand;
import javacake.commands.HelpCommand;
import javacake.commands.ListCommand;
import javacake.commands.ListNoteCommand;
import javacake.commands.OverviewCommand;
import javacake.commands.ReminderCommand;
import javacake.commands.ResetCommand;
import javacake.commands.ScoreCommand;
import javacake.commands.ViewNoteCommand;
import javacake.exceptions.CakeException;

public class Parser {

    /**
     * Allows the user input to be parsed before running 'execute'.
     * @param inputCommand String inputted by user, which needs to be parsed
     *              to identify the intent
     * @return a subclass of the Command Class along
     *         with their respective intent
     * @throws CakeException Shows error when unknown command is inputted
     */
    public static Command parse(String inputCommand) throws CakeException {
        String[] buffer = inputCommand.split("\\s+");
        String commandWord = buffer[0];
        helper(commandWord);
        switch (commandWord) {
        case ("exit"): return new ExitCommand(inputCommand);
        case ("list"): return new ListCommand(inputCommand);
        case ("back"): return new BackCommand(inputCommand);
        case ("score"): return new ScoreCommand(inputCommand);
        case ("reset"): return new ResetCommand(inputCommand);
        case ("help"): return new HelpCommand(inputCommand);
        case ("overview"): return new OverviewCommand(inputCommand);
        case ("listnote"): return new ListNoteCommand(inputCommand);
        case ("reminder"): return new ReminderCommand(inputCommand);
        case ("goto"): return new GoToCommand(inputCommand);
        case ("createnote"): return new CreateNoteCommand(inputCommand);
        case ("editnote"): return new EditNoteCommand(inputCommand);
        case ("deletenote"): return new DeleteNoteCommand(inputCommand);
        case ("viewnote"): return new ViewNoteCommand(inputCommand);
        case ("deadline"): return new AddCommand(inputCommand);
        case ("done"): return new DoneCommand(inputCommand);
        case ("delete"): return new DeleteCommand(inputCommand);
        case ("snooze"): return new EditCommand(inputCommand);
        case ("change"): return new ChangeColorCommand();
        default:
            throw new CakeException("OOPS!!! I'm sorry, but I don't know what that means.");
        }
    }

    /**
     * Method to help handle small typo made by user.
     * Types of typo handled are:
     * 1) if user types one alphabet wrongly, eg. trre instead of tree.
     * 2) if user accidentally types extra or less letter, eg. treee or tre instead of tree.
     */
    private static void helper(String input) throws CakeException {
        String[] commands = {"exit", "list", "back", "help", "score", "reset", "goto",
            "overview", "deadline", "editnote", "createnote", "listnote", "deletenote",
            "change", "reminder", "viewnote", "done", "delete", "snooze"};

        for (int i = 0; i < commands.length; i++) {
            boolean isTypo = false;
            String command = commands[i];
            int length = command.length();

            if (length == input.length()) {
                int similarity = 0;
                for (int j = 0; j < length; j++) {
                    if (input.charAt(j) == command.charAt(j)) {
                        similarity++;
                    }
                }
                if (similarity + 1 == length) {
                    isTypo = true;
                }
            }

            boolean isOneLetterApart = false;

            if (command.length() == input.length() + 1 || command.length() == input.length() - 1) {
                isOneLetterApart = true;
            }

            if (!command.equals(input) && isOneLetterApart && (command.contains(input) || input.contains(command))) {
                isTypo = true;
            }

            if (isTypo) {
                throw new CakeException("Sorry, but do you mean this : " + command);
            }
        }
    }

}
