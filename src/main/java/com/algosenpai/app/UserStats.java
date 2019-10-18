package com.algosenpai.app;

import com.itextpdf.text.Chapter;
import javafx.util.Pair;

import java.util.ArrayList;

/**
 * Handles temporary storage of user stats while the program is running.
 */
public class UserStats {

    /**
     * Inner class to hold the 3 data for each chapter.
     * the chapter name, total questions answered and correct answers.
     */
    static class ChapterStat {
        String chapterName;
        int totalAnswered;
        int correctAnswers;

        ChapterStat(int totalAnswered, int correctAnswers) {
            this.totalAnswered = totalAnswered;
            this.correctAnswers = correctAnswers;
        }

        public ChapterStat(String chapterName,int totalAnswered, int correctAnswers) {
            this.chapterName = chapterName;
            this.totalAnswered = totalAnswered;
            this.correctAnswers = correctAnswers;
        }
    }

    private ArrayList<ChapterStat> data;

    /**
     * Constructor.
     * @param size The initial size of the arraylist containing the data. Should be equal to the number of chapters.
     */
    public UserStats(int size) {
        data = new ArrayList<ChapterStat>();
        for (int i = 0; i < size; i++) {
            data.add(new ChapterStat(0, 0));
        }
    }

    /**
     * Get the stats for a particular chapter.
     * @param index The index of the chapter.
     * @return A pair of (correct answers , total Qs answered).
     */
    public Pair<Integer,Integer> getStats(int index) {
        return new Pair<>(data.get(index).correctAnswers, data.get(index).totalAnswered);
    }

    /**
     * Get the stats for a particular chapter.
     * @param chapterName The name of the chapter.
     * @return A pair of (correct answers , total Qs answered), or null if no such chapter found.
     */
    public Pair<Integer,Integer> getStats(String chapterName) {
        ChapterStat target = getStatsByName(chapterName);
        if (target == null) {
            return null;
        }
        return new Pair<>(target.correctAnswers, target.totalAnswered);
    }

    /**
     * Call this after each question is answered to update the stats for that chapter.
     * @param index The index of the chapter to update.
     * @param wasAnsweredCorrectly Whether the answer that is being added was answered correctly.
     */
    public void updateStats(int index, boolean wasAnsweredCorrectly) {
        data.get(index).totalAnswered++;
        if (wasAnsweredCorrectly) {
            data.get(index).correctAnswers++;
        }
    }

    /**
     * Call this after each question is answered to update the stats for that chapter.
     * @param chapterName The name of the chapter to update.
     * @param wasAnsweredCorrectly Whether the answer that is being added was answered correctly.
     */
    public void updateStats(String chapterName, boolean wasAnsweredCorrectly) {
        ChapterStat target = getStatsByName(chapterName);
        if (target == null) {
            return;
        }
        target.totalAnswered++;
        if (wasAnsweredCorrectly) {
            target.correctAnswers++;
        }
    }



    private ChapterStat getStatsByName(String chapterName) {
        ChapterStat target = null;
        for (ChapterStat item : data) {
            if (item.chapterName.equals(chapterName)) {
                target = item;
            }
        }
        return target;
    }

}
