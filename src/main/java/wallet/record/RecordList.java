package wallet.record;

import wallet.ui.Ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecordList {
    /**
     * Stores the current list of records of the user
     */
    private ArrayList<Record> recordList;

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
     * Add the given record into the recordList
     *
     * @param record The record to be added.
     */
    public void addRecord(Record record) {
        recordList.add(record);
    }

    /**
     * Retrieve the record at the given index of the recordList
     *
     * @param index The index of the record in the recordList.
     * @return The record at the given index.
     */
    public Record getRecord(int index) {
        return recordList.get(index);
    }

    /**
     * Modify the value of the record at the given index in the recordList
     *
     * @param index  The index of the record in the list
     * @param record The record with modified values
     */
    public void modifyRecord(int index, Record record) {
        recordList.set(index, record);
    }

    /**
     * Removes the record at the given index of the record list.
     *
     * @param index The index of the record in the list
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

    /**
     * Creates and populate a corresponding task object given its type.
     *
     * @param type        The type of task to be created
     * @param description The description of the task
     * @return The task object with its corresponding values
     */
    public Record createRecord(String type, String description) {
        String[] info;

        if (description.length() == 0) {
            System.out.println("☹ OOPS!!! The description of " + type + " cannot be empty");
            return null;
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HHmm");

            }

         catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("☹ OOPS!!! The date/time of a " + type + " cannot be empty");
        } /*catch (ParseException e) {
            System.out.println("☹ OOPS!!! The format of date/time is \"dd/mm/yyyy hhmm\"");
        }*/
        return null;
    }
}

    /*
    //B-Tentative Scheduling: Create Tentative Event (does not extend Event class)
    public Task createTentativeEvent(String description) {

        //TODO: Shorten the code
        ArrayList<Date> possibleDates = new ArrayList<Date>();

        if (description.length() == 0) {
            System.out.println("☹ OOPS!!! The description of tentative event cannot be empty");
            return null;
        }

        System.out.println("Key in a possible date and hit enter. Key in ':done' to finish.");
        Ui getDates = new Ui();
        String inputDate = getDates.readLine();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HHmm");

        while (!inputDate.equals(":done")) {

            try {

                Date test = sdf.parse(inputDate);
                possibleDates.add(test);
                System.out.println("Key in a possible date and hit enter. Key in ':done' to finish.");
                inputDate = getDates.readLine();


            } catch (ParseException e) {
                System.out.println("☹ OOPS!!! The format of date/time is \"dd/mm/yyyy hhmm\" !");
                inputDate = getDates.readLine();
                continue;
            }

        }

        if (possibleDates.size() > 0) {
            return new Tentative(description, possibleDates);
        }
        return null;
    }

    public Task updateTentative(Tentative t) {
        System.out.println("Select which date you want for this event: ");
        ArrayList<Date> possibleDates = t.getPossibleDates();
        for(Date d: possibleDates){
            String formatDate = new SimpleDateFormat("dd/MM/yyyy HHmm").format(d);
            System.out.println(Integer.toString(possibleDates.indexOf(d) + 1) + "." + d);
        }

        Ui getDates = new Ui();
        try {
            Integer selected = Integer.parseInt(getDates.readLine());
            String selectedDate = new SimpleDateFormat("dd/MM/yyyy HHmm").format(possibleDates.get(selected-1));
            Task newEvent = createTask("event", t.getDescription() + " /at " + selectedDate);
            return newEvent;

        } catch (IndexOutOfBoundsException e){
            System.out.println("☹ OOPS!!! Wrong Index!");
        }
        return null;
    }
}
*/
