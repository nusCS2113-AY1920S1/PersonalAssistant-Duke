package duke.storage;

import duke.model.list.recipelist.PrepStepList;
import duke.model.task.recipetasks.PrepStep;

import java.io.*;
import java.util.ArrayList;

/**
 * Handles the ability to read and write to the preparation step storage location.
 */
public class PrepStepStorage {
    private final ArrayList<PrepStep> arrPrepStepList = new ArrayList<>();
    private final String filePathPrepStep;

    /**
     * Constructor for the class PrepStepStorage.
     *
     * @param filePathPrepStep the directory in which the preparation steps are to be stored
     */
    public PrepStepStorage(String filePathPrepStep) {
        this.filePathPrepStep = filePathPrepStep;
    }

    /**
     * Writes to file to save the preparation steps to file.
     *
     * @param prepStepList the list containing recipes
     */
    public void saveFile(PrepStepList prepStepList) {
        try {
            FileWriter fileWriter = new FileWriter(filePathPrepStep);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (PrepStep prepStep : prepStepList.getPrepStepList()) {
                bufferedWriter.write(prepStep.toSaveString() + "\n");
            }
            bufferedWriter.close();
        } catch (Exception exc) {
            exc.printStackTrace(); // If there was an error, print the info.
        }
    }

    /**
     * Loads all the save preparation steps in the file.
     *
     * @return the list of preparation steps in preparation step list
     */
    public ArrayList<PrepStep> load() {
        try {
            FileReader fileReader = new FileReader(filePathPrepStep);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String content = "";
            while ((content = bufferedReader.readLine()) != null) {
                String[] split = content.split(" \\| ", 2);
                int index = Integer.parseInt(split[0]);
                if (split.length == 2) {
                    PrepStep prepStep = new PrepStep(index, split[1]);
                    arrPrepStepList.add(prepStep);
                }
            }
            fileReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + filePathPrepStep + "'");
        } catch (IOException ex) {
            System.out.println("Error reading file '" + filePathPrepStep + "'");
        }
        return arrPrepStepList;
    }
}
