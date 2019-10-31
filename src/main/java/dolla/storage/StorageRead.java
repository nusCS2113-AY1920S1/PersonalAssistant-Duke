package dolla.storage;

import dolla.Time;
import dolla.parser.MainParser;
import dolla.task.Debt;
import dolla.task.Entry;
import dolla.task.Limit;
import dolla.task.Record;
import dolla.ui.StorageUi;
import dolla.ui.Ui;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
//import java.io.InputStream;
//import java.io.FileInputStream;
//import java.io.InputStreamReader;
import java.util.ArrayList;

public class StorageRead extends Storage {

    /**
     * This method will extract the data from the save file and load into the respective ArrayList.
     */
    public static void load() {

        Ui.showWelcome();
        ArrayList<String> msg = new ArrayList<String>();

        try {
            //            InputStream inputStream = Storage.class.getResourceAsStream("/dolla.txt");
            //            InputStreamReader isReader = new InputStreamReader(inputStream);
            //            BufferedReader inStream = new BufferedReader(isReader);
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
                case INCOME_TYPE: //check if there is a tag
                    if (numOfElements == 4) {
                        newRecord = new Entry(INCOME, stringToDouble(inArray[1]), inArray[2],
                                Time.readDate(inArray[3])); //income [AMOUNT] [DESCRIPTION] /on [DATE]
                    }
                    /*
                    else if (numOfElements == 5) {
                        newRecord = new income(inArray[1],inArray[2],Time.readDate(inArray[3]),inArray[4]);
                        //income [AMOUNT] [DESCRIPTION] /on [DATE] /tag [TAG]
                    }
                     */
                    break;
                case EXPENSE_TYPE: //check if there is a tag
                    if (numOfElements == 4) {
                        newRecord = new Entry(EXPENSE, stringToDouble(inArray[1]), inArray[2],
                                Time.readDate(inArray[3])); //expense [AMOUNT] [DESCRIPTION] /on [DATE]
                    }
                    break;
                /*
                else if (numOfElements == 5) {
                    newRecord = new expense(inArray[1],inArray[2],Time.readDate(inArray[3]),inArray[4]);
                    //expense [AMOUNT] [DESCRIPTION] /on [DATE] /tag [TAG]
                }
                break;
            case "RI"://no start date, check if there is a tag
                if(numOfElements == 4) {
                    newRecord = new recurringIncome(inArray[1],inArray[2],Time.readDate(inArray[3]));
                    //recurringIncome [AMOUNT] [DESCRIPTION] /on [DATE]
                } else if(numOfElements == 5) {
                    newRecord = new recurringIncome(inArray[1],inArray[2],Time.readDate(inArray[3]),inArray[4]);
                    //recurringIncome [AMOUNT] [DESCRIPTION] /on [DATE] /tag [TAG]
                }
                break;
            case "RE"://no start date, check if there is a tag
                if(numOfElements == 4) {
                    newRecord = new recurringExpanse(inArray[1],inArray[2],Time.readDate(inArray[3]));
                    //recurringExpense [AMOUNT] [DESCRIPTION] /on [DATE]
                } else if (numOfElements == 5) {
                    newRecord = new recurringExpense(inArray[1],inArray[2],Time.readDate(inArray[3]),inArray[4]);
                    //recurringExpense [AMOUNT] [DESCRIPTION] /on [DATE] /tag [TAG]
                }
                break;
                */
                case BUDGET_TYPE: //must include 3 additional word, every,for and tag
                    //if (inArray[3].equals("every")) {
                    newRecord = new Limit(BUDGET,stringToDouble(inArray[1]), inArray[2]);
                    /*
                    }
                    else if (inArray[3].equals("for")) {
                        newRecord = new budgetFor(inArray[1],Time.readDate(inArray[2]));
                    } else if (inArray[3].equals("tag")) {
                        newRecord = new budgetTag(inArray[1],inArray[2]);
                    }
                     */
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
                //case SHORTCUT: //special case for shortcut,only one string
                //newRecord = new shortcut(inArray[1]);
                //break;
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
        Ui.printMsg(msg);
    }

    private static void addToList(String type, Record newRecord) {
        if (type.equals(INCOME_TYPE) || type.equals((EXPENSE_TYPE))
                || type.equals(RECURRING_INCOME_TYPE) || type.equals(RECURRING_EXPENSE_TYPE)) {
            entries.add(newRecord);
        } else if (type.equals(BUDGET_TYPE) || type.equals(SAVING_TYPE)) {
            limits.add(newRecord);
        } else if (type.equals(OWE_TYPE) || type.equals(BUDGET_TYPE)) {
            debts.add(newRecord);
        } else if (type.equals(SHORTCUT)) {
            shortcuts.add(newRecord);
        }
    }
}
