package com.algosenpai.app.logic.command;

import com.algosenpai.app.stats.UserStats;
import com.algosenpai.app.storage.Storage;

import java.util.ArrayList;

public class SetupCommand extends Command {

    private UserStats stats;
    private static String userName;
    private static String gender;
    private int level;
    private int expLevel;

    /**
     * Create new command.
     * @param inputs input from user.
     */
    public SetupCommand(ArrayList<String> inputs) {
        super(inputs);
    }

    /**
     * Initializes quiz command to start quiz.
     * @param inputs user inputs.
     * @param stats the UserStats object used.
     */
    public SetupCommand(ArrayList<String> inputs, UserStats stats) {
        this(inputs);
        this.stats = stats;
    }


    @Override
    public String execute() {
        if (inputs.size() < 3) {
            UserStats previousStats = UserStats.parseString(Storage.loadData("UserData.txt"));
            if (previousStats.getUsername().equals("Default")) {
                return "Hello there! Welcome to the world of DATA STRUCTURES AND ALGORITHMS.\n"
                    + "Can I have your name and gender in the format : 'hello NAME GENDER (boy/girl)' please.";
            } else {
                if (previousStats.getGender().equals("boy")) {
                    gender = "Mr. ";
                    userName = previousStats.getUsername();
                } else {
                    gender = "Ms. ";
                    userName = previousStats.getUsername();
                }
                return "Welcome back " + userName + "! To continue on your adventure, pick a command from 'menu'.";
            }
        } else {
            userName = inputs.get(1);
            stats.setUsername(userName);

            if (inputs.get(2).equals("boy")) {
                gender = "Mr. ";
                stats.setGender("boy");
                stats.setUserLevel(1);
                stats.setUserExp(0);
            } else if (inputs.get(2).equals("girl")) {
                gender = "Ms. ";
                stats.setGender("girl");
                stats.setUserLevel(1);
                stats.setUserExp(0);
            } else {
                return "Could you enter the setup command again with the appropriate gender?";
            }
        }
        stats.saveUserStats("UserData.txt");
        return "Hello " + userName + "! To see a list of commands, type 'menu'.";
    }

    public static String getGender() {
        return gender;
    }

    public static String getUserName() {
        return userName;
    }
}
