package com.algosenpai.app.exceptions;

public class DukeExceptions extends Exception {

    public DukeExceptions(String message) {
        super(message);
    }

    /**
     * To check if the input is empty.
     * @param userInput input keyed in by user
     * @throws DukeExceptions prints out message to inform user to key in a valid command in the correct format
     */
    public static void checkUserInput(String userInput) throws DukeExceptions {
        if (userInput.isEmpty()) {
            throw new DukeExceptions("☹ OOPS!!! Please key in a valid command. e.g. help");
        }
    }

    /**
     * To check if the commands that require an argument is being inputted.
     * @param userInput input keyed in by user
     * @throws DukeExceptions to inform user to key in a valid command in the given format
     */
    public static void checkArgument(String userInput) throws DukeExceptions {
        String[] tokens = userInput.split(" ");
        int count = 0;
        for (String i : tokens) {
            if (isInteger(i)) {
                count++;
            }
        }
        if (count == 0 || count > 1) {
            throw new DukeExceptions("☹ OOPS!!! Please key in a valid command. e.g. select 1.");
        }
    }

    /**
     * If the user meant to input his/her command, check if it is in the correct format.
     * @param userInput answer keyed in by the user
     * @throws DukeExceptions to inform user to key in his/her answer in the given format
     */
    public static void checkAnswer(String userInput) throws DukeExceptions {
        String[] tokens = userInput.split(" --> ");
        for (String i : tokens) {
            if (!isInteger(i)) {
                throw new DukeExceptions("☹ OOPS!!! Please key in your answer in the following format: 12 --> 32");
            }
        }
    }

    /**
     * Returns command to execute after parsing the user input.
     * @param s string taken in
     * @return boolean true if the string can be returned as an integer else boolean false
     */
    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
