package places;

import exceptions.FarmioException;
import org.json.simple.JSONObject;

public class CowFarm extends Farm {
    private final String JSON_KEY_MILK = "milk";
    private final String JSON_KEY_COW = "cow";
    private final String JSON_KEY_FULLCOW = "fullcow";

    private int milk;
    private int cow;
    private int fullCow;
    private int dayToMilk;

    /**
     * Checks whether the cowfarm currently has Milk.
     * @return true if milkfarm has milk, false if milkfarm has no milk.
     */
    public boolean hasMilk() {
        return milk > 0;
    }

    /**
     * Checks whether the cowfarm currently has cow.
     * @return true if cowfarm has cow, false if cowfarm has no cow.
     */
    public boolean hasCow() {
        return cow > 0;
    }

    /**
     * Checks whether the cowfarm currently has fullCow.
     * @return true if cowfarm has fullCow, false if cowfarm has no fullCow.
     */
    public boolean hasFullCow() {
        return fullCow > 0;
    }

    /**
     * Intializes wheatfarm to have no assets.
     */
    public CowFarm() {
        cow = 0;
        milk = 0;
        fullCow = 0;
    }

    /**
     * Constructor for cowfarm when it is loaded from a save file.
     * @param obj the Json Objects from the load file.
     * @throws FarmioException if there are errors in the input.
     */
    public CowFarm(JSONObject obj) throws FarmioException {
        try {
            this.cow = (int) (long) obj.get(JSON_KEY_COW);
            this.milk = (int) (long) obj.get(JSON_KEY_MILK);
            this.fullCow = (int) (long) obj.get(JSON_KEY_FULLCOW);

        } catch (Exception e) {
            throw new FarmioException("Game save corrupted!");
        }
    }

    /**
     * Gets number of cows.
     * @return cow as the amount of cows in the cowfarm.
     */
    public int getCow() {
        return cow;
    }

    /**
     * Gets number units of milk.
     * @return milk as the amount of milk in the cowfarm.
     */
    public int getMilk() {
        return milk;
    }

    /**
     * Gets number of fullCow.
     * @return fullCow as the amount of fullCow in the cowfarm.
     */
    public int getFullCow() {
        return fullCow;
    }

    /**
     * Increases number of cows.
     */
    public void buyCow() {
        cow += 1;
    }

    /**
     * Changes all fullCow to Cow.
     * Adds the amount of milk attained
     * Resets seeds to 0.
     */
    public void milkCow() {
        milk += fullCow;
        cow = fullCow;
        fullCow = 0;
        dayToMilk = 0;
    }

    /**
     * Increase amount of money user has and reset cow to 0.
     * @return the amount of money earned.
     */
    public int sellCow() {
        int earned = cow * Market.PRICE_OF_COW;
        cow = 0;
        return earned;
    }


    /**
     * Increases the amount of money user has and reset milk to 0.
     * @return the amount of money earned.
     */
    @Override
    public int sell() {
        int earned = milk * Market.PRICE_OF_MILK;
        milk = 0;
        return earned;
    }

    @Override
    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        return obj;
    }
}
