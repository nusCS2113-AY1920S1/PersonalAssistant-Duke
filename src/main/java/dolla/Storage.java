package dolla;



import dolla.parser.MainParser;
import dolla.task.Debt;
import dolla.task.Entry;
import dolla.task.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.File;


import java.util.ArrayList;
import java.util.Arrays;

/**
 * duke.Storage handles the saving and loading of data from ./data/duke.txt,
 * as well as creating a new save file if it does not exist
 */
public class Storage {

    protected static ArrayList<Log> entries = new ArrayList<Log>();
    protected static ArrayList<Log> limits = new ArrayList<Log>();
    protected static ArrayList<Log> debts = new ArrayList<Log>();
    protected static ArrayList<Log> shortcuts = new ArrayList<Log>();

    private static double stringToDouble(String str) {
        double newDouble = 0.0;
        try {
            newDouble = Double.parseDouble(str);
        } catch (NumberFormatException e) {
            Ui.printInvalidNumberError(str);
        }
        return newDouble;
    }

    /**
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
     * @return duke.task.TaskList containing data (if any) from ./data/duke.txt.
     */
    public static void load() {
//        ArrayList<Task> list = new ArrayList<Task>();

        Ui.showWelcome();
        ArrayList<String> msg = new ArrayList<String>(Arrays.asList(
                "Hello! I'm Dolla! I help keep track of your finance!",
                "What can I do for you?"
        ));

        try {
            FileReader inFile = new FileReader("./data/duke.txt");
            BufferedReader inStream = new BufferedReader(inFile);
            msg.add("Your save data has been loaded :)");
            String inLine;

            while ((inLine = inStream.readLine()) != null) {
                String[] inArray = inLine.split(" \\| ");
                int numOfElements = inArray.length;
                String type = inArray[0];
                Log newLog = null;
//                System.out.println(inArray[0] + " ===----"+inArray[1]);
                switch(type) {
                case "I": //check if there is a tag
                    if(numOfElements == 4) {
                        newLog = new Entry("income",stringToDouble(inArray[1]),inArray[2],Time.readDateTime(inArray[3])); //income [AMOUNT] [DESCRIPTION] /on [DATE]
                    }
//                    else if (numOfElements == 5) {
//                        newLog = new income(inArray[1],inArray[2],Time.readDate(inArray[3]),inArray[4]); //income [AMOUNT] [DESCRIPTION] /on [DATE] /tag [TAG]
//                    }
                    break;
                case "E": //check if there is a tag
                    if(numOfElements == 4) {
                        newLog = new Entry("expense",stringToDouble(inArray[1]),inArray[2],Time.readDateTime(inArray[3])); //expense [AMOUNT] [DESCRIPTION] /on [DATE]
                    }
//                    else if (numOfElements == 5) {
//                        newLog = new expense(inArray[1],inArray[2],Time.readDate(inArray[3]),inArray[4]); //expense [AMOUNT] [DESCRIPTION] /on [DATE] /tag [TAG]
//                    }
//                    break;
//                case "RI"://no start date, check if there is a tag
//                    if(numOfElements == 4) {
//                        newLog = new recurringIncome(inArray[1],inArray[2],Time.readDate(inArray[3])); //recurringIncome [AMOUNT] [DESCRIPTION] /on [DATE]
//                    } else if(numOfElements == 5) {
//                        newLog = new recurringIncome(inArray[1],inArray[2],Time.readDate(inArray[3]),inArray[4]); //recurringIncome [AMOUNT] [DESCRIPTION] /on [DATE] /tag [TAG]
//                    }
//                    break;
//                case "RE"://no start date, check if there is a tag
//                    if(numOfElements == 4) {
//                        newLog = new recurringExpanse(inArray[1],inArray[2],Time.readDate(inArray[3])); //recurringExpense [AMOUNT] [DESCRIPTION] /on [DATE]
//                    } else if (numOfElements == 5) {
//                        newLog = new recurringExpense(inArray[1],inArray[2],Time.readDate(inArray[3]),inArray[4]); //recurringExpense [AMOUNT] [DESCRIPTION] /on [DATE] /tag [TAG]
//                    }
//                    break;
//                case "BU"://must include 3 additional word, every,for and tag
//                    if(inArray[3].equals("every")) {
//                        newLog = new budgetEvery(inArray[1],Time.readDate(inArray[2]));
//                    } else if (inArray[3].equals("for")) {
//                        newLog = new budgetFor(inArray[1],Time.readDate(inArray[2]));
//                    } else if (inArray[3].equals("tag")) {
//                        newLog = new budgetTag(inArray[1],inArray[2]);
//                    }
//                    break;
//                case "S":
//                    newLog = new saving(inArray[1],Time.readDate(inArray[2]));
//                    break;
                case "O":
                    newLog = new Debt("owe",inArray[1],stringToDouble(inArray[2]),inArray[3],Time.readDate(inArray[4]));
                    break;
                case"B":
                    newLog = new Debt("borrow",inArray[1],stringToDouble(inArray[2]),inArray[3],Time.readDate(inArray[4]));
                    break;
//                case"shortcut"://special case for shortcut,only one string
//                    newLog = new shortcut(inArray[1]);
//                    break;
                default:
                    System.out.println("save file corrupted");
                }

                if(type.equals("I") || type.equals(("E")) || type.equals("RI") || type.equals("RE")) {
                    entries.add(newLog);
                } else if(type.equals("BU") || type.equals("S")) {
                    limits.add(newLog);
                } else if (type.equals("O") || type.equals("B")) {
                    debts.add(newLog);
                } else if (type.equals("shortcut")) {
                    shortcuts.add(newLog);
                }
                save();
            }

        } catch (FileNotFoundException e) {
            msg.add("Looks like it's your first time, let me create a save file for you :)");
            createFolder();
        } catch (IOException e) { // exception handling
            System.out.println("*** there was an error reading duke.txt ***");
            MainParser.exit(); // TODO: Find out what is supposed to happen here
        }

        Ui.printMsg(msg);
//        return list;
    }

    public static ArrayList<Log> getEntriesFromSave() {
        return entries;
    }

    public static ArrayList<Log> getLimitsFromSave() {
        return limits;
    }

    public static ArrayList<Log> getDebtsFromSave() {

        return debts;
    }

    public static ArrayList<Log> getShortcutsFromSave() {
        return shortcuts;
    }

    public static void setEntries(ArrayList<Log> entries) {
        Storage.entries = entries;
        save();
    }

    public static void setLimits(ArrayList<Log> limits) {
        Storage.limits = limits;
        save();
    }

    public static void setDebts(ArrayList<Log> debts) {
        Storage.debts = debts;
        save();
    }

    public static void setShortcuts(ArrayList<Log> shortcuts) {
        Storage.shortcuts = shortcuts;
        save();
    }

    //    /**
//     * This method takes and writes the information of the tasks
//     *  within the specified ArrayList and into a file ./data/duke.txt.
//     *  <p>
//     *      If an error occurs while writing to the file, the method stops running.
//     *  </p>
//     * @param entriesSave An Arraylist containing the entries to be saved.
//     * @param limitsSave An Arraylist containing the limits to be saved.
//     * @param debtsSave An Arraylist containing the debts to be saved.
//     * @param shortcutsSave An Arraylist containing the shortcuts to be saved.
//     */
    public static void save() {
        try (FileWriter file = new FileWriter("./data/duke.txt")) {

            for (Log currEntries : entries) {
                String fileContent = currEntries.formatSave();
                file.write(fileContent);
                file.write(System.lineSeparator());
            }

            for (Log currLimits : limits) {
                String fileContent = currLimits.formatSave();
                file.write(fileContent);
                file.write(System.lineSeparator());
            }

            for (Log currDebts : debts) {
                String fileContent = currDebts.formatSave();
                file.write(fileContent);
                file.write(System.lineSeparator());
            }

            for (Log currShortcuts : shortcuts) {
                String fileContent = currShortcuts.formatSave();
                file.write(fileContent);
                file.write(System.lineSeparator());
            }

        } catch (IOException e) {
            System.out.println("***Error writing to duke.txt***");
        }

    }

//    public static void save(Log saveList) {
//        try (FileWriter file = new FileWriter("./data/duke.txt",true)) {
//                String fileContent = saveList.formatSave();
//                System.out.println(fileContent); ///////////////////////
//                file.write(fileContent);
//                file.write(System.lineSeparator());
//
//        } catch (IOException e) {
//            System.out.println("***Error writing to duke.txt***");
//        }
//
//    }

    /**
     * Create save file called data in root folder.
     */
    public static  void createFolder() {
        File f = new File("data");
        if (!f.exists()) {
            boolean result = false;
            try {
                f.mkdir();
                result = true;
            } catch (SecurityException e) { //security exception?

            }
        }
    }
}
