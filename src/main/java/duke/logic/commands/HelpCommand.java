package duke.logic.commands;

import duke.commons.exceptions.HelpFailException;
import duke.logic.commands.results.CommandResultText;
import duke.model.Model;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Shows the help message.
 */
public class HelpCommand extends Command {
    private static final String HELP_URL = "https://github.com/AY1920S1-CS2113T-W13-3/main/blob/master/docs/UserGuide.adoc";
    private static final String MESSAGE_HELP = "Here is the list of commands:\n"
            + "    Event: event <venue> between <time> and <time>\n"
            + "    Event: delete <index> \n"
            + "    Event: done <index> \n"
            + "    List events: list\n"
            + "    Showing a Profile: profileShow\n"
            + "    Showing bus stop: busStop <bus stop code>\n"
            + "    Showing a bus route: busRoute <bus number>\n"
            + "    Search locations: search <venue>\n"
            + "    Map of locations: map <venue>\n"
            + "    Find event in events: find <keyword>\n"
            + "    For more commands, please visit our Github website.\n"
            + "\n"
            ;

    /**
     * Executes this command and returns a text result.
     *
     * @param model The model object containing event list.
     */
    @Override
    public CommandResultText execute(Model model) throws HelpFailException {
        try {
            Desktop desktop = Desktop.getDesktop();
            desktop.browse(new URI(HELP_URL));
        } catch (URISyntaxException | IOException e) {
            throw new HelpFailException();
        }
        return new CommandResultText(MESSAGE_HELP);
    }
}
