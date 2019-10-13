package UserCode.Conditions;

import Places.ChickenFarm;
import Places.CowFarm;
import Places.Market;
import Places.WheatFarm;

public class ConditionChecker {
    WheatFarm wheatFarm;
    ChickenFarm chickenFarm;
    CowFarm cowFarm;
    Market market;


    public ConditionChecker(WheatFarm wheatFarm, ChickenFarm chickenFarm, CowFarm cowFarm, Market market) {
        this.wheatFarm = wheatFarm;
        this.chickenFarm = chickenFarm;
        this.cowFarm = cowFarm;
        this.market = market;
    }

    public boolean check(BooleanConditionType condition) {
        if (condition == BooleanConditionType.hasSeeds) {
            return wheatFarm.hasSeeds();
        }
        return false;
    }

    public boolean check(boolean isGreater, int val) {
        return (isGreater) ? (market.getMoney() >= val) : (market.getMoney() <= val);
    }
}
