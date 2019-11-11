package com.algosenpai.app.stats;

import com.algosenpai.app.MainApp;
import com.algosenpai.app.exceptions.FileParsingException;
import com.algosenpai.app.storage.Storage;

import com.algosenpai.app.utility.LogCenter;
import javafx.util.Pair;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;
import java.util.logging.Logger;

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
    private String userDataFilePath;
    private String userName;
    private String gender;
    private int level;
    private int expLevel;

    // Array of chapter stats
    private ArrayList<ChapterStat> chapterData;

    // Stats for the current chapter
    private ChapterStat currentChapter;

    //Maps the chapter names to an index value
    private HashMap<String, Integer> chapterNumber = new HashMap<>() {
        {
            put("sorting", 1);
            put("linkedlist", 2);
            put("bitmask", 3);
        }
    };

    private static final Logger logger = LogCenter.getLogger(UserStats.class);

    /**
     * Constructs a new UserStats by reading in from the UserData text file.
     * If the text file doesn't exist, the UserStats variables are populated with default values.
     * @param userDataFilePath the file path to the text file.
     */
    public UserStats(String userDataFilePath) throws FileParsingException {
        chapterData = new ArrayList<>();
        this.userDataFilePath = userDataFilePath;

        File file = new File(userDataFilePath);
        if (!file.isFile()) {
            logger.info("UserData.txt file not found, creating new UserData.txt with default stats");
            this.userName = "Default";
            this.gender = "????";
            this.level = 1;
            this.expLevel = 0;
            final String INIT_COMMENT = "You have not attempted this chapter yet";
            chapterData.add(new ChapterStat("Sorting",1,0,0,0,0,0,INIT_COMMENT));
            chapterData.add(new ChapterStat("Linked List",2,0,0,0,0,0,INIT_COMMENT));
            chapterData.add(new ChapterStat("Bitmask",3,0,0,0,0,0,INIT_COMMENT));
            Storage.saveData(userDataFilePath, this.toString());
        } else {
            String fileContents = null;
            try {
                logger.info("Reading User Stats from UserData.txt.....");
                fileContents = Storage.loadData(userDataFilePath);
            } catch (FileNotFoundException ignored) {
                logger.severe("UserData.txt could not be read due to error.");
                throw new FileParsingException("The file does not exist!");
            }
            UserStats statsLoadedfromFile = UserStats.parseString(fileContents);

            // Call the parsing method and copy over the values.
            // Idk any better way to do this.
            this.userName = statsLoadedfromFile.userName;
            this.gender = statsLoadedfromFile.gender;
            this.level = statsLoadedfromFile.level;
            this.expLevel = statsLoadedfromFile.expLevel;
            this.chapterData = statsLoadedfromFile.chapterData;
        }
    }

    /**
     * Constructor. Needs no explanation.
     */
    public UserStats(String username, String gender, int level, int expLevel,
                     ArrayList<ChapterStat> chapterData) {
        this.userName = username;
        this.gender = gender;
        this.level = level;
        this.expLevel = expLevel;
        this.chapterData = chapterData;
        for (ChapterStat stat : chapterData) {
            chapterNumber.put(stat.chapterName, stat.chapterNumber);
        }
    }

    /**
     * Creates a new UserStats object from another UserStats object passed in.
     * @param previousStats The old userstats.
     */
    public UserStats(UserStats previousStats) {
        this.userName = previousStats.getUsername();
        this.gender = previousStats.getGender();
        this.level = previousStats.getUserLevel();
        this.expLevel = previousStats.getUserExp();
        this.chapterData = previousStats.getChapterData();
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
     * TODO.
     */
    public void updateChapter(int index, int totalAnswered, int correct) {
        index--;
        chapterData.get(index).totalAnswered += totalAnswered;
        chapterData.get(index).correctAnswers += correct;
        chapterData.get(index).attempts++;
        chapterData.get(index).recalculateStats();
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

    /**
     * Gets the level of the user.
     * @return the int value which is the user's level.
     */
    public int getUserLevel() {
        return this.level;
    }

    /**
     * Sets the level of the user.
     * @param level the int value representing the level of the user.
     */
    public void setUserLevel(int level) {
        this.level = level;
    }

    /**
     * Gets the user experience points of the user.
     * @return the user experience points.
     */
    public int getUserExp() {
        return this.expLevel;
    }

    /**
     * Sets the user experience points of the user.
     * @param expLevel the user experience points.
     */
    public void setUserExp(int expLevel) {
        this.expLevel = expLevel;
    }

    /**
     * Gets the percentage of questions correct statistic for the chapter specified.
     * @param chapterIndex the index number of the chapter according to the HashMap of chapters.
     * @return the double value representing the percentage of questions correct.
     */
    public double getPercentageofQuestionsCorrect(int chapterIndex) {
        int chapterNumber = chapterIndex - 1;
        ChapterStat currentChapter = chapterData.get(chapterNumber);
        logger.info("The percentage stat parsed is " + currentChapter.getPercentage());
        return currentChapter.getPercentage();
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
     * @param string The string version of the UserStats (obtained by calling toString()).
     * @return The UserStats object.
     */
    public static UserStats parseString(String string) throws FileParsingException {
        try {
            logger.info("Parsing User Stats from text file..");
            // Get the first 6 lines. 6th line contains the chapterData.
            String [] tokens = string.split("\n",8);
            String userName = tokens[2];
            String gender = tokens[3];
            int level = Integer.parseInt(tokens[4]);
            int expLevel = Integer.parseInt(tokens[5]);

            // No chapters in the list, so exit early, otherwise will cause parsing error.
            if (tokens.length < 8) {
                return new UserStats(userName, gender, level, expLevel, new ArrayList<>());
            }
            // Each chapter's data is separated by 2 newlines, so split like this to get the chapterData
            String[] chapterDataTokens = tokens[7].split("\n\n");
            ArrayList<ChapterStat> chapterStats = new ArrayList<>();
            for (String chapterString: chapterDataTokens) {
                chapterStats.add(ChapterStat.parseString(chapterString));
            }
            logger.info("User Stats have been parsed successfully!");
            return new UserStats(userName, gender, level, expLevel, chapterStats);
        } catch (Exception e) {
            logger.severe("User Stats could not be parsed successfully from text file");
            throw new FileParsingException();
        }
    }

    /**
     * Reset all the progress data for that userStats (does not reset name and gender).
     */
    public void resetAll() {
        expLevel = 0;
        level = 1;

        for (int i = 0; i < chapterData.size(); i++) {
            chapterData.get(i).resetAll();
        }
    }

    /**
     * Get the default UserStats (if the user launches the game for the first time).
     * @return The UserStats object.
     */
    public static UserStats getDefaultUserStats() {
        ArrayList<ChapterStat> chapters = new ArrayList<>();
        final String INIT_COMMENT = "You have not attempted this chapter yet";
        chapters.add(new ChapterStat("Sorting",1,0,0,0,0,0,INIT_COMMENT));
        chapters.add(new ChapterStat("Linked List",2,0,0,0,0,0,INIT_COMMENT));
        chapters.add(new ChapterStat("Bitmask",3,0,0,0,0,0,INIT_COMMENT));
        return new UserStats("Default", "????", 1, 0, chapters);
    }

    /**
     * Saves all the data into the text file.
     */
    public void saveUserStats(String userDataFilePath) {
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

    /**
     * A utility function to set the properties of a UserStats to another UserStats.
     * This is different from direct assignment, as the reference to this object is maintained.
     * @param temp The other UserStats to copy from.
     */
    public void copy(UserStats temp) {

        this.userName = temp.userName;
        this.chapterData = temp.chapterData;
        this.level = temp.level;
        this.gender = temp.gender;
        this.expLevel = temp.expLevel;
    }
}