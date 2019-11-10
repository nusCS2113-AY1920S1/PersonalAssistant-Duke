package cube.logic.command;

import cube.logic.command.exception.CommandException;
import cube.logic.command.util.CommandResult;
import cube.model.ModelManager;
import cube.model.promotion.PromotionList;
import cube.storage.StorageManager;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListPromotionCommandTest {
    /*private class PromotionListStub extends PromotionList {

    }

    private class ModelStub extends ModelManager {
        PromotionList list = new PromotionListStub();
    } */

    @Test
    public void execute_listPromotionCommand() throws CommandException {
        PromotionList list = new PromotionList();
        ModelManager model = new ModelManager();

        ListPromotionCommand command = new ListPromotionCommand();
        CommandResult result = command.execute(model, new StorageManager());

        CommandResult expectedResult = new CommandResult(String.format(ListPromotionCommand.MESSAGE_SUCCESS, list));
        assertEquals(result, expectedResult);
    }
}
