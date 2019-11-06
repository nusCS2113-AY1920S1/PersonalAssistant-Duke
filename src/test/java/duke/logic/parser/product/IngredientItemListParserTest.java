package duke.logic.parser.product;

import duke.model.product.IngredientItemList;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertTrue;

public class IngredientItemListParserTest {
    private IngredientItemList correctList = IngredientItemListParser.getIngredientsInInput("[Cheese, 3] "
        + "[Egg, 4]");


    @Test
    public void parseIngredient_standardInput_success() {
        assertTrue(IngredientItemListParser.getIngredientsInInput("[Cheese, 3] [Egg, 4]").listEquals(correctList));
    }

    @Test void parseIngredient_inputWithoutQuantity_success() {
        assertTrue(IngredientItemListParser.getIngredientsInInput("[Cheese] [Egg]")
                .listEquals(IngredientItemListParser.getIngredientsInInput("[Cheese,0] [Egg,0]")));
    }
}
