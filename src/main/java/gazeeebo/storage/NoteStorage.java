package gazeeebo.storage;

import gazeeebo.notes.Note;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Reads and writes data for the NoteCommands.
 */
public class NoteStorage {
    /**
     * Writes the whole list of Notes to the specified text file.
     * Overrides the existing data in the file.
     *
     * @param fileName the text file to write the data to
     * @param listToWrite the list of Notes to be written to the text file
     * @throws IOException if the file specified cannot be written to or is not a file
     */
    public static void writeToFile(String fileName, ArrayList<Note>listToWrite) throws IOException {
        FileWriter file = new FileWriter("/" + fileName);
        for (Note n : listToWrite) {
            file.write(Note.noteFormatter.format(n.noteDate) + "\n"); //date
            file.write(n.notes.size() + "\n"); //size of individual note list
            for (String s: n.notes) {
                file.write(s + "\n");
            }
        }
        file.close();
    }

    /**
     * Reads the list of Notes from a text file and stores it into the given ArrayList<>Notes</>.
     *
     * @param fileName the text file the data is to be read from
     * @param listToReadTo the ArrayList<>Notes</> that the data is to be stored in
     * @throws IOException if the file specified cannot be created or is not a file
     */
    public static void readFromFile(String fileName, ArrayList<Note> listToReadTo) {
        InputStream inputStream = NoteStorage.class.getResourceAsStream("/" + fileName);
        Scanner txtFile = new Scanner(inputStream);
        while (txtFile.hasNextLine()) {
            String date = txtFile.nextLine();
            int sizeOfNotes = Integer.parseInt(txtFile.nextLine());
            String firstNote = txtFile.nextLine();
            Note newNote = new Note(date, firstNote);
            for (int i = 0; i < sizeOfNotes-1; i++) { //sizeOfNotes-1 as the first note has already been added
                newNote.notes.add(txtFile.nextLine());
            }
            listToReadTo.add(newNote);
        }
    }
}
