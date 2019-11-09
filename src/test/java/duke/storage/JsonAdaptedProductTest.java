package duke.storage;

import org.junit.jupiter.api.Test;

import static duke.testutil.TypicalProducts.CHEESE_CAKE;
import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonAdaptedProductTest {


    @Test
    void toModelType_validProductDetails_returnProduct() {
        JsonAdaptedProduct product = new JsonAdaptedProduct(CHEESE_CAKE);
        assertEquals(CHEESE_CAKE, product.toModelType());
    }
}