package CommandTests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import rims.command.HelpCommand;
import rims.core.ResourceList;
import rims.core.Storage;
import rims.core.Ui;
import rims.exception.RimsException;
import rims.resource.Resource;

import java.io.IOException;
import java.util.ArrayList;

public class HelpCommandTest {
    private HelpCommand commandUnderTest;
    private static Ui ui;
    private static Storage storage;
    private static ResourceList listUnderTest;

    @BeforeAll
    private static void init() throws RimsException, IOException {
        ui = new Ui();
        ArrayList<Resource> emptyList = new ArrayList<Resource>();
        listUnderTest = new ResourceList(ui, emptyList);
        storage = new Storage("unitTestData/resources.txt","unitTestData/reserves.txt");
    }

    /**
     * Execution Test
     */
    @Test
    public void HelpTest() throws RimsException {

        commandUnderTest = new HelpCommand();
        commandUnderTest.execute(ui, storage, listUnderTest);
        System.out.print("Test: valid execution\nStatus passed");
    }
}
