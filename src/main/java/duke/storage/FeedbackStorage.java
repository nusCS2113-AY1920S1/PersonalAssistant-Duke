package duke.storage;

import duke.exception.DukeException;
import duke.list.recipelist.FeedbackList;
import duke.task.recipetasks.Feedback;
import java.io.*;
import java.util.ArrayList;

public class FeedbackStorage {
    private final ArrayList<Feedback> arrFeedbackList = new ArrayList<>();
    private final String filePathFeedback;

    /**
     * Constructor for the class Storage.
     *
     * @param filePathFeedback String containing the directory in which the tasks are to be stored
     */
    public FeedbackStorage(String filePathFeedback) {
        this.filePathFeedback = filePathFeedback;
    }

    /**

     * Writing to file to save the task to file.
     *
     * @param feedbackList contains the task list
     */
    public void saveFile(FeedbackList feedbackList) {
        try {
            FileWriter fileWriter = new FileWriter(filePathFeedback);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (Feedback feedback : feedbackList.getFeedbackList()) {
                bufferedWriter.write(feedback.toSaveString() + "\n");
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
    public ArrayList<Feedback> load() throws DukeException {
        try {
            FileReader fileReader = new FileReader(filePathFeedback);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String content = "";
            while ((content = bufferedReader.readLine()) != null) {
                String[] split = content.split(" \\| ", 2);
                int index = Integer.parseInt(split[0]);
                if (split.length == 2) {
                    Feedback feedback = new Feedback(index, split[1]);
                    arrFeedbackList.add(feedback);
                }
            }
            fileReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + filePathFeedback + "'");
        } catch (IOException ex) {
            System.out.println("Error reading file '" + filePathFeedback + "'");
        }
        return arrFeedbackList;
    }
}
