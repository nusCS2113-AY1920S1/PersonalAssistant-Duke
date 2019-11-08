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

public class PreferenceCommandTest extends MovieHandler{

    @Test
    public void executeCommands_add_throws_exception()  {
        //Controller controller = this;
        String command1 = "preference add -h action";
        String command2 = "preference add -a true, false -t horror";
        String command3 = "preference add -a true -s 1 2 -t horror";
        String command4 = "preference add -a true, -s 1 2, -t horror";
        String command5 = "preference add -a action -t horror";
        String[] commandParse1 = command1.split(" ");
        String[] commandParse2 = command2.split(" ");
        String[] commandParse3 = command3.split(" ");
        String[] commandParse4 = command4.split(" ");
        String[] commandParse5 = command5.split(" ");

        assertThrows(InvalidFormatCommandException.class, () -> {
            CommandParser.rootCommand(commandParse1 , command1, this);
        });
        assertThrows(InvalidFormatCommandException.class, () -> {
            CommandParser.rootCommand(commandParse2 , command2, this);
        });
        assertThrows(InvalidFormatCommandException.class, () -> {
            CommandParser.rootCommand(commandParse3 , command3, this);
        });
        assertThrows(InvalidFormatCommandException.class, () -> {
            CommandParser.rootCommand(commandParse4 , command4, this);
        });
        assertThrows(InvalidFormatCommandException.class, () -> {
            CommandParser.rootCommand(commandParse5 , command5, this);
        });
    }
}
