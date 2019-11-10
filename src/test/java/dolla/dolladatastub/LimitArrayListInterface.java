package dolla.dolladatastub;

import dolla.model.Limit;
import dolla.model.Record;
import dolla.parser.ParserStringList;

import java.util.ArrayList;

/**
 * Allows for quick creation of an ArrayList of limits for initialising DollaData.
 */
public interface LimitArrayListInterface extends ParserStringList {

    private Limit createNewLimit1() {
        return new Limit(LIMIT_TYPE_S, 5, LIMIT_DURATION_D);
    }

    private Limit createNewLimit2() {
        return new Limit(LIMIT_TYPE_S, 50, LIMIT_DURATION_W);
    }

    private Limit createNewLimit3() {
        return new Limit(LIMIT_TYPE_B, 500, LIMIT_DURATION_M);
    }

    default ArrayList<Record> createNewLimitArrayList() {
        ArrayList<Record> newRecordList = new ArrayList<Record>();
        newRecordList.add(createNewLimit1());
        newRecordList.add(createNewLimit2());
        newRecordList.add(createNewLimit3());
        return newRecordList;
    }
}
