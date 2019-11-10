import rims.core.Parser;
import rims.core.ResourceList;
import rims.core.Ui;
import rims.core.Storage;
import rims.command.ReturnCommand;
import rims.exception.*;
import rims.resource.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class ParserReturnTest {
    private static Ui ui;
    private static Storage storage;
    private static ResourceList listUnderTest;
    private static Parser parserUnderTest;

    /**
     * Initialize the necessary classes before each test.
     */
    @BeforeEach
    private void init () throws RimsException {
        ui = new Ui();
        ArrayList<Resource> emptyList = new ArrayList<Resource>();
        listUnderTest = new ResourceList(ui, emptyList);
        parserUnderTest = new Parser(ui,listUnderTest);
    }

    @Test
    private void missingIdTest (){
        ui.printLine();
        ui.print("Missing ID Test");
        String input = "return /id";
        Exception e = assertThrows(RimsException.class, () -> {
            parserUnderTest.parseInput(input);
        });

        assertEquals("", e.getMessage());
        ui.print("Passed");
        ui.printLine();
    }
}