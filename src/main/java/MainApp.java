import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import optix.Optix;
import optix.ui.windows.MainWindow;

import java.io.File;

public class MainApp extends Application {

    private Optix optix;

    @Override
    public void init() throws Exception {
        super.init();

        File currentDir = new File(System.getProperty("user.dir"));
        File filePath = new File(currentDir.toString() + "\\src\\main\\data");

        optix = new Optix(filePath);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = new MainWindow(optix);
        Scene scene = new Scene(root);

        primaryStage.setTitle("Optix");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }
}
