package cube;

import cube.exception.CubeException;
import cube.model.food.FoodList;
import cube.storage.StorageManager;
import cube.util.FileUtilJson;
import javafx.application.Application;
import javafx.stage.Stage;

import cube.ui.MainWindow;

public class MainApp extends Application {

    private StorageManager storageManager;
    private FileUtilJson storage;
    private FoodList foodList;

    public void init(String filePath) {
        storage = new FileUtilJson(filePath);

        try {
            storageManager = storage.load();
            foodList = storageManager.getFoodList();
        } catch (CubeException e) {
            foodList = new FoodList();
            storageManager = new StorageManager();
        }
    }

    @Override
    public void start(Stage stage) {
        //MainWindow mwc = new MainWindow(stage);
        init("data");
        MainWindow mwc = new MainWindow(stage, storageManager, storage, foodList);

        mwc.initComponents();
        mwc.show();
    }
}
