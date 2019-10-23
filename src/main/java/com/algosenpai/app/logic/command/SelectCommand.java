package com.algosenpai.app.logic.command;

import com.algosenpai.app.stats.UserStats;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class SelectCommand extends Command {

    private AtomicInteger chapterNumber;
    private UserStats userStats;

    /**
     * Create new command.
     * @param inputs input from user.
     */
    public SelectCommand(ArrayList<String> inputs, AtomicInteger chapterNumber, UserStats userStats) {
        super(inputs);
        this.chapterNumber = chapterNumber;
        this.userStats = userStats;
    }

    @Override
    public String execute() {
        int index = userStats.getIndexByName(inputs.get(1));
        chapterNumber.set(index);
        return "You have selected Chapter " + chapterNumber.get();
    }
}
