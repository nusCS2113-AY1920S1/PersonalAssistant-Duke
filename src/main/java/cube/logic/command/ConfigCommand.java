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
import cube.storage.config.UiConfig;

public class ConfigCommand extends Command {
    /**
     * Use enums to specify the configuration type to be configured.
     */
    public enum ConfigType {
        UI
    }

    private ConfigType configType;
    private UiConfig uiConfig;

    private final String MESSAGE_SUCCESS = "The %1$s settings has been configured successfully.\n"
        + "Settings will be applied when you restart the program.\n";


    /**
     * Default Constructor for changing UI-related configurations.
     * @param configType Type of configuration to set.
     * @param uiConfig UiConfig object containing configuration parameters to set.
     */
    public ConfigCommand(ConfigType configType, UiConfig uiConfig) {
        this.configType = configType;
        this.uiConfig = uiConfig;
    }

    /**
     * Calls & updates the required functions for updating UiConfig values.
     * @param storage StorageManager object that contains the ConfigStorage object to be saved.
     */
    private void configureUiConfig(StorageManager storage) {
        ConfigStorage configStorage = storage.getConfig();
        configStorage.setUiConfig(uiConfig);
        storage.setConfig(configStorage);
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
        }
        return null;
    }
}
