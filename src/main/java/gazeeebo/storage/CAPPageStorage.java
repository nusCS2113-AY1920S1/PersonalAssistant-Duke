//@@author JasonLeeWeiHern
package gazeeebo.storage;

import gazeeebo.parsers.CAPCommandParser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class CAPPageStorage {
    /** CAP storage file name.*/
    private String relativePathCAPResource
            = "CAP.txt";
    /**
     * Write to the CAP.txt file (save in the file).
     *
     * @param fileContent string to put into the file.
     * @throws IOException catch the error if the read file fails.
     */
    public void writeToCAPFile(final String fileContent) throws IOException {
        FileWriter fileWriter = new FileWriter(relativePathCAPResource);
        fileWriter.write(fileContent);
        fileWriter.flush();
        fileWriter.close();
    }

    /**
     * Read from the file CAP.txt and put the details into a HashMap.
     *
     * @return Returns the HashMap of contacts,
     * key is the contact name and the value is the phone number
     * @throws IOException catch the error if the read file fails.
     */
    public HashMap<String, ArrayList<CAPCommandParser>> readFromCAPFile()
            throws IOException {
        HashMap<String, ArrayList<CAPCommandParser>>
                caplist = new HashMap<>();

        File f = new File(relativePathCAPResource);
        Scanner sc = new Scanner(f);
        while (sc.hasNext()) {
            ArrayList<CAPCommandParser> moduleList = new ArrayList<>();
            String[] splitStringTxtFile = sc.nextLine().split("\\|");
            String semNumber = splitStringTxtFile[0];
            String moduleCode = splitStringTxtFile[1];
            int mc = Integer.parseInt(splitStringTxtFile[2]);
            String grade = splitStringTxtFile[3];
            CAPCommandParser newCAP =
                    new CAPCommandParser(moduleCode, mc, grade);
            boolean isEqual = false;
            for (String key : caplist.keySet()) {
                if (semNumber.equals(key)) {
                    caplist.get(key).add(newCAP);
                    isEqual = true;
                }
            }
            /* semNumber doesn't exist in the list */
            if (!isEqual) {
                moduleList.add(newCAP);
                caplist.put(semNumber, moduleList);
            }
        }
        return caplist;
    }
}
