package com.algosenpai.app.logic.command.utility;

import com.algosenpai.app.logic.command.Command;
import com.algosenpai.app.stats.UserStats;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class SelectLectureChapterCommand extends Command {
    private AtomicInteger chapterNumber;
    private AtomicBoolean isLectureMode;
    private UserStats userStats;

    /**
     * Picks the correct lecture chapter.
     * @param inputs The user's input.
     * @param chapterNumber The chapter number of the selected chapter.
     * @param isLectureMode Variable if it is a lecture mode.
     * @param userStats The user's stats.
     */
    public SelectLectureChapterCommand(ArrayList<String> inputs, AtomicInteger chapterNumber,
                                       AtomicBoolean isLectureMode, UserStats userStats) {
        super(inputs);
        this.userStats = userStats;
        this.chapterNumber = chapterNumber;
        this.isLectureMode = isLectureMode;
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
        this.isLectureMode.set(true);
        return "You have selected Chapter " + chapterNumber.incrementAndGet() + ". Type 'start' to begin.";
    }

    /**
     * Returns default message if the select command is not called properly.
     * @return default message to use select command.
     */
    private String getDefaultMessage() {
        StringBuilder str = new StringBuilder(
                "No such chapter found. Please select the following:\nlecture <chapter name>\n");
        for (String chapter: userStats.getChapters()) {
            str.append(chapter).append("\n");
        }
        return str.toString();
    }
}
