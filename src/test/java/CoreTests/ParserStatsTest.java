package CoreTests;

import rims.core.Parser;
import rims.core.ResourceList;
import rims.core.Ui;
import rims.core.Storage;
import rims.command.ReturnCommand;
import rims.exception.*;
import rims.resource.Resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class ParserStatsTest {
    private static Parser parserUnderTest;
    private static Ui ui;
    private static Storage storage;
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
    public void validInputTest() throws RimsException, IOException {
        ;
    }
}