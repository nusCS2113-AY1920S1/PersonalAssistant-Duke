package duke.model.lists;

import duke.commons.exceptions.RouteDuplicateException;
import duke.commons.exceptions.RouteNodeDuplicateException;
import duke.commons.exceptions.RouteNotFoundException;
import duke.model.transports.Route;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RouteListTest {

    @Test
    void add() throws RouteDuplicateException {
        RouteList routeList = new RouteList();
        Route route1 = new Route("To NUS", "by bus");

        routeList.add(route1);
        assertTrue(routeList.contains(route1));

        assertThrows(RouteDuplicateException.class, () -> {
            routeList.add(route1);
        });
    }

    @Test
    void get() throws RouteDuplicateException {
        RouteList routeList = new RouteList();
        Route route1 = new Route("To NUS", "by bus");
        routeList.add(route1);

        assertEquals(route1, routeList.get(0));

        //test OOB
        assertThrows(IndexOutOfBoundsException.class, () -> {
            routeList.get(-1);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            routeList.get(-2);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            routeList.get(1);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            routeList.get(2);
        });
    }

    @Test
    void remove() throws RouteDuplicateException {
        RouteList routeList = new RouteList();
        Route route1 = new Route("To NUS", "by bus");
        routeList.add(route1);

        assertEquals(route1, routeList.remove(0));

        assertThrows(IndexOutOfBoundsException.class, () -> {
            routeList.remove(0);
        });

        routeList.add(route1);
        assertThrows(IndexOutOfBoundsException.class, () -> {
            routeList.remove(-1);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            routeList.remove(-2);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            routeList.remove(1);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            routeList.remove(2);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            Route newRoute = routeList.remove(-1);
            newRoute.getName();
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            Route newRoute = routeList.remove(-2);
            newRoute.getName();
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            Route newRoute = routeList.remove(1);
            newRoute.getName();
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            Route newRoute = routeList.remove(2);
            newRoute.getName();
        });
    }

    @Test
    void setRoute() throws RouteDuplicateException, RouteNodeDuplicateException, RouteNotFoundException {
        RouteList routeList = new RouteList();
        Route route1 = new Route("To NUS", "by bus");
        Route route2 = new Route("To NTU", "by bus");
        routeList.add(route1);

        routeList.setRoute(route1, route2);
        assertTrue(routeList.contains(route2));

        Route route3 = new Route("To SMU", "by bus");
        assertThrows(RouteNotFoundException.class, () -> {
            routeList.setRoute(route1, route3);
        });

        assertThrows(RouteDuplicateException.class, () -> {
            routeList.setRoute(route2, route2);
        });
    }

    @Test
    void setRoutes() throws RouteDuplicateException, RouteNodeDuplicateException, RouteNotFoundException {
        RouteList routeList = new RouteList();
        Route route1 = new Route("To NUS", "by bus");
        Route route2 = new Route("To NTU", "by bus");

        List<Route> routeArrayList = new ArrayList<>();
        routeArrayList.add(route1);
        routeArrayList.add(route2);

        routeList.setRoutes(routeArrayList);
        assertTrue(routeList.contains(route1));
        assertTrue(routeList.contains(route2));

        routeArrayList.add(route2);
        assertThrows(RouteDuplicateException.class, () -> {
            routeList.setRoutes(routeArrayList);
        });
    }

    @Test
    void size() throws RouteDuplicateException {
        RouteList routeList = new RouteList();
        assertEquals(0, routeList.size());

        Route route1 = new Route("To NUS", "by bus");
        routeList.add(route1);
        assertEquals(1, routeList.size());

        Route route2 = new Route("To NTU", "by bus");
        routeList.add(route2);
        assertEquals(2, routeList.size());
    }

    @Test
    void contains() throws RouteDuplicateException {
        RouteList routeList = new RouteList();
        Route route1 = new Route("To NUS", "by bus");

        assertFalse(routeList.contains(route1));

        routeList.add(route1);
        assertTrue(routeList.contains(route1));

        Route route2 = new Route("To NTU", "by bus");
        assertFalse(routeList.contains(route2));

        routeList.add(route2);
        assertTrue(routeList.contains(route2));
    }

    @Test
    void isEmpty() throws RouteDuplicateException {
        RouteList routeList = new RouteList();
        assertTrue(routeList.isEmpty());

        Route route1 = new Route("To NUS", "by bus");
        routeList.add(route1);
        assertFalse(routeList.isEmpty());
    }
}
