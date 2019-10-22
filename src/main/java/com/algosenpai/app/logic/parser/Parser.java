package com.algosenpai.app.logic.parser;

import com.algosenpai.app.logic.command.Command;
import com.algosenpai.app.logic.command.CommandEnum;
import com.algosenpai.app.logic.command.PrintCommand;

public class Parser {

    /**
     * Handle string entered by user.
     * @param input the raw user input
     * @return the Command with the relevant type and parameter.
     */
    public Command parseInput(String input) {
        String commandWord = "NULL";
        int parameter = 0;

        if (input.contains(" ")) {
            String[] parsedInput = input.split(" ");
            commandWord = parsedInput[0].toLowerCase();
            parameter = Integer.parseInt(parsedInput[1]);
        } else {
            commandWord = input;
        }

        System.out.println(commandWord);

        switch (commandWord) {
        case "hello":
            return new Command(CommandEnum.SETUP, 0, input);
        case "menu":
            return new Command(CommandEnum.MENU, 0, input);
        case "start":
            return new Command(CommandEnum.START, 0, input);
        case "select":
            return new Command(CommandEnum.SELECT, parameter, input);
        case "result":
            return new Command(CommandEnum.RESULT, 0, input);
        case "report":
            return new Command(CommandEnum.REPORT, 0, input);
        case "back":
            return new Command(CommandEnum.BACK, 0, input);
        case "history":
            return new Command(CommandEnum.HISTORY, 0, input);
        case "undo":
            return new Command(CommandEnum.UNDO, 0, input);
        case "clear":
            return new Command(CommandEnum.CLEAR, 0, input);
        case "reset":
            return new Command(CommandEnum.RESET, 0, input);
        case "save":
            return new Command(CommandEnum.SAVE, 0, input);
        case "help":
            return new Command(CommandEnum.HELP, 0, input);
        case "exit":
            return new Command(CommandEnum.EXIT, 0, input);
        case "print":
            return new PrintCommand(CommandEnum.PRINT, 0, input);
        case "archive":
            return new Command(CommandEnum.ARCHIVE, 0, input);
        default:
            return new Command(CommandEnum.INVALID, 0, input);
        }
    }
}
