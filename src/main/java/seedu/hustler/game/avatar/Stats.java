package seedu.hustler.game.avatar;

import seedu.hustler.game.shop.items.ShopItem;
import java.util.Optional;

public interface Stats extends Convertible {

    public int getDamage();
    public int getDefence();
    public int getSpeed();
    public int getStamina();
    public Stats upStats(int level);
    public String getStats(Optional<ShopItem> weapon, Optional<ShopItem> armor);
}
