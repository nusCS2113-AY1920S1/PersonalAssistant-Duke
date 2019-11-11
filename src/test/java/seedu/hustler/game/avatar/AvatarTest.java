package seedu.hustler.game.avatar;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import seedu.hustler.game.shop.items.ShopItem;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 *  Test class for Avatar, contains the unit testing for Avatar and integrated
 *  testing with Stats, Level, and ShopItem.
 */
public class AvatarTest {

    /**
     * The base avatar we will be working with.
     */
    private static Avatar base;

    /**
     * The base level that avatar contains.
     */
    private static Level levelStub;

    /**
     * The base stat that avatar contains.
     */
    private static Stats statsStub;

    @BeforeAll
    static void start() {
        // We will be setting the Level stub's level and xp as 1 and 0 respectively.
        levelStub = new LevelStub(1, 0);
        // We will be setting the Stats stub's damage, defence, stamina, speed as 1, 1, 3, 1 respectively.
        statsStub = new StatsStub();
        // The base avatar will not equip any weapon nor armor.
        base = new Avatar("Avatar", levelStub, statsStub, Optional.empty(), Optional.empty());
    }

    /**
     * Tests if the name change is functional. Also tests the getter function.
     */
    @Test
    public void checkNameChangeCheck() {
        // Will change the name as expected.
        assertEquals("test", base.setName("test").getName());
    }

    /**
     * Tests if the xp gain will chain all the functions needed.
     */
    @Test
    public void gainXpCheck() {
        // Will increment the xp by 1.
        Level additionalXpStub = new LevelStub(1, 1);
        assertEquals(additionalXpStub.getXp(), base.gainXp().getLevel().getXp());
    }

    /**
     * Tests if the toTxt string is working as intended.
     */
    @Test
    public void toTxtCheck() {
        String toTxt;

        // Will output the string as expected as toTxt.
        toTxt = "Name Avatar\nLevel 1 0\nStats 1 1 3 1\nWeapon Optional.empty\nArmor Optional.empty";
        assertEquals(toTxt, base.toTxt());

        // Will output the string with the changed name.
        toTxt = "Name test\nLevel 1 0\nStats 1 1 3 1\nWeapon Optional.empty\nArmor Optional.empty";
        assertEquals(toTxt, base.setName("test").toTxt());
    }

    /**
     * Tests if the avatar are equal to each other if they consists the variables with the same values.
     */
    @Test
    public void equals() {
        Avatar otherBase = new Avatar("Avatar", levelStub, statsStub, Optional.empty(), Optional.empty());

        // Assert true as they are the same object.
        assertEquals(base, base);

        // Assert true as they hold the same values.
        assertEquals(base, otherBase);

        // Assert false as they have different names.
        assertNotEquals(base.setName("test"), otherBase);

        // Assert false as they have different xp.
        assertNotEquals(base.gainXp(), otherBase);
    }

    public static class LevelStub implements Level {

        int level;
        int xp;

        public LevelStub(int level, int xp) {
            this.level = level;
            this.xp = xp;
        }

        @Override
        public int getLevel() {
            return level;
        }

        @Override
        public int getXp() {
            return xp;
        }

        @Override
        public Level increaseXp() {
            return new LevelStub(this.level, this.xp + 1);
        }

        @Override
        public Level upLevel() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canLevel() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int xpNeeded(int level) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String toTxt() {
            return this.level + " " + this.xp;
        }
    }

    public static class StatsStub implements Stats {

        @Override
        public int getDamage() {
            return 1;
        }

        @Override
        public int getDefence() {
            return 1;
        }

        @Override
        public int getStamina() {
            return 3;
        }

        @Override
        public int getSpeed() {
            return 1;
        }

        @Override
        public Stats upStats(int level) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getStats(Optional<ShopItem> weapon, Optional<ShopItem> armor) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String toTxt() {
            return getDamage() + " " + this.getDefence() + " " + this.getStamina() + " " + this.getSpeed();
        }
    }
}
