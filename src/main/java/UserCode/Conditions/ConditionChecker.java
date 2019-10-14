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
        if (condition == BooleanConditionType.TRUE) {
            return true;
        }
        return false;
    }

    public boolean check(Comparator comparator, int val) {
        boolean valid = false;
        switch (comparator) {
            case lessThan:
                valid = market.getMoney() < val;
                break;
            case lessThanOrEquals:
                valid = market.getMoney() <= val;
                break;
            case greaterThan:
                valid = market.getMoney() > val;
                break;
            case greaterThanOrEquals:
                valid = market.getMoney() >= val;
        }
        return valid;
    }

}
