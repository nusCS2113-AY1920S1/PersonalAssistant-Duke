package duchess.commands;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.AddGradeCommand;
import duchess.logic.commands.AddModuleCommand;
import duchess.model.Grade;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddGradeCommandTest {
    private final Storage storage = new Storage("data.json");
    private final Store store = new Store();
    private final Ui ui = new Ui();

    @Test
    public void validCommand_executes() throws DuchessException {
        new AddModuleCommand("Discrete Math", "CS1231").execute(store, ui, storage);
        AddGradeCommand validCommand = new AddGradeCommand(
                14, 30, 23, "midterm", "CS1231");
        validCommand.execute(store, ui, storage);
        Grade addedGrade = store.findModuleByCode("CS1231").get().getGrades().get(0);
        assertEquals(14, addedGrade.getMarks());
        assertEquals(30, addedGrade.getMaxMarks());
        assertEquals(23, addedGrade.getWeightage());
        assertEquals(true, addedGrade.getIsComplete());
        assertTrue(addedGrade.getDescription().equals("midterm"));
    }
}
