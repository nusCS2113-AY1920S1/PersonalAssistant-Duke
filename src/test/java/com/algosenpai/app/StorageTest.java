package com.algosenpai.app;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.algosenpai.app.stats.ChapterStat;
import com.algosenpai.app.stats.UserStats;
import com.algosenpai.app.storage.Storage;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class StorageTest {

    static final String TEST_PATH = "testFile.txt";

    @Test
    public void saveData_fileExists() {

        try {
            new File(TEST_PATH).createNewFile();
            Storage.saveData(TEST_PATH,getDummyUserStats().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void saveData_fileDoesNotExist() {
        try {
            new File(TEST_PATH).delete();
            Storage.saveData(TEST_PATH,getDummyUserStats().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void saveData_randomTest() {
        try {
            Storage.saveData(TEST_PATH,getRandomUserStats().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create a random UserStats with 1000 chapters.
     * Save it, then load it and compare the 2 copies.
     */
    @Test
    public void saveAndLoad_randomTest() {
        // create the file.
        UserStats userStats = getRandomUserStats();

        try {
            Storage.saveData(TEST_PATH,userStats.toString());
            UserStats copy = UserStats.parseString(Storage.loadData(TEST_PATH));
            assertEquals(userStats,copy);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests if save and load works properly with an empty UserStats object (empty string parameters, no ChapterStats).
     */
    @Test
    public void saveAndLoad_emptyUserStats() {
        UserStats userStats = getEmptyUserStats();

        try {
            Storage.saveData(TEST_PATH,userStats.toString());
            UserStats copy = UserStats.parseString(Storage.loadData(TEST_PATH));
            assertEquals(userStats,copy);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Is this method name too long? It tests the case where you try to load from a file that doesn't exist.
     */
    @Test
    public void loadData_fileDoesNotExist_shouldCreateFileWithDefaultStats() {
        new File(TEST_PATH).delete();
        UserStats defaultStats = UserStats.getDefaultUserStats();
        try {
            UserStats loadedStats = UserStats.parseString(Storage.loadData(TEST_PATH));
            assertEquals(defaultStats,loadedStats);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private UserStats getEmptyUserStats() {
        return new UserStats("", "", new ArrayList<>());
    }

    private UserStats getDummyUserStats() {
        ArrayList<ChapterStat> chapterStats = new ArrayList<>();
        chapterStats.add(new ChapterStat("Chapter",1,2,3,4,6,7.0,"df"));
        return new UserStats("Name", "file", chapterStats);
    }

    private UserStats getRandomUserStats() {
        //First, create a userStats with 1000 random chapters.
        ArrayList<ChapterStat> chapterStats = new ArrayList<>();
        ChapterStat chapterStat;
        ChapterStat copy;

        Random random = new Random();

        for (int i = 0; i < 1000; i++) {

            chapterStat = new ChapterStat(
                    RandomString.make(random.nextInt(100) + 1),
                    random.nextInt(),
                    random.nextInt(),
                    random.nextInt(),
                    random.nextInt(),
                    random.nextInt(),
                    random.nextDouble() * random.nextInt(),
                    RandomString.make(random.nextInt(100) + 1)
            );
            chapterStats.add(chapterStat);
        }
        return new UserStats("Test Test Test", "Test test Test", chapterStats);
    }
}
