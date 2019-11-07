/**
 * ConfigCommand.java
 * Support commands related to stored settings configurations.
 *
 * @author kuromono
 */

package cube.logic.command;

import cube.logic.command.util.CommandResult;
import cube.model.ModelManager;
import cube.storage.ConfigStorage;
import cube.storage.StorageManager;
import cube.storage.config.LogConfig;
import cube.storage.config.UiConfig;

public class ConfigCommand extends Command {
    /**
     * Use enums to specify the configuration type to be configured.
     */
    public enum ConfigType {
        UI, LOG, VIEW
    }

    private ConfigType configType;
    private UiConfig uiConfig;
    private LogConfig logConfig;

    private final String MESSAGE_SUCCESS = "The %1$s settings has been configured successfully.\n"
        + "Settings will be applied when you restart the program.\n";

    private String MESSAGE_VIEW = "All the saved configurations are as below:\n";

    /**
     * Default Constructor for listing all configs.
     *
     * @param configType Type of configuration.
     */
    public ConfigCommand(ConfigType configType) {
        this.configType = configType;
    }

    /**
     * Default Constructor for changing UI-related configurations.
     *
     * @param configType Type of configuration to set.
     * @param uiConfig   UiConfig object containing configuration parameters to set.
     */
    public ConfigCommand(ConfigType configType, UiConfig uiConfig) {
        this.configType = configType;
        this.uiConfig = uiConfig;
    }

    /**
     * Default Constructor for changing Logging-related configurations.
     *
     * @param configType Type of configuration to set.
     * @param logConfig  LogConfig object containing configuration parameters to set.
     */
    public ConfigCommand(ConfigType configType, LogConfig logConfig) {
        this.configType = configType;
        this.logConfig = logConfig;
    }

    /**
     * Calls & updates the required functions for updating UiConfig values.
     *
     * @param storage StorageManager object that contains the ConfigStorage object to be saved.
     */
    private void configureUiConfig(StorageManager storage) {
        ConfigStorage configStorage = storage.getConfig();
        configStorage.setUiConfig(uiConfig);
        storage.setConfig(configStorage);
    }

    /**
     * Calls & updates the required functions for updating LogConfig values.
     *
     * @param storage StorageManager object that contains the ConfigStorage object to be saved.
     */
    private void configureLogConfig(StorageManager storage) {
        ConfigStorage configStorage = storage.getConfig();
        configStorage.setLogConfig(logConfig);
        storage.setConfig(configStorage);
    }

    /**
     * Lists the configurations stored in various config classes.
     *
     * @param storage StorageManager object that contains configuration values stored in ConfigStorage.
     */
    private void viewConfig(StorageManager storage) {
        ConfigStorage configStorage = storage.getConfig();
        uiConfig = configStorage.getUiConfig();
        logConfig = configStorage.getLogConfig();
        MESSAGE_VIEW += "+ UI Configurations (Only works in GUI-mode):\n";
        MESSAGE_VIEW += uiConfig.toString();
        MESSAGE_VIEW += "\n+ Logging Configurations:\n";
        MESSAGE_VIEW += logConfig.toString();
    }

    /**
     * Constructs the command result output to be shown to the user.
     */
    @Override
    public CommandResult execute(ModelManager model, StorageManager storage) {

        switch (configType) {
        case UI:
            configureUiConfig(storage);
            return new CommandResult(String.format(MESSAGE_SUCCESS, configType.toString()));
        case LOG:
            configureLogConfig(storage);
            return new CommandResult(String.format(MESSAGE_SUCCESS, configType.toString()));
        case VIEW:
            viewConfig(storage);
            return new CommandResult(MESSAGE_VIEW);
        }
        return null;
    }
}
