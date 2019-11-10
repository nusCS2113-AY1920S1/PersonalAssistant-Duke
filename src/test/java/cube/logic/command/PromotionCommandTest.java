package cube.logic.command;

import cube.logic.command.exception.CommandErrorMessage;
import cube.logic.command.exception.CommandException;
import cube.logic.command.util.CommandResult;
import cube.model.ModelManager;
import cube.model.food.Food;
import cube.model.promotion.Promotion;
import cube.model.promotion.PromotionList;
import cube.storage.StorageManager;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static cube.testutil.Assert.assertThrowEquals;

public class PromotionCommandTest {
    @Test
    public void execute_promotionAcceptedByModel() throws CommandException {
        ModelManager model = new ModelManager();
        StorageManager storage = new StorageManager();
        Food food = new Food("testName");
        Promotion promotion = new Promotion("testName");
        PromotionCommand command = new PromotionCommand(promotion);
        CommandResult result = command.execute(model, storage);

        CommandResult expectedResult = new CommandResult(String.format(PromotionCommand.MESSAGE_SUCCESS, promotion, model.getPromotionList().size()), false, false);
        PromotionList expectedList = new PromotionList();
        expectedList.add(promotion);

        assertEquals(model.getPromotionList(), expectedList);
        assertEquals(result, expectedResult);
    }

    @Test
    public void execute_duplicatedPromotion_throws_PromotionExist() {
        ModelManager model = new ModelManager();
        StorageManager storage = new StorageManager();
        PromotionList list = model.getPromotionList();
        Promotion promotion = new Promotion("testName");
        list.add(promotion);
        PromotionCommand command = new PromotionCommand(promotion);

        assertThrowEquals(CommandException.class, CommandErrorMessage.PROMOTION_ALREADY_EXISTS, () -> {command.execute(model, storage); });
    }
}
