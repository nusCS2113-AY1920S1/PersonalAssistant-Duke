package UserCode.Conditions;

import Farmio.Farmio;
import FarmioExceptions.FarmioException;
import Places.ChickenFarm;
import Places.CowFarm;
import Places.Market;
import Places.WheatFarm;

public class ConditionChecker {

    public ConditionChecker() {
    }

    public static boolean check(BooleanConditionType condition, Farmio farmio) {
        if (condition == BooleanConditionType.hasSeeds) {
            return farmio.getFarmer().getWheatFarm().hasSeeds();
        }
        if (condition == BooleanConditionType.TRUE) {
            return true;
        }
        return false;
    }

    public static boolean check(ValueConditionType valueConditionType, Comparator comparator, int val, Farmio farmio) throws FarmioException {
        int assetValue = 0;
        switch (valueConditionType) {
            case money:
                assetValue = farmio.getFarmer().getMoney();
                break;
        }
        System.out.println("comparing " + assetValue + " against " + val + " with comparator " + comparator.name());
        switch (comparator) {
            case lessThan:
                return assetValue < val;
            case lessThanOrEquals:
                return assetValue <= val;
            case greaterThan:
                return assetValue > val;
            case greaterThanOrEquals:
                return assetValue >= val;
        }
        throw new FarmioException("Error validating condition!");
    }

}
