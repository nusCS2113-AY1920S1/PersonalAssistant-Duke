package seedu.hustler.game.avatar;

import seedu.hustler.game.shop.items.ShopItem;
import java.util.Optional;

/**
 * The Stats interface that AvatarStats must inherit in order
 * to properly increment it's stats.
 */
public interface Stats extends Convertible {

    /**
     * Gets the damage.
     * @return the damage stat value.
     */
    public int getDamage();

    /**
     * Gets the defence.
     * @reutrn the defence stat value.
     */
    public int getDefence();

    /**
     * Gets the speed.
     * @return the speed stat value.
     */
    public int getSpeed();

    /**
     * Gets the stamina.
     * @return the stamina stat value.
     */
    public int getStamina();

    /**
     * Increases the stats based on the current level.
     * @param level the current level of the avatar.
     * @return the updated stats after increment.
     */
    public Stats upStats(int level);

    /**
     * Obtain the stats to print out.
     * @param weapon the weapon equipped, if any.
     * @param armor the armor equipped, if any.
     * @return the String consisting of the stats.
     */
    public String getStats(Optional<ShopItem> weapon, Optional<ShopItem> armor);
}
