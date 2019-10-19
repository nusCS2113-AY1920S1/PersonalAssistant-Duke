package javacake.commands;

import javacake.exceptions.DukeException;
import javacake.storage.Profile;
import javacake.ProgressStack;
import javacake.storage.Storage;
import javacake.ui.Ui;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MegaListCommand extends Command {

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
        try (Stream<Path> walk = Files.walk(Paths.get(progressStack.getDefaultFilePath()))) {
            List<String> result = walk.filter(Files::isDirectory).map(Path::toString).collect(Collectors.toList());
            result = processFileNames(result);
            sb.append(String.join("\n", result)).append("\n");
            sb.append("Type 'goto' to access the topics you are interested in!").append("\n");
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
            String[] individualPath = filesNames.split("\\\\");
            StringBuilder sb = new StringBuilder();
            int numberOfSpaces = individualPath.length - 6;
            sb.append(" ".repeat(Math.max(0, numberOfSpaces) * 5));
            sb.append(individualPath[individualPath.length - 1]);
            processedList.add(sb.toString());
        }
        processedList.remove(0);
        return processedList;
    }
    
}
