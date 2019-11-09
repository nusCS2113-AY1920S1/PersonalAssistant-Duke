package dolla.storage;

import dolla.Time;
import dolla.parser.MainParser;
import dolla.model.Debt;
import dolla.model.Entry;
import dolla.model.Limit;
import dolla.model.Record;
import dolla.model.Bill;
import dolla.ui.StorageUi;
import dolla.ui.Ui;
import dolla.model.Shortcut;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class StorageRead extends Storage {

    /**
     * This method will extract the data from the save file and load into the respective ArrayList.
     */
    public static void load() {

        Ui.showWelcome();
        try {
            FileReader inFile = new FileReader(PATH);
            BufferedReader inStream = new BufferedReader(inFile);
            StorageUi.printStorageLoadMessage();
            String inLine;

            while ((inLine = inStream.readLine()) != null) {
                String[] inArray = inLine.split(DELIMITER);
                int numOfElements = inArray.length;
                String type = inArray[0];
                Record newRecord = null;
                switch (type) {
                case INCOME_TYPE:
                    newRecord = new Entry(INCOME, stringToDouble(inArray[1]), inArray[2],
                                Time.readDate(inArray[3])); //income [AMOUNT] [DESCRIPTION] /on [DATE]

                    break;
                case EXPENSE_TYPE:
                    newRecord = new Entry(EXPENSE, stringToDouble(inArray[1]), inArray[2],
                                Time.readDate(inArray[3])); //expense [AMOUNT] [DESCRIPTION] /on [DATE]
                    break;
                case BUDGET_TYPE:
                    newRecord = new Limit(BUDGET,stringToDouble(inArray[1]), inArray[2]);
                    break;
                case SAVING_TYPE:
                    newRecord = new Limit(SAVING, stringToDouble(inArray[1]), inArray[2]);
                    break;
                case OWE_TYPE:
                    newRecord = new Debt(OWE, inArray[1], stringToDouble(inArray[2]), inArray[3],
                                Time.readDate(inArray[4]));
                    break;
                case BORROW_TYPE:
                    newRecord = new Debt(BORROW, inArray[1], stringToDouble(inArray[2]), inArray[3],
                                Time.readDate(inArray[4]));
                    break;
                case SHORTCUT:
                    newRecord = new Shortcut(inArray[1], stringToDouble(inArray[2]),inArray[3]);
                    break;
                case BILL_TYPE:
                    ArrayList<String> temp = new ArrayList<String>();
                    String [] names = inArray[4].split(", ");
                    for (int i = 0; i < names.length; i++) {
                        temp.add(names[i]);
                    }
                    newRecord = new Bill(BILL, Integer.parseInt(inArray[1]),
                            stringToDouble(inArray[2]), temp);
                    break;
                default:
                    StorageUi.printErrorReadingSaveMessage();
                    break;
                }
                addToList(type, newRecord);
            }
            StorageWrite.save();

        } catch (FileNotFoundException e) {
            StorageUi.printCreateFolderMessage();
            createFolder();
        } catch (IOException e) { // exception handling
            StorageUi.printErrorReadingSaveMessage();
            MainParser.exit(); // TODO: Find out what is supposed to happen here
        }
    }

    private static void addToList(String type, Record newRecord) {
        if (type.equals(INCOME_TYPE) || type.equals((EXPENSE_TYPE))
                || type.equals(RECURRING_INCOME_TYPE) || type.equals(RECURRING_EXPENSE_TYPE)) {
            entries.add(newRecord);
        } else if (type.equals(BUDGET_TYPE) || type.equals(SAVING_TYPE)) {
            limits.add(newRecord);
        } else if (type.equals(OWE_TYPE) || type.equals(BORROW_TYPE)) {
            debts.add(newRecord);
        } else if (type.equals(SHORTCUT)) {
            shortcuts.add(newRecord);
        } else if (type.equals(BILL_TYPE)) {
            bills.add(newRecord);
        }
    }
}
