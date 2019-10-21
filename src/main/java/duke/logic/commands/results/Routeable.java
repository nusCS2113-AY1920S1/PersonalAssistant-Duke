package duke.logic.commands.results;

import duke.model.locations.BusStop;
import duke.model.locations.Venue;

import java.util.ArrayList;

public interface Routeable {

    ArrayList<Venue> getRoute();

    void setRoute(ArrayList<Venue> route);
}
