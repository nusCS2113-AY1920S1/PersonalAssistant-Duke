package com.algosenpai.app.commands;

import com.algosenpai.app.exceptions.FileParsingException;
import com.algosenpai.app.logic.Logic;
import com.algosenpai.app.stats.UserStats;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;


import java.io.File;
import java.io.IOException;

public class SaveCommandTest {

    @BeforeAll
    static void setup() {
        new File("testTextFile.txt").delete();
    }

    @Test
    void saveToCustomFile() throws FileParsingException, IOException {
        UserStats userStats = UserStats.getDefaultUserStats();
        Logic logic = new Logic(userStats);
        String userInput = "save testTextFile.txt";

        logic.executeCommand(userInput).execute();

        UserStats userStats2 = new UserStats("testTextFile.txt");
        Assertions.assertEquals(userStats,userStats2);
    }
}
