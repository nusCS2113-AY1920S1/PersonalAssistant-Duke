package com.algosenpai.app.logic.command;

import com.algosenpai.app.stats.UserStats;

import java.util.ArrayList;

public class HelpCommand extends Command {

    private UserStats userStats;

    /**
     * Create new command.
     * @param inputs input from user.
     */
    public HelpCommand(ArrayList<String> inputs, UserStats userStats) {
        super(inputs);
        this.userStats = userStats;
    }

    @Override
    public String execute() {
        if (inputs.size() < 2) {
            return getDefaultMessage();
        } else {
            int index = userStats.getIndexByName(inputs.get(1));
            switch (index) {
            case 1:
                return "Try solving these problems on Kattis:\n"
                        + "cups, lineup, mjehuric, sidewayssorting";
            case 2:
                return "Try solving these problems on Kattis:\n"
                            + "coconut, integerlists, joinstrings";
            case 3:
                return "Try solving these problems on Kattis:\n"
                        + "committeeassignment, pebblesolitaire";
            default:
                return "Code more!";
            }
        }
    }

    /**
     * Returns default message if the help command is not called properly.
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
