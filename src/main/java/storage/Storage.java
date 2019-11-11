package storage;

import degree.Degree;
import exception.DukeException;
import javafx.Launcher;
import javafx.util.Pair;
import list.DegreeList;
import main.Duke;
import parser.Parser;
import task.TaskList;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


/**
 * Storage class
 * Looks for file in certain folder to be parsed,
 * then it is opened, same file is overwritten.
 *
 * @author Kane Quah
 * @version 1.0
 * @since 08/19
 */
public class Storage {
    private File saveFile;
    private File another_saveFile;
    private String saveFileString;
    private String another_saveFileString;
    private String input = "";
    private String another_input = "";
    private final Path folder = Paths.get("../data/");
    private final String folderName = "../data/";
    //private final Path folder = Paths.get("..\\data\\");
    //private final String folderName = "..\\data\\";
    private HashMap<String, List<String>> data = new HashMap<>();
    private HashMap<String, String> readable = new HashMap<>();
    private List<String> fileNames = new ArrayList<>();
    private List<Pair<String, String>> errorList = new ArrayList<>();
    private boolean jarFlag = false;

    /**
     * Constructs storage class with the target file.
     *
     * @param filePath is name of save file as a string
     */
    public Storage(String filePath, String another_FilePath) throws IOException {
        try {
            if(filePath.isBlank()) {
                throw new DukeException("Save File Must be specified");
            }
            if(another_FilePath.isBlank()) {
                throw new DukeException("Save File Must be specified");
            }
            if(validateFile(filePath)) {
                throw new DukeException("Invalid File Name");
            }
            if (validateFile(another_FilePath)) {
                throw new DukeException("Invalid File Name");
            }
            setSaveFile(filePath, another_FilePath);
        } catch (DukeException e) {
            System.out.println(e.getLocalizedMessage());
            System.out.println("Initializing save.txt");
        }
        finally
        {
            if(input.isBlank()) {
                setSaveFile("save.txt", "savedegree.txt");
            }
        }
        try {
            File jarFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
            if(jarFile.isFile()) {  // Run with JAR file
                jarLoad(jarFile);
                jarFlag = true;
            } else { //run with IDE
                load();
                jarFlag = false;
            }
        } catch (DukeException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }



    /**
     * Sets up a save file for the lists of tasks and degrees.
     *
     * @param file is the name of the save file
     */
    private void setSaveFile(String file, String another_file)
    {
        this.input = file.substring(0, file.indexOf('.'));
        this.another_input = another_file.substring(0, file.indexOf('.'));
        this.saveFileString = folderName + file;
        this.another_saveFileString = folderName + another_file;
        this.saveFile = new File(saveFileString);
        this.another_saveFile = new File(another_saveFileString);
    }



    /**
     * This method reads the folder.
     *
     */
    public void load() throws DukeException {
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(folder)) {
            for (Path path : directoryStream) {
                String file = path.toString().substring(folderName.length());
                //System.out.println("reading... " + file);
                if(validateFile(file))
                {
                    errorList.add(new Pair<>(file, "Invalid File Type"));
                    continue;
                }
                String read = file.substring(0, file.lastIndexOf('.'));
                if(readable.containsKey(read))
                {
                    errorList.add(new Pair<>(file, "Same File Name with Different Extension detected"));
                    continue;
                }
                readable.put(read, path.toString());
                fileNames.add(read);
                //System.out.println(path.toString());
            }
        } catch (IOException ex) {
            throw new DukeException("Error Reading Files");
        }

        for (String file : fileNames) {
            try {
                // put the file's name and its content into the data structure
                System.out.print(readable.get(file));System.out.println(" +" + file);
                //List<String> lines = Files.readAllLines(folder.resolve(readable.get(file)), StandardCharsets.UTF_8);
                List<String> lines = Files.readAllLines(folder.resolve(readable.get(file)), StandardCharsets.UTF_8);
                Path resolvedPath = folder.resolve(readable.get(file));
                data.put(file, lines);
                System.out.println("success for " + file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method to read the internal data files of the jar.
     *
     * @param jarFile the jar file to be read, which is itself
     * @throws IOException if data files are not found
     */
    private void jarLoad(File jarFile) throws IOException {
        final JarFile jar = new JarFile(jarFile);
        final Enumeration<JarEntry> entries = jar.entries(); //gives ALL entries in jar
        while (entries.hasMoreElements()) {
            String pathString = entries.nextElement().getName();
            //The very first run, it will read itself as only data/, so have to skip it
            if (pathString.startsWith("data/") && (pathString.length() > 5)) {
                String file = pathString.substring(5); //file name like save.txt
                String read = file.substring(0, file.lastIndexOf('.')); //file name without the .txt or .csv
                if (read.matches("save")) { //Checks internal save.txt
                    //check existence of external save and savedegrees first
                    File saveDir = new File("../data/");
                    File newFileSave = new File("../data/save.txt");
                    if (!saveDir.exists()) { //if it does not exist, create a dir and use internal save
                        System.out.println("Creating directory: " + saveDir.getName());
                        saveDir.mkdir();
                        newFileSave.createNewFile();

                        InputStreamReader in = new InputStreamReader(getClass().getResourceAsStream("/" + pathString),
                                StandardCharsets.UTF_8);
                        BufferedReader reader = new BufferedReader(in);
                        ArrayList<String> stringList = new ArrayList<>();
                        String str;
                        while ((str = reader.readLine()) != null) {
                            stringList.add(str);
                        }
                        data.put(read, stringList);
                    } else if (!newFileSave.exists()) { //directory exists but no save.txt, use internal
                        newFileSave.createNewFile();

                        InputStreamReader in = new InputStreamReader(getClass().getResourceAsStream("/" + pathString),
                                StandardCharsets.UTF_8);
                        BufferedReader reader = new BufferedReader(in);
                        ArrayList<String> stringList = new ArrayList<>();
                        String str;
                        while ((str = reader.readLine()) != null) {
                            stringList.add(str);
                        }
                        data.put(read, stringList);
                    } else { //if it does exist, use the external save and savedegrees
                        List<String> linesSave = Files.readAllLines(folder.resolve("save.txt"), StandardCharsets.UTF_8);
                        data.put("save", linesSave);
                    }
                } else if (read.matches("savedegree")) { //Checks internal savedegree.txt
                    //check existence of external save and savedegrees first
                    File saveDir = new File("../data/");
                    File newFileSaveDegree = new File("../data/savedegree.txt");
                    if (!saveDir.exists()) { //if it does not exist, create a dir and use internal save and savedegrees
                        System.out.println("Creating directory: " + saveDir.getName());
                        saveDir.mkdir();
                        newFileSaveDegree.createNewFile();

                        InputStreamReader in = new InputStreamReader(getClass().getResourceAsStream("/" + pathString),
                                StandardCharsets.UTF_8);
                        BufferedReader reader = new BufferedReader(in);
                        ArrayList<String> stringList = new ArrayList<>();
                        String str;
                        while ((str = reader.readLine()) != null) {
                            stringList.add(str);
                        }
                        data.put(read, stringList);
                    } else if (!newFileSaveDegree.exists()) { //directory exists but no save files at all, use internal
                        newFileSaveDegree.createNewFile();

                        InputStreamReader in = new InputStreamReader(getClass().getResourceAsStream("/" + pathString),
                                StandardCharsets.UTF_8);
                        BufferedReader reader = new BufferedReader(in);
                        ArrayList<String> stringList = new ArrayList<>();
                        String str;
                        while ((str = reader.readLine()) != null) {
                            stringList.add(str);
                        }
                        data.put(read, stringList);
                    } else { //if it does exist, use the external save and savedegrees
                        List<String> linesSaveDegrees = Files.readAllLines(folder.resolve("savedegree.txt"), StandardCharsets.UTF_8);
                        data.put("savedegree", linesSaveDegrees);
                    }
                } else {
                    InputStreamReader in = new InputStreamReader(getClass().getResourceAsStream("/" + pathString),
                            StandardCharsets.UTF_8);
                    BufferedReader reader = new BufferedReader(in);
                    ArrayList<String> stringList = new ArrayList<>();
                    String str;
                    while ((str = reader.readLine()) != null) {
                        stringList.add(str);
                    }
                    data.put(read, stringList);
                }
            }
        }
    }

    /**
     * Finds the string if it exists
     * Concatenates all the strings in the list with a new line between them, then returns the string
     *
     * @param key is a String related to the data
     * @return single String with newline chars separating each string in the list
     */
    private String getLineOutput(String key)
    {
        String result = "";
        if(data.containsKey(key))
            for(String lines: data.get(key))
            {
                result = result.concat(lines);
                result = result.concat("\n");
            }
        return result;
    }

    /**
     * Fetches the list of strings which represent the output
     *
     * @param key is a String related to the data
     * @return list of strings which is the data linked to they key or null
     */
    public List<String> fetchListOutput(String key)
    {
        return data.getOrDefault(key, null);
    }

    /**
     * This method overwrites the file.
     * It overwrites using the data stored in TaskList currently
     */
    public void store(TaskList list) throws DukeException {
        File saveDir = new File("../data/");
        if (!saveDir.exists()) { //if it does not exist, create a dir and use internal save
            System.out.println("Creating directory: " + saveDir.getName());
            saveDir.mkdir();
        }
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.saveFile))) {
            for (int i = 0; i < list.size(); i++) {
                String fileContent = list.get(i).getType() + " | "
                        + (list.get(i).checkCompletion() ? "1" : "0") + " | " + list.get(i).getDescription();
                if (list.get(i).getType().matches("D|E")) {
                    fileContent += " | " + list.get(i).getDueDate();
                } else if (list.get(i).getType().matches("R|A|W|F")) {
                    fileContent += " | " + list.get(i).getAfter();
                }
                fileContent += " | " + list.get(i).getUserDefinedPriority();
                bufferedWriter.write(fileContent);
                bufferedWriter.newLine();
            }
        } catch (IOException | DukeException e) {
            throw new DukeException("Storage Attempt Failed");
        }
    }

  /**
   * Adds a degree to storage
   *
   * @params degrees
   * @throws DukeException
   */
    public void add_degrees(DegreeList degrees) throws DukeException {
        File saveDir = new File("../data/");
        if (!saveDir.exists()) { //if it does not exist, create a dir and use internal save
            System.out.println("Creating directory: " + saveDir.getName());
            saveDir.mkdir();
        }
        ArrayList<String> degree_list = degrees.getDegrees();
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(this.another_saveFile))) {
            for(int i = 0; i < degree_list.size(); i++) {
                String fileContent = "degree-"+degree_list.get(i)+"-"+i;
                bw.write(fileContent);
                bw.newLine();
            }
        } catch (IOException e) {
            throw new DukeException("Storage failed");
        }
    }


  /**
   * Valid File Name checker
   *
   * @returns true if the file name is valid
   */
    private boolean validateFile (String file) {
        if(file.isBlank()) {
            return true;
            //throw new DukeException("File Name cannot be blank");
        }
        if(file.indexOf('.', file.indexOf('.') + 1) != -1) {
            return true;
            //throw new DukeException("File Name should not contain multiple dots");
        }
        if(file.lastIndexOf('.') == -1 || file.lastIndexOf('.') == file.length() - 1) {
            return true;
            //throw new DukeException("File Name has no extension");
        }
        return !file.substring(file.lastIndexOf('.') + 1).matches(Parser.acceptedExtensions);
            //throw new DukeException("File should be of extensions: " + Parser.acceptedExtensions.replace("|", ", "));
    }

    /**
     * Returns TaskList in expected format
     *
     * @return returns an empty string if not initialized or the tasklist with '\n' chars separating
     */
    public String getTaskList() { return getLineOutput(input);}
}
