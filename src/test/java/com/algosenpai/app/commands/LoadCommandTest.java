package com.algosenpai.app.commands;

import com.algosenpai.app.exceptions.FileParsingException;
import com.algosenpai.app.logic.Logic;
import com.algosenpai.app.stats.ChapterStat;
import com.algosenpai.app.stats.UserStats;
import com.algosenpai.app.storage.Storage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.util.ArrayList;

public class LoadCommandTest {

    @Test
    void loadFromValidFile() throws IOException, FileParsingException {
        UserStats userStats = UserStats.getDefaultUserStats();
        UserStats userStats1 = new UserStats("ABC","girl",1,2,new ArrayList<ChapterStat>());
        Storage.saveData("testTextFile.txt",userStats1.toString());

        Logic logic = new Logic(userStats);

        String userInput = "load testTextFile.txt";
        logic.executeCommand(userInput).execute();
        Assertions.assertEquals(userStats,userStats1);

    }
}
