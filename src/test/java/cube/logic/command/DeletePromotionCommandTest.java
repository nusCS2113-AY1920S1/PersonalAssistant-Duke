package cube.logic.command;

import cube.logic.command.exception.CommandErrorMessage;
import cube.logic.command.exception.CommandException;
import cube.logic.command.util.CommandResult;
import cube.model.ModelManager;
import cube.model.promotion.Promotion;
import cube.model.promotion.PromotionList;
import cube.storage.StorageManager;
import org.junit.jupiter.api.Test;

import java.util.Date;

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

        // Add dates to promotion to prevent null exceptions.
        toDelete.setStartDate(new Date());
        toDelete.setEndDate(new Date());

        list.add(toDelete);
        DeletePromotionCommand command = new DeletePromotionCommand(1, "INDEX");
        CommandResult result = command.execute(model, new StorageManager());

        PromotionList expectedList = new PromotionList();
        CommandResult expectedResult = new CommandResult(
                String.format(DeletePromotionCommand.MESSAGE_SUCCESS_SINGLE, toDelete, 0));

        // Size should be same for both after deletion.
        assertEquals(model.getPromotionList().size(), expectedList.size());
        assertEquals(result, expectedResult);
    }

    @Test
    public void execute_invalid_index() {
        DeletePromotionCommand deleteTooLargeIndex = new DeletePromotionCommand(1, "INDEX");
        assertThrowEquals(CommandException.class,
                CommandErrorMessage.INVALID_INDEX, () -> {
            deleteTooLargeIndex.execute(new ModelManager(), new StorageManager());
        });
        DeletePromotionCommand deleteNegativeIndex = new DeletePromotionCommand(-1, "INDEX");
        assertThrowEquals(CommandException.class,
                CommandErrorMessage.INVALID_INDEX, () -> {
            deleteNegativeIndex.execute(new ModelManager(), new StorageManager());
        });
    }

}
