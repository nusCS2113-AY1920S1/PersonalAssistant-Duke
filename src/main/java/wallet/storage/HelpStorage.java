package wallet.storage;

import java.io.*;

/**
 * The HelpStorage Class prints help section files.
 */
public class HelpStorage {

    public static final String DEFAULT_HELP_FILEPATH_EXPENSE = "./help-docs/expense.txt";
    public static final String DEFAULT_HELP_FILEPATH_GENERAL = "./help-docs/general.txt";
    public static final String DEFAULT_HELP_FILEPATH_LOAN = "./help-docs/loan.txt";
    public static final String DEFAULT_HELP_FILEPATH_CONTACT = "./help-docs/contact.txt";
    public static final String DEFAULT_HELP_FILEPATH_COMMAND_HISTORY = "./help-docs/cmdhistory.txt";
    public static final String MESSAGE_ERROR_ACCESS_SECTION = "Error in accessing help section. Contact admin for help.";

    /**
     * Prints file content based on the help section user selected.
     */
    public void sectionData(int input) {

        String path = null;

        switch (input) {

            case 1:
                path = DEFAULT_HELP_FILEPATH_GENERAL;
                break;

            case 2:
                path = DEFAULT_HELP_FILEPATH_EXPENSE;
                break;

            case 3:
                path = DEFAULT_HELP_FILEPATH_LOAN;
                break;

            case 4:
                path = DEFAULT_HELP_FILEPATH_CONTACT;
                break;

            case 5:
                path = DEFAULT_HELP_FILEPATH_COMMAND_HISTORY;
                break;

            default:
                path = null;

        }

        if (path != null) {

            try {

                RandomAccessFile raf = new RandomAccessFile(path, "r");
                String str;
                while (raf.getFilePointer() != raf.length()) {
                    str = raf.readLine();
                    String[] data = str.split(",");
                    if (data.length == 2) {
                        String row = String.format("%-20s %s", data[0], data[1]);
                        System.out.println(row);
                    } else {
                        System.out.println();
                        System.out.println(data[0]);
                    }
                }
                raf.close();


            } catch (FileNotFoundException e) {

                System.out.println(MESSAGE_ERROR_ACCESS_SECTION);

            } catch (IOException e) {

                System.out.println(MESSAGE_ERROR_ACCESS_SECTION);

            }

        }
    }
}
