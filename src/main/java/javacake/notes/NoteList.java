package javacake.notes;

import javacake.exceptions.DukeException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NoteList {

    public ArrayList<Note> al = new ArrayList<>();

    /**
     * Using Depth-First-Search to find all text files.
     * In current directory, the text files are notes.
     * @return ArrayList storing all the notes.
     * @throws DukeException If text file does not exist.
     */
    public ArrayList<Note> compileNotes() throws DukeException {
        try {
            Stream<Path> walk = Files.walk(Paths.get("data/notes/"));
            List<String> result = walk.filter(Files::isRegularFile)
                    .map(x -> x.toString()).collect(Collectors.toList());

            for (String resultName : result) {
                String processedName = processFileNames(resultName);
                al.add(new Note(processedName));
            }
        } catch (NullPointerException | IOException e) {
            throw new DukeException("Content not found!" + "\nPls key 'back' or 'list' to view previous content!");
        }
        return al;
    }

    /**
     * Format the names to only display the last partition of file path.
     * @param resultName File paths that are not processed.
     * @return String containing the last partition of file path.
     * @throws DukeException If index of array does not exist.
     */
    private String processFileNames(String resultName) throws DukeException {
        try {
            String[] partitionsInFilePath = resultName.split("\\\\");
            return partitionsInFilePath[partitionsInFilePath.length - 1];
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException(e.getMessage());
        }
    }
}
