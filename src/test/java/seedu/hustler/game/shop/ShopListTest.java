package seedu.hustler.game.shop;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Optional;

public class ShopListTest {

    @Test
    public void purchaseItemsOutOfBounds() {
        assertEquals(Optional.empty(), new ShopList().buy(1000));
    }

    @Test
    public void checkFixedShopSize() {
        assertEquals(6, new ShopList().size());
    }

    @Test
    public void checkPurchasedStatus() {
        String newlyPopulatedString = "0\n0\n0\n0\n0\n0";
        assertEquals(newlyPopulatedString, new ShopList().itemsStatus());
    }





}
