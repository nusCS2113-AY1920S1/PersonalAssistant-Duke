package duke.logic.parsers;

import duke.commons.exceptions.ParseException;

import duke.logic.commands.AddCommand;
import duke.logic.commands.ProfileAddCommand;
import duke.logic.commands.AddSampleItineraryCommand;
import duke.logic.commands.DeleteCommand;
import duke.logic.commands.EditorCommand;
import duke.logic.commands.ExitCommand;
import duke.logic.commands.FindCommand;
import duke.logic.commands.GetBusRouteCommand;
import duke.logic.commands.GetBusStopCommand;
import duke.logic.commands.HelpCommand;
import duke.logic.commands.ListCommand;
import duke.logic.commands.ListItineraryCommand;
import duke.logic.commands.LocationSearchCommand;
import duke.logic.commands.MarkDoneCommand;
import duke.logic.commands.NewItineraryCommand;
import duke.logic.commands.ProfileSetPreferenceCommand;
import duke.logic.commands.ProfileShowCommand;
import duke.logic.commands.PromptCommand;
import duke.logic.commands.RecommendationsCommand;
import duke.logic.commands.RouteAddCommand;
import duke.logic.commands.RouteDeleteCommand;
import duke.logic.commands.RouteGenerateCommand;
import duke.logic.commands.RouteListCommand;
import duke.logic.commands.RouteNodeAddCommand;
import duke.logic.commands.RouteNodeDeleteCommand;
import duke.logic.commands.RouteNodeListCommand;
import duke.logic.commands.RouteNodeNeighboursCommand;
import duke.logic.commands.RouteNodeShowCommand;
import duke.logic.commands.RouteShowCommand;
import duke.logic.commands.ShowItineraryCommand;
import duke.logic.commands.StaticMapCommand;
import duke.logic.commands.ViewScheduleCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ParserTest {

    @Test
    void parse() throws Exception {
        assertTrue(Parser.parseComplexCommand("bye") instanceof ExitCommand);
        assertTrue(Parser.parseComplexCommand("edit") instanceof EditorCommand);
        assertTrue(Parser.parseComplexCommand("list") instanceof ListCommand);
        assertTrue(Parser.parseComplexCommand("help") instanceof HelpCommand);
        assertTrue(Parser.parseComplexCommand("view") instanceof ViewScheduleCommand);
        assertTrue(Parser.parseComplexCommand("done 1") instanceof MarkDoneCommand);
        assertTrue(Parser.parseComplexCommand("delete 1") instanceof DeleteCommand);
        assertTrue(Parser.parseComplexCommand("find this") instanceof FindCommand);
        assertTrue(Parser.parseComplexCommand("event sentosa between Mon and Wed") instanceof AddCommand);
        assertTrue(Parser.parseComplexCommand("search sentosa") instanceof LocationSearchCommand);
        assertTrue(Parser.parseComplexCommand("cancel") instanceof PromptCommand);

        assertTrue(Parser.parseComplexCommand("addThisList SundayList") instanceof AddSampleItineraryCommand);
        assertTrue(Parser.parseComplexCommand("listItinerary") instanceof ListItineraryCommand);
        assertTrue(Parser.parseComplexCommand("recommend orchard "
                + "between 23/04/19 and 25/04/19") instanceof RecommendationsCommand);
        assertTrue(Parser.parseComplexCommand("newItinerary 23/04/20 25/04/20 MBS "
                + "TwoDayHoliday 1 /venue Orchard /do dancing /and singing /and swinging 2 "
                + "/venue Changi /do running /and jumping /and swinging") instanceof NewItineraryCommand);
        assertTrue(Parser.parseComplexCommand("showItinerary 1") instanceof ShowItineraryCommand);

        assertTrue(Parser.parseComplexCommand("busRoute 900") instanceof GetBusRouteCommand);
        assertTrue(Parser.parseComplexCommand("busStop 66211") instanceof GetBusStopCommand);
        assertTrue(Parser.parseComplexCommand("routeAdd Day trip to Sentosa") instanceof RouteAddCommand);
        assertTrue(Parser.parseComplexCommand("routeNodeAdd 1 1 at 17009 by bus") instanceof RouteNodeAddCommand);
        assertTrue(Parser.parseComplexCommand("routeGenerate amk hub to clementi by bus")
                instanceof RouteGenerateCommand);
        assertTrue(Parser.parseComplexCommand("routeNodeList 1 1") instanceof RouteNodeListCommand);
        assertTrue(Parser.parseComplexCommand("routeShow 1") instanceof RouteShowCommand);
        assertTrue(Parser.parseComplexCommand("routeNodeShow 1 1") instanceof RouteNodeShowCommand);
        assertTrue(Parser.parseComplexCommand("routeNodeNearby 1 1") instanceof RouteNodeNeighboursCommand);
        assertTrue(Parser.parseComplexCommand("routeDelete 1") instanceof RouteDeleteCommand);
        assertTrue(Parser.parseComplexCommand("routeNodeDelete 1 1") instanceof RouteNodeDeleteCommand);
        assertTrue(Parser.parseComplexCommand("map sentosa") instanceof StaticMapCommand);
        assertTrue(Parser.parseComplexCommand("routeList 1") instanceof RouteListCommand);

        assertTrue(Parser.parseComplexCommand("profile name 01/01/01") instanceof ProfileAddCommand);
        assertTrue(Parser.parseComplexCommand("profileShow") instanceof ProfileShowCommand);
        assertTrue(Parser.parseComplexCommand("profileSet sports true") instanceof ProfileSetPreferenceCommand);
        assertThrows(ParseException.class, () -> {
            Parser.parseComplexCommand("error");
        });
    }
}
