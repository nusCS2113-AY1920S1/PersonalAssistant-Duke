package junittesting;

import javacake.JavaCake;
import javacake.exceptions.CakeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GotoTest {

    private static JavaCake javaCake;
    private static String response;
    private static String expectedResponse;

    @BeforeEach
    void init() {
        try {
            javaCake = new JavaCake();
        } catch (CakeException e) {
            System.out.println(e.getMessage());
        }
        getResponse("list");
        if (response.contains("Type 'list' to view main topics.")) {
            getResponse("list");
        } else if (response.contains("subtopics available")) {
            return;
        } else {
            //test again seems buggy//
            getResponse("list");
            getResponse("list");
            assertTrue(response.contains("subtopics available"));
        }
    }

    @Test
    void validIndex() {
        getResponse("goto 1");
        getResponse("goto 1");
        expectedResponse = response;
        getResponse("back");
        getResponse("back");
        getResponse("goto 1.1");
        assertEquals(expectedResponse, response);
    }

    @Test
    void outOfBoundsIndex() {
        getResponse("list");
        int invalidIndex = findNumOfSubtopics(response) + 1;
        getResponse("goto " + invalidIndex);
        expectedResponse = "out of bounds";
        assertTrue(response.contains(expectedResponse));

        getResponse("goto -1");
        expectedResponse = "out of bounds";
        assertTrue(response.contains(expectedResponse));


        //no goto index before list
    }

    @Test
    void indexOverflow() {
        getResponse("goto 2147483649");
        expectedResponse = "Please input valid index!";
        assertEquals(expectedResponse, response);
    }

    @Test
    void noindexsuppled() {
        getResponse("goto ");
        expectedResponse = "Please input valid index!";
        assertEquals(expectedResponse, response);
    }

    @Test
    void nonintIndex() {
        getResponse("goto ../");
        expectedResponse = "Please input valid index!";
        assertEquals(expectedResponse, response);
    }

    private int findNumOfSubtopics(String response) {
        return Integer.parseInt(response.substring(13, 14));
    }

    private void getResponse(String input) {
        response = javaCake.getResponse(input);
    }

}
