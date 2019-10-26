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

    public UiConfig getUiConfig() {
        return uiConfig;
    }

    public void setUiConfig(UiConfig uiConfig) {
        this.uiConfig = uiConfig;
    }

    public ReminderConfig getReminderConfig() {
        return reminderConfig;
    }

    public void setReminderConfig(ReminderConfig reminderConfig) {
        this.reminderConfig = reminderConfig;
    }
}
