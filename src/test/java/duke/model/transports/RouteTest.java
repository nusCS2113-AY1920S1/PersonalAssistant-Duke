package duke.model.transports;

import duke.commons.exceptions.NullResultException;
import duke.commons.exceptions.QueryOutOfBoundsException;
import duke.commons.exceptions.RouteNodeDuplicateException;
import duke.model.locations.BusStop;
import duke.model.locations.RouteNode;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RouteTest {

    @Test
    void constructor() {
        Route route = new Route("To Sentosa", "tomorrow");

        assertEquals("To Sentosa", route.getName());
        assertEquals("tomorrow", route.getDescription());
        assertEquals(0, route.size());
    }

    @Test
    void constructorAlt() {
        ArrayList<RouteNode> nodes = new ArrayList<>();
        Route route = new Route(nodes, "To Sentosa", "tomorrow");

        assertEquals("To Sentosa", route.getName());
        assertEquals("tomorrow", route.getDescription());
        assertEquals(0, route.size());
    }

    @Test
    void addNode() throws RouteNodeDuplicateException {
        Route route = new Route("To Sentosa", "tomorrow");
        BusStop v1 = new BusStop("45039","Opp Yew Tee Ind Est", "Woodlands Rd",
                1.39585817355572, 103.75427816224409);

        route.add(v1);

        assertThrows(RouteNodeDuplicateException.class, () ->{
            route.add(v1);
        });
    }

    @Test
    void addNodeByIndex() throws RouteNodeDuplicateException {
        Route route = new Route("To Sentosa", "tomorrow");
        BusStop v1 = new BusStop("45039","Opp Yew Tee Ind Est", "Woodlands Rd",
                1.39585817355572, 103.75427816224409);

        assertThrows(QueryOutOfBoundsException.class, () ->{
            route.addNode(v1, 1);
        });

        assertThrows(QueryOutOfBoundsException.class, () ->{
            route.addNode(v1, 2);
        });

        assertThrows(QueryOutOfBoundsException.class, () ->{
            route.addNode(v1, -1);
        });

        assertThrows(QueryOutOfBoundsException.class, () ->{
            route.addNode(v1, -2);
        });

        route.add(v1);
        assertThrows(RouteNodeDuplicateException.class, () ->{
            route.addNode(v1, 1);
        });
    }

    @Test
    void remove() throws RouteNodeDuplicateException {
        Route route = new Route("To Sentosa", "tomorrow");

        assertThrows(IndexOutOfBoundsException.class, () ->{
            route.remove(0);
        });

        assertThrows(IndexOutOfBoundsException.class, () ->{
            route.remove(-1);
        });

        assertThrows(IndexOutOfBoundsException.class, () ->{
            route.remove(-2);
        });

        BusStop v1 = new BusStop("45039","Opp Yew Tee Ind Est", "Woodlands Rd",
                1.39585817355572, 103.75427816224409);
        route.add(v1);

        assertThrows(IndexOutOfBoundsException.class, () ->{
            route.remove(1);
        });

        assertThrows(IndexOutOfBoundsException.class, () ->{
            route.remove(2);
        });

        assertEquals(1, route.size());
        route.remove(0);
        assertEquals(0, route.size());
    }

    @Test
    void getNode() throws RouteNodeDuplicateException {
        Route route = new Route("To Sentosa", "tomorrow");
        BusStop v1 = new BusStop("45039","Opp Yew Tee Ind Est", "Woodlands Rd",
                1.39585817355572, 103.75427816224409);
        route.add(v1);

        assertEquals(v1, route.getNode(0));

        assertThrows(IndexOutOfBoundsException.class, () -> {
            route.getNode(-1);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            route.getNode(-2);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            route.getNode(1);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            route.getNode(2);
        });

        BusStop v2 = new BusStop("25269", "Tuas Checkpoint", "Bus stop 2",
                1.34942405517095, 103.636127935782);
        assertNotEquals(v2, route.getNode(0));
    }

    @Test
    void getNodeByName() throws RouteNodeDuplicateException, NullResultException {
        Route route = new Route("To Sentosa", "tomorrow");
        BusStop v1 = new BusStop("45039","Opp Yew Tee Ind Est", "Woodlands Rd",
                1.39585817355572, 103.75427816224409);
        route.add(v1);

        assertEquals(v1, route.getNodeByName("Opp Yew Tee Ind Est"));

        assertThrows(NullResultException.class, () -> {
            route.getNodeByName("Not Yew Tee!");
        });
    }

    @Test
    void getNodesAsVenue() {
        Route route = new Route("To Sentosa", "tomorrow");
        assertNotNull(route.getNodesAsVenue());
    }

    @Test
    void getStartNode() throws RouteNodeDuplicateException {
        Route route = new Route("To Sentosa", "tomorrow");
        BusStop v1 = new BusStop("45039","Opp Yew Tee Ind Est", "Woodlands Rd",
                1.39585817355572, 103.75427816224409);
        assertNull(route.getStartNode());

        route.add(v1);
        BusStop v2 = new BusStop("25269", "Tuas Checkpoint", "Bus stop 2",
                1.34942405517095, 103.636127935782);
        route.add(v2);

        assertEquals(v1, route.getStartNode());
        assertNotEquals(v2, route.getStartNode());
    }

    @Test
    void getEndNode() throws RouteNodeDuplicateException {
        Route route = new Route("To Sentosa", "tomorrow");
        BusStop v1 = new BusStop("45039","Opp Yew Tee Ind Est", "Woodlands Rd",
                1.39585817355572, 103.75427816224409);
        assertNull(route.getStartNode());

        route.add(v1);
        BusStop v2 = new BusStop("25269", "Tuas Checkpoint", "Bus stop 2",
                1.34942405517095, 103.636127935782);
        route.add(v2);

        assertEquals(v2, route.getEndNode());
        assertNotEquals(v1, route.getEndNode());
    }

    @Test
    void isSameRoute() throws RouteNodeDuplicateException {
        Route route = new Route("To Sentosa", "tomorrow");
        Route route2 = new Route("To Sentosa", "tomorrow");

        assertTrue(route.isSameRoute(route2));

        BusStop v1 = new BusStop("45039","Opp Yew Tee Ind Est", "Woodlands Rd",
                1.39585817355572, 103.75427816224409);
        route.add(v1);

        assertFalse(route.isSameRoute(route2));
    }
}
