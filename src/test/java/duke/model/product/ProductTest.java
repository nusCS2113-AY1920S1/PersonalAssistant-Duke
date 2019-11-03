package duke.model.product;

import duke.logic.message.ProductMessageUtils;
import duke.testutil.ProductBuilder;
import org.junit.jupiter.api.Test;

import static duke.logic.command.product.ProductCommandTestUtil.VALID_COST;
import static duke.logic.command.product.ProductCommandTestUtil.VALID_NAME;
import static duke.testutil.Assert.assertThrows;
import static duke.testutil.TypicalProducts.CHEESE_CAKE;
import static duke.testutil.TypicalProducts.EGG_TART;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductTest {

    @Test
    public void createNewProduct_emptyName_throwsParseExceptionWithMessage() {
        assertThrows(IllegalArgumentException.class, ProductMessageUtils.MESSAGE_MISSING_PRODUCT_NAME,
                () -> new ProductBuilder("").build());
    }

    @Test
    public void equal() {
        // same object -> returns true
        assertTrue(CHEESE_CAKE.equals(CHEESE_CAKE));

        // null -> returns false
        assertFalse(CHEESE_CAKE.equals(null));

        // Different cost but same name -> return true
        Product editedCheese_cake = new ProductBuilder(CHEESE_CAKE).withIngredientCost(VALID_COST).build();
        assertTrue(CHEESE_CAKE.equals(editedCheese_cake));

        // Different name but with all other info same -> return false
        editedCheese_cake = new ProductBuilder(CHEESE_CAKE).withName(VALID_NAME).build();
        assertFalse(CHEESE_CAKE.equals(editedCheese_cake));

        // Different product
        assertFalse(CHEESE_CAKE.equals(EGG_TART));
    }
}
