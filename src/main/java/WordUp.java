import Dictionary.WordBank;
import scene.MainScene;
import storage.Storage;
import ui.Ui;
import javafx.application.Application;
import javafx.stage.Stage;


public class WordUp extends Application{

    public Ui ui;
    public Storage storage;
    public WordBank wordBank;
    private Stage window;
    public static void main(String[] args) {
        launch(args);
    }

    public WordUp() {
        ui = new Ui();
        storage = new Storage("C:\\Users\\user\\gitclones\\main\\src\\data\\wordup.txt");
        wordBank = new WordBank(storage);
    }

    @Override
    public void start(Stage stage) {
        window = stage;

        window.setScene(new MainScene(ui, wordBank, storage, window).getScene());
        window.show();

        //Step 2. Formatting the window to look as expected
        window.setTitle("WordUp");
        window.setResizable(false);
        window.setMinHeight(600.0);
        window.setMinWidth(400.0);
    }
}

