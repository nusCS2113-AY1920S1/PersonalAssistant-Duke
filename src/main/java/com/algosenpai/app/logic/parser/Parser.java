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
}
