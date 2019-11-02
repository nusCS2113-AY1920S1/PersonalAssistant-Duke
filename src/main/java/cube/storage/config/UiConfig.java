/**
 * Config object containing configurations for UI.
 *
 * @author kuromono
 */
package cube.storage.config;

public class UiConfig {

    private double windowHeight;
    private double windowWidth;

    /**
     * Default constructor.
     * Creates a new instance of UiConfig class with default settings.
     */
    public UiConfig() {
        this.windowHeight = 750;
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
