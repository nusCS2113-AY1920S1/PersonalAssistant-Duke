package duke.logic.commands;

import duke.logic.commands.results.CommandResultText;
import duke.model.Model;

/**
 * Shows the help message.
 */
public class HelpCommand extends Command {
    private static final String MESSAGE_HELP = "Here is the list of commands:\n"
            + "Add Event:\n"
            + "    Event: event <venue> between <time> and <time>\n"
            + "\n"
            + "Delete Event:\n"
            + "    Event: delete <index> \n"
            + "\n"
            + "Mark Event as done:\n"
            + "    Event: done <index> \n"
            + "\n"
            + "Listing:\n"
            + "    List events: list\n"
            + "    Showing a Profile: profileShow\n"
            + "    Showing bus stop: busStop <bus stop code>\n"
            + "    e.g. busStop 17009\n"
            + "    Showing a bus route: busRoute <bus number>\n"
            + "    e.g. busRoute 96\n"
            + "\n"
            + "Querying\n"
            + "    Search locations: search <venue>\n"
            + "    Map of locations: map <venue>\n"
            + "    Find event in events: find <keyword>\n"
            + "\n"
            + "Route\n"
            + "    routeAdd NAME\n"
            + "    routeNodeAdd INDEXROUTE INDEXNODE at LOCATION by CONSTRAINT\n"
            + "    routeGenerate STARTLOCATION to ENDLOCATION by CONSTRAINT\n"
            + "    routeList INDEX_ROUTE\n"
            + "    routeNodeList INDEX_ROUTE INDEX_NODE\n"
            + "    routeShow INDEX_ROUTE\n"
            + "    routeNodeShow INDEX_ROUTE INDEX_NODE\n"
            + "    routeNodeNearby INDEX_ROUTE INDEX_NODE\n"
            + "    routeDelete INDEX_ROUTE\n"
            + "    routeNodeDelete INDEX_ROUTE INDEX_NODE\n"
            + "\n"
            + "For more info, visit this link:\n"
            + "https://github.com/AY1920S1-CS2113T-W13-3/main/blob/master/docs/UserGuide.adoc\n"
            ;

    /**
     * Executes this command and returns a text result.
     *
     * @param model The model object containing event list.
     */
    @Override
    public CommandResultText execute(Model model) {
        return new CommandResultText(MESSAGE_HELP);
    }
}
