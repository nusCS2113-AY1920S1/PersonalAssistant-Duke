package cube.testutil;

import cube.logic.parser.ParserUtil;
import cube.logic.parser.exception.ParserException;
import cube.model.food.Food;
import cube.model.food.FoodList;

public class SampleUtil {

    /**
     * Generates some sample Food Products for testing.
     *
     * @param NUM_OF_PRODUCTS Number of sample Food Products to store in Foodlist.
     * @return Generated FoodList of size NUM_OF_PRODUCTS
     */
    public static FoodList generateSampleData(int NUM_OF_PRODUCTS) throws ParserException {
        FoodList foodList = new FoodList();

        for (int i = 0; i < NUM_OF_PRODUCTS; i += 1) {
            int testFoodIndex = i + 1;
            Food testFood = new Food("Food_" + testFoodIndex);
            testFood.setType("food");
            testFood.setPrice(testFoodIndex);
            testFood.setCost(i);
            testFood.setStock(5000);
            testFood.setExpiryDate(ParserUtil.parseStringToDate("31/12/2020"));

            foodList.add(testFood);
        }

        return foodList;
    }
}
