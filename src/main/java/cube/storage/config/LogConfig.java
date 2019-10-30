/**
 * Config object containing configurations for logging.
 *
 * @author kuromono
 */
package cube.storage.config;

public class LogConfig {
    private int maxFileCount;
    private int maxFileSizeBytes;
    private String logFileName;
    private String currentLogLevel;

    /**
     * Default constructor.
     * Creates a new instance of LogConfig class with default settings.
     */
    public LogConfig() {
        this.maxFileCount = 1;
        this.maxFileSizeBytes = (int)(Math.pow(2, 20) * 10); // 10MB
        this.logFileName = "cube.log";
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

    public void setMaxFileSizeBytes(int maxFileSizeBytes) {
        this.maxFileSizeBytes = maxFileSizeBytes;
    }

    public String getLogFileName() {
        return logFileName;
    }

    public void setLogFileName(String logFileName) {
        this.logFileName = logFileName;
    }

    public String getCurrentLogLevel() {
        return currentLogLevel;
    }

    public void setCurrentLogLevel(String currentLogLevel) {
        this.currentLogLevel = currentLogLevel;
    }

}
