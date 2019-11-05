package owlmoney.model.goals;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Contains details for each achievement.
 */
public class Achievement {
    private String name;
    private double amount;
    private String category;
    private Date date;

    /**
     * Creates instance of achievement.
     *
     * @param name Goal Name.
     * @param amount Goal Target Amount.
     * @param category Currently is GOALS.
     * @param date Goal targeted date.
     */
    public Achievement(String name, double amount, String category, Date date) {
        this.name = name;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    /**
     * Gets achievement name.
     *
     * @return name of achievement.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets category for achievement.
     *
     * @return category for achievement.
     */
    public String getCategory() {
        return this.category;
    }

    /**
     * Gets amount for achievement.
     *
     * @return amount for achievement.
     */
    public double getAmount() {
        return this.amount;
    }

    /**
     * Gets date for achievement.
     *
     * @return date for achievement.
     */
    public String getDate() {
        DateFormat temp = new SimpleDateFormat("dd MMMM yyyy");
        return temp.format(this.date);
    }

    public Date getAchievementDateInDateFormat() {
        return this.date;
    }
}
