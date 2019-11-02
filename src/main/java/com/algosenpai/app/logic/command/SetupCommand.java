package com.algosenpai.app.logic.command;

import com.algosenpai.app.stats.UserStats;
import com.algosenpai.app.storage.Storage;

import java.util.ArrayList;

public class SetupCommand extends Command {

    private UserStats stats;
    private String userName;
    private String gender;
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
            UserStats previousStats = stats.parseString(Storage.loadData("UserData.txt"));
            if (previousStats.getUsername().equals("Default")) {
                return "Hmm.. seems like you have no previous data... \n"
                    + "Could you type out the command in the correct format of 'hello NAME GENDER (boy/girl)' please";
            } else {
                if (previousStats.getGender().equals("boy")) {
                    gender = "Mr. ";
                    userName = previousStats.getUsername();
                    return "Welcome back " + gender + userName + "!";
                } else {
                    gender = "Ms. ";
                    userName = previousStats.getUsername();
                    return "Welcome back " + gender + userName + "!";
                }
            }
        }
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

        stats.saveUserStats("UserData.txt");
        String responseString = "Hello " + gender + userName + "!";
        return responseString;
    }
}
