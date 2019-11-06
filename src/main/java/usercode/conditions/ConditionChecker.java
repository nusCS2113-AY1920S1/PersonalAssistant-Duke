package usercode.conditions;

import farmio.Farmio;
import exceptions.FarmioException;

public class ConditionChecker {

    public ConditionChecker() {
    }

    /**
     * Checks if the BooleanCondition is true.
     *
     * @param condition the Condition object to be checked
     * @param farmio the Farmio object
     * @return if the Condition is true
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
        if (condition == BooleanConditionType.TRUE) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the ValueCondition is true.
     *
     * @param valueConditionType ValueConditionType of the ValueCondition
     * @param comparator the comparator used to compare the asset to the user specified value
     * @param val the user specified value to check against
     * @param farmio the Farmio Object
     * @return if the Condition is true
     * @throws FarmioException if there was an error with the comparator or asset
     */
    public static boolean check(ValueConditionType valueConditionType, Comparator comparator,
                                int val, Farmio farmio) throws FarmioException {
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
            throw new FarmioException("Error validating condition!");
        }
    }

}
