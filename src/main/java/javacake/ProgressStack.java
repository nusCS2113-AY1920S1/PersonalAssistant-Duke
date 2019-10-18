package javacake;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class ProgressStack {
    private File f1;
    //private String defaultFilePath = null;
    //private String currentFilePath = null;


    private String defaultFilePath = "content/MainList";
    private static String currentFilePath = "content/MainList";
    private static ArrayList<String> filePathQueries = new ArrayList<>();

    //private File[] listOfFiles;
    private List<String> listOfFiles = new ArrayList<>();
    private static boolean isDirectory = true;

    public ProgressStack() {

    }

    /*public ProgressStack() throws DukeException {
        try {
            f1 = new File(getClass().getResource("/content/MainList").toURI());
        } catch (URISyntaxException e) {
            throw new DukeException("Unable to load file directory");
        }
        System.out.println(f1.getAbsolutePath());
        defaultFilePath = f1.getPath();
        currentFilePath = f1.getPath();
    }*/

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

        //File folder = new File(filePath);
        String[] tempListFiles = currentFilePath.split("/");
        int currFileSlashCounter = tempListFiles.length;
        listOfFiles.clear();

        try {


            //assert listOfFiles != null;
            //Arrays.sort(listOfFiles); //in case the files stored locally are not in alphabetical order

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
                    if (e == null) {
                        break;
                    }
                    String name = e.getName();
                    //System.out.println(name);
                    if (name.startsWith(currentFilePath)) {
                        String[] listingFiles = name.split("/");
                        if (listingFiles.length == currFileSlashCounter + 1) {
                            System.out.println(name + " == " + currFileSlashCounter);
                            listOfFiles.add(listingFiles[currFileSlashCounter]);
                        }
                        //                        else {
                        //                            System.out.println(name);
                        //                            listOfFiles.add(name);
                        //                        }

                        //                        StringBuilder reducedFilePath = new StringBuilder();
                        //                        for (int i = 0; i < filesCapture.length - 1; i++) {
                        //                            reducedFilePath.append(filesCapture[i]).append("/");
                        //                        }
                        //                        String finalTrim = reducedFilePath.toString();
                        //                        finalTrim = finalTrim.substring(0, finalTrim.length() - 1);
                        /* Do something with this entry. */


                    }
                }
            }

            if (!isJarMode) { //non-jar
                //System.out.println(filePath);
                InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(currentFilePath);
                //System.out.println("hi" + inputStream.available());

                //listOfFiles = folder.listFiles();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                String currentLine;
                while ((currentLine = br.readLine()) != null) {
                    //System.out.println(currentLine);
                    listOfFiles.add(currentLine);
                }
                br.close();
                //assert listOfFiles != null;
                //Arrays.sort(listOfFiles); //in case the files stored locally are not in alphabetical order
            }
        } catch (NullPointerException | IOException e) {
            throw new DukeException("Content not found!" + "\nPls key 'back' or 'list' to view previous content!");
        }
    }

    private File getFileFromRes(String filepath) throws DukeException {
        URL resource = getClass().getClassLoader().getResource(filepath);
        if (resource == null) {
            throw new DukeException("FUCCCCK");
        } else {
            return new File(resource.getFile());
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
        System.out.println("CURR: " + currentFilePath);
        System.out.println("Default: " + defaultFilePath);
        if (!currentFilePath.equals(defaultFilePath)) {
            if (!currentFilePath.contains(".txt")) {
                System.out.println("case a");
                currentFilePath = gotoParentFilePath(currentFilePath);
            } else {
                System.out.println("case b");
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
     * @throws DukeException When the text file in currentFilePath is not found.
     */
    public String readQuery() throws DukeException {
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
            throw new DukeException("FILE DED BRO");
        }
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
    public void insertQueries() throws DukeException {
        clearQueries();
        loadFiles(currentFilePath);
        for (String listOfFile : listOfFiles) {
            if (listOfFile.contains(".txt")) {
                isDirectory = false;
                filePathQueries.add(listOfFile);
            } else {
                isDirectory = true;
                filePathQueries.add(listOfFile);
            }
        }

    }

    public void clearQueries() {
        filePathQueries.clear();
    }

}