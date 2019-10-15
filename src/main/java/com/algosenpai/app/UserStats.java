package com.algosenpai.app;

import javafx.util.Pair;

import java.util.ArrayList;

/**
 * Handles temporary storage of user stats while the program is running.
 * The relationship between the various members is:
 *
 * <p>
 * chapterData: An ArrayList that holds the data for all chapters. Chapters are addressed by index, but can also be
 * searched by name.
 * characterImagePath: a string containing the path of the image that is used for the user's character in game.
 * The two data above are stored permanently to a text file, and retrieved from the text file when the program starts.
 *</p>
 *
 * <p>
 * currentChapter: A single ChapterStat object that stores the chapter data of the chapter that is currently running.
 * This is reset for every run. The contents of currentChapter can be transferred to chapterData by calling
 * saveCurrentChapterToChapterData()
 * </p>
 */
public class UserStats {

    // Array of chapter stats
    private ArrayList<ChapterStat> chapterData;

    // Stats for the current chapter
    private ChapterStat currentChapter;

    private String characterImagePath = "miku.png";

    /**
     * Checkstyle.
     */
    public String getCharacterImagePath() {
        return characterImagePath;
    }

    /**
     * Checkstyle.
     */
    public void setCharacterImagePath(String characterImagePath) {
        this.characterImagePath = characterImagePath;
    }

    /**
     * Constructor.
     * @param size The initial size of the arraylist containing the data. Should be equal to the number of chapters.
     */
    public UserStats(int size) {
        chapterData = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            chapterData.add(new ChapterStat(0, 0, 0));
        }
    }

    /**
     * Get the stats for a particular chapter.
     * @param index The index of the chapter.
     * @return The ChapterStat object for that chapter
     */
    public Pair<Integer,Integer> getStatsByIndex(int index) {
        return new Pair<>(chapterData.get(index).correctAnswers, chapterData.get(index).totalAnswered);
    }

    /**
     * Get the stats for a particular chapter.
     * @param chapterName The name of the chapter.
     * @return The ChapterStat object for that chapter, or null of the chapter does not exist.
     */
    public ChapterStat getStatsByName(String chapterName) {
        ChapterStat target = null;
        for (ChapterStat item : chapterData) {
            if (item.chapterName.equals(chapterName)) {
                target = item;
            }
        }
        return target;
    }

    /**
     * Update the stats for the current Chapter according to whether the user answered correctly or incorrectly.
     * @param wasAnsweredCorrectly Whether the user answered correctly or incorrectly.
     */
    public void updateCurrentChapter(boolean wasAnsweredCorrectly) {
        currentChapter.totalAnswered++;
        if (wasAnsweredCorrectly) {
            currentChapter.correctAnswers++;
        }
    }

    /**
     * Reset the number of question and number of correct answers of the current chapter to zero.
     */
    public void resetCurrentChapter() {
        currentChapter.totalAnswered = 0;
        currentChapter.correctAnswers = 0;
    }

    /**
     * After the chapter has been played, add the number answered/number correct to the permanently stored chapterData
     * associated with that chapter.
     * NOTE: This function increments the number of attempts done, so only call it once per attempt.
     * @param index The index of the chapter in chapterData.
     */
    public void saveCurrentChapterToChapterData(int index) {
        chapterData.get(index).correctAnswers += currentChapter.correctAnswers;
        chapterData.get(index).totalAnswered += currentChapter.totalAnswered;
        // Increment the number of attempts, we assume this function is called once per attempt.
        chapterData.get(index).attempts++;
    }

    /**
     * After the chapter has been played, add the number answered/number correct to the permanently stored chapterData
     * associated with that chapter.
     * @param name The name of the chapter in chapterData.
     */
    public void saveCurrentChapterToChapterData(String name) {
        int index = getIndexByName(name);
        if (index == -1) {
            return;
        }
        saveCurrentChapterToChapterData(index);
    }

    public Pair<Integer,Integer> getCurrentChapter() {
        return new Pair<>(currentChapter.correctAnswers, currentChapter.totalAnswered);
    }

    /**
     * Call this after each question is answered to update the stats for that chapter.
     * @param index The index of the chapter to update.
     * @param wasAnsweredCorrectly Whether the answer that is being added was answered correctly.
     */
    public void updateStats(int index, boolean wasAnsweredCorrectly) {
        chapterData.get(index).totalAnswered++;
        if (wasAnsweredCorrectly) {
            chapterData.get(index).correctAnswers++;
        }
    }

    /**
     * Call this after each question is answered to update the stats for that chapter.
     * @param chapterName The name of the chapter to update.
     * @param wasAnsweredCorrectly Whether the answer that is being added was answered correctly.
     */
    public void updateStats(String chapterName, boolean wasAnsweredCorrectly) {
        int index = getIndexByName(chapterName);
        if (index == -1) {
            return;
        }
        updateStats(index,wasAnsweredCorrectly);
    }


    /**
     * Helper function to get the index of a chapter in the chapterData List, given its name.
     * @param name The Name of the Chapter to be searched.
     * @return The index of the chapter if it exists, -1 if the chapter does not exist.
     */
    private int getIndexByName(String name) {
        for (int i = 0; i < chapterData.size(); i++) {
            if (chapterData.get(i).chapterName.equals(name)) {
                return i;
            }
        }
        return -1;
    }


}
