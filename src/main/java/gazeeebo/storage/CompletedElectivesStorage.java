package gazeeebo.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class CompletedElectivesStorage {
    /**
     * Completed electives storage file name.
     */
    private String relativePathCompletedElectivesResource
            = "CompletedElectives.txt";

    /**
     * This method writes to the file CompletedElectives.txt.
     *
     * @param fileContent save the completed technical electives into this file.
     * @throws IOException catch the error if the read file fails.
     */
    public void writeToCompletedElectivesFile(String fileContent) throws IOException {
        FileWriter fileWriter = new FileWriter(relativePathCompletedElectivesResource);
        fileWriter.write(fileContent);
        fileWriter.flush();
        fileWriter.close();
    }

    /**
     * This method read from the file
     * CompletedElectives.txt and put the details into a HashMap.
     *
     * @return Returns the HashMap of completedEMap,
     *         key is the specialization title and the value is
     *         list of completed technical electives under that specialization.
     * @throws FileNotFoundException catch the error if the read file fails.
     */
    public HashMap<String, ArrayList<String>> readFromCompletedElectivesFile() throws FileNotFoundException {
        HashMap<String, ArrayList<String>> completedEMap = new HashMap<>();
        File file = new File(relativePathCompletedElectivesResource);
        Scanner sc = new Scanner(file);
        while (sc.hasNext()) {
            ArrayList<String> completedElectiveList = new ArrayList<>();
            String[] split = sc.nextLine().split("\\|");
            String checkKey = split[0];
            if (completedEMap.containsKey(checkKey)) {
                completedEMap.get(checkKey).add(split[1]);
            } else {
                completedElectiveList.add(split[1]);
                completedEMap.put(checkKey, completedElectiveList);
            }
        }
        return completedEMap;
    }
}
