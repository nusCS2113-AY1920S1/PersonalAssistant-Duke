package cube;

import cube.exception.CubeException;
import cube.storage.StorageManager;
import cube.ui.MainWindow;
import cube.util.FileUtilJson;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    private StorageManager storageManager;
    private FileUtilJson<StorageManager> storage;

    public void init(String filePath) {

        storageManager = new StorageManager();
        storage = new FileUtilJson<>(filePath, "cube.json", storageManager);

        try {
            storageManager = storage.load();
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
