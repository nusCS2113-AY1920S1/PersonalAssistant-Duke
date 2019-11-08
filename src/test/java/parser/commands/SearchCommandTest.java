package parser.commands;

import com.sun.prism.ReadbackRenderTarget;
import entertainment.pro.Main;
import entertainment.pro.commons.exceptions.EmptyCommandException;
import entertainment.pro.commons.exceptions.Exceptions;
import entertainment.pro.commons.exceptions.MissingInfoException;
import entertainment.pro.logic.movieRequesterAPI.RetrieveRequest;
import entertainment.pro.logic.parsers.CommandParser;
import entertainment.pro.ui.Controller;
import entertainment.pro.ui.MovieHandler;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SearchCommandTest {
    private Controller controller;

    @Test
    public void executeCommands_valid_results() {
        String command1 = "search movies /current";
        String command2 = "SEARCH MOVIES /CURRENT";
        try {
            CommandParser.parseCommands(command1, controller);
        } catch (IOException | EmptyCommandException | MissingInfoException | Exceptions e) {
            e.printStackTrace();
        }
    }
}
