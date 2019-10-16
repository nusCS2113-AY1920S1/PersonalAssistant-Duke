package com.algosenpai.app;

import com.algosenpai.app.command.BackCommand;
import com.algosenpai.app.command.ClearCommand;
import com.algosenpai.app.command.Command;
import com.algosenpai.app.command.ConvertStringToIntegerArrayCommand;
import com.algosenpai.app.command.ExitCommand;
import com.algosenpai.app.command.HelpCommand;
import com.algosenpai.app.command.HistoryCommand;
import com.algosenpai.app.command.MenuCommand;
import com.algosenpai.app.command.ReportCommand;
import com.algosenpai.app.command.ResetCommand;
import com.algosenpai.app.command.ResultCommand;
import com.algosenpai.app.command.SaveCommand;
import com.algosenpai.app.command.SelectCommand;
import com.algosenpai.app.command.StartCommand;
import com.algosenpai.app.command.UndoCommand;
import com.algosenpai.app.exceptions.DukeExceptions;


public class Parser {

    /**
     * Returns command to execute after parsing the user input.
     * @param userInput user's input command or answer
     * @return command to execute
     * @throws DukeExceptions if user input is invalid
     */

    public static Command parse(String userInput) throws DukeExceptions {
        String[] outputs = userInput.trim().split(" ");
        String firstWord = outputs[0];
        int num = Integer.parseInt(outputs[1]);

        if (firstWord.equals("exit")) {
            return new ExitCommand();
        } else if (firstWord.equals("menu")) {
            return new MenuCommand();
        } else if (firstWord.equals("start")) {
            return new StartCommand();
        } else if (firstWord.equals("select")) {
            return new SelectCommand(num);
        } else if (firstWord.equals("result")) {
            return new ResultCommand(num);
        } else if (firstWord.equals("report")) {
            return new ReportCommand();
        } else if (firstWord.equals("back")) {
            return new BackCommand();
        } else if (firstWord.equals("history")) {
            return new HistoryCommand(num);
        } else if (firstWord.equals("undo")) {
            return new UndoCommand();
        } else if (firstWord.equals("clear")) {
            return new ClearCommand(num);
        } else if (firstWord.equals("reset")) {
            return new ResetCommand();
        } else if (firstWord.equals("save")) {
            return new SaveCommand();
        } else if (firstWord.equals("help")) {
            return new HelpCommand();
        } else if (isInteger(firstWord)) {
            return new ConvertStringToIntegerArrayCommand(userInput);
        } else {
            throw new DukeExceptions("☹ OOPS!!! I'm sorry, please enter a valid command.");
        }
    }

    /**
     * Returns command to execute after parsing the user input.
     * @param s string taken in
     * @return boolean true if the string can be returned as an integer else boolean false
     */

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
