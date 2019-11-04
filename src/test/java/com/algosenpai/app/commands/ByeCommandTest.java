package com.algosenpai.app.commands;

import com.algosenpai.app.logic.Logic;
import com.algosenpai.app.logic.command.Command;
import com.algosenpai.app.stats.UserStats;
import javafx.scene.Node;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;

public class ByeCommandTest extends ApplicationTest {

    @Test
    void testSoundLevelUpperBoundary() throws IOException {
        UserStats stats = new UserStats("./UserData.txt");
        Logic logic = new Logic(stats);
        Command command = logic.executeCommand("exit");
        String actualText = command.execute();
        Assertions.assertEquals("Bye!", actualText);
    }


    <T extends Node> T find() {
        return lookup("#dialogContainer").query();
    }
}
