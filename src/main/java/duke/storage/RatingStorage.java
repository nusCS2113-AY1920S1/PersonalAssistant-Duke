package duke.storage;

import duke.exception.DukeException;
import duke.model.list.recipelist.RatingList;
import duke.model.task.recipetasks.Rating2;

import java.io.*;
import java.util.ArrayList;

public class RatingStorage {
    private final ArrayList<Rating2> arrRating2List = new ArrayList<>();
    private final String filePathRating;

    /**
     * Constructor for the class Storage.
     *
     * @param filePathRating String containing the directory in which the tasks are to be stored
     */
    public RatingStorage(String filePathRating) {
        this.filePathRating = filePathRating;
    }

    /**

     * Writing to file to save the task to file.
     *
     * @param ratingList contains the task list
     */
    public void saveFile(RatingList ratingList) {
        try {
            FileWriter fileWriter = new FileWriter(filePathRating);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (Rating2 rating2 : ratingList.getRating2List()) {
                bufferedWriter.write(rating2.toSaveString() + "\n");
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
    public ArrayList<Rating2> load() throws DukeException {
        try {
            FileReader fileReader = new FileReader(filePathRating);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String content = "";
            while ((content = bufferedReader.readLine()) != null) {
                String[] split = content.split(" \\| ", 2);
                int index = Integer.parseInt(split[0]);
                if (split.length == 2) {
                    Rating2 rating2 = new Rating2(index, split[1]);
                    arrRating2List.add(rating2);
                }
            }
            fileReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + filePathRating + "'");
        } catch (IOException ex) {
            System.out.println("Error reading file '" + filePathRating + "'");
        }
        return arrRating2List;
    }
}
