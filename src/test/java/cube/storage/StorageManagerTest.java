/**
 * Testing for StorageManager utilities
 *
 * @author kuromono
 */

package cube.storage;

import cube.logic.parser.exception.ParserException;
import cube.model.food.FoodList;
import cube.storage.config.LogConfig;
import cube.storage.config.UiConfig;
import cube.testutil.SampleUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StorageManagerTest {

    private static StorageManager storageManager;

    /**
     * Initializes variables used for the tests.
     */
    @BeforeAll
    public static void initVariables() throws ParserException {
        storageManager = new StorageManager();
    }

    /**
     * Test Getters/Setters for ConfigStorage within StorageManager.
     */
    @Test
    public void config_storage_test() throws ParserException {
        ConfigStorage configStorage = new ConfigStorage();

        // Sets UiConfig variables: WindowHeight=888.88, WindowWidth=888.88
        UiConfig uiConfig = new UiConfig();
        uiConfig.setWindowHeight(888.88);
        uiConfig.setWindowWidth(888.88);

        // Sets LogConfig variables: LogLevel: OFF, MaxFileSize: 8MB, MaxFileCount: 8
        LogConfig logConfig = new LogConfig();
        logConfig.setMaxFileSizeMB(8);
        logConfig.setCurrentLogLevel("OFF");
        logConfig.setMaxFileCount(8);

        configStorage.setUiConfig(uiConfig);
        configStorage.setLogConfig(logConfig);
        storageManager.setConfig(configStorage);

        assertEquals(storageManager.getConfig().getUiConfig().getWindowHeight(), uiConfig.getWindowHeight());
        assertEquals(storageManager.getConfig().getUiConfig().getWindowWidth(), uiConfig.getWindowWidth());

        assertEquals(storageManager.getConfig().getLogConfig().getCurrentLogLevel(), logConfig.getCurrentLogLevel());
        assertEquals(storageManager.getConfig().getLogConfig().getMaxFileCount(), logConfig.getMaxFileCount());
        assertEquals(storageManager.getConfig().getLogConfig().getMaxFileSizeBytes(), logConfig.getMaxFileSizeBytes());
    }

    /**
     * Test Getters/Setters for FoodStorage within StorageManager.
     */
    @Test
    public void food_storage_test() throws ParserException {
        // Generates test foodlist of size 5.
        FoodList foodList = SampleUtil.generateSampleData(5);
        storageManager.storeFoodList(foodList);

        assertEquals(storageManager.getFoodList().toString(), foodList.toString());
    }
}
