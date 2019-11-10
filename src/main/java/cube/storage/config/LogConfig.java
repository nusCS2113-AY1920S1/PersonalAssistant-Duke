/**
 * Config object containing configurations for logging.
 *
 * @author kuromono
 */

package cube.storage.config;

public class LogConfig {
    private int maxFileCount;
    private int maxFileSizeBytes;
    private String currentLogLevel;

    /**
     * Default constructor.
     * Creates a new instance of LogConfig class with default settings.
     */
    public LogConfig() {
        this.maxFileCount = 1;
        this.maxFileSizeBytes = (int)(Math.pow(2, 20) * 10); // 10MB
        this.currentLogLevel = "INFO";
    }

    public int getMaxFileCount() {
        return maxFileCount;
    }

    public void setMaxFileCount(int maxFileCount) {
        this.maxFileCount = maxFileCount;
    }

    public int getMaxFileSizeBytes() {
        return maxFileSizeBytes;
    }

    public void setMaxFileSizeMB(int maxFileSizeMB) {
        this.maxFileSizeBytes = (int)(Math.pow(2, 20) * maxFileSizeMB);
    }

    public String getCurrentLogLevel() {
        return currentLogLevel;
    }

    public void setCurrentLogLevel(String currentLogLevel) {
        this.currentLogLevel = currentLogLevel;
    }

    @Override
    public String toString() {
        String result = "";
        result += String.format("%1$s = %2$s\n", "maxFileCount", maxFileCount);
        result += String.format("%1$s = %2$s\n", "maxFileSizeBytes", maxFileSizeBytes);
        result += String.format("%1$s = %2$s\n", "currentLogLevel", currentLogLevel);
        return result;
    }
}
