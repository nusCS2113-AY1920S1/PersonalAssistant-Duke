package duketest.command;

import duke.command.AddDoAfterCommand;
import duke.exceptions.DukeException;

import java.util.ArrayList;
import java.util.List;

import temp.storage.FileHandling;
import duke.tasks.TaskList;
import duke.ui.Ui;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddDoAfterCommandTest {
    private final TaskList taskList = new TaskList();
    private final Ui ui = new Ui();
    private final FileHandling storage = new FileHandling("storeData.txt");

    @Test
    public void checkValidCommand() {
        List<String> splitInput = new ArrayList<>();
        splitInput.add("do-after");
        AddDoAfterCommand obj = new AddDoAfterCommand(splitInput);

        assertThrows(DukeException.class,() ->
                obj.execute(taskList, ui, storage));
        splitInput.add("return book");
    }

    @Test
    public void checkValidFormat() {
        List<String> splitInput = new ArrayList<>();
        splitInput.add("do-after");
        splitInput.add("return-book");
        splitInput.add("exams");
        AddDoAfterCommand obj = new AddDoAfterCommand(splitInput);
        assertThrows(DukeException.class,() ->
                obj.execute(taskList, ui, storage));
    }
}
