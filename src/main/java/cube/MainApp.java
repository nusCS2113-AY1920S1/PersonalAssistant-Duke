package cube;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import cube.ui.MainWindow;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) {
        MainWindow mwc = new MainWindow(stage);
        mwc.initComponents();
        mwc.show();
    }
}
