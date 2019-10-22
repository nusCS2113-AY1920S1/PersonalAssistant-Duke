package com.algosenpai.app;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class UserStorageTest {

    @Test
    public void fileIO_fileExists() {

        try {
            new File("testFile.txt").createNewFile();
            UserStorage.saveStatsToFile("testFile.txt",getDummyUserStats());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void fileIO_fileDoesNotExist() {
        try {
            new File("testFile.txt").delete();
            UserStorage.saveStatsToFile("testFile.txt",getDummyUserStats());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test public void fileIO_randomTest() {
        try {
            UserStorage.saveStatsToFile("testFile.txt",getRandomUserStats());
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                    random.nextDouble()*random.nextInt(),
                    RandomString.make(random.nextInt(100) + 1)
            );
            chapterStats.add(chapterStat);
        }
        return new UserStats("Test Test Test", "Test test Test", chapterStats);
    }
}
