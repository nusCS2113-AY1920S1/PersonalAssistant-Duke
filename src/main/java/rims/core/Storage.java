package rims.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

import rims.reserve.Reservation;
import rims.resource.Item;
import rims.resource.Resource;
import rims.resource.Room;

/**
 * Current format for text file: For every line: 0) ITEM/ROOM 1) ID (of unique
 * item/room) 2) DESCRIPTION 3) Booked or not? Optional: 4) Loan ID (if booked)]
 * 5) Loan start date 6) Loan end date
 */
public class Storage {
    // protected HashMap<String, ArrayList<Resource>> resources = new
    // HashMap<String, ArrayList<Resource>>();
    protected ArrayList<Resource> resources = new ArrayList<Resource>();
    protected ArrayList<Reservation> Reservations = new ArrayList<Reservation>();
    protected File resourceFile;
    protected File reserveFile;
    private Ui ui;

    public Storage(String ResourceFileName, String ReserveFileName, Ui ui)
            throws FileNotFoundException, ParseException {
        resourceFile = new File(ResourceFileName);
        reserveFile = new File(ReserveFileName);
        this.ui = ui;
        readFromResourceFile();
        readFromReserveFile();
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
    public void readFromResourceFile() throws FileNotFoundException, ParseException {
        Scanner fileScanner = new Scanner(resourceFile);

        while (fileScanner.hasNextLine()) {
            Resource input = null;
            String[] line = fileScanner.nextLine().split(",");
            if (line[1].equals("R")) {
                input = new Item(line[0], line[1], line[2], line[3]);
            } else if (line[1].equals("I")) {
                input = new Room(line[0], line[1], line[2], line[3]);
            } else {
                ;
            }
            resources.add(input);
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
    public void readFromReserveFile() throws FileNotFoundException, ParseException {
        Scanner fileScanner = new Scanner(reserveFile);
        while (fileScanner.hasNextLine()) {
            String[] line = fileScanner.nextLine().split(",");
            Reservation newReservation = new Reservation(line[0], line[1], line[2], line[3], line[4],line[5]);
            Reservations.add(newReservation);
        }
    }

    /**
     * Put contents of a ResourceList into a text file for future reference.
     *
     * @param resources ResourceList to put contents into text file.
     * @throws IOException when file given is directory, or file does not exist and
     *                     cannot be created.
     */
    public void saveToResourceFile(ArrayList<Resource> resources) throws IOException {
        BufferedWriter fileWriter = new BufferedWriter(new FileWriter(resourceFile, false));
        String line;
        for (int i = 0; i < resources.size(); i++) {
            line = resources.get(i).toDataString();
            fileWriter.write(line);
            ui.ErrorPrint("Saved:" + line);
            fileWriter.newLine();
        }
        fileWriter.close();
    }

    public void saveToReserveFile(ArrayList<Reservation> reserves) throws IOException {
        BufferedWriter fileWriter = new BufferedWriter(new FileWriter(reserveFile, false));
        String line;
        for (int i = 0; i < reserves.size(); i++) {
            line = reserves.get(i).toDataString();
            fileWriter.write(line);
            ui.ErrorPrint("Saved:" + line);
            fileWriter.newLine();
        }
        fileWriter.close();
    }

    public ArrayList<Resource> getResources() {
        return resources;
    }

    public ArrayList<Reservation> getReservations() {
        return Reservations;
    }
}