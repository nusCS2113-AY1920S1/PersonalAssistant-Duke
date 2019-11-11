package com.algosenpai.app.logic.command.utility;

import com.algosenpai.app.exceptions.FileParsingException;
import com.algosenpai.app.logic.command.Command;
import com.algosenpai.app.stats.UserStats;
import com.algosenpai.app.storage.Storage;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class HelpCommand extends Command {

    private UserStats userStats;

    /**
     * Create new Help command.
     * @param inputs input from user.
     * @param userStats the userStats object used.
     */
    public HelpCommand(ArrayList<String> inputs, UserStats userStats) {
        super(inputs);
        this.userStats = userStats;
    }

    @Override
    public String execute()  {
        if (inputs.size() < 2) {
            return getDefaultMessage();
        } else {
            int index = userStats.getIndexByName(inputs.get(1));
            if (index == -1) {
                return getDefaultMessage();
            }

            try {
                userStats = UserStats.parseString(Storage.loadData("UserData.txt"));
            } catch (FileNotFoundException | FileParsingException e) {
                e.printStackTrace();
            }

            double percentageStat = userStats.getPercentageofQuestionsCorrect(index);
            return getRecommendedQuestions(index, percentageStat);
        }
    }

    /**
     * Returns default message if the help command is not called properly.
     * @return default message to use select command.
     */
    private String getDefaultMessage() {
        StringBuilder str = new StringBuilder(
                "No such chapter found. Please select the following:\nhelp <chapter name>\n");
        for (String chapter: userStats.getChapters()) {
            str.append(chapter).append("\n");
        }
        return str.toString();
    }

    /**
     * Gets the appropriate questions to recommend to the user depending on their proficiency level
     * (aka the percentage of questions they get correct for the chapter selected).
     * @param index the index of the chapter selected.
     * @param percentageStat the statistic that is used to determine their proficiency, parsed from their user stats.
     * @return the questions to be recommended to practice.
     */
    private String getRecommendedQuestions(int index, double percentageStat) {
        String responseString = "Try solving these problems on Kattis:\n";

        if (index == 1) {
            if (percentageStat < 40.0) {
                return responseString += "lineup, mjehuric, sidewayssorting";
            } else if (percentageStat < 60.0) {
                return responseString += "chartingprogress, classy, dyslectionary";
            } else {
                return responseString += "lawnmower, sortofsorting, musicyourway";
            }
        } else if (index == 2) {
            if (percentageStat < 40.0) {
                return responseString += "evenup, pairingsocks, coconut";
            } else if (percentageStat < 60.0) {
                return responseString += "throwns, integerlists, joinstrings";
            } else  {
                return responseString += "restaurant, ferryloading4, teque";
            }
        } else if (index == 3) {
            if (percentageStat < 40.0) {
                return responseString += "committeeassignment, pebblesolitaire";
            } else if (percentageStat < 60.0) {
                return responseString += "pebblesolitaire2, equalsumeasy";
            } else {
                return responseString += "robotturtles, hidingchickens";
            }
        }
        return getDefaultMessage();
    }

}
