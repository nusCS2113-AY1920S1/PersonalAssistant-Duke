package entertainment.pro.logic.cinemaRequesterAPI;


import entertainment.pro.commons.exceptions.Exceptions;
import entertainment.pro.logic.cinemarequesterapi.CinemaRetrieveRequest;
import entertainment.pro.model.CinemaInfoObject;
import entertainment.pro.ui.MovieHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class cinemaRetrieveRequestTest {

    private String jsonTest = "{\n" +
            "   \"html_attributions\" : [],\n" +
            "   \"results\" : [\n" +
            "      {\n" +
            "         \"formatted_address\" : \"321 Clementi Ave 3, #03-03 321, Singapore 129905\",\n" +
            "         \"geometry\" : {\n" +
            "            \"location\" : {\n" +
            "               \"lat\" : 1.3119172,\n" +
            "               \"lng\" : 103.7650692\n" +
            "            },\n" +
            "            \"viewport\" : {\n" +
            "               \"northeast\" : {\n" +
            "                  \"lat\" : 1.313184979892722,\n" +
            "                  \"lng\" : 103.7663153798927\n" +
            "               },\n" +
            "               \"southwest\" : {\n" +
            "                  \"lat\" : 1.310485320107278,\n" +
            "                  \"lng\" : 103.7636157201073\n" +
            "               }\n" +
            "            }\n" +
            "         },\n" +
            "         \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/movies-71.png\",\n" +
            "         \"id\" : \"72283adc8e916b57df806defafdf5307fdb2dfd6\",\n" +
            "         \"name\" : \"WE Cinemas\",\n" +
            "         \"photos\" : [\n" +
            "            {\n" +
            "               \"height\" : 1836,\n" +
            "               \"html_attributions\" : [\n" +
            "                  \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/114630621165040729787/photos\\\"\\u003eAaron Ng\\u003c/a\\u003e\"\n" +
            "               ],\n" +
            "               \"photo_reference\" : \"CmRaAAAAYgXMe0WWs7oGiDWJTSpEzaLWIczPU5b0tXiEyvshjqT-QIlPnyDBOh95Y-ceDEicCt2DTP5HXPcCgD2L4qe-i6kwUxZsW2cWW9xQfqAIDb7XjL6zNNTlSDCxr00YCEzlEhALIdDtEPq4ICe6-pY8QMMrGhTEus1sM7IGce2tXOKDMXv89SIggw\",\n" +
            "               \"width\" : 3264\n" +
            "            }\n" +
            "         ],\n" +
            "         \"place_id\" : \"ChIJ91kj6o0a2jERgiLubxWHDTo\",\n" +
            "         \"plus_code\" : {\n" +
            "            \"compound_code\" : \"8Q68+Q2 Singapore\",\n" +
            "            \"global_code\" : \"6PH58Q68+Q2\"\n" +
            "         },\n" +
            "         \"rating\" : 4.3,\n" +
            "         \"reference\" : \"ChIJ91kj6o0a2jERgiLubxWHDTo\",\n" +
            "         \"types\" : [ \"movie_theater\", \"point_of_interest\", \"establishment\" ],\n" +
            "         \"user_ratings_total\" : 972\n" +
            "      }\n" +
            "   ],\n" +
            "   \"status\" : \"OK\"\n" +
            "}";

    private String jsonTestFail = "{\n" +
            "   \"html_attributions\" : [],\n" +
            "   \"results\" : [],\n" +
            "   \"status\" : \"ZERO_RESULTS\"\n" +
            "}";
    @Test
    public void searchNearestCinemasTestSuccess() {
        CinemaRetrieveRequest retrieveRequest = new CinemaRetrieveRequest(new MovieHandler());
        retrieveRequest.searchNearestCinemas("Clementi");
        assertEquals(1, retrieveRequest.getParsedCinemas().size());
        assertEquals("WE Cinemas", retrieveRequest.getParsedCinemas().get(0).getName());
        retrieveRequest.searchNearestCinemas("Bukit Timah");
        assertEquals(3, retrieveRequest.getParsedCinemas().size());
        assertEquals("EagleWings Cinematics", retrieveRequest.getParsedCinemas().get(0).getName());
        retrieveRequest.searchNearestCinemas("Choa Chu kang");
        assertEquals(1, retrieveRequest.getParsedCinemas().size());
        assertEquals("Shaw Theatres", retrieveRequest.getParsedCinemas().get(0).getName());
    }

    @Test
    public void searchNearestCinemasTestFailure() {
        CinemaRetrieveRequest retrieveRequest = new CinemaRetrieveRequest(new MovieHandler());
        retrieveRequest.searchNearestCinemas("null");
        assertEquals(0, retrieveRequest.getParsedCinemas().size());
        retrieveRequest.searchNearestCinemas("n n");
        assertEquals(0, retrieveRequest.getParsedCinemas().size());
        retrieveRequest.searchNearestCinemas("n n n");
        assertEquals(0, retrieveRequest.getParsedCinemas().size());
    }

    @Test
    public void searchCinemasJsonTestSuccess() {
        CinemaRetrieveRequest retrieveRequest = new CinemaRetrieveRequest(new MovieHandler());
        retrieveRequest.fetchedCinemasJson(jsonTest);
        assertEquals(1, retrieveRequest.getParsedCinemas().size());
        assertEquals("WE Cinemas", retrieveRequest.getParsedCinemas().get(0).getName());
    }

    @Test
    public void searchCinemasJsonTestFailure() {
        CinemaRetrieveRequest retrieveRequest = new CinemaRetrieveRequest(new MovieHandler());
        retrieveRequest.fetchedCinemasJson(jsonTestFail);
        assertEquals(0, retrieveRequest.getParsedCinemas().size());
    }
}
