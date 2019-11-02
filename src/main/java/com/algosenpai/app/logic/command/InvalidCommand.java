//@@author carrieng0323852

package com.algosenpai.app.logic.command;

import com.algosenpai.app.logic.constant.Commands;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

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
        String input = new String();
        for (String i : inputs) {
            input += i;
        }
        return "Sorry please input a valid command. Did you mean... " + compare(input).toString();
    }

    static ArrayList<String> compare(String input) {
        String[] names = Commands.getNames();
        int num = -1;
        ArrayList<String> strings = new ArrayList<>();

        for (String s: names) {
            int temp = editDist(input, s, input.length(), s.length());
            if (temp > num) {
                num = temp;
                if (!strings.isEmpty()) {
                    clear(strings);
                }
                strings.add(s);
            } else if (temp == num) {
                strings.add(s);
            }
        }
        return strings;
    }

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

    private static int minimum(int a, int b, int c) {
        if (a <= b && a <= c) {
            return a;
        } else if (b <= a && b <= c) {
            return b;
        } else {
            return c;
        }
    }

    private static ArrayList<String> clear(ArrayList<String> list) {
        while (!list.isEmpty()) {
            list.remove(0);
        }
        return list;
    }
}
