package sgtravel.model.transports;

import sgtravel.commons.exceptions.NullResultException;
import sgtravel.commons.exceptions.OutOfBoundsException;
import sgtravel.commons.exceptions.DuplicateRouteNodeException;
import sgtravel.model.locations.BusStop;
import sgtravel.model.locations.RouteNode;
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
    void addNode() throws DuplicateRouteNodeException, OutOfBoundsException {
        Route route = new Route("To Sentosa", "tomorrow");
        BusStop v1 = new BusStop("45039","Opp Yew Tee Ind Est", "Woodlands Rd",
                1.39585817355572, 103.75427816224409);

        route.add(v1);
        assertEquals(1, route.size());

        assertThrows(DuplicateRouteNodeException.class, () -> {
            route.add(v1);
        });

        assertThrows(DuplicateRouteNodeException.class, () -> {
            route.addNode(v1, 0);
        });
        BusStop v2 = new BusStop("25269", "Tuas Checkpoint", "Bus stop 2",
                1.34942405517095, 103.636127935782);
        route.addNode(v2, 0);
        assertEquals(2, route.size());
    }

    @Test
    void addNodeByIndex() throws DuplicateRouteNodeException {
        Route route = new Route("To Sentosa", "tomorrow");
        BusStop v1 = new BusStop("45039","Opp Yew Tee Ind Est", "Woodlands Rd",
                1.39585817355572, 103.75427816224409);

        assertThrows(OutOfBoundsException.class, () -> {
            route.addNode(v1, 1);
        });

        assertThrows(OutOfBoundsException.class, () -> {
            route.addNode(v1, 2);
        });

        assertThrows(OutOfBoundsException.class, () -> {
            route.addNode(v1, -1);
        });

        assertThrows(OutOfBoundsException.class, () -> {
            route.addNode(v1, -2);
        });

        route.add(v1);
        assertThrows(DuplicateRouteNodeException.class, () -> {
            route.addNode(v1, 1);
        });
    }

    @Test
    void remove() throws DuplicateRouteNodeException {
        Route route = new Route("To Sentosa", "tomorrow");

        assertThrows(IndexOutOfBoundsException.class, () -> {
            route.remove(0);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            route.remove(-1);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            route.remove(-2);
        });

        BusStop v1 = new BusStop("45039","Opp Yew Tee Ind Est", "Woodlands Rd",
                1.39585817355572, 103.75427816224409);
        route.add(v1);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            route.remove(1);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            route.remove(2);
        });

        assertEquals(1, route.size());
        route.remove(0);
        assertEquals(0, route.size());
    }

    @Test
    void getNode() throws DuplicateRouteNodeException {
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
    void getNodeByName() throws DuplicateRouteNodeException, NullResultException {
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
    void getStartNode() throws DuplicateRouteNodeException {
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
    void getEndNode() throws DuplicateRouteNodeException {
        Route route = new Route("To Sentosa", "tomorrow");
        BusStop v1 = new BusStop("45039","Opp Yew Tee Ind Est", "Woodlands Rd",
                1.39585817355572, 103.75427816224409);
        assertNull(route.getEndNode());

        route.add(v1);
        BusStop v2 = new BusStop("25269", "Tuas Checkpoint", "Bus stop 2",
                1.34942405517095, 103.636127935782);
        route.add(v2);

        assertEquals(v2, route.getEndNode());
        assertNotEquals(v1, route.getEndNode());
    }

    @Test
    void isSameRoute() throws DuplicateRouteNodeException {
        Route route = new Route("To Sentosa", "tomorrow");
        Route route2 = new Route("To Sentosa", "tomorrow");

        assertTrue(route.isSameRoute(route2));

        BusStop v1 = new BusStop("45039","Opp Yew Tee Ind Est", "Woodlands Rd",
                1.39585817355572, 103.75427816224409);
        route.add(v1);

        assertFalse(route.isSameRoute(route2));
    }

    @Test
    void setName() {
        Route route = new Route("To Sentosa", "tomorrow");
        assertEquals("To Sentosa", route.getName());

        route.setName("To MBS");
        assertEquals("To MBS", route.getName());

        route.setName("From NUS");
        assertEquals("From NUS", route.getName());
    }

    @Test
    void setDescription() {
        Route route = new Route("To Sentosa", "tomorrow");
        assertEquals("tomorrow", route.getDescription());

        route.setDescription("the day after");
        assertEquals("the day after", route.getDescription());

        route.setDescription("ok i don't wanna");
        assertEquals("ok i don't wanna", route.getDescription());
    }

    @Test
    void getNodes() throws DuplicateRouteNodeException {
        Route route = new Route("To Sentosa", "tomorrow");
        BusStop v1 = new BusStop("45039","Opp Yew Tee Ind Est", "Woodlands Rd",
                1.39585817355572, 103.75427816224409);
        route.add(v1);
        assertEquals(1, route.getNodes().size());

        BusStop v2 = new BusStop("25269", "Tuas Checkpoint", "Bus stop 2",
                1.34942405517095, 103.636127935782);
        route.add(v2);
        assertEquals(2, route.getNodes().size());
    }
}
