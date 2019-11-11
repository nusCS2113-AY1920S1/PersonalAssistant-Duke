package CoreTests;

import rims.core.Parser;
import rims.core.ResourceList;
import rims.core.Ui;
import rims.exception.*;
import rims.resource.Resource;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

//@@author isbobby
public class ParserReturnTest {
    private static Parser parserUnderTest;
    private static Ui ui;
    private static ResourceList listUnderTest;

    @BeforeAll
    private static void init() throws RimsException {
        ui = new Ui();
        ArrayList<Resource> emptyList = new ArrayList<Resource>();
        listUnderTest = new ResourceList(ui, emptyList);
        parserUnderTest = new Parser(ui, listUnderTest);
    }

    @BeforeEach
    private void setup() {
        ;
    }

    @AfterEach
    private void cleanup() {
        ;
    }

    /**
     * This test tests for the exception handling when user enters "return /id"
     * without specifying user id.
     */
    @Test
    public void missingIDTest() throws RimsException, IOException {
        ui.printLine();
        String input = "return /id     ";
        Exception e = assertThrows(RimsException.class, () -> {
            parserUnderTest.parseInput(input);
        });
        assertEquals("Please specify the borrower's user ID.", e.getMessage());
        System.out.print("Test: Input has missing ID\nStatus: Passed\n");
        ui.printLine();
    }

    /**
     * This test tests for the exception handling when user enters "return /id A"
     * with an invalid user id that is not a positive integer.
     */
    @Test
    public void invalidUserIdTest() throws RimsException, IOException {
        String input = "return /id A";
        Exception e = assertThrows(RimsException.class, () -> {
            parserUnderTest.parseInput(input);
        });
        assertEquals("Please specify an integer value that is valid & non-negative!", e.getMessage());

        String input2 = "return /id -1";
        Exception e2 = assertThrows(RimsException.class, () -> {
            parserUnderTest.parseInput(input2);
        });
        assertEquals("Please specify an integer value that is valid & non-negative!", e2.getMessage());
        System.out.print("Test: Input has an invalid user id\nStatus: Passed\n");
    }

    /**
     * This test tests for the exception handling when user enters "return /id 1"
     * and user with id 1 has no reservations.
     */
    @Test
    public void userHasNoBookingTest() throws RimsException, IOException {
        String input_1 = "return /id 5";
        Exception e = assertThrows(RimsException.class, () -> {
            parserUnderTest.parseInput(input_1);
        });
        assertEquals("User 5 has not made any bookings yet!", e.getMessage());
        System.out.print("Test: Input User has no reservations\nStatus: Passed\n");

    }

    // /**
    //  * This test tests for the exception handling when user enters "return /id 1"
    //  * and user with id 1 has no reservations.
    //  * 
    //  * @throws ParseException
    //  */
    // @Test
    // public void invalidReservationIDTest() throws RimsException, IOException, ParseException {
    //     Resource testitem = new Item(1,"testitem");
    //     Date datefrom = stringToDate("10/10/2021 1000");
    //     Date datetill = stringToDate("11/10/2021 1000");
    //     testitem.book(1, 1, datefrom, datetill);
    //     listUnderTest.add(testitem);

    //     String input_1 = "return /id 1";
    //     parserUnderTest.parseInput(input_1);
    //     //assertEquals("User 1 has not made any bookings yet!", e.getMessage());
    //     System.out.print("Test: Trying to return a non-existing reservation\nStatus: Passed\n");

    // }

    /**
     * Converts a date and time inputted by the user in String format, into a Date object.
     * @param stringDate the date and time inputted by the user in String format.
     * @return a Date object representing the date and time inputted by the user.
     */
    public Date stringToDate(String stringDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
        Date dateValue = formatter.parse(stringDate);
        return dateValue;
    }

}