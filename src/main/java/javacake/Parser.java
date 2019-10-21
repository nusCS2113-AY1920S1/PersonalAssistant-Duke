package javacake;

import javacake.commands.AddCommand;
import javacake.commands.ChangeColorCommand;
import javacake.commands.Command;
import javacake.commands.ExitCommand;
import javacake.commands.ListCommand;
import javacake.commands.BackCommand;
import javacake.commands.HelpCommand;
import javacake.commands.ResetCommand;
import javacake.commands.ScoreCommand;
import javacake.commands.GoToCommand;
import javacake.commands.MegaListCommand;
import javacake.exceptions.DukeException;
import javacake.tasks.Task;
import javacake.tasks.ToDo;
import javacake.tasks.Deadline;
import javacake.tasks.RecurringTask;
import com.joestelmach.natty.DateGroup;
import javacake.ui.MainWindow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Parser {

    /**
     * Allows the user input to be parsed before running 'execute'.
     * @param inputCommand String inputted by user, which needs to be parsed
     *              to identify the intent
     * @return a subclass of the Command Class along
     *         with their respective intent
     * @throws DukeException Shows error when unknown command is inputted
     */
    public static Command parse(String inputCommand) throws DukeException {
        String[] buffer = inputCommand.split("\\s+");
        String input = buffer[0];
        if (input.equals("exit")) {
            return new ExitCommand();
        } else if (input.equals("list")) {
            return new ListCommand();
        } else if (input.equals("back")) {
            return new BackCommand();
        } else if (input.equals("help")) {
            return new HelpCommand(inputCommand);
        } else if (input.equals("score")) {
            return new ScoreCommand();
        } else if (input.equals("reset")) {
            return new ResetCommand();
        } else if (input.equals("goto")) {
            if (inputCommand.length() <= 4) {
                throw new DukeException("Please specify index number in 'goto' command!");
            }
            return new GoToCommand(inputCommand);
        } else if (input.equals("tree")) {
            return new MegaListCommand();
        } else if (input.equals("deadline")) {
            return new AddCommand(inputCommand);
        } else if (input.equals("change")) {
            MainWindow.isChanged = true;
            return new ChangeColorCommand();
        } else {
            throw new DukeException("OOPS!!! I'm sorry, but I don't know what that means.");
        }
    }

}
