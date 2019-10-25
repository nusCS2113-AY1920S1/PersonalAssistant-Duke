package duke.storage;

import duke.exception.DukeException;
import duke.model.list.recipelist.PrepStepList;
import duke.model.task.recipetasks.PrepStep;

import java.io.*;
import java.util.ArrayList;

public class PrepStepStorage {
    private final ArrayList<PrepStep> arrPrepStepList = new ArrayList<>();
    private final String filePathPrepStep;

    /**
     * Constructor for the class Storage.
     *
     * @param filePathPrepStep String containing the directory in which the tasks are to be stored
     */
    public PrepStepStorage(String filePathPrepStep) {
        this.filePathPrepStep = filePathPrepStep;
    }

    /**

     * Writing to file to save the task to file.
     *
     * @param prepStepList contains the task list
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
     * Load all the save tasks in the file.
     *
     * @return the list of tasks in taskList
     * @throws DukeException if Duke is not able to load the tasks from the file or unable to open the file
     */
    public ArrayList<PrepStep> load() throws DukeException {
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
