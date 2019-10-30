package com.algosenpai.app.logic.command;

import com.algosenpai.app.stats.UserStats;

import java.io.IOException;
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
    public String execute() throws IOException {
        userName = inputs.get(1);
        stats.setUsername(userName);

        if (inputs.get(2).equals("boy")) {
            gender = "Mr. ";
            stats.setGender("boy");
        } else if (inputs.get(2).equals("girl")) {
            gender = "Ms. ";
            stats.setGender("girl");
        } else {
            return "Could you enter the setup command again with the appropriate gender?";
        }

        stats.saveUserStats();
        String responseString = "Hello " + gender + userName + "!";
        return responseString;
    }

}
