//@@author jessteoxizhi

package gazeeebo.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class PlacesPageStorage {

    private final String relativePathPlacesResource
            = "Places.txt";

    /**
     * Write to Places.txt file
     * @param fileContent Concatenate all the places into a single string
     * @throws IOException exception when the file is unable to be written
     */

    public void writePlacesFile(String fileContent) throws IOException {
        FileWriter fileWriter = new FileWriter(relativePathPlacesResource);
        fileWriter.write(fileContent);
        fileWriter.flush();
        fileWriter.close();
    }

    /**
     * Read Places.txt file
     * @return A HashMap of the places and their locations.
     * @throws IOException exception when the file is unable to be read
     */

    public HashMap<String, String> readPlaces() throws IOException {
        HashMap<String, String> placesList = new HashMap<String, String>();
        File f = new File(relativePathPlacesResource);
        Scanner sc = new Scanner(f);
        while (sc.hasNext()) {
            try {
                String[] split = sc.nextLine().split("\\|");
                placesList.put(split[0], split[1]);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Places.txt cannot be read, check format of Places.txt");
            }
        }
        return placesList;
    }
}
