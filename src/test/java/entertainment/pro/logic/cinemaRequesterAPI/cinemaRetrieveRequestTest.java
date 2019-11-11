package entertainment.pro.logic.cinemaRequesterAPI;

import entertainment.pro.commons.exceptions.Exceptions;
import entertainment.pro.commons.strings.PromptMessages;
import entertainment.pro.logic.cinemarequesterapi.CinemaRetrieveRequest;
import entertainment.pro.storage.utils.OfflineSearchStorage;
import entertainment.pro.ui.MovieHandler;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.*;

public class cinemaRetrieveRequestTest {

    private String VALID_CINEMA_FILE_PATH = "/data/ValidCinema.json";
    private String INVALID_CINEMA_FILE_PATH = "/data/InvalidCinema.json";
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
        try {
            retrieveRequest.fetchedCinemasJson(getString(VALID_CINEMA_FILE_PATH));
        } catch (Exceptions e) {
            e.printStackTrace();
        }
        assertEquals(1, retrieveRequest.getParsedCinemas().size());
        assertEquals("WE Cinemas", retrieveRequest.getParsedCinemas().get(0).getName());
    }

    @Test
    public void searchCinemasJsonTestFailure() {
        CinemaRetrieveRequest retrieveRequest = new CinemaRetrieveRequest(new MovieHandler());
        try {
            retrieveRequest.fetchedCinemasJson(getString(INVALID_CINEMA_FILE_PATH));
        } catch (Exceptions e) {
            e.printStackTrace();
        }
        assertEquals(0, retrieveRequest.getParsedCinemas().size());
    }

    public static String getString(String filename) throws Exceptions {
        InputStream inputStream = OfflineSearchStorage.class.getResourceAsStream(filename);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line = "";
        String dataFromJSON = "";
        try {
            while ((line = bufferedReader.readLine()) != null) {
                dataFromJSON += line;
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
        } catch (IOException e) {
            throw new Exceptions(PromptMessages.IO_EXCEPTION_IN_OFFLINE);
        }
        return dataFromJSON;
    }
}
