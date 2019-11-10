package ui;

import executor.command.CommandExport;
import executor.command.CommandType;
import org.junit.jupiter.api.Test;
import storage.StorageManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandExportTest {

    @Test
    void execute() {
        StorageManager storageManager = new StorageManager();
        CommandExport c1 = new CommandExport("export");
        try {
            c1.execute(storageManager);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertEquals(CommandType.EXPORT, c1.getCommandType());

        CommandExport c2 = new CommandExport("export hahaha");
        try {
            c2.execute(storageManager);
            String error1 = "Incorrect Command but DUKE$$$ understands"
                    + " you would want to export Wallet to csv !\n";
            assertTrue(c2.getInfoCapsule().getOutputStr().contains(
                    "data.csv has been created ! Please check the project folder \n" + error1));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        CommandExport c3 = new CommandExport("export");
        try {
            c3.execute(storageManager);
            String headerMessage = "data.csv has been created ! Please check the project folder \n";
            assertTrue(c3.getInfoCapsule().getOutputStr().contains(headerMessage));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        String description = "Exports txt into CSV\n"
                + "FORMAT : export \n";
        assertEquals(CommandType.EXPORT, c3.getCommandType());
        assertEquals(description, c3.getDescription());
    }
}
