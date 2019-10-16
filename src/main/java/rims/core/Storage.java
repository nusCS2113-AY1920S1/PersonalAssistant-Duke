package rims.core;

import rims.command.*;
import rims.exception.*;
import rims.resource.*;

import java.util.*;
import java.io.*;
import java.text.*;

/**
 * Current format for text file:
 * For every line:
 * 0) ITEM/ROOM
 * 1) ID (of unique item/room)
 * 2) DESCRIPTION
 * 3) Booked or not?
 * Optional:
 * 4) Loan ID (if booked)]
 * 5) Loan start date
 * 6) Loan end date
 */
public class Storage {
    protected HashMap<String, ArrayList<Resource>> resources = new HashMap<String, ArrayList<Resource>>();
    protected File file;

    public Storage(String filename) throws FileNotFoundException, ParseException {
        file = new File(filename);
        readFromFile();
    }

    /**
     * Obtains the contents of a ResourceList line by line
     * from a text file in a specified file path.
     *
     * @throws FileNotFoundException when specified file path does not lead to a valid file type.
     * @throws ParseException when unable to parse an integer for ID or checking if a resource is booked.
     */
    public void readFromFile() throws FileNotFoundException, ParseException {
        Scanner fileScanner = new Scanner(file);
        while (fileScanner.hasNextLine()) {
            String[] line = fileScanner.nextLine().split("`");
            boolean isBooked = Integer.parseInt(line[3]) == 1 ? true : false;
            if (line[0].equals("I")) {
                Item newItem;
                if (isBooked) {
                    newItem = new Item(line[2], Integer.parseInt(line[1]), Integer.parseInt(line[4]), line[5], line[6]);
                }
                else {
                    newItem = new Item(line[2], Integer.parseInt(line[1]));
                }
                if (resources.containsKey(line[2])) {
                    resources.get(line[2]).add(newItem);
                }
                else {
                    resources.put(line[2], new ArrayList<Resource>(Arrays.asList(newItem)));
                }
            }
            else if (line[0].equals("R")) {
                Room newRoom;
                if (isBooked) {
                    newRoom = new Room(line[2], Integer.parseInt(line[1]), Integer.parseInt(line[4]), line[5], line[6]);
                }
                else {
                    newRoom = new Room(line[2], Integer.parseInt(line[1]));
                }
                resources.put(line[2], new ArrayList<Resource>(Arrays.asList(newRoom)));
            }
        }
    }

    /**
     * Put contents of a ResourceList into a text file for future reference.
     *
     * @param resources ResourceList to put contents into text file.
     * @throws IOException when file given is directory, or file does not exist and cannot be created.
     */
    public void saveToFile(HashMap<String, ArrayList<Resource>> resources) throws IOException {
        BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file, false));
        for (Map.Entry<String, ArrayList<Resource>> entry : resources.entrySet()) {
            ArrayList<Resource> thisResourceArray = entry.getValue();
            for (int i = 0; i < thisResourceArray.size(); i++) {
                Resource thisResource = thisResourceArray.get(i);
                int isBooked = (thisResource.isBookedOrReserved() == true) ? 1 : 0;
                String line = thisResource.getType() + "`" + thisResource.getId() + "`" + thisResource.getName() + "`" + isBooked;
                if (thisResource.isBookedOrReserved()) {
                    line += "`";
                    line += thisResource.getLoanId();
                    line += "`";
                    line += thisResource.dateToString(thisResource.getDateBookedFrom());
                    line += "`";
                    line += thisResource.dateToString(thisResource.getDateBookedTill());
                }
                fileWriter.write(line);
                fileWriter.newLine();
            }
        }
        fileWriter.close();
    }

    public HashMap<String, ArrayList<Resource>> getResources() {
        return resources;
    }

}