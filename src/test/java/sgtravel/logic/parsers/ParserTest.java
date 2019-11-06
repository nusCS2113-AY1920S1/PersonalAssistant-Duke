package sgtravel.logic.parsers;

import sgtravel.commons.exceptions.ParseException;

import sgtravel.logic.commands.AddCommand;
import sgtravel.logic.commands.ProfileAddCommand;
import sgtravel.logic.commands.AddSampleItineraryCommand;
import sgtravel.logic.commands.DeleteCommand;
import sgtravel.logic.commands.EditorCommand;
import sgtravel.logic.commands.ExitCommand;
import sgtravel.logic.commands.FindCommand;
import sgtravel.logic.commands.GetBusRouteCommand;
import sgtravel.logic.commands.GetBusStopCommand;
import sgtravel.logic.commands.HelpCommand;
import sgtravel.logic.commands.ListCommand;
import sgtravel.logic.commands.ListItineraryCommand;
import sgtravel.logic.commands.LocationSearchCommand;
import sgtravel.logic.commands.MarkDoneCommand;
import sgtravel.logic.commands.NewItineraryCommand;
import sgtravel.logic.commands.ProfileSetPreferenceCommand;
import sgtravel.logic.commands.ProfileShowCommand;
import sgtravel.logic.commands.PromptCommand;
import sgtravel.logic.commands.RecommendationsCommand;
import sgtravel.logic.commands.RouteAddCommand;
import sgtravel.logic.commands.RouteDeleteCommand;
import sgtravel.logic.commands.RouteGenerateCommand;
import sgtravel.logic.commands.RouteListCommand;
import sgtravel.logic.commands.RouteNodeAddCommand;
import sgtravel.logic.commands.RouteNodeDeleteCommand;
import sgtravel.logic.commands.RouteNodeListCommand;
import sgtravel.logic.commands.RouteNodeNeighboursCommand;
import sgtravel.logic.commands.RouteNodeShowCommand;
import sgtravel.logic.commands.RouteShowCommand;
import sgtravel.logic.commands.ShowItineraryCommand;
import sgtravel.logic.commands.StaticMapCommand;
import sgtravel.logic.commands.ViewScheduleCommand;
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
        assertTrue(Parser.parseComplexCommand("event sentosa between 12/12/20 and 12/12/99") instanceof AddCommand);
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
