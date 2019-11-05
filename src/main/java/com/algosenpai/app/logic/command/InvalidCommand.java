//@@author carrieng0323852

package com.algosenpai.app.logic.command;

import com.algosenpai.app.logic.constant.Commands;

import java.util.ArrayList;
import java.util.List;

public class InvalidCommand extends Command {

    /**
     * Create new command.
     * @param inputs input from user.
     */
    public InvalidCommand(ArrayList<String> inputs) {
        super(inputs);
    }

    @Override
    public String execute() {
        String input = "";
        for (String i : inputs) {
            if (!Commands.isInteger(i)) {
                input += i;
            }
        }
        return "Sorry please input a valid command. Did you mean... " + compare(input);
    }

    /**
     * Returns the closest possible word(s) to the invalid command entered by user.
     * @param input invalid command that the user entered
     * @return possible command(s) that the user might have meant
     */

    private static String compare(String input) {
        int num = 100;
        List<String> name = Commands.getNames();
        ArrayList<String> strings = new ArrayList<>();

        for (String s: name) {
            int temp = editDist(input, s, input.length(), s.length());
            if (temp < num) {
                num = temp;
                if (!strings.isEmpty()) {
                    clear(strings);
                }
                strings.add(s);
            } else if (temp == num) {
                strings.add(s);
            }
        }
        return strings.toString();
    }

    /**
     * Returns the edit distance from one word to another, which in other words,
     * means how many steps of the 3 operations (insert, remove and replace)
     * are needed to change one word to another.
     * @param input invalid command that the user entered
     * @param known one of the list of commands
     * @param x length of input
     * @param y length of known
     * @return minimum steps to convert input to known
     */

    private static int editDist(String input, String known, int x, int y) {
        if (x == 0) {
            return y;
        }

        if (y == 0) {
            return x;
        }

        if (input.charAt(x - 1) == known.charAt(y - 1)) {
            return editDist(input, known, x - 1, y - 1);
        }

        return 1 + minimum(editDist(input, known, x, y - 1), editDist(input, known, x - 1, y),
        editDist(input, known, x - 1, y - 1));
    }

    /**
     * Returns the minimum of the three operations.
     * @param a inserting
     * @param b removing
     * @param c replacing
     * @return the minimum of the three operations
     */

    private static int minimum(int a, int b, int c) {
        if (a <= b && a <= c) {
            return a;
        } else if (b <= a && b <= c) {
            return b;
        } else {
            return c;
        }
    }

    /**
     * Clears an arraylist.
     * @param list input arraylist
     * @return arraylist that has been cleared
     */

    private static ArrayList<String> clear(ArrayList<String> list) {
        while (!list.isEmpty()) {
            list.remove(0);
        }
        return list;
    }
}
