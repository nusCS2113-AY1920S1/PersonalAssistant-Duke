//@@author carrieng0323852

package com.algosenpai.app.logic.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Parser {

    /**
     * Processes user input.
     * @param input raw user input
     * @return ArrayList of the user inputs.
     */
    public static ArrayList<String> parseInput(String input) {
        ArrayList<String> inputs;
        inputs = new ArrayList<>(
                Arrays.asList(input.trim().replaceAll(" +", " ").split(" ")));
        inputs.set(0, inputs.get(0).toLowerCase());
        return inputs;
    }

    /**
     * Checks string conversion to integer.
     * @param s input to check string conversion to integer.
     * @return true if string can be converted to integer.
     */
    public static boolean isInteger(String s) {
        Scanner sc = new Scanner(s.trim());
        if (!sc.hasNextInt()) {
            return false;
        }
        sc.nextInt();
        return !sc.hasNext();
    }

    /**
     * Finds out if a string is an integer.
     * @param s input string
     * @return boolean true if string is an integer else false otherwise
     */

    public static boolean integer(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean allCharacters(String s) {
        return ((!s.equals(""))) && (s != null) && (s.matches("^[a-zA-Z]*$"));
    }
}
