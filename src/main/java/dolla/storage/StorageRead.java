package dolla.storage;

import dolla.Time;
import dolla.parser.MainParser;
import dolla.task.Debt;
import dolla.task.Entry;
import dolla.task.Limit;
import dolla.task.Record;
import dolla.ui.Ui;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class StorageRead extends Storage {

    /**
     * TODO: UPDATE THIS!
     * Returns a duke.task.TaskList containing tasks from a save file (if available, else create one),
     * else returns an empty duke.task.TaskList.
     * <p>
     *     This method first tries to read from ./data/duke.txt. For every line
     *     in the file, this method checks the type of task stored, and then converts
     *     them into a task accordingly and stores into a duke.task.TaskList.
     * </p>
     * <p>
     *     If ./data/duke.txt is not found, an empty duke.task.TaskList will be returned.
     * </p>
     * <p>
     *     If an error occurs while reading from ./data/duke.txt, exit duke.
     * </p>
     */
    public static void load() {

        Ui.showWelcome();
        ArrayList<String> msg = new ArrayList<String>();

        try {
            InputStream inputStream = Storage.class.getResourceAsStream("/dolla.txt");
            InputStreamReader isReader = new InputStreamReader(inputStream);
            BufferedReader inStream = new BufferedReader(isReader);
            //            FileReader inFile = new FileReader("./data/dolla.txt");
            //            BufferedReader inStream = new BufferedReader(inFile);
            msg.add("Your save data has been loaded :)");
            String inLine;

            while ((inLine = inStream.readLine()) != null) {
                String[] inArray = inLine.split(" \\| ");
                int numOfElements = inArray.length;
                String type = inArray[0];
                Record newRecord = null;
                switch (type) {
                case "I": //check if there is a tag
                    if (numOfElements == 4) {
                        newRecord = new Entry("income", stringToDouble(inArray[1]), inArray[2],
                                Time.readDate(inArray[3])); //income [AMOUNT] [DESCRIPTION] /on [DATE]
                    }
                    /*
                    else if (numOfElements == 5) {
                        newRecord = new income(inArray[1],inArray[2],Time.readDate(inArray[3]),inArray[4]);
                        //income [AMOUNT] [DESCRIPTION] /on [DATE] /tag [TAG]
                    }
                     */
                    break;
                case "E": //check if there is a tag
                    if (numOfElements == 4) {
                        newRecord = new Entry("expense", stringToDouble(inArray[1]), inArray[2],
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
                case "BU": //must include 3 additional word, every,for and tag
                    //if (inArray[3].equals("every")) {
                    newRecord = new Limit("budget",stringToDouble(inArray[1]), inArray[2]);
                    /*
                    }
                    else if (inArray[3].equals("for")) {
                        newRecord = new budgetFor(inArray[1],Time.readDate(inArray[2]));
                    } else if (inArray[3].equals("tag")) {
                        newRecord = new budgetTag(inArray[1],inArray[2]);
                    }
                     */
                    break;
                case "S":
                    newRecord = new Limit("saving", stringToDouble(inArray[1]), inArray[2]);
                    break;
                case "O":
                    newRecord = new Debt("owe", inArray[1], stringToDouble(inArray[2]), inArray[3],
                            Time.readDate(inArray[4]));
                    break;
                case"B":
                    newRecord = new Debt("borrow", inArray[1], stringToDouble(inArray[2]), inArray[3],
                            Time.readDate(inArray[4]));
                    break;
                //case"shortcut": //special case for shortcut,only one string
                //newRecord = new shortcut(inArray[1]);
                //break;
                default:
                    System.out.println("save file corrupted");
                }

                if (type.equals("I") || type.equals(("E")) || type.equals("RI") || type.equals("RE")) {
                    entries.add(newRecord);
                } else if (type.equals("BU") || type.equals("S")) {
                    limits.add(newRecord);
                } else if (type.equals("O") || type.equals("B")) {
                    debts.add(newRecord);
                } else if (type.equals("shortcut")) {
                    shortcuts.add(newRecord);
                }
                StorageWrite.save();
            }

        } catch (FileNotFoundException e) {
            msg.add("Looks like it's your first time, let me create a save file for you :)");
            createFolder();
        } catch (IOException e) { // exception handling
            System.out.println("*** there was an error reading dolla.txt ***");
            MainParser.exit(); // TODO: Find out what is supposed to happen here
        }
        Ui.printMsg(msg);
    }
}
