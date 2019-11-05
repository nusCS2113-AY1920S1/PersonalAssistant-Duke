package duke.model.locations;

import duke.ModelStub;
import duke.commons.exceptions.FileLoadFailException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TrainStationTest {
    private ArrayList<String> v1Stations = new ArrayList<>();
    private ArrayList<String> v2Stations = new ArrayList<>();

    private TrainStation v1 = new TrainStation(v1Stations, "Woodlands MRT", "start here",
            1.437094, 103.786483);
    private TrainStation v2 = new TrainStation(v2Stations, "Sembawang", "stop here",
            1.04491, 103.8200);

    @Test
    void getTrainCode() {
        v1Stations.add("NS9");
        v2Stations.add("NS11");
        v1.setTrainCodes(v1Stations);
        v2.setTrainCodes(v2Stations);

        assertEquals(v1Stations, v1.getTrainCodes());
        assertNotEquals(v1Stations, v2.getTrainCodes());
    }

    @Test
    void getAddress() {
        assertEquals("Woodlands MRT", v1.getAddress());
        assertNotEquals("Woodlands MRT", v2.getAddress());
    }

    @Test
    void getDescription() {
        assertEquals("start here", v1.getDescription());
        assertNotEquals("start here", v2.getDescription());
    }

    @Test
    void getLatitude() {
        assertEquals(1.437094, v1.getLatitude());
        assertNotEquals(1.437094, v2.getLatitude());
    }

    @Test
    void getLongitude() {
        assertEquals(103.786483, v1.getLongitude());
        assertNotEquals(103.786483, v2.getLongitude());
    }

    @Test
    void getDistX() {
        assertEquals(0, v1.getDistX());
    }

    @Test
    void getDistY() {
        assertEquals(0, v2.getDistY());
    }

    @Test
    void getDistance() {
        assertFalse(v2.getDistance(v1) < 15000);
        assertTrue(v1.getDistance(v2) > 1000);
        assertEquals(v1.getDistance(v2), v2.getDistance(v1));
    }

    @Test
    void setLatitude() {
        v1.setLatitude(0);
        assertEquals(0, v1.getLatitude());
    }

    @Test
    void setLongitude() {
        v2.setLongitude(0);
        assertEquals(0, v2.getLongitude());
    }

    @Test
    void testToString() {
        assertEquals("Woodlands MRT (1.437094, 103.786483)", v1.toString());
    }

    @Test
    void testEquals() {
        Venue v = new Venue("Woodlands MRT", 1.437094, 103.786483,
                0, 0);
        assertTrue(v.equals(v1));
        assertFalse(v2.equals(v1));
    }

    @Test
    void testFetch() throws FileLoadFailException {
        TrainStation v3 = new TrainStation(new ArrayList<String>(), "Woodlands", "",
                0, 0);
        ModelStub model = new ModelStub();
        v3.fetchData(model);

        v1.setDescription("");

        assertTrue(v1.equals(v3));
        assertFalse(v2.equals(v3));
    }

    @Test
    void getDisplayInfo() throws FileLoadFailException {
        ModelStub model = new ModelStub();
        TrainStation trainStation = new TrainStation(new ArrayList<>(), "Woodlands", "",
                0, 0);
        trainStation.fetchData(model);

        String expected = "Woodlands MRT\n\n(1.437094, 103.786483)";
        assertEquals(expected, trainStation.getDisplayInfo());
    }
}
