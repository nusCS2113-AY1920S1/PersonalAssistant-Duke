package javacake.notes;

import javacake.exceptions.CakeException;
import javacake.storage.Storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FilenameUtils;

public class NoteList {

    private ArrayList<Note> al = new ArrayList<>();

    /**
     * Using Depth-First-Search to find all text files.
     * In current directory, the text files are notes.
     * @return ArrayList storing all the notes.
     * @throws CakeException If text file does not exist.
     */
    public ArrayList<Note> compileNotes() throws CakeException {
        try {
            Stream<Path> walk = Files.walk(Paths.get(Storage.returnNotesDefaultFilePath()));
            List<String> result = walk.filter(Files::isRegularFile)
                    .map(Path::toString).collect(Collectors.toList());

            for (String resultName : result) {
                String processedName = processFileNames(resultName);
                al.add(new Note(processedName));
            }
        } catch (NullPointerException | IOException e) {
            throw new CakeException("Content not found!" + "\nPls key 'back' or 'list' to view previous content!");
        }
        return al;
    }

    /**
     * Format the names to only display the last partition of file path.
     * @param resultName File paths that are not processed.
     * @return String containing the last partition of file path.
     * @throws CakeException If index of array does not exist.
     */
    private String processFileNames(String resultName) throws CakeException {
        try {
            File file = new File(resultName);
            String fileName = file.getName();
            return FilenameUtils.removeExtension(fileName);
        } catch (IndexOutOfBoundsException e) {
            throw new CakeException(e.getMessage());
        }
    }
}
