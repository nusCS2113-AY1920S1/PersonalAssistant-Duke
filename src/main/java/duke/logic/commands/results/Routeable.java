package duke.logic.commands.results;

import duke.model.locations.BusStop;

import java.util.ArrayList;

public interface Routeable {

    ArrayList<BusStop> getRoute();

    void setRoute(ArrayList<BusStop> route);
}
