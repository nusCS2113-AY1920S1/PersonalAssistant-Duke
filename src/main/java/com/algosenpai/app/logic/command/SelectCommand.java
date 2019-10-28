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
        if (inputs.size() < 2) {
            return getDefaultMessage();
        }
        int index = userStats.getIndexByName(inputs.get(1));
        if (index < 0) {
            return getDefaultMessage();
        }
        chapterNumber.set(index - 1);
        return "You have selected Chapter " + chapterNumber.get();
    }

    /**
     * Returns default message if the select command is not called properly.
     * @return default message to use select command.
     */
    public String getDefaultMessage() {
        StringBuilder str = new StringBuilder(
                "No such chapter found. Please select the following:\nselect <chapter name>\n");
        for (String chapter: userStats.getChapters()) {
            str.append(chapter).append("\n");
        }
        return str.toString();
    }
}
