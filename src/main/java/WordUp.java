import dictionary.Bank;
import scene.MainScene;
import storage.Storage;
import ui.Ui;
import javafx.application.Application;
import javafx.stage.Stage;


public class WordUp extends Application {

    public Ui ui;
    public Storage storage;
    public Bank bank;
    private Stage window;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Constructor of the word up containing UI, storage, and word bank.
     */
    public WordUp() {
        ui = new Ui();
        storage = new Storage("data/wordup.txt");
        bank = new Bank(storage);
    }

    @Override
    public void start(Stage stage) {
        window = stage;

        window.setScene(new MainScene(ui, bank, storage, window).getScene());
        window.show();

        //Step 2. Formatting the window to look as expected
        window.setTitle("WordUp");
        window.setResizable(false);
        window.setMinHeight(600.0);
        window.setMinWidth(400.0);
    }
}

