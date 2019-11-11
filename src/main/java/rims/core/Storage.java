package rims.core;

import rims.resource.Item;
import rims.resource.Room;
import rims.resource.Reservation;
import rims.resource.ReservationList;
import rims.resource.Resource;

import rims.exception.RimsException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Scanner;


//@@author rabhijit
/**
 * Converts data files containing a text version of the stored resources and reservations into an
 * array of Resource instances, containing their respective Reservations.
 *
 * <p>Format of data files:
 * Resource.txt: [ resource id ] [ type ] [ name ]
 * Reserve.txt: [ reservation id ] [ resource id ] [ user id ] [ date from ] [ date until ]
 */
public class Storage {
    protected ArrayList<Resource> resources = new ArrayList<Resource>();
    protected File resourceFile;
    protected File reservationFile;

    /**
     * Constructor for the Storage class. Accesses the resource file path
     * and reserve file path and runs the method readResourceFile() to convert the
     * text representation of Resources and Reservations into an actual array of Resources.
     * @param resourceFile the file path where the text version of Resources are stored.
     * @param reserveFile the file path where the text version of Reservations are stored.
     * @throws RimsException when a new file cannot be created with the given path for resource and reservations.
     */
    public Storage(String resourceFile, String reserveFile) throws RimsException {
        this.resourceFile = new File(resourceFile);
        this.reservationFile = new File(reserveFile);
        try {
            this.resourceFile.getParentFile().mkdir();
            this.resourceFile.createNewFile();
            this.reservationFile.getParentFile().mkdir();
            this.reservationFile.createNewFile();
        } catch (IOException e) {
            throw new RimsException("Cannot create a new file!");
        }
        readResourceFile();
    }

    /**
     * Obtains the contents of a ResourceList line by line from a text file in a
     * specified file path.
     *
     * <p>Data retrieval:
     * 1. Open the resource file
     * 2. Loop through each single entry to fetch [ resource id ] [ type ] [ name ]
     * 3. For each entry, open reservation file and fetch any reservations under
     *    this resource_id
     * 4. Create a new resource object using the above data
     * 5. Add this object to ResourceList object
     *
     * @throws RimsException when Scanner cannot detect a valid file or when Resource is missing arguments.
     */
    public void readResourceFile() throws RimsException {
        resources.clear();
        Scanner fileScanner;
        try {
            fileScanner = new Scanner(resourceFile);
        } catch (FileNotFoundException e1) {
            throw new RimsException("File " + resourceFile.toString()
                    + " not found, is a directory or cannot be opened!");
        }
        while (fileScanner.hasNextLine()) {
            String inputString = fileScanner.nextLine();
            String[] input = inputString.split(",");
            if (input.length < 3) {
                throw new RimsException("\nResource entry has insufficient information: " + inputString
                        + "\nEither delete the invalid entry or fill in the missing details!");
            }
            ReservationList reservations = readReserveFile(input[0]);
            if (input[1].equals("I")) {
                Item newItem = new Item(Integer.parseInt(input[0]), input[2], reservations);
                resources.add(newItem);
            } else if (input[1].equals("R")) {
                Room newRoom = new Room(Integer.parseInt(input[0]), input[2], reservations);
                resources.add(newRoom);
            }
        }
    }

    //@@author isbobby
    /**
     * Obtains the contents of a ResourceList line by line from a text file in a
     * specified file path.
     *
     * @throws RimsException when specified file path does not lead to a valid file type,
     *                       and when unable to parse an integer for ID, 
     *                       or checking if a resource is booked.
     */
    public ReservationList readReserveFile(String resourceId) throws RimsException {
        Scanner fileScanner;
        try {
            fileScanner = new Scanner(reservationFile);
        } catch (FileNotFoundException e1) {
            if (resourceFile.isDirectory()) {
                throw new RimsException("File is a directory!");
            } else if (!resourceFile.exists()) {
                throw new RimsException("File does not exist!");
            } else {
                throw new RimsException("File cannot be opened for some reason!");
            }
        }
        ReservationList resourceReservations = new ReservationList();
        while (fileScanner.hasNextLine()) {
            String inputString = fileScanner.nextLine();
            String[] line = inputString.split(",");
            if (line.length < 5) {
                throw new RimsException("\nReservation/loan entry has insufficient information: " + inputString
                        + "\nEither delete the invalid entry or fill in the missing details!");
            }
            if (line[1].equals(resourceId)) {
                Reservation newReservation = new Reservation(Integer.parseInt(line[0]),
                    Integer.parseInt(line[1]), Integer.parseInt(line[2]), line[3], line[4]);
                resourceReservations.add(newReservation);
            }
        }
        return resourceReservations;
    }

    //@@author rabhijit
    /**
     * Put contents of a ResourceList into a text file for future reference.
     *
     * @param resources ResourceList to put contents into text file.
     * @throws RimsException when file given is directory, or file does not exist and
     *                     cannot be created.
     */
    public void saveToFile(ArrayList<Resource> resources) throws RimsException {
        BufferedWriter resourceFileWriter;
        BufferedWriter reservationFileWriter;
        try {
            resourceFileWriter = new BufferedWriter(new FileWriter(resourceFile, false));
            reservationFileWriter = new BufferedWriter(new FileWriter(reservationFile, false));
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
                }
            }
            resourceFileWriter.close();
            reservationFileWriter.close();
        } catch (IOException e) {
            if (resourceFile.isDirectory()) {
                throw new RimsException("File is a directory!");
            } else if (!resourceFile.exists()) {
                throw new RimsException("File does not exist!");
            } else {
                throw new RimsException("File cannot be opened for some reason!");
            }
        }
    }

    /**
     * Returns array of Resources that was created from text format.
     * @return the aforementioned array.
     */
    public ArrayList<Resource> getResources() {
        return resources;
    }
}