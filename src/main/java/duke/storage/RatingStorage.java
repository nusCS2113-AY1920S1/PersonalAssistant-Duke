package duke.storage;

import duke.model.list.recipelist.RatingList;
import duke.model.task.recipetasks.Rating2;

import java.io.*;
import java.util.ArrayList;

/**
 * Handles the ability to read and write to the rating storage location.
 */
public class RatingStorage {
    private final ArrayList<Rating2> arrRating2List = new ArrayList<>();
    private final String filePathRating;

    /**
     * Constructor for the class RatingStorage.
     *
     * @param filePathRating the directory in which the ratings are to be stored
     */
    public RatingStorage(String filePathRating) {
        this.filePathRating = filePathRating;
    }

    /**
     * Writes to file to save the ratings to file.
     *
     * @param ratingList the list containing ratings
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
     * Loads all the save ratings in the file.
     *
     * @return the list of ratings in rating list
     */
    public ArrayList<Rating2> load() {
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
