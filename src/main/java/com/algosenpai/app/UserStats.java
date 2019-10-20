package com.algosenpai.app;

import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

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

    //username of the player
    private String username;
    // Array of chapter stats
    private ArrayList<ChapterStat> chapterData;

    // Stats for the current chapter
    private ChapterStat currentChapter;

    private String characterImagePath = "miku.png";

    //Maps the chapter names to an index value
    private HashMap<String, Integer> chapterNumber;

    /**
     * Constructs a new UserStats by reading in from the UserData text file.
     * ChapterStat objects are passed from the parser into here to be stored into
     * their respective data structures.
     */
    public UserStats() throws FileNotFoundException {
        chapterData = new ArrayList<>();
        chapterNumber = new HashMap<>();
        UserStorageParser userStorageParser = new UserStorageParser();
        //Reads in redundant blank lines
        userStorageParser.nextLine();
        userStorageParser.nextLine();
        this.username = userStorageParser.nextLine();

        while (userStorageParser.hasMoreTokens()) {
            userStorageParser.nextLine();
            ChapterStat currStat = userStorageParser.nextChapterStat();
            chapterData.add(currStat);
            chapterNumber.put(currStat.chapterName, currStat.chapterNumber);
        }

    }

    /**
     * Get the stats for a particular chapter by searching for the chapter number then
     * calling the getStatsByIndex function.
     * @param chapterName The name of the chapter.
     * @return The ChapterStat object for that chapter, or null of the chapter does not exist.
     */
    public ChapterStat getStatsByName(String chapterName) {
        if (chapterNumber.containsKey(chapterName)) {
            int index = chapterNumber.get(chapterName);
            return getStatsByIndex(index);
        }
        return null;
    }

    /**
     * Get the stats for a particular chapter.
     * @param index The index of the chapter.
     * @return The ChapterStat object for that chapter
     */
    public ChapterStat getStatsByIndex(int index) {
        ChapterStat currentChapter = chapterData.get(index);
        return new ChapterStat(currentChapter);
    }

    /**
     * Update the stats for the current Chapter according to whether the user answered correctly or incorrectly.
     * @param wasAnsweredCorrectly Whether the user answered correctly or incorrectly.
     */
    public void updateCurrentChapter(boolean wasAnsweredCorrectly) {
        currentChapter.totalAnswered++;
        if (wasAnsweredCorrectly) {
            currentChapter.correctAnswers++;
        } else {
            currentChapter.wrongAnswers++;
        }
        currentChapter.percentage = (double)currentChapter.correctAnswers / currentChapter.totalAnswered;
    }

    /**
     * Reset the number of question and number of correct answers of the current chapter to zero.
     */
    public void resetCurrentChapter() {
        currentChapter.totalAnswered = 0;
        currentChapter.correctAnswers = 0;
        currentChapter.wrongAnswers = 0;
        currentChapter.percentage = 0.0;
        currentChapter.comments = "Try your best!";
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


    /**
     * Helper function to get the index of a chapter in the chapterData List, given its name.
     * @param name The Name of the Chapter to be searched.
     * @return The index of the chapter if it exists, -1 if the chapter does not exist.
     */
    private int getIndexByName(String name) {
        if (chapterNumber.containsKey(name)) {
            return chapterNumber.get(name);
        }
        return -1;
    }

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
     * Gets the statistics for the current chapter.
     * @return a pair containing the number of correct answers and the total questions answered.
     */
    public Pair<Integer,Integer> getCurrentChapter() {
        return new Pair<>(currentChapter.correctAnswers, currentChapter.totalAnswered);
    }
}