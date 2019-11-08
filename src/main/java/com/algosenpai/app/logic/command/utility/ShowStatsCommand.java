package com.algosenpai.app.logic.command.utility;

import com.algosenpai.app.logic.command.Command;
import com.algosenpai.app.stats.ChapterStat;
import com.algosenpai.app.stats.UserStats;

import java.util.ArrayList;

public class ShowStatsCommand extends Command {

    private UserStats userStats;

    /**
     * Create new command.
     *
     * @param inputs The words following the command word.
     */
    public ShowStatsCommand(ArrayList<String> inputs, UserStats userStats) {
        super(inputs);
        this.userStats = userStats;
    }


    @Override
    public String execute()  {
        String result = "";
        result += "Here are your stats: \n";
        result += "Name: " + userStats.getUsername() + "\n";
        result += "Gender: " + userStats.getGender() + "\n";
        result += "Level: " + userStats.getUserLevel() + "\n";
        result += "Exp: " + userStats.getUserExp() + "\n";
        for (ChapterStat stat : userStats.getChapterData()) {
            result += stat.toString() + "\n";
        }
        return result;
    }
}
