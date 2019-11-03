package javacake.commands;

import edu.emory.mathcs.backport.java.util.Collections;
import javacake.Logic;
import javacake.exceptions.CakeException;
import javacake.storage.StorageManager;
import javacake.ui.Ui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class OverviewCommand extends Command {

    private static String currentFilePath = "content/MainList";
    private static int expectedForwardSlash = 3;
    private static int indentations = 5;

    /**
     * Constructor for OverviewCommand.
     * Checks that no parameters are included.
     * @param inputCommand Overview command from user.
     * @throws CakeException If other parameter is appended to command.
     */
    public OverviewCommand(String inputCommand) throws CakeException {
        checksParam(inputCommand);
        type = CmdType.TREE;
    }

    /**
     * Executing MegaListCommand prints the entire directory tree of content files.
     * Set to defaultFilePath and insertQueries to enable link to GoToCommand.
     * Walk function recursively finds files that are directory and insert into result list.
     * @param logic TaskList containing current tasks
     * @param ui the Ui responsible for outputting messages
     * @param storageManager storage container
     * @return formatted directory of content.
     * @throws CakeException when file is not found.
     */
    public String execute(Logic logic, Ui ui, StorageManager storageManager) throws CakeException {

        logic.setDefaultFilePath();
        logic.insertQueries();

        StringBuilder sb = new StringBuilder();
        sb.append("Here is the lesson directory!").append("\n");
        CodeSource src = Logic.class.getProtectionDomain().getCodeSource();
        if (runningFromJar()) { //jar
            sb.append(processJarFile(src));
        } else {
            sb.append(processNonJarFile());
        }
        return sb.toString();
    }

    /**
     * Processes Jar Files by unzipping the jar file.
     * Looks for file names that corresponds to current file path.
     * @param src Source of the code.
     * @return String of names of directories.
     * @throws CakeException If file path does not exist.
     */
    private String processJarFile(CodeSource src) throws CakeException {
        List<String> collectionOfNames = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        try {
            URL jar = src.getLocation();
            ZipInputStream zip = new ZipInputStream(jar.openStream());
            while (true) {
                ZipEntry e = zip.getNextEntry();
                if (e == null) {
                    break;
                }
                String name = e.getName();
                if (name.startsWith(currentFilePath)) {
                    collectionOfNames.add(name);
                }
            }
            Collections.sort(collectionOfNames);
            List<String> result = processFileNames(collectionOfNames);
            sb.append(String.join("\n", result)).append("\n");
            sb.append(getEndingMessage());
        } catch (IOException e) {
            throw new CakeException(e.getMessage());
        }
        return sb.toString();
    }

    /**
     * Processes in Non-Jar files such as IDEs.
     * Uses Depth-First-Search to process files that are directories.
     * Returns the list of names used for processing.
     * @return String of names of directories.
     * @throws CakeException If file path does not exist.
     */
    private String processNonJarFile() throws CakeException {
        StringBuilder sb = new StringBuilder();
        try {
            Stream<Path> walk = Files.walk(Paths.get("src/main/resources/content/MainList"));
            List<String> result = walk.filter(Files::isDirectory)
                    .map(x -> x.toString()).collect(Collectors.toList());
            result = processFileNamesIfNotJar(result);
            sb.append(String.join("\n", result)).append("\n");
            sb.append(getEndingMessage());
        } catch (IOException e) {
            throw new CakeException(e.getMessage());
        }
        return sb.toString();
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
                int numberOfSpaces = individualPath.length - expectedForwardSlash;
                sb.append(" ".repeat(Math.max(0, numberOfSpaces) * indentations));
                sb.append(individualPath[individualPath.length - 1]);
                processedList.add(sb.toString());
            }
        }
        processedList.remove(0);
        return processedList;
    }

    /**
     * Method used to format the directory names.
     * Based on the number of '\', prepend a multiple of 5 of spaces.
     * Remove first directory name as it is misleading.
     * @param listOfFilesNames contains file names generated from file walk.
     * @return formatted list of file names ready to be displayed.
     */
    private List<String> processFileNamesIfNotJar(List<String> listOfFilesNames) {
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

    /**
     * Checks if the program is running in a JAR file.
     * @return True if the program is running in a JAR file.
     */
    public static boolean runningFromJar() {
        try {
            String jarFilePath = new File(OverviewCommand.class
                    .getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .getPath())
                    .toString();

            jarFilePath = URLDecoder.decode(jarFilePath, StandardCharsets.UTF_8);

            try (ZipFile zipFile = new ZipFile(jarFilePath)) {
                ZipEntry zipEntry = zipFile.getEntry("META-INF/MANIFEST.MF");
                return zipEntry != null;
            }
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Displays ending message to improve user interface.
     * @return String of ending message.
     */
    private String getEndingMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("Type 'goto [index]' to access the topics you are interested in!").append("\n");
        sb.append("E.g. 'goto 1.2' will bring you to 1. Java Basics -> 2. Read").append("\n");
        return sb.toString();
    }
    
}
