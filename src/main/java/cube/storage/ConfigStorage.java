/**
 * Object to store user configurable options.
 *
 * @author kuromono
 */
package cube.storage;

import cube.storage.config.ReminderConfig;
import cube.storage.config.UiConfig;

public class ConfigStorage {
    private UiConfig uiConfig;
    private ReminderConfig reminderConfig;

    /**
     * Default constructor.
     * Creates a new instance of ConfigStorage class with default settings.
     */
    public ConfigStorage() {
        this.uiConfig = new UiConfig();
        this.reminderConfig = new ReminderConfig();
    }

    /**
     * Getter for UiConfig.
     * @return UiConfig object containing UI specific configurations.
     */
    public UiConfig getUiConfig() {
        return uiConfig;
    }

    /**
     * Setter for UiConfig.
     * @param uiConfig The UiConfig object that contains UI specific configurations.
     */
    public void setUiConfig(UiConfig uiConfig) {
        this.uiConfig = uiConfig;
    }

    /**
     * Getter for ReminderConfig.
     * @return ReminderConfig object containing Reminder Command specific configurations.
     */
    public ReminderConfig getReminderConfig() {
        return reminderConfig;
    }

    /**
     * Setter for ReminderConfig.
     * @param reminderConfig The ReminderConfig object that contains Reminder Command specific configurations.
     */
    public void setReminderConfig(ReminderConfig reminderConfig) {
        this.reminderConfig = reminderConfig;
    }
}
