package com.algosenpai.app;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.algosenpai.app.stats.ChapterStat;
import com.algosenpai.app.stats.UserStats;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;


public class UserStatsTest {

    @Test
    public void stringParsing_randomTest() {

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

        UserStats userStats = new UserStats("Test Test Test", "Test test Test", chapterStats);
        UserStats copyOfUserStats = UserStats.parseString(userStats.toString());
        assertEquals(userStats,copyOfUserStats);

    }

    /**
     * Tests if parsing works correctly if all the parameters of userStats are blank.
     */
    @Test
    public void stringParsing_emptyStats() {
        UserStats userStats = new UserStats("","",new ArrayList<>());
        UserStats copy = UserStats.parseString(userStats.toString());
        assertEquals(copy,userStats);
    }
}
