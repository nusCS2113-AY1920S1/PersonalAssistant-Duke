package places;

import exceptions.FarmioException;
import org.json.simple.JSONObject;

public class WheatFarm extends Farm {

    private static final String JSON_KEY_SEED = "seed";
    private static final String JSON_KEY_SEEDLING = "seedling";
    private static final String JSON_KEY_WHEAT = "wheat";
    private static final String JSON_KEY_GRAIN = "grain";

    private int seeds;
    private int seedlings;
    private int wheat;
    private int grain;
    private int dayToGrow;

    /**
     * Checks whether the wheatfarm currently has seeds.
     * @return true if wheatfarm has seeds, false if wheatfarm has no seeds.
     */
    public boolean hasSeeds() {
        return seeds > 0;
    }

    /**
     * Checks whether the wheatfarm currently has wheat.
     * @return true if wheatfarm has wheat, false if wheatfarm has no wheat.
     */
    public boolean hasWheat() {
        return wheat > 0;
    }

    /**
     * Checks whether the wheatfarm currently has grain.
     * @return true if wheatfarm has grain, false if wheatfarm has no grain.
     */
    public boolean hasGrain() {
        return grain > 0;
    }

    /**
     * Intializes wheatfarm to have no assets.
     */
    public WheatFarm() {
        seeds = 0;
        seedlings = 0;
        wheat = 0;
        grain = 0;
    }

    /**
     * Constructor for wheatfarm when it is loaded from a save file.
     * @param obj the Json Objects from the load file.
     * @throws FarmioException if there are errors in the input.
     */
    public WheatFarm(JSONObject obj) throws FarmioException {
        try {
            this.seeds = (int) (long) obj.get(JSON_KEY_SEED);
            this.seedlings = (int) (long) obj.get(JSON_KEY_SEEDLING);
            this.wheat = (int) (long) obj.get(JSON_KEY_WHEAT);
            this.grain = (int) (long) obj.get(JSON_KEY_GRAIN);
        } catch (Exception e) {
            throw new FarmioException("Game save corrupted!");
        }
    }

    /**
     * Gets number of seeds.
     * @return seeds as the amount of seeds in the wheatfarm.
     */
    public int getSeeds() {
        return seeds;
    }

    /**
     * Gets number of seedlings.
     * @return seedlings as the amount of seedlings in the wheatfarm.
     */
    public int getSeedlings() {
        return seedlings;
    }

    /**
     * Gets number of wheat.
     * @return wheat as the amount of wheat in the wheatfarm.
     */
    public int getWheat() {
        return wheat;
    }

    /**
     * Gets number of grains.
     * @return seeds as the amount of grain in the wheatfarm.
     */
    public int getGrain() {
        return grain;
    }

    /**
     * Increases number of seeds.
     */
    public void buySeeds() {
        seeds += 1;
    }

    /**
     * Changes all seeds to seedlings.
     * Resets seeds to 0.
     */
    public void plantSeeds() {
        seedlings += seeds;
        seeds = 0;
        dayToGrow = 0;
    }

    /**
     * Changes all seedlings to wheat if day is more than 1.
     * Resets seedlings to 0.
     */
    public void growSeedlings() {
        if (seedlings > 0) {
            dayToGrow++;
        }
        if (dayToGrow >= 1) {
            wheat += seedlings;
            seedlings = 0;
            dayToGrow = 0;
        }
    }

    /**
     * Changes all wheat to grains.
     * Resets wheat to 0.
     */
    public void harvestWheat() {
        grain += wheat;
        wheat = 0;
    }

    /**
     * Increases the amount of money user has.
     * @return the amount of money earned.
     */
    @Override
    public int sell() {
        int earned = grain * Market.PRICE_OF_WHEAT;
        grain = 0;
        return earned;
    }

    @Override
    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put(JSON_KEY_SEED, seeds);
        obj.put(JSON_KEY_SEEDLING, seedlings);
        obj.put(JSON_KEY_WHEAT, wheat);
        obj.put(JSON_KEY_GRAIN, grain);
        return obj;
    }
}