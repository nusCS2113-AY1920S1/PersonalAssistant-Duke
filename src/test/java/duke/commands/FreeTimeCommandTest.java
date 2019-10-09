package duke.commands;

import duke.UiStub;
import duke.commons.exceptions.DukeException;
import duke.storage.Storage;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.Test;

class FreeTimeCommandTest {

    @Test
    void execute() throws DukeException {
        UiStub ui = new UiStub(new VBox());
        Storage storage = new Storage("tasks.txt", ui);
        FreeTimeCommand freeTimeCommand = new FreeTimeCommand(1);
        freeTimeCommand.execute(ui, storage);
    }
}
