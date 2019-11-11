package Command;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import rims.command.HelpCommand;
import rims.command.ReserveCommand;
import rims.core.ResourceList;
import rims.core.Storage;
import rims.core.Ui;
import rims.exception.RimsException;
import rims.resource.Resource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HelpCommandTest {
    private HelpCommand commandUnderTest;
    private static Ui ui;
    private static Storage storage;
    private static ResourceList listUnderTest;

    @BeforeAll
    private static void init() throws RimsException {
        ui = new Ui();
        ArrayList<Resource> emptyList = new ArrayList<Resource>();
        listUnderTest = new ResourceList(ui, emptyList);
    }

    @Test
    public void HelpTest() throws RimsException {

        commandUnderTest = new HelpCommand();
        Exception e = assertThrows(RimsException.class, () -> {
            commandUnderTest.execute(ui, storage, listUnderTest);
        });

        assertEquals("COMMANDS CURRENTLY SUPPORTED BY RIMS:\n"+
                "add - add a new resource to inventory"+
                "delete - delete an existing resource from inventory"+
                "loan - loan out an item from now till your desired future date"+
                "reserve - reserve an item between two future dates"+
                "return - return a loan or reservation"+
                "list - see all resources and current reservations"+
                "\t" + "list /item - see all loans and future reservations of a particular item"+
                "\t" + "list /room - see all loans and future reservations of a particular room"+
                "\t" + "list /date - see all future reservations on a particular date"+
                "deadlines - view all currently active loans and reservations"+
                "stats - view loan and reservation statistics"+
                "undo - undo the last command that modified inventory data", e.getMessage());
        System.out.print("Test 1: passed");
    }
}
