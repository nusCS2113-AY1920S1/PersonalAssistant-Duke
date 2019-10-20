package wallet.model.record;

import java.util.ArrayList;

public class RecordList {
    /**
     * Stores the current list of records of the user.
     */
    private ArrayList<Record> recordList;

    public RecordList() {
        this.recordList = new ArrayList<Record>();
    }

    /**
     * Constructs a new recordList object.
     *
     * @param recordList The list of records to be added.
     */
    public RecordList(ArrayList<Record> recordList) {
        this.recordList = recordList;
    }

    /**
     * Returns the list of records in the recordList.
     *
     * @return The list of records.
     */
    public ArrayList<Record> getRecordList() {
        return recordList;
    }

    /**
     * Add the given record into the recordList.
     *
     * @param record The record to be added.
     */
    public void addRecord(Record record) {
        recordList.add(record);
    }

    /**
     * Retrieve the record at the given index of the recordList.
     *
     * @param index The index of the record in the recordList.
     * @return The record at the given index.
     */
    public Record getRecord(int index) {
        return recordList.get(index);
    }

    /**
     * Modify the value of the record at the given index in the recordList.
     *
     * @param index  The index of the record in the list.
     * @param record The record with modified values.
     */
    public void modifyRecord(int index, Record record) {
        recordList.set(index, record);
    }

    /**
     * Removes the record at the given index of the record list.
     *
     * @param index The index of the record in the list.
     */
    public void deleteRecord(int index) {
        recordList.remove(index);
    }

    /**
     * Get the current number of records in the recordList.
     *
     * @return The number of records in the list.
     */
    public int getRecordListSize() {
        return recordList.size();
    }
}
