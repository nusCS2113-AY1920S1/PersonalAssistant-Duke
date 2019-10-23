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
 * Current format for text file: For every line: 0) ITEM/ROOM 1) ID (of unique
 * item/room) 2) DESCRIPTION 3) Booked or not? Optional: 4) Loan ID (if booked)]
 * 5) Loan start date 6) Loan end date
 */
public class Storage {
    // protected HashMap<String, ArrayList<Resource>> resources = new
    // HashMap<String, ArrayList<Resource>>();
    protected ArrayList<Resource> resources;
    protected ArrayList<Reservation> Reservations;
    protected ReservationList TempReservations;
    protected File resourceFile;
    protected File reservationFile;
    private Ui ui;

    public Storage(String ResourceFileName, String ReserveFileName, Ui ui)
            throws FileNotFoundException, ParseException {
        resourceFile = new File(ResourceFileName);
        reservationFile = new File(ReserveFileName);
        resources = new ArrayList<Resource>();
        Reservations = new ArrayList<Reservation>();
        TempReservations = new ReservationList();
        this.ui = ui;
        readFromResourceFile();
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
            String resource_id = line[0];
            ReservationList reservations = readReservationsFromReserveFile(resource_id);
            input = new Item(line[0], line[1], line[2], reservations);
            this.resources.add(input);
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
    public ReservationList readReservationsFromReserveFile(String resource_id)
            throws FileNotFoundException, ParseException {
        Scanner fileScanner = new Scanner(reservationFile);
        ReservationList reservations = new ReservationList();
        while (fileScanner.hasNextLine()) {
            String[] line = fileScanner.nextLine().split(",");
            if (line[1].equals(resource_id)) {
                Reservation newReservation = new Reservation(line[0], line[1], line[2], line[3], line[4]);
                reservations.addNewReservation(newReservation);
            }
        }
        return reservations;
    }

    /**
     * Put contents of a ResourceList into a text file for future reference.
     *
     * @param resources ResourceList to put contents into text file.
     * @throws IOException when file given is directory, or file does not exist and
     *                     cannot be created.
     */
    public void saveToFiles(ArrayList<Resource> resources) throws IOException {
        BufferedWriter fileWriter = new BufferedWriter(new FileWriter(resourceFile, false));
        
        String line;

        for (int i = 0; i < resources.size(); i++) {
            Resource thisResource = resources.get(i);
            line = thisResource.toDataString();

            fileWriter.write(line);
            ui.ErrorPrint("Saved:" + line);
            fileWriter.newLine();

            if (thisResource.getReservations().size() > 0) {
                ReservationList thisReservationList = thisResource.getReservations();
                saveToTempReservationList(thisReservationList);
                }
            }

        saveToReservationFile(this.TempReservations);
        fileWriter.close();
    }

    public void saveToTempReservationList (ReservationList reservations ) {
        for(int i=0; i<reservations.size(); i ++){
            this.TempReservations.addNewReservation(reservations.getReservationByIndex(i));
        }
    }

    public void saveToReservationFile(ReservationList reservations) throws IOException {
        BufferedWriter ReservationfileWriter = new BufferedWriter(new FileWriter(reservationFile, false));
        String ReservationLine;
        for (int i = 0; i < reservations.size(); i++) {
            ReservationLine = reservations.getReservationByIndex(i).toDataString();
            ReservationfileWriter.write(ReservationLine);
            ui.ErrorPrint("Saved:" + ReservationLine);
            ReservationfileWriter.newLine();
        }
        ReservationfileWriter.close();
    }

    public ArrayList<Resource> getResources() {
        return resources;
    }

    public ArrayList<Reservation> getReservations() {
        return Reservations;
    }
}