package ui;


import executor.command.CommandTagList;
import executor.command.CommandType;
import executor.command.CommandWeather;
import org.junit.jupiter.api.Test;
import storage.StorageManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandWeatherTest {

    @Test
    void execute() {
        StorageManager storageManager = new StorageManager();
        CommandWeather c1 = new CommandWeather("weather");
        c1.execute(storageManager);
        assertEquals(CommandType.WEATHER, c1.getCommandType());
        CommandWeather c2 = new CommandWeather("weather");
        c2.execute(storageManager);
        String error1 = "Please enter in either of the following format : \n"
                + "1. weather /until now \n"
                + "2. weather /until later \n"
                + "3. weather /until tomorrow \n";
        assertTrue(c2.getInfoCapsule().getOutputStr().contains(error1));
    }
}
