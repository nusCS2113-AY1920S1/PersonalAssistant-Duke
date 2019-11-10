package seedu.hustler.game.avatar;

import seedu.hustler.game.shop.items.ShopItem;
import seedu.hustler.game.shop.items.armors.Armor;
import seedu.hustler.game.shop.items.weapons.Weapon;
import java.util.Optional;

/**
 * The avatar which contains information and statistics which is dependent
 * on the productivity of the user.
 */
public class Avatar implements Convertible {

    /**
     * The name of the avatar.
     */
    private final String name;

    /**
     * Level of the avatar.
     */
    private final Level level;

    /**
     * Stats of the avatar.
     */
    private final Stats stats;

    /**
     * The equipped weapon of the avatar, if any.
     */
    private Optional<ShopItem> weapon;

    /**
     * The equipped armor of the avatar, if any.
     */
    private Optional<ShopItem> armor;

    /**
     * Default initialization of the level and stat.
     */
    public Avatar() {
        this.name = "Avatar";
        this.level = new AvatarLevel();
        this.stats = new AvatarStats();
        this.weapon = Optional.empty();
        this.armor = Optional.empty();
    }

    /**
     * Constructs the avatar instance with the name, level and stat with the equipment
     * of the avatar, if any.
     * @param avatarLevel the level of the avatar.
     * @param avatarStats the statistics of the avatar.
     * @param weapon the weapon equipped by the avatar, if any.
     * @param armor the armor equipped by the avatar, if any.
     */
    public Avatar(String name, Level avatarLevel, Stats avatarStats, Optional<ShopItem> weapon, Optional<ShopItem> armor) {
        this.name = name;
        this.level = avatarLevel;
        this.stats = avatarStats;
        this.weapon = weapon;
        this.armor = armor;
    }

    /**
     * Sets the name for the avatar.
     * @param preferredName the new name to update to the avatar.
     * @return the new instance of the avatar with the updated name.
     */
    public Avatar setName(String preferredName) {
        return new Avatar(preferredName, this.level, this.stats, this.weapon, this.armor);
    }

    /**
     * Gets the name for the avatar.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Increases avatar xp by 1.
     * @return the new instance of the avatar with the updated xp.
     */
    public Avatar gainXp() {
        Level newLevel = new AvatarLevel(this.level.getLevel(), this.level.getXp()).increaseXp();
        return new Avatar(this.name, newLevel, this.stats, this.weapon, this.armor);
    }

    /**
     * Equips the shop item to the User's avatar.
     * @param equipment the equipment to be equipped.
     * @return the avatar with the updated items.
     */
    public Avatar equip(ShopItem equipment) {
        if (equipment.getType().equals("Weapon")) {
            this.weapon = Optional.of(equipment);
        } else if (equipment.getType().equals("Armor")) {
            this.armor = Optional.of(equipment);
        }
        return this;
    }

    /**
     * Checks if the avatar can level up.
     * @return true if avatar can level up; false if otherwise.
     */
    public Boolean canLevel() {
        return this.level.canLevel();
    }

    /**
     * Completes the levelling up by increasing the level and stats of the avatar.
     * @return the new instance of the avatar with the updated Level and Stats.
     */
    public Avatar levelUp() {
        Level newLevel = new AvatarLevel(this.level.getLevel(), this.level.getXp()).upLevel();
        Stats newStats = new AvatarStats((AvatarStats) this.stats).upStats(newLevel.getLevel());
        return new Avatar(this.name, newLevel, newStats, this.weapon, this.armor);
    }

    @Override
    public String toString() {
        String equipment = (weapon.isEmpty() && armor.isEmpty()) ? "" : ("\n\t------ Equipped ------"
            + (weapon.map(x -> "\n\t[ " + x.toString() + "]").orElse(""))
                + (armor.map(x -> "\n\t[ " + x.toString() + "]").orElse("")));
        return this.name + ", " + this.level.toString() + "\n"
            + this.stats.getStats(weapon, armor) + "\n" + equipment;
    }

    @Override
    public String toTxt() {
        return "Name " + this.name + "\n"
            + "Level " + this.level.toTxt() + "\n"
            + "Stats " + this.stats.toTxt() + "\n"
            + "Weapon " + this.weapon.toString() + "\n"
            + "Armor " + this.armor.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this ||
                other instanceof Avatar && this.toTxt().equals(((Avatar) other).toTxt());
    }
}
