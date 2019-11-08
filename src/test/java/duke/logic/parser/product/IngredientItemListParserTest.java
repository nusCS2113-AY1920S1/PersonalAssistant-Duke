package duke.logic.parser.product;

import duke.model.product.IngredientItemList;
import duke.testutil.IngredientItemListBuilder;
import org.junit.jupiter.api.Test;

import static duke.logic.parser.product.IngredientItemListParser.getIngredientsInInput;
import static duke.testutil.TypicalIngredientItems.CREAM_CHEESE_3;
import static duke.testutil.TypicalIngredientItems.EGG_2;
import static duke.testutil.TypicalIngredientItems.BUTTER_0;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class IngredientItemListParserTest {
    private static final IngredientItemList REFERENCE_LIST =
            new IngredientItemListBuilder().addAllIngredients(CREAM_CHEESE_3, EGG_2, BUTTER_0).build();



    @Test
    public void parseIngredient_standardInput_success() {
        //standard input -> return true
        assertTrue(getIngredientsInInput("[Cream cheese, 3] [Egg, 2] [Butter, 0]").listEquals
                (REFERENCE_LIST));

        //standard but with duplicate -> return true
        assertTrue(getIngredientsInInput("[Cream cheese, 3] [Cream cheese, 3] [Egg, 2] [Egg, 2] [Butter, 0]").listEquals
                (REFERENCE_LIST));
        //
        //duplicate product name, different ingredient. take behind -> return true
        assertTrue(getIngredientsInInput("[Cream cheese, 2] [Cream cheese, 3] [Egg, 2] [Butter, 0]").listEquals
                (REFERENCE_LIST));

        //duplicate product name, different ingredient. take behind -> return false
        assertFalse(getIngredientsInInput("[Cream cheese, 3] [Cream cheese, 2] [Egg, 2] [Butter, 0]").listEquals
                (REFERENCE_LIST));

        // Input with no quantity are set to default quantity 0.0 -> returns true
        assertTrue(getIngredientsInInput("[Cream cheese, 3] [Egg, 2] [Butter]").listEquals
                (REFERENCE_LIST));
        //Input with no different capitalization are standardized -> returns true
        assertTrue(getIngredientsInInput("[Cream CheeSe, 3] [eGG, 2] [bUtter, 0]").listEquals
                (REFERENCE_LIST));

        //Input with different number format -> returns true
        assertTrue(getIngredientsInInput("[Cream cheese, 3.0] [Butter, 0.0][Egg, 2.0] ").listEquals
                (REFERENCE_LIST));

        //Input with different orders -> returns true
        assertTrue(getIngredientsInInput("[Cream cheese, 3] [Butter, 0] [Egg, 2] ").listEquals
                (REFERENCE_LIST));

        //Input with spaces in between entries -> returns true
        assertTrue(getIngredientsInInput("[Cream cheese, 3]         [Butter, 0][Egg, 2] ").listEquals
                (REFERENCE_LIST));

        //Input with different name -> returns false
        assertFalse(getIngredientsInInput("[Cream_cheese, 3][Butter, 0][Egg, 2] ").listEquals
                (REFERENCE_LIST));
        //Input with different portion -> returns false
        assertFalse(getIngredientsInInput("[Cream cheese, 3][Butter, 2][Egg, 2] ").listEquals
                (REFERENCE_LIST));
    }

    @Test
    public void parseIngredient_invalidInput_throwCommandException() {
        //input with  -> return true
        //assertThrows(ParseException.class, () -> {
        //    getIngredientsInInput("-er, 2]----[Egg, 2] ");
        //});
    }
}
