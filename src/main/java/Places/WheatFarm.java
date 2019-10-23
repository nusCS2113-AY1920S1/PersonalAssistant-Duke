package Places;

import org.json.simple.JSONObject;

public class WheatFarm extends Farm {
    private int seeds;
    private int greenWheat;
    private int ripeWheat;
    public boolean hasSeeds() {
        return seeds > 0;
    }
    public boolean hasWheat() {
        return greenWheat > 0;
    }
    public boolean hasRipened() {
        return ripeWheat > 0;
    }

    public WheatFarm() {
        seeds = 0;
        greenWheat = 0;
        ripeWheat = 0;
    }

    public WheatFarm(JSONObject obj){
        this.seeds = (int) (long) obj.get("seeds");
        this.greenWheat = (int) (long) obj.get("wheat_green");
        this.ripeWheat = (int) (long) obj.get("wheat_ripe");
    }

    public int getSeeds() {
        return seeds;
    }

    public int getGreenWheat() {
        return greenWheat;
    }

    public int getRipeWheat() {
        return ripeWheat;
    }

    public void buySeeds() {
        seeds += 1;
    }

    public void plantSeeds() {
        greenWheat += seeds;
        seeds = 0;
    }
    public void harvestWheat() {
        ripeWheat += greenWheat;
        greenWheat = 0;
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
        obj.put("wheat_green", greenWheat);
        obj.put("wheat_ripe", ripeWheat);
        return obj;
    }
}