//@@author carrieng0323852
package com.algosenpai.app.logic.parser;

import java.util.ArrayList;
import java.util.Arrays;

public class Parser {

    /**
     * Handle string entered by user.
     * @param input the raw user input
     * @return the Command with the relevant type and parameter.
     */
    public static ArrayList<String> parseInput(String input) {
        ArrayList<String> inputs = new ArrayList<>(Arrays.asList(input.split(" ")));
        inputs.set(0, inputs.get(0).toLowerCase());
        return inputs;
    }


}
