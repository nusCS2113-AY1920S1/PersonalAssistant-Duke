package cube.storage;

public class ConfigStorage {
    private double windowHeight;
    private double windowWidth;

    public ConfigStorage() {
        this.windowHeight = 600;
        this.windowWidth = 450;
    }

    public ConfigStorage(int windowHeight, int windowWidth) {
        this.windowHeight = windowHeight;
        this.windowWidth = windowWidth;
    }

    public double getWindowHeight() {
        return windowHeight;
    }

    public double getWindowWidth() {
        return windowWidth;
    }
}
