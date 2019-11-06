//@@author Xdecosee

package wallet.storage;

import wallet.model.help.Help;

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

    private static final String DEFAULT_HELP_FILENAME_PATHS = "/helppaths.txt";
    private static final String MESSAGE_ERROR_ACCESS_LIST = "Error in setting up help section.";


    /**
     * Returns a list of help sections.
     *
     * @return A list of help sections.
     */
    public ArrayList<Help> helpData() {
        ArrayList<Help> choices = new ArrayList<>();

        try {
            InputStream is = getClass().getResourceAsStream(DEFAULT_HELP_FILENAME_PATHS);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String str;

            while ((str = br.readLine()) != null) {
                String[] data = str.split(",");
                if (data.length == 2) {
                    ArrayList<String> sectionData = readData(data[1].trim());
                    Help section = new Help(data[0].trim(), sectionData);
                    choices.add(section);
                }

            }
            br.close();
            isr.close();
            is.close();

        } catch (NullPointerException | IOException e) {
            System.out.println(MESSAGE_ERROR_ACCESS_LIST);
        }

        return choices;
    }

    /**
     * Retrieve help section content.
     *
     * @param filePath file path to a help section.
     * @return list with help section content.
     */
    private ArrayList<String> readData(String filePath) throws IOException {

        ArrayList<String> sectionContent = new ArrayList<>();
        InputStream is = getClass().getResourceAsStream(filePath);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String str;
        while ((str = br.readLine()) != null) {

            String[] data = str.split("\\|");
            if (data.length == 2) {
                String row = String.format("%-20s %s", data[0].trim(), data[1].trim());
                sectionContent.add(row);
            } else {
                sectionContent.add("");
                sectionContent.add(data[0].trim());
            }
        }
        br.close();
        isr.close();
        is.close();

        return sectionContent;
    }
}
