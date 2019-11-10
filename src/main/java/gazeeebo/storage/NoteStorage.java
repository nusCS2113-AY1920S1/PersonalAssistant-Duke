//@@author yueyuu

package gazeeebo.storage;

import gazeeebo.logger.LogCenter;
import gazeeebo.notes.Note;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Reads and writes data for the NoteCommands.
 */
public class NoteStorage {
    private static final Logger logger = Logger.getLogger(NoteStorage.class.getName());

    /**
     * Writes the whole list of Notes to the specified text file.
     * Overrides the existing data in the file.
     *
     * @param fileName the text file to write the data to
     * @param listToWrite the list of Notes to be written to the text file
     */
    public static void writeToFile(String fileName, ArrayList<Note> listToWrite) {
        LogCenter.setUpLogger(logger);
        try {
            FileWriter file = new FileWriter(fileName);
            for (Note n : listToWrite) {
                file.write(Note.noteFormatter.format(n.noteDate) + "\n"); //date
                file.write(n.notes.size() + "\n"); //size of individual note list
                for (String s : n.notes) {
                    file.write(s + "\n");
                }
            }
            file.flush();
            file.close();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Can't write to " + fileName + " file. Creating a new one...", e);
            File f = new File(fileName);
            try {
                f.createNewFile();
            } catch (IOException a) {
                logger.log(Level.SEVERE, "Can't create a new " + fileName + " file.", a);
            }
        }
    }

    /**
     * Reads the list of Notes from a text file and stores it into the given container.
     *
     * @param fileName the text file the data is to be read from
     * @param listToReadTo the container that the data is to be stored in
     */
    public static void readFromFile(String fileName, ArrayList<Note> listToReadTo) {
        LogCenter.setUpLogger(logger);
        File f = new File(fileName);
        try {
            Scanner txtFile = new Scanner(f);
            while (txtFile.hasNextLine()) {
                String date = txtFile.nextLine();
                int sizeOfNotes = Integer.parseInt(txtFile.nextLine());
                String firstNote = txtFile.nextLine();
                Note newNote = new Note(date, firstNote);
                for (int i = 0; i < sizeOfNotes - 1; i++) { //sizeOfNotes-1 as the first note has already been added
                    newNote.notes.add(txtFile.nextLine());
                }
                listToReadTo.add(newNote);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Can't read from " + fileName + " file. Creating a new one...", e);
            try {
                f.createNewFile();
            } catch (IOException a) {
                logger.log(Level.SEVERE, "Can't create a new " + fileName + " file.", a);
            }
        }
    }

}
