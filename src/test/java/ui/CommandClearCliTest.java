package ui;

import executor.command.CommandClearCli;
import org.junit.jupiter.api.Test;
import storage.StorageManager;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandClearCliTest {
    @Test
    void executeTest() {
        StorageManager storageManager = new StorageManager();
        CommandClearCli genericTest = new CommandClearCli("");
        genericTest.execute(storageManager);
        assertEquals(UiCode.CLEAR_CLI, genericTest.getInfoCapsule().getUiCode());
        assertEquals("Command Clear Executed.", genericTest.getInfoCapsule().getOutputStr());
    }
}
