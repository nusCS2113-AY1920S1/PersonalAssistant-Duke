/**
 * Object to store user configurable options.
 *
 * @author kuromono
 */

package cube.storage;

import cube.storage.config.ReminderConfig;
import cube.storage.config.UiConfig;
import cube.storage.config.LogConfig;

public class ConfigStorage {
    private UiConfig uiConfig;
    private ReminderConfig reminderConfig;
    private LogConfig logConfig;

    /**
     * Default constructor.
     * Creates a new instance of ConfigStorage class with default settings.
     */
    public ConfigStorage() {
        this.uiConfig = new UiConfig();
        this.reminderConfig = new ReminderConfig();
        this.logConfig = new LogConfig();
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

    /**
     * Getter for LogConfig.
     * @return LogConfig object containing logger configurations.
     */
    public LogConfig getLogConfig() {
        return logConfig;
    }

    /**
     * Setter for LogConfig.
     * @param logConfig The LogConfig object that contains logger configurations to be set.
     */
    public void setLogConfig(LogConfig logConfig) {
        this.logConfig = logConfig;
    }
}
