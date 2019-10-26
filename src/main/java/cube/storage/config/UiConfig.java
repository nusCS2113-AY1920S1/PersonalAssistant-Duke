package cube.storage.config;

public class UiConfig {

    private double windowHeight;
    private double windowWidth;

    /**
     * Default constructor.
     * Creates a new instance of ConfigStorage class with default settings.
     */
    public UiConfig() {
        this.windowHeight = 600;
        this.windowWidth = 450;
    }

    /**
     * Constructor that takes in 2 arguments.
     * Creates a new instance of ConfigStorage class with supplied settings.
     */
    public UiConfig(int windowHeight, int windowWidth) {
        this.windowHeight = windowHeight;
        this.windowWidth = windowWidth;
    }

    /**
     * Retrieves the user configured window height for JavaFX window.
     * @return Window Height.
     */
    public double getWindowHeight() {
        return windowHeight;
    }


    public void setWindowHeight(double windowHeight) {
        this.windowHeight = windowHeight;
    }

    /**
     * Retrieves the user configured window width for JavaFX window.
     * @return Window Width.
     */
    public double getWindowWidth() {
        return windowWidth;
    }


    public void setWindowWidth(double windowWidth) {
        this.windowWidth = windowWidth;
    }
}
