//@@author carrieng0323852

package com.algosenpai.app.logic.command;

import java.util.ArrayList;

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
        return "Invalid Command!";

        /*String input = new String();
        for (String i : inputs) {
            input += i;
        }
        return "Sorry please input a valid command." + compare(input);*/
    }


    // /**
    //* Choose command that is closest to the input by user.
    //* @param input command entered by user.
    //* @return the closest command.
    //*/
    /*
    public static String compare(String input) {
        String str = new String();
        double num = -1.000;
        String[] strings = new String[20];
        DecimalFormat df = new DecimalFormat("#.###");
        String[] names = Commands.getNames();
        int count = 0;

        for (String s: names) {
            double temp = ((double) countPairs(input, input.length(), s, s.length()) / (double) s.length());
            df.format(temp);
            if (temp > 0.500 && temp > num) {
                for (String ss : strings) {
                    ss = "";
                }
                count = 0;
                strings[count] = str;
            } else if (temp > 0.500 && temp == num) {
                strings[count++] = s;
                count++;
            }
        }
        if (!strings[0].isEmpty()) {
            return "Did you mean..." + strings;
        } else {
            return " To view the list of commands, enter `menu`.";
        }
    }*/

    ///**
    //* To compare the number of characters that the invalid command entered by user has with each valid command.
    //* @param unknownCommand input by user
    //* @param l1 length of the command inputted by user
    //* @param knownCommand one of the valid command
    //* @param l2 length of valid command
    //* @return number of similar characters
    //*/

    /*public static int countPairs(String unknownCommand, int l1, String knownCommand, int l2) {

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
    */
}
