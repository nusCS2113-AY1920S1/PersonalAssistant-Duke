package cube;

import cube.exception.CubeException;
import cube.model.FoodList;
import cube.storage.ConfigStorage;
import cube.storage.StorageManager;
import cube.util.FileUtilJson;
import javafx.application.Application;
import javafx.stage.Stage;

import cube.ui.MainWindow;

public class MainApp extends Application {

    private StorageManager storageManager;
    private ConfigStorage configStorage;
    private FileUtilJson storage;
    private FoodList foodList;

    public void init(String filePath) {
        storage = new FileUtilJson(filePath);

        try {
            storageManager = storage.load();
            configStorage = storage.loadConfig();
            foodList = storageManager.getFoodList();
        } catch (CubeException e) {
            foodList = new FoodList();
            configStorage = new ConfigStorage();
            storageManager = new StorageManager();
        }
    }

    @Override
    public void start(Stage stage) {
        init("data");
        //stage.setMinHeight(configStorage.getWindowHeight());
        //stage.setMinWidth(configStorage.getWindowWidth());

        MainWindow mwc = new MainWindow(stage, storageManager, storage, foodList);

        mwc.initComponents();
        mwc.show();
    }
}
