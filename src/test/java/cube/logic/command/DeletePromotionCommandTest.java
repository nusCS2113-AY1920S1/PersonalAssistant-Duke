package cube.logic.command;

import cube.logic.command.exception.CommandErrorMessage;
import cube.logic.command.exception.CommandException;
import cube.logic.command.util.CommandResult;
import cube.model.ModelManager;
import cube.model.promotion.Promotion;
import cube.model.promotion.PromotionList;
import cube.storage.StorageManager;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static cube.testutil.Assert.assertThrowEquals;

public class DeletePromotionCommandTest {
    /*
    private class PromotionListStub extends PromotionList {

    }*/

    @Test
    public void execute_valid_index() throws CommandException {
        ModelManager model = new ModelManager();
        PromotionList list = model.getPromotionList();
        Promotion toDelete = new Promotion("testName");
        list.add(toDelete);
        DeletePromotionCommand command = new DeletePromotionCommand(1, "INDEX");
        CommandResult result = command.execute(model, new StorageManager());

        PromotionList expectedList = new PromotionList();
        CommandResult expectedResult = new CommandResult(String.format(DeletePromotionCommand.MESSAGE_SUCCESS_SINGLE, toDelete, 0));
        assertEquals(model.getPromotionList(), expectedList);
        assertEquals(result, expectedResult);
    }

    @Test
    public void execute_invalid_index() {
        DeletePromotionCommand deleteTooLargeIndex = new DeletePromotionCommand(1, "INDEX");
        assertThrowEquals(CommandException.class, CommandErrorMessage.INVALID_INDEX, () -> {deleteTooLargeIndex.execute(new ModelManager(), new StorageManager());});
        DeletePromotionCommand deleteNegativeIndex = new DeletePromotionCommand(-1, "INDEX");
        assertThrowEquals(CommandException.class, CommandErrorMessage.INVALID_INDEX, () -> {deleteNegativeIndex.execute(new ModelManager(), new StorageManager());});
    }

}
