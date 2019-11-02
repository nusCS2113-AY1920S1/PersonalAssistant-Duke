package duke.logic.commands;

import duke.ModelStub;
import duke.commons.exceptions.CorruptedFileException;
import duke.commons.exceptions.FileLoadFailException;
import duke.commons.exceptions.FileNotSavedException;
import duke.logic.commands.results.CommandResultText;
import duke.model.lists.EventList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class EditCommandTest {

    @Test
    void execute() throws FileNotSavedException, FileLoadFailException {
        ModelStub modelStub = new ModelStub();
        EditCommand editCommand = new EditCommand(false, new EventList());
        CommandResultText resultText = editCommand.execute(modelStub);
        assertEquals(resultText.getMessage(), "Edit is cancelled. Changes are not saved.\n");
        assertNotEquals(resultText.getMessage(), "Edit is cancelled. Changes are not saved.");
        EditCommand editCommand1 = new EditCommand(true, new EventList());
        CommandResultText resultText1 = editCommand1.execute(modelStub);
        assertEquals(resultText1.getMessage(), "Here are the list of events:\n");
        assertNotEquals(resultText1.getMessage(), "Here are the list of events:");
    }
}
