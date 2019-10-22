package seedu.hustler.parser;

import seedu.hustler.command.Command;
import seedu.hustler.command.avatarCommand.CheckAvatarCommand;
import seedu.hustler.command.avatarCommand.SetNameCommand;
import seedu.hustler.command.shopCommand.BuyCommand;
import seedu.hustler.command.shopCommand.ShopListCommand;
import seedu.hustler.command.taskCommand.*;
import seedu.hustler.data.CommandLog;
import seedu.hustler.command.*;
import seedu.hustler.logic.CommandLineException;

/**
 * Takes raw user input as string, makes sense out of the input using
 * regex and then performs operations based on the input.
 */
public class CommandParser extends Parser {
    /**
     * Takes raw input and splits it into task type (eg. todo) and task
     * description (eg. finish work). In cases like task type: list, bye,
     * the output array only contains task type.
     *
     * @param rawInput users single line string input
     * @return an array split into task type and task description
     */
    public String[] split(String rawInput) {
        String[] userInput = rawInput.split(" ", 2);
        return userInput;
    }

    /**
     * Default constructor.
     */

    /**
     * This method takes the raw user input and attempts to decipher
     * the user's intentions (whether the user wants to find a task, add
     * a task, etc.), thereafter returning the corresponding command.
     *
     * @param rawInput user's single line string input
     * @return an instruction, of type Command, to be executed.
     */
    public Command parse(String rawInput) throws CommandLineException {
        String[] userInput = this.split(rawInput);

        if (userInput[0].equals("/find")) {
            return new FindCommand(userInput);
        } else if (userInput[0].equals("/delete")) {
            CommandLog.recordCommand(rawInput);
            return new DeleteCommand(userInput);
        } else if (userInput[0].equals("/list")) {
            return new ListCommand();
        } else if (userInput[0].equals("/remind")) {
            return new RemindCommand();
        } else if (userInput[0].equals("/done")) {
            CommandLog.recordCommand(rawInput);
            return new DoneCommand(userInput);
        } else if (userInput[0].equals("/show")) {
            return new ScheduleCommand(userInput);
        } else if (userInput[0].equals("/snooze")) {
            return new SnoozeCommand(rawInput);
        } else if (userInput[0].equals("/avatar")) {
            if (userInput[1].equals("stats")) {
                return new CheckAvatarCommand();
            } else if (userInput[1].contains("setname")) {
                return new SetNameCommand(userInput);
            } else {
                return new InvalidCommand();
            }
        } else if (userInput[0].equals("/achievement")) {
            return new AchievementCommand();
        } else if (userInput[0].equals("/add")) {
            CommandLog.recordCommand(rawInput);
            return new AddCommand(userInput);
        } else if (userInput[0].equals("/timer")) {
            return new TimerCommand(userInput);
        } else if (userInput[0].equals("/undo")) {
            return new UndoCommand(userInput);
        } else if (userInput[0].equals("/pausetimer")) {
            return new PauseTimerCommand();
        } else if (userInput[0].equals("/resumetimer")) {
            return new ResumeTimerCommand();
        } else if (userInput[0].equals("/stoptimer")) {
            return new StopTimerCommand();
        } else if (userInput[0].equals("/showtimer")) {
            return new ShowTimerCommand();
        } else if (userInput[0].equals("/shop")) {
            return new ShopListCommand();
        } else if (userInput[0].equals("buy")) {
            try {
                int index = Integer.parseInt(userInput[1]);
                return new BuyCommand(index);
            } catch (NumberFormatException e) {
                System.out.println("\tPlease input buy <index>!");
                return new InvalidCommand();
            }
        } else if (userInput[0].equals("/prioritize") || userInput[0].equals("/chronological")
                || userInput[0].equals("/normal")) {
            return new SortCommand(rawInput);
        } else if (userInput[0].equals("/bye")) {
            return new ByeCommand();
        } else {
            return new InvalidCommand();
        }
    }
}
