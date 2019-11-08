package sgtravel.logic.parsers;

import sgtravel.ModelStub;
import sgtravel.commons.exceptions.FileLoadFailException;
import sgtravel.commons.exceptions.QueryFailedException;
import sgtravel.logic.api.ApiConstraintParser;
import sgtravel.model.locations.BusStop;
import sgtravel.model.locations.TrainStation;
import sgtravel.model.locations.Venue;
import sgtravel.model.transports.TransportationMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;

class ApiConstraintParserTest {
    @Test
    void getNearestBusStop() throws QueryFailedException, FileLoadFailException {
        ModelStub model = new ModelStub();
        HashMap<String, BusStop> busStopMap = model.getBusStops();

        BusStop busStop = new BusStop("17009", model);

        //queried location is a bus stop, so it definitely is the same
        BusStop actual = ApiConstraintParser.getNearestBusStop(busStop, busStopMap);
        assertTrue(actual.equals(busStop));

        //very close location
        Venue venue = new Venue("Near Clementi Int", 1.31491572870620, 103.76412225438470,
                0, 0);
        BusStop actual2 = ApiConstraintParser.getNearestBusStop(venue, busStopMap);
        assertTrue(actual2.equals(busStop));
    }

    @Test
    void getNearestTrainStation() throws FileLoadFailException, QueryFailedException {
        ModelStub model = new ModelStub();
        HashMap<String, TrainStation> trainStationMap = model.getMap().getTrainMap();
        TrainStation trainStation = new TrainStation("Ang Mo Kio", model);

        TrainStation actual = ApiConstraintParser.getNearestTrainStation(trainStation, trainStationMap);
        assertTrue(actual.equals(trainStation));

        Venue venue = new Venue("Near Ang Mo Kio MRT", 1.369, 103.8495, 0, 0);
        TrainStation actual2 = ApiConstraintParser.getNearestTrainStation(venue, trainStationMap);
        assertTrue(actual2.equals(trainStation));
    }

    @Test
    void getNearestTransport() throws QueryFailedException, FileLoadFailException {
        ModelStub model = new ModelStub();
        TransportationMap map = model.getMap();

        BusStop busStop = new BusStop("17009", model);

        Venue venue1 = ApiConstraintParser.getNearestTransport(busStop, map);
        assertTrue(venue1.equals(busStop));

        Venue venueBusStop = new Venue("Near Clementi Int", 1.31491572870620, 103.76412225438470,
                0, 0);
        Venue venue2 = ApiConstraintParser.getNearestTransport(venueBusStop, map);
        assertTrue(venue2.equals(busStop));

        TrainStation trainStation = new TrainStation("Ang Mo Kio", model);
        Venue venue3 = ApiConstraintParser.getNearestTransport(trainStation, map);
        assertTrue(venue3.equals(trainStation));

        Venue venueTrainStation = new Venue("Near Ang Mo Kio MRT", 1.369, 103.8495, 0, 0);
        Venue venue4 = ApiConstraintParser.getNearestTransport(venueTrainStation, map);
        assertTrue(venue4.equals(trainStation));
    }
}
