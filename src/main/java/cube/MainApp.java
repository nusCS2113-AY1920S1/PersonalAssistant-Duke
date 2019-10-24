package cube;

import cube.exception.CubeException;
import cube.model.Food;
import cube.model.FoodList;
import cube.storage.StorageManager;
import cube.util.FileUtilJson;
import javafx.application.Application;
import javafx.stage.Stage;

import cube.ui.MainWindow;

public class MainApp extends Application {

    private StorageManager storageManager;
    private FileUtilJson storage;

    public void init(String filePath) {
        storage = new FileUtilJson(filePath);

        try {
            storageManager = storage.load();
            Food.updateRevenue(storageManager.getRevenue());
        } catch (CubeException e) {
            storageManager = new StorageManager();
        }
    }

    @Override
    public void start(Stage stage) {
        init("data");

        MainWindow mwc = new MainWindow(stage, storageManager, storage);

        mwc.initComponents();
        mwc.show();
    }
}
