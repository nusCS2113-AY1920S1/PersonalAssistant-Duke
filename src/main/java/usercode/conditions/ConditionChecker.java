package usercode.conditions;

import farmio.Farmio;
import exceptions.FarmioException;

public class ConditionChecker {

    public ConditionChecker() {
    }

    /**
     * Checks the condition.
     * @param condition something
     * @param farmio something
     * @return
     */
    public static boolean check(BooleanConditionType condition, Farmio farmio) {
        if (condition == BooleanConditionType.hasSeeds) {
            return farmio.getFarmer().getWheatFarm().hasSeeds();
        }
        if (condition == BooleanConditionType.hasWheat) {
            return farmio.getFarmer().getWheatFarm().hasWheat();
        }
        if (condition == BooleanConditionType.hasGrain) {
            return farmio.getFarmer().getWheatFarm().hasGrain();
        }
        if (condition == BooleanConditionType.hasGold) {
            return farmio.getFarmer().hasGold();
        }
        if (condition == BooleanConditionType.TRUE) {
            return true;
        }
        return false;
    }

    /**
     * Something.
     * @param valueConditionType something
     * @param comparator something
     * @param val something
     * @param farmio something
     * @return
     * @throws FarmioException something
     */
    public static boolean check(ValueConditionType valueConditionType, Comparator comparator, int val, Farmio farmio) {
        //throws FarmioException {
        int assetValue = 0;
        switch (valueConditionType) {
        case gold:
            assetValue = farmio.getFarmer().getGold();
            break;
        default:
        }
        switch (comparator) {
        case lessThan:
            return assetValue < val;
        case lessThanOrEquals:
            return assetValue <= val;
        case greaterThan:
            return assetValue > val;
        case greaterThanOrEquals:
            return assetValue >= val;
        default:
            return false; //not sure what the default should be
        }
        //throw new FarmioException("Error validating condition!");
    }

}
