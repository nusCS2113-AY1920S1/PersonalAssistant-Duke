package UserCode.Conditions;

import Places.ChickenFarm;
import Places.CowFarm;
import Places.WheatFarm;

public class ConditionChecker {
    public static boolean check(Condition condition, WheatFarm wheatFarm, ChickenFarm chickenFarm, CowFarm cowFarm) {
        if (condition == Condition.hasSeeds) {
            return wheatFarm.hasSeeds();
        }
        return false;
    }
}
