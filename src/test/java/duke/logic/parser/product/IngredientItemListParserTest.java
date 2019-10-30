package duke.logic.parser.product;

import duke.model.product.IngredientItemList;

public class IngredientItemListParserTest {
    private IngredientItemList correctList = IngredientItemListParser.getIngredientsInInput("[Cheese, 3] "
        + "[Egg, 4]");


    //@Test
    //public void parseIngredient_standardInput_success() {
    //    assertTrue(IngredientItemListParser.getIngredientsInInput("[Cheese, 3] [Egg, 4]").listEquals(correctList));
    //}
}
