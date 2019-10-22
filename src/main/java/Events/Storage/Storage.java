package Events.Storage;

import Events.EventTypes.Event;
import UserElements.UI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class containing file path and scanner, allowing for reading from and writing to the storage file.
 * Allows for creation of new storage file if necessary.
 */
public class Storage {
    private File file;
    private Scanner scanFile;

    /**
     * Creates new Model_Class.Storage object
     *
     * @param file The storage file
     */
    public Storage(File file) {
        this.file = file;
    }

    /**
     * Reads from the file whenever the program is run.
     * Stores all read information into an array of strings to be returned.
     *
     * @param ui user interface
     * @return Array of strings containing all information from the read file
     */
    public ArrayList<String> readFromFile(UI ui) {
        boolean fileAssigned = false;
        System.out.print(ui.getLineSeparation());

        try {
            this.scanFile = new Scanner(file);
            fileAssigned = true;
            System.out.println("Event list loaded!");
        } catch (FileNotFoundException FNFe) {

            System.out.println("No Duke file found!\nCreating new file...");

            try {
                file.createNewFile();
            } catch (IOException IOe) {
                System.out.println("Failed! Please ensure data folder exists, then try again!");
            }

            System.out.println("New file created!\nAssigning...");
        }
        System.out.print(ui.getLineSeparation());

        ArrayList<String> readFromFile = new ArrayList<>();

        String fileContent;
        if (this.scanFile != null) {
            while (this.scanFile.hasNextLine()) {
                fileContent = this.scanFile.nextLine();
                readFromFile.add(fileContent);
            }
        }

        return readFromFile;
    }

    /**
     * Saves current information to the storage file.
     *
     * @param events EventList where information is extracted to be saved
     * @param ui     User interface
     */
    public void saveToFile(EventList events, UI ui) {
        String toWriteToFile = "";
        for (Event currEvent : events.getEventArrayList()) {
            toWriteToFile += currEvent.toStringForFile() + "\n";
        }

        try {
            FileWriter writer = new FileWriter(file);
            writer.write(toWriteToFile);
            writer.close();
        } catch (IOException IOe) {
            ui.errorWritingToFile();
        }
    }
}

