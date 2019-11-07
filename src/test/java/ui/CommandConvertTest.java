package ui;

import executor.command.CommandConvert;
import executor.command.CommandType;
import org.junit.jupiter.api.Test;
import storage.StorageManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandConvertTest {

    @Test
    void execute() {
        StorageManager storageManager = new StorageManager();
        CommandConvert c1 = new CommandConvert("convert");
        c1.execute(storageManager);
        assertEquals(CommandType.CONVERT, c1.getCommandType());
        CommandConvert c2 = new CommandConvert("convert /from usd /to SGD");
        c2.execute(storageManager);
        String error1 = "Please enter a valid amount\n";
        assertTrue(c2.getInfoCapsule().getOutputStr().contains(error1));
        CommandConvert c3 = new CommandConvert("convert 2000 /from usd /to SGD");
        c3.execute(storageManager);
        assertTrue(c3.getInfoCapsule().getOutputStr().contains("DUKE$$$ has converted "));
        assertEquals(c3.getDescription(), "Command that converts the user input cash amount from"
                + "one currency to another and prints it on the User Interface.\n"
                + "FORMAT : convert <amount> /from <Base currency ISO e.g USD > /to <Required Currency ISO e.g SGD>");
    }

}
