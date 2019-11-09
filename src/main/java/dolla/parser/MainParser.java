package dolla.parser;

import dolla.ModeStringList;

import dolla.model.Debt;
import dolla.ui.DebtUi;
import dolla.ui.Ui;

import dolla.command.Command;
import dolla.command.ErrorCommand;
import dolla.command.ExitCommand;
import dolla.command.SwitchModeCommand;

/**
 * MainParser directs the program to the appropriate parser depending on the current mode and the user's input.
 */
public class MainParser implements ParserStringList, ModeStringList {

    /**
     * Returns a command corresponding to the user input by directing the input to the relevant parser.
     * @param mode The mode Dolla is currently on.
     * @return a command corresponding to the user input.
     */
    public static Command handleInput(String mode, String inputLine) {

        inputLine = inputLine.trim();
        if (inputLine.isEmpty()) {
            Ui.printInvalidCommandError();
            return new ErrorCommand();
        }

        String[] inputArray = inputLine.split(SPACE);
        String command = inputArray[0];

        boolean isExitCommand = isExitCommand(command);
        boolean isSwitchMode = isSwitchModeCommand(command);
        if (isExitCommand) {
            return new ExitCommand();
        } else if (isSwitchMode) {
            return new SwitchModeCommand(command);
        }

        switch (mode) {
        case MODE_DOLLA:
            DollaParser dollaParser = new DollaParser(inputLine);
            return dollaParser.parseInput();
        case MODE_ENTRY:
            EntryParser entryParser = new EntryParser(inputLine);
            return entryParser.parseInput();
        case MODE_DEBT:
            DebtsParser debtsParser = new DebtsParser(inputLine);
            return debtsParser.parseInput();
        case MODE_LIMIT:
            LimitParser limitParser = new LimitParser(inputLine);
            return limitParser.parseInput();
        case MODE_SHORTCUT:
            ShortcutParser shortcutParser = new ShortcutParser(inputLine);
            return shortcutParser.parseInput();
        case MODE_MODIFY_ENTRY:
        case MODE_MODIFY_LIMIT:
        case MODE_MODIFY_DEBT:
        case MODE_MODIFY_SHORTCUT:
            ModifyParser modifyParser = new ModifyParser(mode, inputLine);
            return modifyParser.parseInput();
        default:
            Ui.printInvalidCommandError();
            return new ErrorCommand();
        }

    }

    private static boolean isExitCommand(String command) {
        return command.equals(COMMAND_BYE);
    }

    private static boolean isSwitchModeCommand(String command) {
        return command.equalsIgnoreCase(MODE_DOLLA) || command.equalsIgnoreCase(MODE_ENTRY)
                || command.equalsIgnoreCase(MODE_LIMIT) || command.equalsIgnoreCase(MODE_DEBT)
                || command.equalsIgnoreCase(MODE_SHORTCUT);
    }

    /**
     * This method will exit the entire program after printing a goodbye message.
     */
    public static void exit() {
        Ui.printExitMsg();
    }
}
