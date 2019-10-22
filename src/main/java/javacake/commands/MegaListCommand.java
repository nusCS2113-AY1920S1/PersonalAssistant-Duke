package javacake.commands;

import javacake.exceptions.DukeException;
import javacake.storage.Profile;
import javacake.ProgressStack;
import javacake.storage.Storage;
import javacake.ui.Ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class MegaListCommand extends Command {

    private static String currentFilePath = "content/MainList";

    public MegaListCommand() {
        type = CmdType.TREE;
    }

    /**
     * Executing MegaListCommand prints the entire directory tree of content files.
     * Set to defaultFilePath and insertQueries to enable link to GoToCommand.
     * Walk function recursively finds files that are directory and insert into result list.
     * @param progressStack tracks current location in program.
     * @param ui the Ui responsible for outputting messages.
     * @param storage Storage needed to write the updated data.
     * @param profile Profile of the user.
     * @return formatted directory of content.
     * @throws DukeException when file is not found.
     */
    public String execute(ProgressStack progressStack, Ui ui, Storage storage, Profile profile) throws DukeException {

        progressStack.setDefaultFilePath();
        progressStack.insertQueries();

        StringBuilder sb = new StringBuilder();
        sb.append("Here is the lesson directory!").append("\n");

        List<String> collectionOfNames = new ArrayList<>();

        try {
            CodeSource src = ProgressStack.class.getProtectionDomain().getCodeSource();
            boolean isJarMode = true;
            if (src != null) { //jar
                URL jar = src.getLocation();
                ZipInputStream zip = new ZipInputStream(jar.openStream());
                while (true) {
                    ZipEntry e = zip.getNextEntry();
                    if (e == null) {
                        isJarMode = false;
                        break;
                    }

                    String name = e.getName();
                    //sb.append(name).append("\n");
                    if (name.startsWith(currentFilePath)) {
                        collectionOfNames.add(name);
                    }
                }
            }
            if (isJarMode) {
                List<String> result = processFileNames(collectionOfNames);
                sb.append(String.join("\n", result)).append("\n");
                sb.append("Type 'goto' to access the topics you are interested in!").append("\n");
            } else {

            }
            return sb.toString();
        } catch (IOException e) {
            throw new DukeException(e.getMessage());
        }
    }

    /**
     * Method used to format the directory names.
     * Based on the number of '\', prepend a multiple of 5 of spaces.
     * Remove first directory name as it is misleading.
     * @param listOfFilesNames contains file names generated from file walk.
     * @return formatted list of file names ready to be displayed.
     */
    private List<String> processFileNames(List<String> listOfFilesNames) {
        List<String> processedList = new ArrayList<>();
        for (String filesNames : listOfFilesNames) {
            String[] individualPath = filesNames.split("/");
            StringBuilder sb = new StringBuilder();
            if (!(individualPath[individualPath.length - 1]).contains(".txt")) {
                int numberOfSpaces = individualPath.length - 3;
                sb.append(" ".repeat(Math.max(0, numberOfSpaces) * 5));
                sb.append(individualPath[individualPath.length - 1]);
                processedList.add(sb.toString());
            }
        }
        processedList.remove(0);
        return processedList;
    }
    
}
