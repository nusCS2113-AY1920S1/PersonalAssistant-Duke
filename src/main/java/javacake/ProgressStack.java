package javacake;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;


public class ProgressStack {
    private String defaultFilePath = "src/main/resources/MainList";
    private static String currentFilePath = "src/main/resources/MainList";
    private static ArrayList<String> filePathQueries = new ArrayList<>();

    private File[] listOfFiles;
    private static boolean isDirectory = true;


    public ProgressStack() {

    }

    /**
     * Returns the starting file path to application content.
     * @return starting file path to application content.
     */
    public String getDefaultFilePath() {
        return defaultFilePath;
    }

    /**
     * Stores all files in the filePath into listOfFiles.
     * @param filePath path to the root directory.
     */
    public void loadFiles(String filePath) throws DukeException {
        //InputStream inputStream = this.getClass().getResourceAsStream(filePath);
        File folder = new File(filePath);
        try {
            listOfFiles = folder.listFiles();
            assert listOfFiles != null;
            Arrays.sort(listOfFiles); //in case the files stored locally are not in alphabetical order
        } catch (NullPointerException e) {
            throw new DukeException("Content not found!" + "\nPls key 'back' or 'list' to view previous content!");
        }
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
    public String gotoFilePath(int index) throws DukeException {

        try {
            return filePathQueries.get(index);
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException(e.getMessage() + "\n Pls key 'back' or 'list' to view previous content");
        }
    }

    /**
     * Update the currentFilePath by concatenating the updatedPath.
     * updatedPath is given by gotoFilePath method.
     *
     * @param updatedPath particular path to be updated into currentFilePath.
     */
    public void updateFilePath(String updatedPath) {
        currentFilePath += ("/" + updatedPath);
    }

    /**
     * Checks if file in currentFilePath is a directory or file.
     * Returns once if it is a directory.
     * Returns twice if it is a file.
     * Used for BackCommand.
     */
    public void backToPreviousPath() {
        File currentFile = new File(currentFilePath);
        if (!currentFilePath.equals(defaultFilePath)) {
            if (currentFile.isDirectory()) {
                currentFilePath = gotoParentFilePath(currentFilePath);
            } else {
                currentFilePath = gotoParentFilePath(gotoParentFilePath(currentFilePath));
            }
        }
    }

    /**
     * Creates a file path to parent directory by
     * removing child file or directory name from filePath.
     * @param filePath input file path to be reduced.
     * @return file path to parent directory relative to initial file path.
     */
    public String gotoParentFilePath(String filePath) {
        String[] filesCapture = filePath.split("/");
        StringBuilder reducedFilePath = new StringBuilder();
        for (int i = 0; i < filesCapture.length - 1; i++) {
            reducedFilePath.append(filesCapture[i]).append("/");
        }
        String finalTrim = reducedFilePath.toString();
        finalTrim = finalTrim.substring(0, finalTrim.length() - 1);
        return finalTrim;
    }


    /**
     * Displays the all directories found in currentFilePath.
     */
    public String displayDirectories() {
        StringBuilder sb = new StringBuilder();
        sb.append("Here are the ").append(filePathQueries.size()).append(" subtopics available!\n");
        for (String queries : filePathQueries) {
            sb.append(queries).append("\n");
        }
        sb.append("Key in the index to learn more about the topic!").append("\n");
        return sb.toString();
    }

    /**
     * Reads the content in content text file.
     *
     * @throws IOException When the text file in currentFilePath is not found.
     */
    public String readQuery() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(currentFilePath));
        StringBuilder sb = new StringBuilder();
        String sentenceRead;
        while ((sentenceRead = br.readLine()) != null) {
            sb.append(sentenceRead).append("\n");
        }
        return sb.toString();
    }

    /**
     * Checks current directory contains directory or text files.
     *
     * @return true if current directory contains directory.
     */
    public boolean containsDirectory() {
        if (isDirectory) {
            return true;
        }
        return false;
    }


    /**
     * Calls insertQueries method to instantiate new file paths.
     * Checks if new file path accesses a directory or file.
     * If directory, display all the file names within the directory.
     * Else read the content of the text file.
     * @return String of formatted file names or text file content.
     * @throws DukeException when file or directory is not found.
     */
    public String processQueries() throws DukeException {
        insertQueries();
        try {
            if (isDirectory) {
                return displayDirectories();
            }
            else {
                return readQuery();
            }
        } catch (IOException e) {
            throw new DukeException(e.getMessage());
        }
    }

    /**
     * Clears all file paths in filePathQueries.
     * Load all files in currentFilePath.
     * Update isDirectory if current directory contains directories.
     * Adds new list of file names in filePathQueries to be processed.
     */
    public void insertQueries() throws DukeException {
        clearQueries();
        loadFiles(currentFilePath);
        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                isDirectory = false;
                filePathQueries.add(listOfFile.getName());
            } else if (listOfFile.isDirectory()) {
                isDirectory = true;
                filePathQueries.add(listOfFile.getName());
            }
        }

    }

    public void clearQueries() {
        filePathQueries.clear();
    }

}