package Places;

import Simulate.PlantSeedSimulation;

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
        seeds = 1;
        greenWheat = 0;
        ripeWheat = 0;
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
        int earned = ripeWheat * 10;
        ripeWheat = 0;
        return earned;
    }
}