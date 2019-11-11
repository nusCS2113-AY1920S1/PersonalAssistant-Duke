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
public class ParserListTest {
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
     * User enters a list command without specifying parameter.
     */
    @Test
    public void missingParameterTest() {
        String input = "list /";
        Exception e = assertThrows(RimsException.class, () -> {
            parserUnderTest.parseInput(input);
        });
        assertEquals("Please specify the parameter you want to view a detailed list of.", e.getMessage());
        System.out.print("Test: Missing parameters in input\nStatus: Passed\n");
    }

    /**
     * User enters a list command with a wrong parameter.
     */
    @Test
    public void invalidParamTest() {
        String input = "list /trash";

        Exception e = assertThrows(RimsException.class, () -> {
            parserUnderTest.parseInput(input);
        });

        assertEquals("Invalid list parameter! Please specify '/date', '/room' " + "or '/item' to view a detailed list.",
                e.getMessage());
        System.out.print("Test: User enters a list command with a wrong parameter\nStatus: Passed\n");
    }

    /**
     * User enters an invalid date as argument.
     */
    @Test
    public void invalidDateTest() {
        String input = "list /date aaa";

        Exception e = assertThrows(RimsException.class, () -> {
            parserUnderTest.parseInput(input);
        });

        assertEquals("Please enter a valid day / time.", e.getMessage());
        System.out.print("Test: User enters an invalid date as argument.\nStatus: Passed\n");
    }

    /**
     * Valid list item test.
     * 
     * @throws ParseException
     * @throws RimsException
     */
    @Test
    public void validItemListTest() throws RimsException, ParseException {
        String input="list /item testobject";
        parserUnderTest.parseInput(input);
        System.out.print("Test: valid list item command.\nStatus: Passed\n");
    }
}