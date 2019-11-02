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

/**
 * Takes raw user input as string, makes sense out of the input using
 * regex and then performs operations based on the input.
 */
public class CommandParser extends Parser {
    /**
     * This method takes the raw user input and attempts to decipher
     * the user's intentions (whether the user wants to find a task, add
     * a task, etc.), thereafter returning the corresponding command.
     *
     * @param rawInput user's single line string input
     * @return an instruction, of type Command, to be executed.
     */
    public Command parse(String rawInput) {
        String[] userInput = rawInput.split(" ", 2);
        String command = userInput[0].toLowerCase();

        if (command.equals("/find")) {
            return new FindCommand(userInput);
        } else if (command.equals("/delete")) {
            CommandLog.recordCommand(rawInput);
            return new DeleteCommand(userInput);
        } else if (command.equals("/redo")) {
            return new RedoCommand(userInput);
        } else if (command.equals("/list")) {
            return new ListCommand(userInput);
        } else if (command.equals("/remind")) {
            return new RemindCommand(userInput);
        } else if (command.equals("/done")) {
            CommandLog.recordCommand(rawInput);
            return new DoneCommand(userInput);
        } else if (command.equals("/snooze")) {
            CommandLog.recordCommand(rawInput);
            return new SnoozeCommand(userInput);
        } else if (command.equals("/avatar")) {
            return new CheckAvatarCommand(userInput);
        } else if (command.equals("/setname")) {
            CommandLog.recordCommand(rawInput);
            return new SetNameCommand(userInput);
        } else if (command.equals("/achievements")) {
            return new AchievementCommand(userInput);
        } else if (command.equals("/add")) {
            CommandLog.recordCommand(rawInput);
            return new AddCommand(userInput);
        } else if (command.equals("/timer")) {
            return new TimerCommand(userInput);
        } else if (command.equals("/undo")) {
            return new UndoCommand(userInput);
        } else if (command.equals("/pausetimer")) {
            return new PauseTimerCommand();
        } else if (command.equals("/resumetimer")) {
            return new ResumeTimerCommand();
        } else if (command.equals("/stoptimer")) {
            return new StopTimerCommand();
        } else if (command.equals("/showtimer")) {
            return new ShowTimerCommand();
        } else if (command.equals("/shop")) {
            return new ShopListCommand(userInput);
        } else if (command.equals("/inventory")) {
            return new InventoryCommand(userInput);
        } else if (command.equals("/equip")) {
            return new EquipCommand(userInput);
        } else if (command.equals("/buy")) {
            return new BuyCommand(userInput);
        } else if (command.equals("/remove"))  {
            return new RemoveEntry(userInput);
        } else if (command.equals("/update")) {
            return new UpdateEntry(userInput);
        } else if (command.equals("/addfromlist")) {
            return new AddEntry(userInput);
        } else if (command.equals("/sort")) {
            return new SortCommand(userInput);
        } else if (command.equals("/bye")) {
            return new ByeCommand(userInput);
        } else {
            return new InvalidCommand();
        }
    }
}
