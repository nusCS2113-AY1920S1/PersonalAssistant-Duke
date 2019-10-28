package seedu.hustler.logic.parser;

import seedu.hustler.logic.command.Command;
import seedu.hustler.logic.command.achievementCommand.AchievementCommand;
import seedu.hustler.logic.command.shop.BuyCommand;
import seedu.hustler.logic.command.avatar.CheckAvatarCommand;
import seedu.hustler.logic.command.avatar.EquipCommand;
import seedu.hustler.logic.command.avatar.SetNameCommand;
import seedu.hustler.logic.command.schedulecommands.AddEntry;
import seedu.hustler.logic.command.schedulecommands.RemoveEntry;
import seedu.hustler.logic.command.schedulecommands.UpdateEntry;
import seedu.hustler.logic.command.shop.InventoryCommand;
import seedu.hustler.logic.command.shop.ShopListCommand;
import seedu.hustler.logic.command.task.*;
import seedu.hustler.logic.command.timer.*;
import seedu.hustler.data.CommandLog;
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
        } else if (userInput[0].equals("/redo")) {
            return new RedoCommand(userInput);
        } else if (userInput[0].equals("/list")) {
            return new ListCommand(userInput);
        } else if (userInput[0].equals("/remind")) {
            return new RemindCommand(userInput);
        } else if (userInput[0].equals("/done")) {
            CommandLog.recordCommand(rawInput);
            return new DoneCommand(userInput);
        } else if (userInput[0].equals("/show")) {
            return new ScheduleCommand(userInput);
        } else if (userInput[0].equals("/snooze")) {
            CommandLog.recordCommand(rawInput);
            return new SnoozeCommand(userInput);
        } else if (userInput[0].equals("/avatar") && userInput[1].equals("stats")) {
            return new CheckAvatarCommand(userInput);
        } else if (userInput[0].equals("/setname")) {
            CommandLog.recordCommand(rawInput);
            return new SetNameCommand(userInput);
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
        } else if (userInput[0].equals("/inventory")) {
            return new InventoryCommand(userInput);
        } else if (userInput[0].equals("/equip")) {
            return new EquipCommand(userInput);
        } else if (userInput[0].equals("/buy")) {
            return new BuyCommand(userInput);
        } else if (userInput[0].equals("/remove"))  {
            return new RemoveEntry(userInput);
        } else if (userInput[0].equals("/update")) {
            return new UpdateEntry(userInput);
        } else if (userInput[0].equals("/addFromList")) {
            return new AddEntry(userInput);
        } else if (userInput[0].equals("/sort")) {
            return new SortCommand(userInput);
        } else if (userInput[0].equals("/bye")) {
            return new ByeCommand(userInput);
        } else {
            return new InvalidCommand();
        }
    }
}
