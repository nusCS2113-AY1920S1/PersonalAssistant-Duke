package com.algosenpai.app.logic.command;

import com.algosenpai.app.stats.UserStats;

import java.util.ArrayList;

public class StatsCommand extends Command {

    UserStats userStats;

    /**
     * Create new command.
     * @param inputs input from user.
     */
    public StatsCommand(ArrayList<String> inputs, UserStats userStats) {
        super(inputs);
        this.userStats = userStats;
    }

    @Override
    public String execute() {
        return userStats.toString();
    }
}
