package places;

import exceptions.FarmioException;
import org.json.simple.JSONObject;

public class ChickenFarm extends Farm {
    private static final String JSON_KEY_FULLCHICKEN = "fullChicken";
    private static final String JSON_KEY_EGG = "egg";
    private static final String JSON_KEY_CHICKEN = "chicken";

    private int fullChicken;
    private int chicken;
    private int egg;
    private int dayToGrow;


    /**
     * Checks whether the chickenfarm currently has eggs.
     * @return true if chickenfarm has eggs, false if chickenfarm has no eggs.
     */
    public boolean hasEgg() {
        return egg > 0;
    }

    /**
     * Checks whether the chickenfarm currently has chickens.
     * @return true if chickenfarm has chicken, false if chickenfarm has no chicken.
     */
    public boolean hasChicken() {
        return chicken > 0;
    }

    /**
     * Checks whether the chickenfarm currently has fullChicken.
     * @return true if chickenfarm has fullChicken, false if chickenfarm has no fullChicken.
     */
    public boolean hasFullChicken() {
        return fullChicken > 0;
    }

    /**
     * Intializes chickenfarm to have no assets.
     */
    public ChickenFarm() {
        chicken = 0;
        egg = 0;
        fullChicken = 0;
    }

    /**
     * Constructor for chickenfarm when it is loaded from a save file.
     * @param obj the Json Objects from the load file.
     * @throws FarmioException if there are errors in the input.
     */
    public ChickenFarm(JSONObject obj) throws FarmioException {
        try {
            this.chicken = (int) (long) obj.get(JSON_KEY_CHICKEN);
            this.egg = (int) (long) obj.get(JSON_KEY_EGG);
            this.fullChicken = (int) (long) obj.get(JSON_KEY_FULLCHICKEN);
        } catch (Exception e) {
            throw new FarmioException("Game save corrupted!");
        }
    }

    /**
     * Gets number of chicken.
     * @return chicken as the amount of chickens in the chickenfarm.
     */
    public int getChicken() {
        return chicken;
    }

    /**
     * Gets number of eggs.
     * @return eggs as the amount of eggs in the chickenfarm.
     */
    public int getEgg() {
        return egg;
    }

    /**
     * Gets number of fullChicken.
     * @return fullChicken as the amount of fullChicken in the chickenfarm.
     */
    public int getFullChicken() {
        return fullChicken;
    }


    /**
     * Increases number of seeds.
     */
    public void buyChicken() {
        chicken += 1;
    }


    /**
     * Changes all seedlings to wheat if day is more than 1.
     * Resets seedlings to 0.
     */
    public void layEggs() {
        if (chicken > 0) {
            dayToGrow++;
        }
        if (dayToGrow >= 1) {
            fullChicken += chicken;
            chicken = 0;
            dayToGrow = 0;
        }
    }

    /**
     * Changes all fullchicken to egg.
     * Resets wheat to 0.
     */
    public void collectEgg() {
        egg += fullChicken;
        fullChicken = 0;
    }

    /**
     * Increases the amount of money user has.
     * @return the amount of money earned.
     */
    @Override
    public int sell() {
        int earned = egg * Market.PRICE_OF_WHEAT;
        egg = 0;
        return earned;
    }

    @Override
    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        return obj;
    }

}