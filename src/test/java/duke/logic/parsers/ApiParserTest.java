package duke.logic.parsers;

import duke.ModelStub;
import duke.commons.exceptions.ApiException;
import duke.commons.exceptions.DuplicateRouteNodeException;
import duke.commons.exceptions.FileLoadFailException;
import duke.commons.exceptions.QueryFailedException;
import duke.commons.exceptions.OutOfBoundsException;
import duke.logic.api.ApiParser;
import duke.model.locations.BusStop;
import duke.model.locations.CustomNode;
import duke.model.locations.RouteNode;
import duke.model.locations.TrainStation;
import duke.model.locations.Venue;
import duke.model.transports.Route;
import javafx.scene.image.Image;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ApiParserTest {
    private static final String DIMENSIONS = "512";
    private static final String ZOOM_LEVEL = "16";
    private static final String RED_VALUE_OTHER = "255";
    private static final String GREEN_VALUE_OTHER = "122";
    private static final String BLUE_VALUE_OTHER = "0";
    private static final String LINE_WIDTH = "2";

    @Test
    void locationSearchTest() throws ApiException {
        Venue expected = new Venue("21 LOWER KENT RIDGE ROAD NATIONAL UNIVERSITY OF SINGAPORE " +
                "(LT24) SINGAPORE 119077", 1.295891879784, 103.780531726731, 22125.2865001485,
                30918.7200026438);
        Venue actual = ApiParser.getLocationSearch("NUS");

        assertTrue(expected.equals(actual));
    }

    @Test
    void getStaticMap() throws QueryFailedException, OutOfBoundsException, FileLoadFailException,
            DuplicateRouteNodeException {
        ModelStub model = new ModelStub();
        Route route = new Route("Test route", "");
        route.add(new BusStop("17009", model));
        route.add(new BusStop("18339", model));
        route.add(new BusStop("12209", model));
        route.add(new BusStop("94071", model));

        RouteNode node = route.getNode(0);
        ArrayList<String> points = ApiParser.generateOtherPoints(route, node, 0);
        ArrayList<Venue> nearbyNodes = ApiParser.getNeighbour(model, node);

        String rgb = RED_VALUE_OTHER + "," + GREEN_VALUE_OTHER + "," + BLUE_VALUE_OTHER;
        String param = ApiParser.generateStaticMapParams(DIMENSIONS, DIMENSIONS, ZOOM_LEVEL,
                String.valueOf(node.getLatitude()), String.valueOf(node.getLongitude()), "",
                ApiParser.generateLineParam(points, rgb), ApiParser.generatePointParam(route, node, nearbyNodes));

        //NoClassDefFoundError is thrown when image is generated
        assertThrows(NoClassDefFoundError.class, () -> {
            Image actual = ApiParser.getStaticMap(param);
            assert (actual != null);
        });
    }

    @Test
    void getStaticMapNeighbours() throws QueryFailedException, FileLoadFailException, DuplicateRouteNodeException {
        ModelStub model = new ModelStub();
        Route route = new Route("Test route", "");
        route.add(new BusStop("17009", model));
        route.add(new BusStop("18339", model));
        route.add(new BusStop("12209", model));
        route.add(new BusStop("94071", model));

        //ExceptionInInitializerError is thrown when image is generated
        assertThrows(NoClassDefFoundError.class, () -> {
            Image actual = ApiParser.generateStaticMapNeighbours(model, route, route.getStartNode(), 0);
            assert (actual != null);
        });
    }

    @Test
    void generateStaticMapParams() throws QueryFailedException, OutOfBoundsException, FileLoadFailException,
            DuplicateRouteNodeException {
        ModelStub model = new ModelStub();
        Route route = new Route("Test route", "");
        route.add(new BusStop("17009", model));
        route.add(new BusStop("18339", model));
        route.add(new BusStop("12209", model));
        route.add(new BusStop("94071", model));

        RouteNode node = route.getNode(0);
        ArrayList<String> points = ApiParser.generateOtherPoints(route, node, 0);
        ArrayList<Venue> nearbyNodes = ApiParser.getNeighbour(model, node);

        String rgb = RED_VALUE_OTHER + "," + GREEN_VALUE_OTHER + "," + BLUE_VALUE_OTHER;
        String actual = ApiParser.generateStaticMapParams(DIMENSIONS, DIMENSIONS, ZOOM_LEVEL,
                String.valueOf(node.getLatitude()), String.valueOf(node.getLongitude()), "",
                ApiParser.generateLineParam(points, rgb), ApiParser.generatePointParam(route, node, nearbyNodes));

        String expected = "layerchosen=default&lat=1.31491572870629&lng=103.76412225438476&zoom=16&height=512&" +
                "width=512&polygons=&lines=[[1.29379302903228,103.78507910002646],[1.32653202760922," +
                "103.76429968220569],[1.31990193658073,103.95514880285305]]:255,122,0:2&points=[1.31497399268611," +
                "103.76502698264449,\"0,0,0\",\"1\"]|[1.3152,103.7652,\"0,0,0\",\"2\"]|[1.31528191560846," +
                "103.76536000516927,\"0,0,0\",\"3\"]|[1.31749570803723,103.7638854582427,\"0,0,0\",\"4\"]|[" +
                "1.31726409983754,103.76240145648879,\"0,0,0\",\"5\"]|[1.31491572870629,103.76412225438476,\"122," +
                "255,0\",\"C\"]|[1.29379302903228,103.78507910002646,\"255,122,0\",\"O\"]|[1.32653202760922," +
                "103.76429968220569,\"255,122,0\",\"O\"]|[1.31990193658073,103.95514880285305,\"122,255,0\",\"O\"]" +
                "&color=&fillColor=";
        assertEquals(expected, actual);
    }

    @Test
    void generateStaticMapLines() throws QueryFailedException, FileLoadFailException, DuplicateRouteNodeException {
        ModelStub model = new ModelStub();
        Route route = new Route("Test route", "");
        route.add(new BusStop("17009", model));
        route.add(new BusStop("18339", model));
        route.add(new BusStop("12209", model));
        route.add(new BusStop("94071", model));

        ArrayList<String> points = ApiParser.generateOtherPoints(route, route.getNode(0), 0);
        String rgb = RED_VALUE_OTHER + "," + GREEN_VALUE_OTHER + "," + BLUE_VALUE_OTHER;
        String actual = ApiParser.generateStaticMapLines(points, rgb, LINE_WIDTH);

        String expected = "[[1.29379302903228,103.78507910002646],[1.32653202760922,103.76429968220569]," +
                "[1.31990193658073,103.95514880285305]]:255,122,0:2";
        assertEquals(expected, actual);

        actual = ApiParser.generateLineParam(points, rgb);
        assertEquals(expected, actual);
    }

    @Test
    void generateOtherPoints() throws QueryFailedException, FileLoadFailException, DuplicateRouteNodeException {
        ModelStub model = new ModelStub();
        Route route = new Route("Test route", "");

        route.add(new BusStop("17009", model));
        route.add(new BusStop("18339", model));
        route.add(new BusStop("12209", model));
        route.add(new BusStop("94071", model));

        assertEquals(3, ApiParser.generateOtherPoints(route, route.getStartNode(), 0).size());
    }

    @Test
    void getLocationSearchName() throws QueryFailedException, FileLoadFailException {
        ModelStub model = new ModelStub();
        BusStop busStop = new BusStop("17009", model);
        TrainStation trainStation = new TrainStation("Ang Mo Kio", model);
        CustomNode customNode = new CustomNode("Somewhere in Com 2", "", 0.71, 1.34);

        assertEquals("17009", ApiParser.getLocationSearchName(busStop));
        assertEquals("Ang Mo Kio MRT", ApiParser.getLocationSearchName(trainStation));
        assertEquals("Somewhere in Com 2", ApiParser.getLocationSearchName(customNode));
    }

    @Test
    void getNeighbours() throws QueryFailedException, OutOfBoundsException, FileLoadFailException {
        ModelStub model = new ModelStub();
        BusStop newNode = new BusStop("17009", model);
        assertEquals(5, ApiParser.getNeighbour(model, newNode).size());

        BusStop newNode2 = new BusStop("44859", model);
        assertEquals(5, ApiParser.getNeighbour(model, newNode2).size());
    }

    @Test
    void isWithinDistance() throws QueryFailedException, FileLoadFailException {
        ModelStub model = new ModelStub();
        //blk 322 amk
        BusStop node1 = new BusStop("54009", model);

        //amk interchange
        BusStop node2 = new BusStop("54247", model);

        //clementi
        BusStop node3 = new BusStop("17229", model);

        assertTrue(ApiParser.isWithinDistance(node1, node2));
        assertTrue(ApiParser.isWithinDistance(node2, node1));

        assertFalse(ApiParser.isWithinDistance(node1, node3));
        assertFalse(ApiParser.isWithinDistance(node3, node1));
    }
}
