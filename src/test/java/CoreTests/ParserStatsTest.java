package CoreTests;

import rims.core.Parser;
import rims.core.ResourceList;
import rims.core.Ui;
import rims.exception.*;
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
public class ParserStatsTest {
    private static Parser parserUnderTest;
    private static Ui ui;
    private static ResourceList listUnderTest;

    @BeforeAll 
    private static void init () throws RimsException {
        ui = new Ui();
        ArrayList<Resource> emptyList = new ArrayList<Resource>();
        listUnderTest = new ResourceList(ui, emptyList);
        parserUnderTest = new Parser(ui, listUnderTest);
    }

    @BeforeEach
    private void setup (){
        ;
    }

    @AfterEach
    private void cleanup (){
        ;
    }

    @Test
    public void missingTillDateTest() throws RimsException, IOException {
        String input = "stats /from";

        Exception e = assertThrows(RimsException.class, () -> {
            parserUnderTest.parseInput(input);
        });
        
        assertEquals("Please specify the date for which you want to view statistics.", e.getMessage());
        System.out.print("Test: Missing parameters in input\nStatus: Passed\n");
    }

    @Test
    public void invalidDateFormatTest() throws RimsException, IOException {
        String input = "stats /from 11/11/2020 2300 /till 14/11/2020";

        Exception e = assertThrows(RimsException.class, () -> {
            parserUnderTest.parseInput(input);
        });
        
        assertEquals("Please enter a valid day / time.", e.getMessage());
        System.out.print("Test: Invalid Dates passed as arguments\nStatus: Passed\n");
    }

    @Test
    public void validInputTest() throws RimsException, IOException, ParseException {
        String input = "stats /from 11/11/2020 2300 /till 14/11/2020 2300";
        parserUnderTest.parseInput(input);
        System.out.print("Test: Valid input\nStatus: Passed\n");
    }
}