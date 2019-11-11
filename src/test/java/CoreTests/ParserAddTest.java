package CoreTests;

import rims.core.Parser;
import rims.core.ResourceList;
import rims.core.Ui;
import rims.exception.*;
import rims.resource.Item;
import rims.resource.Resource;
import java.text.ParseException;
import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

//@@author isbobby
public class ParserAddTest {
    private static Parser parserUnderTest;
    private static Ui ui;
    private static ResourceList listUnderTest;
    private static Resource resourceUnderTest;

    @BeforeAll
    private static void init() throws RimsException {
        ui = new Ui();
        ArrayList<Resource> emptyList = new ArrayList<Resource>();
        listUnderTest = new ResourceList(ui, emptyList);

        resourceUnderTest = new Item(1, "testobject");
        listUnderTest.add(resourceUnderTest);

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
     * Test when user enters an add command without specifying item name.
     */
    @Test
    public void missingItemNameTest() {
        String input = "add /item ";
        Exception e = assertThrows(RimsException.class, () -> {
            parserUnderTest.parseInput(input);
        });
        assertEquals("Please specify the name of the item to add to your inventory.", e.getMessage());
        ui.formattedPrint("Test: user enters an add command without specifying item name\n\tStatus: Passed");
    }

    /**
     * Test when user enters too large a quantity.
     */
    @Test
    public void quantityTooLargeTest() {
        String input = "add /item a /qty 101";
        Exception e = assertThrows(RimsException.class, () -> {
            parserUnderTest.parseInput(input);
        });
        assertEquals("You can only add up to 100 items at a time!", e.getMessage());
        ui.formattedPrint("Test: user enters too large a quantity.\n\tStatus: Passed");
    }

    /**
     * Test when user enters an add command without specifying item quantity.
     */
    @Test
    public void missingItemQtyTest() {
        String input = "add /item test /qty";
        Exception e = assertThrows(RimsException.class, () -> {
            parserUnderTest.parseInput(input);
        });
        assertEquals("Please specify an integer value that is valid & non-negative!", e.getMessage());
        ui.formattedPrint("Test: user enters an add command without specifying item quantity.\n\tStatus: Passed");
    }

    /**
     * Test when user enters an add command with invalid item quantity.
     */
    @Test
    public void invalidItemQtyTest() {
        String input = "add /item test /qty a";
        Exception e = assertThrows(RimsException.class, () -> {
            parserUnderTest.parseInput(input);
        });
        assertEquals("Please specify an integer value that is valid & non-negative!", e.getMessage());
        ui.formattedPrint("Test: user enters an add command with invalid item quantity\n\tStatus: Passed");
    }

    /**
     * Test when user enters an invalid resource name.
     */
    @Test
    public void invalidResourceNameTest() {
        String input = "add /item ,test,     /qty 1";
        Exception e = assertThrows(RimsException.class, () -> {
            parserUnderTest.parseInput(input);
        });
        assertEquals("Please do not enter ',' in your input!", e.getMessage());
        ui.formattedPrint("Test: user enters an invalid resource name.\n\tStatus: Passed");
    }

    /**
     * Test when user trys to enter quantity argument for rooms.
     */
    @Test
    public void specifyingQuantityForRoomsTest() {
        String input = "add /room test /qty 1";
        Exception e = assertThrows(RimsException.class, () -> {
            parserUnderTest.parseInput(input);
        });
        assertEquals("Rooms do not require quantity!", e.getMessage());
        ui.formattedPrint("Test: user trys to enter quantity argument for rooms.\n\tStatus: Passed");
    }

    /**
     * Test when user enters a valid command to add item.
     * 
     * @throws ParseException
     * @throws RimsException
     */
    @Test
    public void validItemInputTest() throws RimsException, ParseException {
        String input = "add /item goodboy /qty 1";
        parserUnderTest.parseInput(input);
        ui.formattedPrint("Test: user enters a valid add item command.\n\tStatus: Passed");
    }

    /**
     * Test when user enters a valid command to add room.
     * 
     * @throws ParseException
     * @throws RimsException
     */
    @Test
    public void validRoomInputTest() throws RimsException, ParseException {
        String input = "add /room goodgirl";
        parserUnderTest.parseInput(input);
        ui.formattedPrint("Test: user enters a valid add room command.\n\tStatus: Passed");
    }
}