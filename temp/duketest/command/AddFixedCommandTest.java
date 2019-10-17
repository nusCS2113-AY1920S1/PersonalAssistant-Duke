package duketest.command;

import duke.command.AddDoAfterCommand;
import duke.exceptions.DukeException;
import temp.storage.FileHandling;
import duke.tasks.TaskList;
import duke.ui.Ui;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddFixedCommandTest {
    private final TaskList taskList = new TaskList();
    private final Ui ui = new Ui();
    private final FileHandling storage = new FileHandling("storeData.txt");

    @Test
    public void checkValidCommand() {
        List<String> splitInput = new ArrayList<>();
        splitInput.add("fixed");
        AddDoAfterCommand obj = new AddDoAfterCommand(splitInput);

        assertThrows(DukeException.class, () ->
                obj.execute(taskList, ui, storage));
        splitInput.add("return book");
    }

    @Test
    public void checkValidFormat() {
        List<String> splitInput = new ArrayList<>();
        splitInput.add("fixed");
        splitInput.add("read");
        splitInput.add("journal");
        splitInput.add("/needs");
        AddDoAfterCommand obj = new AddDoAfterCommand(splitInput);
        assertThrows(DukeException.class, () ->
                obj.execute(taskList, ui, storage));
    }
}
