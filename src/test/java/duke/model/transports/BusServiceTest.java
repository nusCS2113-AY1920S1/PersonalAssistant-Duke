package duke.model.transports;

import duke.commons.enumerations.Direction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BusServiceTest {

    @Test
    void constructor() {
        BusService busService = new BusService("48");

        assertEquals("48", busService.getBus());
        assertNotNull(busService.getDirection(Direction.FORWARD));
        assertNotNull(busService.getDirection(Direction.BACKWARD));
    }

    @Test
    void addRoute() {
        BusService busService = new BusService("48");
        assertEquals(0, busService.getDirection(Direction.FORWARD).size());
        assertEquals(0, busService.getDirection(Direction.BACKWARD).size());

        String newBusStop = "4848";
        busService.addRoute(newBusStop, Direction.FORWARD);
        assertEquals(1, busService.getDirection(Direction.FORWARD).size());
        assertEquals(0, busService.getDirection(Direction.BACKWARD).size());

        busService.addRoute(newBusStop, Direction.BACKWARD);
        assertEquals(1, busService.getDirection(Direction.FORWARD).size());
        assertEquals(1, busService.getDirection(Direction.BACKWARD).size());
    }

    @Test
    void getDirection() {
        BusService busService = new BusService("48");
        String newBusStop1 = "4848";
        busService.addRoute(newBusStop1, Direction.FORWARD);

        String newBusStop2 = "484848";
        busService.addRoute(newBusStop2, Direction.BACKWARD);

        assertEquals(1, busService.getDirection(Direction.FORWARD).size());
        assertEquals(1, busService.getDirection(Direction.BACKWARD).size());

        assertEquals("4848", busService.getDirection(Direction.FORWARD).get(0));
        assertEquals("484848", busService.getDirection(Direction.BACKWARD).get(0));
    }
}
