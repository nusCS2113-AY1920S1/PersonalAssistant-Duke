package seedu.hustler.game.avatar;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import seedu.hustler.game.shop.items.ShopItem;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 *  Test class for AvatarStats. Has unit testing for AvatarStats and
 *  integration testing with ShopItem.
 */
public class AvatarStatsTest {

    /**
     * The base AvatarStats to test with.
     */
    private static AvatarStats base;

    /**
     * The ShopItem stub to be used in the implementations.
     */
    private static Optional<ShopItem> equipmentStub;

    /**
     * Initialize a base AvatarStats for tests to work with.
     */
    @BeforeAll
    static void start() {
        // Base stats of damage, defence, stamina and speed are 1, 1, 3, 1 respectively.
        base = new AvatarStats();
        // Stats increment of damage, defence, stamina and speed are all 10 respectively.
        equipmentStub = Optional.of(new EquipmentStub());
    }

    /**
     * Tests if the given stats of a newly constructed stats are correct. Also tests if
     * the getter functions are correct.
     */
    @Test
    public void newStatsCheck() {
        // If stats are the baseline stats, return true.
        assertEquals(1, base.getDamage());
        assertEquals(1, base.getDefence());
        assertEquals(3, base.getStamina());
        assertEquals(1, base.getSpeed());
    }

    /**
     * Tests if the given constructed stats are correct.
     */
    @Test
    public void givenStatsCheck() {
        AvatarStats constructed = new AvatarStats(10, 10, 10, 10);

        // If stats are as given, return true.
        assertEquals(10, constructed.getDamage());
        assertEquals(10, constructed.getDefence());
        assertEquals(10, constructed.getStamina());
        assertEquals(10, constructed.getSpeed());

        // If stats are not of baseline stats, return false.
        assertNotEquals(base.getDamage(), constructed.getDamage());
        assertNotEquals(base.getDefence(), constructed.getDefence());
        assertNotEquals(base.getStamina(), constructed.getStamina());
        assertNotEquals(base.getSpeed(), constructed.getSpeed());
    }

    /**
     * Tests if the given stats are correct in the txt format.
     */
    @Test
    public void toTxtCheck() {
        AvatarStats constructedAvatarStats = new AvatarStats(10, 10, 10, 15);

        // If txt is as given, return true.
        assertEquals("1 1 3 1", base.toTxt());
        assertEquals("10 10 10 15", constructedAvatarStats.toTxt());
    }

    /**
     * Tests if increment of the stat line is correct. Will use toTxt function to
     * simplify checks instead of getter function.
     */
    @Test
    public void checkUpStatsValue() {
        // Checks if stats increment of damage, defence, stamina and speed are +2, +1, +2, +1 respectively.
        assertEquals("3 2 5 2", base.upStats(2).toTxt());

        // Checks if stats increment of damage, defence, stamina and speed are +1, +2, +2, +1 respectively.
        assertEquals("2 3 5 2", base.upStats(3).toTxt());

        // Checks if stats increment of damage, defence, stamina and speed are +3, +3, +4, +2 respectively.
        assertEquals("4 4 7 3", base.upStats(2).upStats(3).toTxt());
    }

    /**
     * Tests if the AvatarStats class actually implements immutability.
     */
    @Test
    public void immutabilityCheck() {
        // Should still return the base stat values.
        base.upStats(1000);
        assertEquals(new AvatarStats(), base);

        // Should still return the base Stat values.
        base.upStats(1).upStats(1).upStats(1);
        assertEquals(new AvatarStats(), base);

        // Supports chaining when creating a new instance.
        AvatarStats upgradedAvatarStats = new AvatarStats(3, 2, 5, 2);
        assertEquals(upgradedAvatarStats, base.upStats(2));
    }

    /**
     * Tests if the getting stats using the ShopItem interface is correct.
     */
    @Test
    public void statsWithShopItemCheck() {
        String statsStr;

        // Should give the same String as the base if there are no items equipped.
        statsStr = "Damage: 1\nDefence: 1\nStamina: 3\nSpeed: 1";
        assertEquals(statsStr, base.getStats(Optional.empty(), Optional.empty()));

        //Should give an additional Damage increment of 10 if a weapon type ShopItem is equipped.
        statsStr = "Damage: 11\nDefence: 1\nStamina: 3\nSpeed: 1";
        assertEquals(statsStr, base.getStats(equipmentStub, Optional.empty()));

        //Should give an additional Defence and Stamina increment of 10 if an armor type ShopItem is equipped.
        statsStr = "Damage: 1\nDefence: 11\nStamina: 13\nSpeed: 1";
        assertEquals(statsStr, base.getStats(Optional.empty(), equipmentStub));

        //Should give an additional Damage, Defence, Stamina increment of 10 if both are equipped.
        statsStr = "Damage: 11\nDefence: 11\nStamina: 13\nSpeed: 1";
        assertEquals(statsStr, base.getStats(equipmentStub, equipmentStub));
    }

    /**
     * Checks if same stats will return true.
     */
    @Test
    public void equals() {
        AvatarStats otherBase = new AvatarStats();

        // If same object, returns true.
        assertEquals(base, base);

        // If same stat line, returns true.
        assertEquals(base, otherBase);

        // If different stat line, returns false.
        assertNotEquals(base, base.upStats(2));
        assertNotEquals(base, otherBase.upStats(2));
    }

    /**
     * Initializes an equipment stub to be able to use the method getStats in the AvatarStats class.
     */
    public static class EquipmentStub implements ShopItem {

        @Override
        public int getCost() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Boolean isPurchased() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getType() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ShopItem setIsPurchased(boolean isPurchased) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Boolean isSameType(ShopItem other) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int getDamageIncr() {
            return 10;
        }

        @Override
        public int getDefenceIncr() {
            return 10;
        }

        @Override
        public int getStaminaIncr() {
            return 10;
        }

        @Override
        public boolean canPurchase(int points) {
            throw new AssertionError("This method should not be called.");
        }
    }
}
