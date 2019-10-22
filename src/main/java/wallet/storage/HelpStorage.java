//@@author Xdecosee

package wallet.storage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * The HelpStorage Class prints help section files.
 */
public class HelpStorage {

    public static final String DEFAULT_HELP_FILENAME_PATHS = "/helppaths.txt";
    public static final String MESSAGE_ERROR_ACCESS_SECTION = "Error! Contact admin for help.";
    private ArrayList<String[]> pathList;

    /**
     * Returns a list of help section names and relative paths.
     *
     * @return A list of help section files information.
     */
    public ArrayList<String[]> retrievePaths() {
        ArrayList<String[]> pathList = new ArrayList<>();
        try {
            InputStream is = getClass().getResourceAsStream(DEFAULT_HELP_FILENAME_PATHS);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String str;
            while ((str = br.readLine()) != null) {
                ;
                String[] data = str.split(",");
                if (data.length == 2) {
                    String[] row = {data[0].trim(), data[1].trim()};
                    pathList.add(row);
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
        this.pathList = pathList;
        return pathList;
    }

    /**
     * Prints file content based on the help section user selected.
     *
     * @param input User selected help section index.
     */
    public void sectionData(int input) {
        String[] chosenPair = pathList.get(input - 1);
        String chosenPath = chosenPair[1];

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
