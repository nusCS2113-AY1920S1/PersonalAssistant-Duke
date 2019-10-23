package rims.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

import rims.resource.Item;
import rims.resource.Reservation;
import rims.resource.ReservationList;
import rims.resource.Resource;

/**
 * Current format of data storage: -
 * <p>
 * Resource.txt [ resource id ] [ type ] [ name ]
 * <p>
 * Reserve.txt [ reservation id ] [ resource id ] [ user id ] [ date from ] [
 * date until ]
 * <p>
 * 
 */
public class Storage {
    protected ArrayList<Resource> resources = new ArrayList<Resource>();
    protected File resourceFile;
    protected File reservationFile;

    public Storage(String resourceFile, String reserveFile) throws FileNotFoundException, ParseException {
        this.resourceFile = new File(resourceFile);
        this.reservationFile = new File(reserveFile);
        readResourceFile();
    }

    /**
     * Obtains the contents of a ResourceList line by line from a text file in a
     * specified file path.
     * 
     * Data retrieval:<br>
     * 1. Open the resource file<br>
     * 2. Loop through each single entry to fetch [ resource id ] [ type ] [ name
     * ]<br>
     * 3. For each entry, open reservsation file and fetch any reservations under
     * this resource_id <br>
     * 4. Create a new resource object using the above data <br>
     * 5. Add this object to ResourceList object
     *
     * @throws FileNotFoundException when specified file path does not lead to a
     *                               valid file type.
     * @throws ParseException        when unable to parse an integer for ID or
     *                               checking if a resource is booked.
     */
    public void readResourceFile() throws FileNotFoundException, ParseException {
        Scanner fileScanner = new Scanner(resourceFile);
        while (fileScanner.hasNextLine()) {
            String[] input = fileScanner.nextLine().split(",");
            String resourceId = input[0];
            ReservationList reservations = readReserveFile(resourceId);
            Resource newResource = new Item(input[0], input[1], input[2], reservations);
            this.resources.add(newResource);
        }
    }
    // reservationId, resourceId, userId, fromDate, tillDate

    /**
     * Obtains the contents of a ResourceList line by line from a text file in a
     * specified file path.
     *
     * @throws FileNotFoundException when specified file path does not lead to a
     *                               valid file type.
     * @throws ParseException        when unable to parse an integer for ID or
     *                               checking if a resource is booked.
     */
    public ReservationList readReserveFile(String resourceId) throws FileNotFoundException, ParseException {
        Scanner fileScanner = new Scanner(reservationFile);
        ReservationList resourceReservations = new ReservationList();
        while (fileScanner.hasNextLine()) {
            String[] line = fileScanner.nextLine().split(",");
            if (line[1].equals(resourceId)) {
                Reservation newReservation = new Reservation(line[0], line[1], line[2], line[3], line[4]);
                resourceReservations.addNewReservation(newReservation);
            }
        }
        return resourceReservations;
    }

    /**
     * Put contents of a ResourceList into a text file for future reference.
     *
     * @param resources ResourceList to put contents into text file.
     * @throws IOException when file given is directory, or file does not exist and
     *                     cannot be created.
     */
    public void saveToFile(ArrayList<Resource> resources) throws IOException {
        ReservationList totalReservations = new ReservationList();
        BufferedWriter resourceFileWriter = new BufferedWriter(new FileWriter(resourceFile, false));
        BufferedWriter reservationFileWriter = new BufferedWriter(new FileWriter(reservationFile, false));
        String resourceLine;
        String reservationLine;
        for (int i = 0; i < resources.size(); i++) {
            Resource thisResource = resources.get(i);
            ReservationList thisReservationList = thisResource.getReservations();
            resourceLine = thisResource.toDataString();
            resourceFileWriter.write(resourceLine);
            resourceFileWriter.newLine();
            for (int j = 0; j < thisReservationList.size(); j++) {
                reservationLine = thisReservationList.getReservationByIndex(j).toDataString();
                reservationFileWriter.write(reservationLine);
                reservationFileWriter.newLine();
                // hmm
            }
        }
        resourceFileWriter.close();
        reservationFileWriter.close();
    }

    public ArrayList<Resource> getResources() {
        return resources;
    }
}