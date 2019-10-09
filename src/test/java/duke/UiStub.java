package duke;

import duke.ui.Ui;
import javafx.scene.layout.VBox;

public class UiStub extends Ui {
    public UiStub(VBox dialogContainer) {
        super(dialogContainer);
    }

    @Override
    public void show(String message) {
        System.out.println(message);
    }
}
