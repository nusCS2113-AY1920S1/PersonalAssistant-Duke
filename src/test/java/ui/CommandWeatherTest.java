package ui;


import executor.command.CommandTagList;
import executor.command.CommandType;
import executor.command.CommandWeather;
import org.junit.jupiter.api.Test;
import storage.StorageManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandWeatherTest {

    @Test
    void execute() {
        StorageManager storageManager = new StorageManager();
        CommandWeather c1 = new CommandWeather("weather");
        c1.execute(storageManager);
        assertEquals(CommandType.WEATHER, c1.getCommandType());





    }


}
