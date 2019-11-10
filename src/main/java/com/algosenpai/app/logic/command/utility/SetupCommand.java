package com.algosenpai.app.logic.command.utility;

import com.algosenpai.app.exceptions.FileParsingException;
import com.algosenpai.app.logic.command.Command;
import com.algosenpai.app.stats.UserStats;
import com.algosenpai.app.storage.Storage;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class SetupCommand extends Command {

    private UserStats stats;
    private static String userName;
    private static String gender;
    private static int level;
    private static int expLevel;

    /**
     * Create new command.
     *
     * @param inputs input from user.
     */
    public SetupCommand(ArrayList<String> inputs) {
        super(inputs);
    }

    /**
     * Initializes quiz command to start quiz.
     *
     * @param inputs user inputs.
     * @param stats  the UserStats object used.
     */
    public SetupCommand(ArrayList<String> inputs, UserStats stats) {
        this(inputs);
        this.stats = stats;
    }


    @Override
    public String execute() {
        if (inputs.size() < 3) {
            UserStats previousStats = null;

            try {
                previousStats = UserStats.parseString(Storage.loadData("UserData.txt"));
            } catch (FileParsingException | FileNotFoundException e) {
                e.printStackTrace();
            }

            if (previousStats.getUsername().equals("Default")) {
                return "Hello there! Welcome to the world of DATA STRUCTURES AND ALGORITHMS.\n"
                        + "Can I have your name and gender in the format : 'hello NAME GENDER (boy/girl)' please.";
            } else {
                gender = previousStats.getGender();
                userName = previousStats.getUsername();
                level = previousStats.getUserLevel();
                expLevel = previousStats.getUserExp();
                stats = new UserStats(previousStats);
                return "Welcome back " + userName + "! To continue on your adventure, pick a command from 'menu'.";
            }
        } else {
            userName = inputs.get(1);
            gender = inputs.get(2).toLowerCase();
            level = 1;
            expLevel = 0;
            if (gender.equals("boy") || gender.equals("girl")) {
                stats.setUsername(userName);
                stats.setGender(gender);
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

    public static int getLevel() {
        return level;
    }

    public static int getExpLevel() {
        return expLevel;
    }
}
