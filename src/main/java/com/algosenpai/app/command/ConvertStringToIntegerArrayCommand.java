package com.algosenpai.app.command;

import com.algosenpai.app.exceptions.DukeExceptions;

public class ConvertStringToIntegerArrayCommand extends Command {

    /**
     * Chapter user is at.
     */

    private int chapter;

    /**
     * Input by user.
     */
    private String userInput;

    public ConvertStringToIntegerArrayCommand(String userInput) {
        this.userInput = userInput;
    }

    @Override
    public void execute(){

    }

    /**
     * Returns the answer of the user as an array of integers.
     *
     * @param userInput the original user answer with arrows
     * @return userAnswer the array of integers based on the user's original answer
     */
    private int[] userAnswer(String userInput) {
        String[] newArray = userInput.split(" -> ");
        int[] intArray = new int[10000];
        int count = 0;
        for (String i : newArray) {
            intArray[count] = Integer.parseInt(i);
            count++;
        }
        return intArray;
    }

}
