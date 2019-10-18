package wallet.storage;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

/**
 * The HelpStorage Class prints help section files.
 */
public class HelpStorage {

    public static final String DEFAULT_HELP_FILENAME_EXPENSE = "/expense.txt";
    public static final String DEFAULT_HELP_FILENAME_GENERAL = "/general.txt";
    public static final String DEFAULT_HELP_FILENAME_LOAN = "/loan.txt";
    public static final String DEFAULT_HELP_FILENAME_CONTACT = "/contact.txt";
    public static final String DEFAULT_HELP_FILENAME_COMMAND_HISTORY = "/cmdhistory.txt";
    public static final String MESSAGE_ERROR_ACCESS_SECTION = "Error! Contact admin for help.";

    /**
     * Prints file content based on the help section user selected.
     *
     * @param input User selected help section index.
     */
    public void sectionData(int input) {

        String chosenPath;

        switch (input) {

        case 1:
            chosenPath = DEFAULT_HELP_FILENAME_GENERAL;
            break;

        case 2:
            chosenPath = DEFAULT_HELP_FILENAME_EXPENSE;
            break;

        case 3:
            chosenPath = DEFAULT_HELP_FILENAME_LOAN;
            break;

        case 4:
            chosenPath = DEFAULT_HELP_FILENAME_CONTACT;
            break;

        case 5:
            chosenPath = DEFAULT_HELP_FILENAME_COMMAND_HISTORY;
            break;

        default:
            chosenPath = null;
            break;

        }


        try {

            InputStream is = getClass().getResourceAsStream(chosenPath);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String str;
            while ((str = br.readLine()) != null) {
                ;
                String[] data = str.split("\\|");
                if (data.length == 2) {
                    String row = String.format("%-20s %s", data[0].trim(), data[1].trim());
                    System.out.println(row);
                } else {
                    System.out.println();
                    System.out.println(data[0].trim());
                }
            }
            br.close();
            isr.close();
            is.close();


        } catch (NullPointerException e) {

            System.out.println(MESSAGE_ERROR_ACCESS_SECTION);

        } catch (FileNotFoundException e) {

            System.out.println(MESSAGE_ERROR_ACCESS_SECTION);

        } catch (IOException e) {

            System.out.println(MESSAGE_ERROR_ACCESS_SECTION);

        }


    }
}
