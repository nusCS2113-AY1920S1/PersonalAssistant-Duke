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

import java.util.Arrays;


public class Parser {

    /**
     * Returns command to execute after parsing the user input.
     *
     * @param userInput user's input command or answer
     * @return command to execute
     * @throws DukeExceptions if user input is invalid or in the wrong format
     */
    public static Command parse(String userInput) {
        userInput = userInput.toLowerCase();

        String[] outputs = userInput.split(" "); //for commands

        try {
            DukeExceptions.checkUserInput(userInput);
            if (userInput.contains("exit")) {
                return new ExitCommand();
            } else if (userInput.contains("menu")) {
                return new MenuCommand();
            } else if (userInput.contains("start")) {
                return new StartCommand();
            } else if (userInput.contains("select")) {
                try {
                    DukeExceptions.checkArgument(userInput);
                    int num = Integer.parseInt(outputs[1].trim());
                    return new SelectCommand(num);
                } catch (DukeExceptions e) {
                    System.out.println("error\n" + e);
                }
            } else if (userInput.contains("result")) {
                try {
                    DukeExceptions.checkArgument(userInput);
                    int num = Integer.parseInt(outputs[1].trim());
                    return new ResultCommand(num);
                } catch (DukeExceptions e) {
                    System.out.println("error\n" + e);
                }
            } else if (userInput.contains("report")) {
                return new ReportCommand();
            } else if (userInput.contains("back")) {
                return new BackCommand();
            } else if (userInput.contains("history")) {
                try {
                    DukeExceptions.checkArgument(userInput);
                    int num = Integer.parseInt(outputs[1].trim());
                    return new HistoryCommand(num);
                } catch (DukeExceptions e) {
                    System.out.println("error\n" + e);
                }
            } else if (userInput.contains("undo")) {
                return new UndoCommand();
            } else if (userInput.contains("clear")) {
                try {
                    DukeExceptions.checkArgument(userInput);
                    int num = Integer.parseInt(outputs[1].trim());
                    return new ClearCommand(num);
                } catch (DukeExceptions e) {
                    System.out.println("error\n" + e);
                }
            } else if (userInput.contains("reset")) {
                return new ResetCommand();
            } else if (userInput.contains("save")) {
                return new SaveCommand();
            } else if (userInput.contains("help")) {
                return new HelpCommand();
            } else {
                if (hasDigit(outputs[0])) {
                    try {
                        DukeExceptions.checkAnswer(userInput);
                        return new ConvertStringToIntegerArrayCommand(userInput);
                    } catch (DukeExceptions e) {
                        System.out.println("error\n" + e);
                    }
                }
            }
        } catch (DukeExceptions e) {
            String closestCommand = possibleCommand(userInput);
            System.out.println(e + "Did you mean" + closestCommand);

        }
        return null;
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

    /**
     * Returns the command that is closest to whatever the user has typed in.
     * @param userInput the command user's wants to execute
     * @return closest command the user might have meant
     */

    public static String possibleCommand(String userInput) {
        String[] all = {"menu",
                        "start", "select", "report",
                        "result", "back", "history",
                        "undo", "clear", "reset",
                        "save", "help", "exit"};
        String str = "";
        double num = -1;
        for (String s : all) {
            double temp = countPairs(userInput, userInput.length(), s, s.length()) / s.length();
            if (temp > num) {
                num = temp;
                str = s;
            }
        }
        return str;
    }

    /**
     * To get the number of characters the two strings have in common.
     * @param unknownCommand the command user has inputted
     * @param l1 length of the unknownCommand
     * @param knownCommand one of the commands in the list of commands
     * @param l2 length of a particular command
     * @return the number of pairs of alphabets that the both strings have in common
     */

    public static int countPairs(String unknownCommand, int l1, String knownCommand, int l2) {

        int []freq1 = new int[26];
        int []freq2 = new int[26];
        Arrays.fill(freq1, 0);
        Arrays.fill(freq2, 0);

        int count = 0;

        for (int i = 0; i < l1; i++) {
            freq1[unknownCommand.charAt(i) - 'a']++;
        }
        for (int i = 0; i < l2; i++) {
            freq2[knownCommand.charAt(i) - 'a']++;
        }
        for (int i = 0; i < 26; i++) {
            count += (Math.min(freq1[i], freq2[i]));
        }

        return count;
    }

    /**
     * To tell if the user's input is meant to be an answer by checking if there is a digit inside.
     * @param s takes in the string that is to be checked
     * @return true if string contains a digit otherwise false
     */
    public static boolean hasDigit(String s) {
        for (char c: s.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }
}
