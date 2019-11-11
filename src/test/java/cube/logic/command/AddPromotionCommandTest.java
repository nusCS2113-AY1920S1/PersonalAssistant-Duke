package cube.logic.command;

import cube.logic.command.exception.CommandErrorMessage;
import cube.logic.command.exception.CommandException;
import cube.logic.command.util.CommandResult;
import cube.model.ModelManager;
import cube.model.food.Food;
import cube.model.food.FoodList;
import cube.model.promotion.Promotion;
import cube.model.promotion.PromotionList;
import cube.storage.StorageManager;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static cube.testutil.Assert.assertThrowEquals;

public class AddPromotionCommandTest {
    @Test
    public void execute_promotionAcceptedByModel() throws CommandException {
        ModelManager model = new ModelManager();
        StorageManager storage = new StorageManager();
        Food food = new Food("testName");
        Promotion promotion = new Promotion("testName");
        AddPromotionCommand command = new AddPromotionCommand(promotion);

        // Fixes added to test
        promotion.setStartDate(new Date());
        promotion.setEndDate(new Date());
        food.setPrice(88);
        FoodList foodList = new FoodList();
        foodList.add(food);
        model.setFoodList(foodList);

        CommandResult result = command.execute(model, storage);

        CommandResult expectedResult = new CommandResult(
                String.format(AddPromotionCommand.MESSAGE_SUCCESS, promotion,
                        model.getPromotionList().size()), false, false);
        PromotionList expectedList = new PromotionList();
        expectedList.add(promotion);


        assertEquals(model.getPromotionList().toString(), expectedList.toString());
        assertEquals(result, expectedResult);
    }

    @Test
    public void execute_duplicatedPromotion_throws_PromotionExist() {
        ModelManager model = new ModelManager();
        StorageManager storage = new StorageManager();
        PromotionList list = model.getPromotionList();
        Promotion promotion = new Promotion("testName");
        list.add(promotion);
        AddPromotionCommand command = new AddPromotionCommand(promotion);

        // Fixes added to test
        Food food = new Food("testName");
        promotion.setStartDate(new Date());
        promotion.setEndDate(new Date());
        food.setPrice(88);
        FoodList foodList = new FoodList();
        foodList.add(food);
        model.setFoodList(foodList);

        assertThrowEquals(CommandException.class,
                CommandErrorMessage.PROMOTION_ALREADY_EXISTS, () -> {
            command.execute(model, storage);
        });
    }
}
