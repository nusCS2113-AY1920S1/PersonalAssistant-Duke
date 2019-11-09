package entertainment.pro.logic.parsers.commands;

import entertainment.pro.commons.exceptions.EmptyCommandException;
import entertainment.pro.commons.exceptions.Exceptions;
import entertainment.pro.commons.exceptions.InvalidFormatCommandException;
import entertainment.pro.commons.exceptions.MissingInfoException;
import entertainment.pro.logic.parsers.CommandParser;
import entertainment.pro.ui.Controller;
import entertainment.pro.ui.MovieHandler;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.Array;
import java.util.Arrays;
import java.util.List;

public class PreferenceCommandTest extends MovieHandler{

    @Test
    public void executeCommands_add_throws_exception()  {
        String command1 = "preference add -h action";
        String command2 = "preference add -a true, false -t horror";
        String command3 = "preference add -a true -s 1 2 -t horror";
        String command4 = "preference add -a true, -s 1 2, -t horror";
        String command5 = "preference add -a action -t horror";
        String command6 = "preference add -at true";
        String command7 = "preference add";
        List<String> commandList = Arrays.asList(command1, command2, command3, command4, command5,
                command6, command7);
        for (int i = 0; i < commandList.size(); i += 1) {
            String[] commandParse = commandList.get(i).split(" ");
            String testCommand = commandList.get(i);
            assertThrows(InvalidFormatCommandException.class, () -> {
                CommandParser.rootCommand(commandParse , testCommand, this);
            });
        }
    }

    @Test
    public void executeCommands_remove_throws_exception()  {
        String command1 = "preference remove -h action";
        String command2 = "preference remove -a true, false -t horror";
        String command3 = "preference remove -a true -s 1 2 -t horror";
        String command4 = "preference remove -a true, -s 1 2, -t horror";
        String command5 = "preference remove -a action -t horror";
        String command6 = "preference remove -at true";
        String command7 = "preference remove";
        List<String> commandList = Arrays.asList(command1, command2, command3, command4, command5,
                command6, command7);
        for (int i = 0; i < commandList.size(); i += 1) {
            String[] commandParse = commandList.get(i).split(" ");
            String testCommand = commandList.get(i);
            assertThrows(InvalidFormatCommandException.class, () -> {
                CommandParser.rootCommand(commandParse , testCommand, this);
            });
        }
    }

    @Test
    public void executeCommands_clear_throws_exception()  {
        String command1 = "preference clear -h action";
        String command2 = "preference clear -a true, false -t horror";
        String command3 = "preference clear -a true -s 1 2 -t horror";
        String command4 = "preference clear -a true, -s 1 2, -t horror";
        String command5 = "preference clear -a action -t horror";
        String command6 = "preference clear -at true";
        String command7 = "preference clear";
        List<String> commandList = Arrays.asList(command1, command2, command3, command4, command5,
                command6, command7);
        for (int i = 0; i < commandList.size(); i += 1) {
            String[] commandParse = commandList.get(i).split(" ");
            String testCommand = commandList.get(i);
            assertThrows(InvalidFormatCommandException.class, () -> {
                CommandParser.rootCommand(commandParse , testCommand, this);
            });
        }
    }
}
