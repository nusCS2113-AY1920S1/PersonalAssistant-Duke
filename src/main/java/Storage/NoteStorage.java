package Storage;

import notes.Note;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 */
public class NoteStorage {
    public static void writeToFile(String fileName, ArrayList<Note>listToWrite) throws IOException {
        //SETTLE THE IOException !!!!!!!!!!!!!!!!!!
        //overriding the file
        FileWriter file = new FileWriter("data/note/" + fileName);
        for (Note n : listToWrite) {
            file.write(Note.noteFormatter.format(n.noteDate) + "\n"); //date
            file.write(n.notes.size() + "\n"); //size of individual note list
            for (String s: n.notes) {
                file.write(s + "\n");
            }
        }
        file.close();
    }

    public static void readFromFile(String fileName, ArrayList<Note> listToReadTo) throws IOException {
        File data = new File("data/note/" + fileName);
        if (data.createNewFile()) {
            System.out.println("Existing " + fileName + " file does not exist.");
            System.out.println("I have created a new text file for you.");
        }
        assert !data.createNewFile() : "There is no " + fileName + " file to read from (NoteStorage)";
        Scanner txtFile = new Scanner(data);
        while (txtFile.hasNextLine()) {
            String date = txtFile.nextLine();
            int sizeOfNotes = Integer.parseInt(txtFile.nextLine());
            String firstNote = txtFile.nextLine();
            Note newNote = new Note(date, firstNote);
            for (int i = 0; i < sizeOfNotes-1; i++) { //sizeOfNotes-1 as the first note had already been added
                newNote.notes.add(txtFile.nextLine());
            }
            listToReadTo.add(newNote);
        }
    }
}
