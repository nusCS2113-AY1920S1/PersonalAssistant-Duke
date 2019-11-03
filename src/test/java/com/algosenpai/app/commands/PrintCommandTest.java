package com.algosenpai.app.commands;

import com.algosenpai.app.logic.Logic;
import com.algosenpai.app.logic.command.Command;
import com.algosenpai.app.stats.UserStats;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;


public class PrintCommandTest {

    @Test
    void testPrintMissingInput() throws IOException {
        UserStats stats = new UserStats("./UserData.txt");
        Logic logic = new Logic(stats);
        Command command = logic.executeCommand("print");
        String actualText = command.execute();
        Assertions.assertEquals("Please use the following format\n"
                + "print <archive | quiz |  user> <filename>.pdf", actualText);
    }

    @Test
    void testPrintWrongFileExtension() throws IOException {
        UserStats stats = new UserStats("./UserData.txt");
        Logic logic = new Logic(stats);
        Command command = logic.executeCommand("print user testing.ppp");
        String actualText = command.execute();
        Assertions.assertEquals("Wrong file extension", actualText);
    }

    @Test
    void testPrintCorrectInput() throws IOException {
        UserStats stats = new UserStats("./UserData.txt");
        Logic logic = new Logic(stats);
        Command command = logic.executeCommand("print user testing.pdf");
        String actualText = command.execute();
        Assertions.assertEquals("Successfully write to pdf", actualText);
    }

    @Test
    void testPrintWrongDataSource() throws IOException {
        UserStats stats = new UserStats("./UserData.txt");
        Logic logic = new Logic(stats);
        Command command = logic.executeCommand("print hello testing.pdf");
        String actualText = command.execute();
        Assertions.assertEquals("Please use the following format\n"
                + "print <archive | quiz |  user> <filename>.pdf", actualText);
    }

}
