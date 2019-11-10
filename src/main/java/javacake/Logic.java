package javacake;

import edu.emory.mathcs.backport.java.util.Collections;
import javacake.exceptions.CakeException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;


public class Logic {

    private String defaultFilePath = "content/MainList";
    private static String currentFilePath = "content/MainList";

    private List<String> listOfFiles = new ArrayList<>();
    private static boolean isDirectory = true;
    private int numOfFiles = 0;
    private static final String SLASHES = "/";

    /**
     * Private constructor to ensure exactly one logic object.
     */
    private Logic() {

    }

    private static final Logic INSTANCE = new Logic();

    /**
     * Using Singleton pattern to ensure exactly one logic object.
     * @return The only instance of logic object.
     */
    public static Logic getInstance() {
        return INSTANCE;
    }

    /**
     * Stores all files in the currentFilePath into listOfFiles.
     */
    private void loadFiles() throws CakeException {
        String[] tempListFiles = splitFilePathIntoPartitions();
        int currFileSlashCounter = tempListFiles.length;
        listOfFiles.clear();
        try {
            CodeSource src = generateCodeSource();
            if (runningFromJar()) {
                processJarFile(src, currFileSlashCounter);
            } else {
                processNonJarFile();
            }
        } catch (NullPointerException e) {
            throw new CakeException("Content not found!\n" + "Pls key 'back' or 'list' to view previous content!");
        }
    }

    /**
     * Checks if current file is running from Jar.
     * @return True if current file is a Jar file.
     */
    private static boolean runningFromJar() {
        try {
            String jarFilePath = new File(Logic.class
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
     * Splits current file path into their partitions.
     * @return String array after splitting the current file path by slashes.
     */
    private String[] splitFilePathIntoPartitions() {
        return currentFilePath.split(SLASHES);
    }

    /**
     * Generates code source used for Jar files.
     * @return Code source based on current logic class.
     */
    private CodeSource generateCodeSource() {
        return Logic.class.getProtectionDomain().getCodeSource();
    }

    /**
     * Processes Non-Jar file by reading the files in current file path.
     * @throws CakeException If file does not exist.
     */
    private void processNonJarFile() throws CakeException {
        InputStream inputStream;
        BufferedReader br;
        try {
            inputStream = generateInputStream();
            br = generateBufferedReader(inputStream);
            readAndWrite(br);
            sortFiles();
            updateNumberOfFiles();
            br.close();
        } catch (IOException e) {
            throw new CakeException(e.getMessage());
        }
    }

    /**
     * Generates buffered reader based on current input stream.
     * @param inputStream Input stream based on current file path.
     * @return Buffered Reader based on current input stream.
     */
    private BufferedReader generateBufferedReader(InputStream inputStream) {
        return new BufferedReader(new InputStreamReader(inputStream));
    }

    /**
     * Generates input stream based on current file path.
     * @return InputStream based on current file path.
     */
    private InputStream generateInputStream() {
        return ClassLoader.getSystemClassLoader().getResourceAsStream(currentFilePath);
    }

    /**
     * Sorts the strings in listOfFiles.
     */
    private void sortFiles() {
        Collections.sort(listOfFiles);
    }

    /**
     * Reads and writes into listOfFiles used in Non-Jar files.
     * @param br BufferedReader to read file names in non-jar files.
     * @throws CakeException If line does not exist.
     */
    private void readAndWrite(BufferedReader br) throws CakeException {
        try {
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                listOfFiles.add(currentLine);
            }
        } catch (IOException e) {
            throw new CakeException(e.getMessage());
        }
    }

    /**
     * Processes Jar files.
     * @param src CodeSource in Jar file.
     * @param currFileSlashCounter Current number of file slashes.
     * @throws CakeException If the file does not exist.
     */
    private void processJarFile(CodeSource src, int currFileSlashCounter) throws CakeException {
        URL jar = src.getLocation();
        try {
            ZipInputStream zip = new ZipInputStream(jar.openStream());
            processZipFile(zip, currFileSlashCounter);
        } catch (IOException e) {
            throw new CakeException(e.getMessage());
        }
    }

    /**
     * Processes zip file and updates the list of files.
     * @param zip Zip file.
     * @param currFileSlashCounter current number of file flashes.
     * @throws CakeException If zip does not exist.
     */
    private void processZipFile(ZipInputStream zip, int currFileSlashCounter) throws CakeException {
        ZipEntry e;
        try {
            while ((e = zip.getNextEntry()) != null) {
                String name = e.getName();
                updateListOfFiles(name, currFileSlashCounter);
            }
        } catch (IOException err) {
            throw new CakeException(err.getMessage());
        }
    }

    /**
     * Verifies if it is a child file and starts with current file path.
     * Get the name of the child file and adds it to the list of files.
     * Sorts the file collection and updates the total number of files.
     * @param name Name of current file entry.
     * @param currFileSlashCounter Current number of file slashes.
     */
    private void updateListOfFiles(String name, int currFileSlashCounter) {
        if (name.startsWith(currentFilePath)) {
            String[] listingFiles = name.split(SLASHES);
            if (isChildDirectory(listingFiles, currFileSlashCounter)) {
                String childFile;
                childFile = nameOfChildFile(listingFiles, currFileSlashCounter);
                addFileToListOfFiles(childFile);
                sortFileCollection();
                updateNumberOfFiles();
            }
        }
    }

    /**
     * Adds current file to the list number of files.
     * @param childFile Name of the child file.
     */
    private void addFileToListOfFiles(String childFile) {
        listOfFiles.add(childFile);
    }

    /**
     * Updates the total number of files.
     */
    private void updateNumberOfFiles() {
        numOfFiles = listOfFiles.size();
    }

    /**
     * Sorts all the file in the collection.
     */
    private void sortFileCollection() {
        Collections.sort(listOfFiles);
    }

    /**
     * Returns the name of the child file.
     * @param listingFiles All the files within a file path.
     * @param currFileSlashCounter Total number of file slashes.
     * @return name of child file.
     */
    private String nameOfChildFile(String[] listingFiles, int currFileSlashCounter) {
        return listingFiles[currFileSlashCounter];
    }

    /**
     * Checks if the current file is a child directory.
     * Checks if the number of listing files equals to slash counter + 1.
     * @param listingFiles All the files within a file path.
     * @param currFileSlashCounter Total number of file slashes.
     * @return true if it is child directory.
     */
    private boolean isChildDirectory(String[] listingFiles, int currFileSlashCounter) {
        return (listingFiles.length == currFileSlashCounter + 1);
    }


    /**
     * Returns the total number of files.
     * @return the total number of files.
     */
    public int getNumOfFiles() {
        return numOfFiles;
    }

    /**
     * Method is only invoked when List command is called.
     */
    public void setDefaultFilePath() {
        currentFilePath = defaultFilePath;
    }

    public String getFullFilePath() {
        return currentFilePath;
    }

    /**
     * Method is invoked when GoTo command is called.
     * Based on the index, return the particular filePath.
     *
     * @param index Index of the new path found in filePathQueries.
     * @return the particular filePath based on the input index.
     */
    public String gotoFilePath(int index) throws CakeException {

        try {
            return listOfFiles.get(index);
        } catch (IndexOutOfBoundsException e) {
            throw new CakeException(e.getMessage() + "\n Pls key 'back' or 'list' to view previous content");
        }
    }

    /**
     * Update the currentFilePath by concatenating the updatedPath.
     * updatedPath is given by gotoFilePath method.
     * @param updatedPath particular path to be updated into currentFilePath.
     */
    public void updateFilePath(String updatedPath) {
        currentFilePath += (SLASHES + updatedPath);
    }

    /**
     * Checks if file in currentFilePath is a directory or file.
     * Returns once if it is a directory.
     * Returns twice if it is a file.
     * Used for BackCommand.
     */
    public void backToPreviousPath() throws CakeException {
        if (isNotAFileOrMainList()) { // if it is not a file or main list
            currentFilePath = gotoParentFilePath(currentFilePath);
        } else if (isATextFile()) {  // if it is a text file
            currentFilePath = gotoParentFilePath(gotoParentFilePath(currentFilePath));
        }
    }

    /**
     * Checks if current file path contains a text file.
     * @return True if current file path contains a text file.
     */
    private boolean isATextFile() {
        return (currentFilePath.contains(".txt"));
    }

    /**
     * Checks if current file path is not main list and not a text file.
     * @return True if current file path is not main list and not a text file.
     */
    private boolean isNotAFileOrMainList() {
        return (!currentFilePath.equals(defaultFilePath) && !currentFilePath.contains(".txt"));
    }

    /**
     * Creates a file path to parent directory by
     * removing child file or directory name from filePath.
     * @param filePath input file path to be reduced.
     * @return file path to parent directory relative to initial file path.
     * @throws CakeException If file path does not have parameter.
     */
    public String gotoParentFilePath(String filePath) throws CakeException {
        try {
            String[] filesCapture = filePath.split(SLASHES);
            StringBuilder reducedFilePath = new StringBuilder();
            for (int i = 0; i < filesCapture.length - 1; i++) {
                reducedFilePath.append(filesCapture[i]).append(SLASHES);
            }
            String parentFilePath = reducedFilePath.toString();
            parentFilePath = parentFilePath.substring(0, parentFilePath.length() - 1);
            return parentFilePath;
        } catch (Exception e) {
            throw new CakeException(e.getMessage());
        }
    }


    /**
     * Displays the all directories found in currentFilePath.
     */
    public String displayDirectories() {
        StringBuilder sb = new StringBuilder();
        sb.append("Here are the ").append(listOfFiles.size()).append(" subtopics available!\n");
        for (String queries : listOfFiles) {
            sb.append(queries).append("\n");
        }
        sb.append("Key in the index to learn more about the topic or do the quiz!").append("\n");
        return sb.toString();
    }

    /**
     * Reads the content in content text file.
     *
     * @throws CakeException When the text file in currentFilePath is not found.
     */
    public String readQuery() throws CakeException {
        try {
            InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(currentFilePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String sentenceRead;
            while ((sentenceRead = br.readLine()) != null) {
                sb.append(sentenceRead).append("\n");
            }
            br.close();
            return sb.toString();
        } catch (IOException e) {
            throw new CakeException("Unable to read text file");
        }
    }

    /**
     * Checks current directory contains directory or text files.
     *
     * @return true if current directory contains directory.
     */
    public boolean containsDirectory() {
        return isDirectory;
    }

    /**
     * Calls insertQueries method to instantiate new file paths.
     * Checks if new file path accesses a directory or file.
     * If directory, display all the file names within the directory.
     * Else read the content of the text file.
     * @return String of formatted file names or text file content.
     * @throws CakeException when file or directory is not found.
     */
    public String processQueries() throws CakeException {
        insertQueries();
        if (isDirectory) {
            return displayDirectories();
        } else {
            return readQuery();
        }
    }

    /**
     * Clears all file paths in filePathQueries.
     * Load all files in currentFilePath.
     * Update isDirectory if current directory contains directories.
     * Adds new list of file names in filePathQueries to be processed.
     */
    public void insertQueries() throws CakeException {
        clearQueries();
        loadFiles();
        for (String listOfFile : listOfFiles) {
            if (listOfFile.contains(".txt")) {
                isDirectory = false;
            } else {
                isDirectory = true;
            }
        }

    }

    /**
     * Clears all entries in listOfFiles.
     */
    private void clearQueries() {
        listOfFiles.clear();
    }

}