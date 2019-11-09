/**
 * Testing for ConfigCommand utilities
 *
 * @author kuromono
 */

package cube.logic.command;

import cube.logic.command.exception.CommandException;
import cube.logic.command.util.CommandResult;
import cube.model.ModelManager;
import cube.storage.StorageManager;
import cube.storage.config.LogConfig;
import cube.storage.config.UiConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConfigCommandTest {

    private static ModelManager model;
    private static StorageManager storage;

    private static UiConfig uiConfig;
    private static LogConfig logConfig;

    /**
     * Initializes variables used for the tests.
     */
    @BeforeAll
    public static void initVariables() {
        model = new ModelManager();
        storage = new StorageManager();

        // Sets UiConfig variables: WindowHeight=888.88, WindowWidth=888.88
        uiConfig = new UiConfig();
        uiConfig.setWindowHeight(888.88);
        uiConfig.setWindowWidth(888.88);

        // Sets LogConfig variables: LogLevel: OFF, MaxFileSize: 8MB, MaxFileCount: 8
        logConfig = new LogConfig();
        logConfig.setMaxFileSizeMB(8);
        logConfig.setCurrentLogLevel("OFF");
        logConfig.setMaxFileCount(8);
    }

    /**
     * Resets ModelManager & StorageManager for every test run.
     */
    @AfterEach
    public void clearVariables() {
        model = new ModelManager();
        storage = new StorageManager();
    }

    /**
     * Test changing the configuration values for UiConfig.
     */
    @Test
    @Order(1)
    public void change_ui_config() throws CommandException {
        ConfigCommand command = new ConfigCommand(ConfigCommand.ConfigType.UI, uiConfig);
        CommandResult result = command.execute(model, storage);
        UiConfig resultUiConfig = storage.getConfig().getUiConfig();

        CommandResult expectedResult = new CommandResult(String.format(ConfigCommand.MESSAGE_SUCCESS, ConfigCommand.ConfigType.UI.toString()));
        assertEquals(result, expectedResult);
        assertEquals(resultUiConfig, uiConfig);
    }

    /**
     * Test changing the configuration values for LogConfig.
     */
    @Test
    @Order(2)
    public void change_log_config() throws CommandException {
        ConfigCommand command = new ConfigCommand(ConfigCommand.ConfigType.LOG, logConfig);
        CommandResult result = command.execute(model, storage);
        LogConfig resultLogConfig = storage.getConfig().getLogConfig();

        CommandResult expectedResult = new CommandResult(String.format(ConfigCommand.MESSAGE_SUCCESS, ConfigCommand.ConfigType.LOG.toString()));
        assertEquals(result, expectedResult);
        assertEquals(resultLogConfig, logConfig);
    }

    /**
     * Test viewing all modified variables in config.
     */
    @Test
    @Order(3)
    public void view_config() throws CommandException {
        ConfigCommand command = new ConfigCommand(ConfigCommand.ConfigType.VIEW);
        CommandResult result = command.execute(model, storage);

        CommandResult expectedResult = new CommandResult(ConfigCommand.MESSAGE_VIEW);
        assertEquals(result, expectedResult);
    }
}
