package javacake.notes;

import javacake.commands.Command;
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
    private static final char[] ILLEGAL_CHARACTERS = { '/', '\n', '\r', '\t',
            '\0', '\f', '`', '?', '*', '\\', '<', '>', '|', '\"', ':', '.', ','};


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
                if (processedName != null) {
                    al.add(new Note(processedName));
                }
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
            String rawFileName = FilenameUtils.removeExtension(fileName);
            System.out.println("RAW" + rawFileName);
            if (!checkForIllegalChar(rawFileName) && !rawFileName.contains(" ")) {
                return rawFileName;
            } else {
                return null;
            }
        } catch (IndexOutOfBoundsException e) {
            throw new CakeException(e.getMessage());
        }
    }


    /**
     * Checks for illegal characters in a keyword.
     * @param word Parameter within a input command.
     * @return True if illegal character is found.
     */
    private static boolean checkForIllegalChar(String word) {
        for (char illegalChar : ILLEGAL_CHARACTERS) {
            if (word.indexOf(illegalChar) >= 0) {
                return true;
            }

        }
        return false;
    }
}
