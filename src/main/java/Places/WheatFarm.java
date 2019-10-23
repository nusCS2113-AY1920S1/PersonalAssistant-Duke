package Places;

import org.json.simple.JSONObject;

public class WheatFarm extends Farm {
    private int seeds;
    private int seedlings;
    private int ripeWheat;
    private int grain;
    public boolean hasSeeds() {
        return seeds > 0;
    }
    public boolean hasWheat() {
        return grain > 0;
    }
    public boolean hasRipened() {
        return ripeWheat > 0;
    }

    public WheatFarm() {
        seeds = 0;
        seedlings = 0;
        ripeWheat = 0;
        grain = 0;
    }

    public WheatFarm(JSONObject obj){
        this.seeds = (Integer) obj.get("seeds");
        this.seedlings = (Integer) obj.get("wheat_green");
        this.ripeWheat = (Integer) obj.get("wheat_ripe");
        this.grain = (Integer) obj.get("grain");
    }

    public int getSeeds() {
        return seeds;
    }

    public int getGreenWheat() {
        return seedlings;
    }

    public int getRipeWheat() {
        return ripeWheat;
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
        ripeWheat += seedlings;
        seedlings = 0;
    }
    public void harvestWheat() {
        grain += ripeWheat;
        seedlings = 0;
    }
    @Override
    public int sell() {
        int earned = ripeWheat * Market.PRICE_OF_WHEAT;
        ripeWheat = 0;
        return earned;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("seeds", seeds);
        obj.put("wheat_green", seedlings);
        obj.put("wheat_ripe", ripeWheat);
        return obj;
    }
}