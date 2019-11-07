/**
 * Config object containing configurations for UI.
 *
 * @author kuromono
 */
package cube.storage.config;

public class UiConfig {

    private static final double MIN_WINDOW_HEIGHT = 600;
    private static final double MIN_WINDOW_WIDTH = 600;
    private static double MAX_WINDOW_HEIGHT = 1000;
    private static double MAX_WINDOW_WIDTH = 1000;
    private double windowHeight;
    private double windowWidth;

    /**
     * Default constructor.
     * Creates a new instance of UiConfig class with default settings.
     */
    public UiConfig() {
        this.windowHeight = 600;
        this.windowWidth = 600;
    }

    /**
     * Constructor that takes in 2 arguments.
     * Creates a new instance of UiConfig class with supplied settings.
     */
    public UiConfig(int windowHeight, int windowWidth) {
        this.windowHeight = windowHeight;
        this.windowWidth = windowWidth;
    }

    public static double getMinWindowHeight() {
        return MIN_WINDOW_HEIGHT;
    }

    public static double getMinWindowWidth() {
        return MIN_WINDOW_WIDTH;
    }

    public static double getMaxWindowHeight() {
        return MAX_WINDOW_HEIGHT;
    }

    public static void setMaxWindowHeight(double maxWindowHeight) {
        MAX_WINDOW_HEIGHT = maxWindowHeight;
    }

    public static double getMaxWindowWidth() {
        return MAX_WINDOW_WIDTH;
    }

    public static void setMaxWindowWidth(double maxWindowWidth) {
        MAX_WINDOW_WIDTH = maxWindowWidth;
    }

    public double getWindowHeight() {
        return windowHeight;
    }

    public void setWindowHeight(double windowHeight) {
        this.windowHeight = windowHeight;
    }

    public double getWindowWidth() {
        return windowWidth;
    }

    public void setWindowWidth(double windowWidth) {
        this.windowWidth = windowWidth;
    }

    @Override
    public String toString() {
        String result = "";
        result += String.format("%1$s = %2$s\n", "windowHeight", windowHeight);
        result += String.format("%1$s = %2$s\n", "windowWidth", windowWidth);
        return result;
    }
}
