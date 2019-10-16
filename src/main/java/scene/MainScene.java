package scene;

import Dictionary.WordBank;
import command.Command;
import command.QuizCommand;
import exception.ChangeSceneException;
import exception.WordUpException;
import javafx.scene.Scene;
import javafx.stage.Stage;
import parser.Parser;
import storage.Storage;
import ui.Ui;

public class MainScene extends NewScene {
    public MainScene(Ui ui, WordBank wordBank, Storage storage, Stage window) {
        super(ui, wordBank, storage, ui.greet(), window);
        setupHandleInput();
    }

    @Override
    public Scene getScene() {
        return scene;
    }

    @Override
    public void resolveException(WordUpException e) {
        window.setScene(new QuizScene(ui, wordBank, storage, window).getScene());
    }

    @Override
    public String getResponse(String fullCommand) throws ChangeSceneException {
        Command c = Parser.parse(fullCommand);
        if (c instanceof QuizCommand) {
            throw new ChangeSceneException();
        }
        return c.execute(ui, wordBank, storage);
    }
}
