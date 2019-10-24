package Places;

import Exceptions.FarmioException;
import org.json.simple.JSONObject;

public class WheatFarm extends Farm {

    private final String JSON_KEY_SEED = "seed";
    private final String JSON_KEY_SEEDLING = "seedling";
    private final String JSON_KEY_WHEAT = "wheat";
    private final String JSON_KEY_GRAIN = "grain";

    private int seeds;
    private int seedlings;
    private int wheat;
    private int grain;

    public boolean hasSeeds() {
        return seeds > 0;
    }

    public boolean hasWheat() {
        return wheat > 0;
    }

    public boolean hasGrain() {
        return grain > 0;
    }

    public WheatFarm() {
        seeds = 0;
        seedlings = 0;
        wheat = 0;
        grain = 0;
    }

    public WheatFarm(JSONObject obj) throws FarmioException {
        try {
            this.seeds = (int) (long) obj.get(JSON_KEY_SEED);
            this.seedlings = (int) (long) obj.get(JSON_KEY_SEEDLING);
            this.wheat = (int) (long) obj.get(JSON_KEY_WHEAT);
            this.grain = (int) (long) obj.get(JSON_KEY_GRAIN);
        } catch(Exception e){
            throw new FarmioException("Game save corrupted!");
        }
    }

    public int getSeeds() {
        return seeds;
    }

    public int getSeedlings() {
        return seedlings;
    }

    public int getWheat() {
        return wheat;
    }

    public int getGrain() {
        return grain;
    }

    public void buySeeds() {
        seeds += 1;
    }

    public void plantSeeds() {
        seedlings += seeds;
        seeds = 0;
    }

    public void growSeedlings() {
        wheat += seedlings;
        seedlings = 0;
    }
    public void harvestWheat() {
        grain += wheat;
        seedlings = 0;
    }
    @Override
    public int sell() {
        int earned = wheat * Market.PRICE_OF_WHEAT;
        wheat = 0;
        return earned;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put(JSON_KEY_SEED, seeds);
        obj.put(JSON_KEY_SEEDLING, seedlings);
        obj.put(JSON_KEY_WHEAT, wheat);
        obj.put(JSON_KEY_GRAIN, grain);
        return obj;
    }
}