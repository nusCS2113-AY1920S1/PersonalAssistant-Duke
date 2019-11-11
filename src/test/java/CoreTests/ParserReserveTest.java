package CoreTests;

import rims.core.Parser;
import rims.core.ResourceList;
import rims.core.Ui;
import rims.exception.*;
import rims.resource.Item;
import rims.resource.Resource;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

//@@author isbobby
public class ParserReserveTest {
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
     * User enters a valid input
     */
    @Test
    public void validInputTest() throws RimsException, IOException, ParseException {
        String input = "reserve /item testobject /qty 1 /id 1 /from 15/11/2020 1000 /by 15/11/2020 1200";
        parserUnderTest.parseInput(input);

        ui.formattedPrint("Test: valid input\n\tStatus: passed");
    }

    /**
     * User enters invalid datatype when they are supposed to enter integer.
     */
    @Test
    public void invalidIntegerInputTest() throws RimsException, IOException, ParseException {
        String input = "reserve /item testobject /qty a /id a /from 15/11/2020 1000 /by 15/11/2020 1200";    
        Exception e = assertThrows(RimsException.class, () -> {
            parserUnderTest.parseInput(input);
        });
        assertEquals("Please specify an integer value that is valid & non-negative!", e.getMessage());
        ui.formattedPrint("Test: Invalid type in input\n\tStatus: Passed");
    }

    /**
     * User enters invalid date format.
     */
    @Test
    public void invalidDateFormatTest() throws RimsException, IOException, ParseException {
        String input = "reserve /item testobject /qty 1 /id 1 /from 15/11/2020 /by 15/11/2020 1200";
    
        Exception e = assertThrows(RimsException.class, () -> {
            parserUnderTest.parseInput(input);
        });
        
        assertEquals("Please enter a valid day / time.", e.getMessage());
        ui.formattedPrint("Test: Invalid date format in input\n\tStatus: Passed");
    }

    /**
     * User tries to reserve a non-existing item
     */
    @Test
    public void itemDoesNotExistTest() throws RimsException, IOException, ParseException {
        String input = "reserve /item what /qty 1 /id 1 /from 15/11/2020 /by 15/11/2020 1200";
    
        Exception e = assertThrows(RimsException.class, () -> {
            parserUnderTest.parseInput(input);
        });
        
        assertEquals("This resource does not exist in your inventory!", e.getMessage());
        ui.formattedPrint("Test: Invalid date format in input\n\tStatus: Passed");
    }
}