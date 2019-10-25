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
import rims.resource.Room;
import rims.resource.Reservation;
import rims.resource.ReservationList;
import rims.resource.Resource;

/**
 * Resource.txt [ resource id ] [ type ] [ name ]
 * Reserve.txt [ reservation id ] [ resource id ] [ user id ] [ date from ] [ date until ]
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
            ReservationList reservations = readReserveFile(input[0]);
            if (input[1].equals("I")) {
                Item newItem = new Item(Integer.parseInt(input[0]), input[2], reservations);
                resources.add(newItem);
            }
            else if (input[1].equals("R")) {
                Room newRoom = new Room(Integer.parseInt(input[0]), input[2], reservations);
                resources.add(newRoom);
            }
        }
    }

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
                Reservation newReservation = new Reservation(Integer.parseInt(line[0]), Integer.parseInt(line[1]), Integer.parseInt(line[2]), line[3], line[4]);
                if (newReservation.isStillValid()) {
                    resourceReservations.add(newReservation);
                }
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
        BufferedWriter resourceFileWriter = new BufferedWriter(new FileWriter(resourceFile, false));
        BufferedWriter reservationFileWriter = new BufferedWriter(new FileWriter(reservationFile, false));
        String resourceLine;
        String reservationLine;
        for (int i = 0; i < resources.size(); i++) {
            Resource thisResource = resources.get(i);
            resourceFileWriter.write(thisResource.toDataFormat());
            resourceFileWriter.newLine();

            ReservationList thisReservationList = thisResource.getReservations();
            for (int j = 0; j < thisReservationList.size(); j++) {
                reservationLine = thisReservationList.getReservationByIndex(j).toDataFormat();
                reservationFileWriter.write(reservationLine);
                reservationFileWriter.newLine();
                // can this be simplified / moved elsewhere?
            }
        }
        resourceFileWriter.close();
        reservationFileWriter.close();
    }

    public ArrayList<Resource> getResources() {
        return resources;
    }
}