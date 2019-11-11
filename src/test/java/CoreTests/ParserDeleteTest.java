package CoreTests;

import rims.core.Parser;
import rims.core.ResourceList;
import rims.core.Ui;
import rims.exception.*;
import rims.resource.Item;
import rims.resource.Resource;
import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

//@@author isbobby
public class ParserDeleteTest {
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
     * Tests when a user miss out item name.
     */
    @Test
    public void missingItemNameTest(){
        String input = "delete /item";
        Exception e = assertThrows(RimsException.class, () -> {
            parserUnderTest.parseInput(input);
        });
        assertEquals("Please specify the item to delete from your inventory.", e.getMessage());
        System.out.print("Test: when a user miss out item name.\nStatus: Passed\n");
    }

    /**
     * Tests Tests when a user miss out the parameter.
     */
    @Test
    public void missingParamTest(){
        String input = "delete";
        Exception e = assertThrows(RimsException.class, () -> {
            parserUnderTest.parseInput(input);
        });
        assertEquals("Please specify the resource to delete from your inventory.", e.getMessage());
        System.out.print("Test: when a user miss out the parameter.\nStatus: Passed\n");
    }
}