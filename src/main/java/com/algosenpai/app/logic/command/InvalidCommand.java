//@@author carrieng0323852

package com.algosenpai.app.logic.command;

import com.algosenpai.app.logic.constant.Commands;

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
        String input = inputs.get(0);
        return "!!!? Did you mean..." + compare(input);
    }

    /**
     * Choose command that is closest to the input by user.
     * @param input command entered by user.
     * @return the closest command.
     */

    public static String compare(String input) {
        String str = "";
        double num = -1;
        String[] names = Commands.getNames();

        for (String s: names) {
            double temp = countPairs(input, input.length(), s, s.length());
            if (temp > num) {
                str = s;
            }
        }
        return str;
    }

    /**
     * To compare the number of characters that the invalid command entered by user has with each valid command.
     * @param unknownCommand input by user
     * @param l1 length of the command inputted by user
     * @param knownCommand one of the valid command
     * @param l2 length of valid command
     * @return number of similar characters
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

}
