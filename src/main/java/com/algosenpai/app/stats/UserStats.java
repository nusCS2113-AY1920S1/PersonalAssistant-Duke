package com.algosenpai.app.stats;

import com.algosenpai.app.storage.Storage;
import com.algosenpai.app.storage.UserStorageParser;
import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    private String userName;
    private String gender;
    private int level;
    private int expLevel;
    private String userDataFilePath;

    // Array of chapter stats
    private ArrayList<ChapterStat> chapterData;

    // Stats for the current chapter
    private ChapterStat currentChapter;

    //Maps the chapter names to an index value
    private HashMap<String, Integer> chapterNumber;

    /**
     * Constructs a new UserStats by reading in from the UserData text file.
     * If the text file doesn't exist, the UserStats variables are populated with default values.
     * @param userDataFilePath the file path to the text file.
     */
    public UserStats(String userDataFilePath) throws IOException {
        chapterData = new ArrayList<>();
        chapterNumber = new HashMap<>();
        this.userDataFilePath = userDataFilePath;

        File file = new File(userDataFilePath);
        if (!file.isFile()) {
            this.userName = "Name";
            this.gender = "???";
            this.level = 1;
            this.expLevel = 0;
        } else {
            String userStatsString = Files.readString(Paths.get(userDataFilePath), StandardCharsets.US_ASCII);
            String [] tokens = userStatsString.split("\n",7);
            this.userName = tokens[2];
            this.gender = tokens[3];
            this.level = Integer.parseInt(tokens[4]);
            this.expLevel = Integer.parseInt(tokens[5]);
        }
    }

    /**
     * Constructor. Needs no explanation.
     */
    public UserStats(String username, String gender, int level, int expLevel, ArrayList<ChapterStat> chapterData) {
        this.userName = username;
        this.gender = gender;
        this.level = level;
        this.expLevel = expLevel;
        this.chapterData = chapterData;
        chapterNumber = new HashMap<>();
        for (ChapterStat stat : chapterData) {
            chapterNumber.put(stat.chapterName, stat.chapterNumber);
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
        }
        currentChapter.recalculateStats();
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
        chapterData.get(index).recalculateStats();
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
    public int getIndexByName(String name) {
        if (chapterNumber.containsKey(name)) {
            return chapterNumber.get(name);
        }
        return -1;
    }

    public ArrayList<String> getChapters() {
        return new ArrayList<>(chapterNumber.keySet());
    }

    /**
     * Gets the gender of the user.
     * @return the gender of the user.
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the gender of the user.
     * @param gender the String representing the gender of the user.
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Gets the username of the user.
     * @return the String representing the name of the user.
     */
    public String getUsername() {
        return this.userName;
    }

    /**
     * Sets the username of the user.
     * @param username the String representing the name of the user.
     */
    public void setUsername(String username) {
        this.userName = username;
    }

    public int getUserLevel() {
        return this.level;
    }

    public void setUserLevel(int level) {
        this.level = level;
    }

    public int getUserExp() {
        return this.expLevel;
    }

    public void setUserExp(int expLevel) {
        this.expLevel = expLevel;
    }

    /**
     * Gets the statistics for the current chapter.
     * @return a pair containing the number of correct answers and the total questions answered.
     */
    public Pair<Integer,Integer> getCurrentChapter() {
        return new Pair<>(currentChapter.correctAnswers, currentChapter.totalAnswered);
    }

    /**
     * Returns the string representation of userStats, which can be saved in a text file.
     * @return The string representation of userStats.
     */
    @Override
    public String toString() {
        String result = "";
        result += "AlgoSenpai Adventures Overall Report\n\n";
        result += userName + "\n";
        result += gender + "\n";
        result += level + "\n";
        result += expLevel + "\n";

        for (ChapterStat chapterStat:chapterData) {
            result += "\n" + chapterStat.toString();
        }
        return result;
    }

    /**
     * Given the string representation, it returns the UserStats object.
     * TODO Handle invalid string.
     * @param string The string version of the UserStats (obtained by calling toString()).
     * @return The UserStats object.
     */
    public static UserStats parseString(String string) {
        // Get the first 6 lines. 6th line contains the chapterData.
        String [] tokens = string.split("\n",6);
        String userName = tokens[2];
        String gender = tokens[3];
        int level = Integer.parseInt(tokens[4]);
        int expLevel = Integer.parseInt(tokens[5]);

        // No chapters in the list, so exit early, otherwise will cause parsing error.
        if (tokens.length < 6) {
            return new UserStats(userName, gender, level, expLevel, new ArrayList<>());
        }
        // Each chapter's data is separated by 2 newlines, so split like this to get the chapterData
        String[] chapterDataTokens = tokens[5].split("\n\n");
        ArrayList<ChapterStat> chapterStats = new ArrayList<>();
        for (String chapterString: chapterDataTokens) {
            chapterStats.add(ChapterStat.parseString(chapterString));
        }
        return new UserStats(userName, gender, level, expLevel, chapterStats);
    }

    /**
     * Get the default UserStats (if the user launches the game for the first time).
     * @return The UserStats object.
     */
    public static UserStats getDefaultUserStats() {
        // TODO Currently it returns an empty object, but it should ideally be a list of all chapters, with 0 attempts.
        return new UserStats("Name", "nil", 1, 0, new ArrayList<>());
    }

    /**
     * Saves all the data into the text file.
     */
    public void saveUserStats() throws IOException {
        Storage.saveData(userDataFilePath, this.toString());
    }

    public ArrayList<ChapterStat> getChapterData() {
        return chapterData;
    }

    /**
     * Overriding the equals method so JUnit can work.
     * We manually check if each property is equal.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UserStats) {

            UserStats other = (UserStats) obj;
            boolean isEqual = true;

            if (other.chapterData.size() != chapterData.size()) {
                return false;
            }

            // Check if each chapter is equal
            for (int i = 0; i < chapterData.size(); i++) {
                isEqual = isEqual && chapterData.get(i).equals(other.chapterData.get(i));
            }

            return isEqual
                    && userName.equals(other.userName)
                    && gender.equals(other.gender)
                    && level == other.level
                    && expLevel == other.expLevel;

        } else {
            return false;
        }
    }
}