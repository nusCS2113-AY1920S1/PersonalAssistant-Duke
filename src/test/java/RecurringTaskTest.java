import duke.DukeContext;
import duke.command.Ui;
import duke.exception.DukeFatalException;
import duke.task.Storage;

import java.io.File;

import static org.junit.jupiter.api.Assertions.fail;

public class RecurringTaskTest {
    private DukeContext ctx;

    @BeforeClass
    public void setupCtx () {
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            dataDir.mkdir();
        }
        Ui ui = new Ui(System.in, System.out);
        try {
            ctx = new DukeContext(new Storage("data" + File.separator + "test.tsv"), ui);
            ctx.storage.writeTaskFile("");
        } catch (DukeFatalException excp) {
            fail("Could not setup storage for testing!");
        }
    }

    @AfterClass
    public void clearCtx() {
        try {
            ctx.storage.writeTaskFile("");
        } catch (DukeFatalException excp) {
            fail("Something happened to data file while cleaning up after testing!");
        }
    }
}
