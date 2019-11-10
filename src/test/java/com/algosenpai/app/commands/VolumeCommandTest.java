package com.algosenpai.app.commands;

import com.algosenpai.app.logic.Logic;
import com.algosenpai.app.logic.command.Command;
import com.algosenpai.app.stats.UserStats;
import com.algosenpai.app.ui.controller.MusicController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;


public class VolumeCommandTest {

    @Test
    void testSoundLevelUpperBoundary() throws Exception {
        UserStats stats = new UserStats("./UserData.txt");
        Logic logic = new Logic(stats);
        Command command = logic.executeCommand("volume 110");
        String actualText = command.execute();
        Assertions.assertEquals("Sound level is from 0 to 100", actualText);
    }

    @Test
    void testSoundLevelLowerBoundary() throws Exception {
        UserStats stats = new UserStats("./UserData.txt");
        Logic logic = new Logic(stats);
        Command command = logic.executeCommand("volume -1");
        String actualText = command.execute();
        Assertions.assertEquals("Sound level is from 0 to 100", actualText);
    }

    @Test
    void testSoundLevelWrongInput() throws Exception {
        UserStats stats = new UserStats("./UserData.txt");
        Logic logic = new Logic(stats);
        Command command = logic.executeCommand("volume testing");
        String actualText = command.execute();
        Assertions.assertEquals("Select a number from 0 to 100 to adjust the sound", actualText);
    }

    @Test
    void testSoundLevelMissingInput() throws Exception {
        UserStats stats = new UserStats("./UserData.txt");
        Logic logic = new Logic(stats);
        Command command = logic.executeCommand("volume");
        String actualText = command.execute();
        Assertions.assertEquals("volume <sound level>", actualText);
    }

}
