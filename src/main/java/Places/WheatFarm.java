package Places;

import Simulate.PlantSeedSimulation;

public class WheatFarm extends Farm {
    private int seeds = 0;
    private int greenWheat = 0;
    private int ripeWheat = 0;
    public boolean hasSeeds() {
        return seeds > 0;
    }
    public boolean hasWheat() {
        return greenWheat > 0;
    }
    public boolean hasRipened() {
        return ripeWheat > 0;
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