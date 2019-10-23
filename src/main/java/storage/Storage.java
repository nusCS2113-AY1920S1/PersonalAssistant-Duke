package storage;

import degree.Degree;
import exception.DukeException;
import javafx.util.Pair;
import parser.Parser;
import task.TaskList;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    private String saveFileString;
    private String input = "";
    private final Path folder = Paths.get("..\\data\\");
    private final String folderName = "..\\data\\";
    private HashMap<String, List<String>> data = new HashMap<>();
    private HashMap<String, String> readable = new HashMap<>();
    private List<String> fileNames = new ArrayList<>();
    private List<Pair<String, String>> errorList = new ArrayList<>();

    /**
     * Constructs storage class with the target file.
     *
     * @param filePath is name of save file as a string
     */
    public Storage(String filePath) {
        try {
            if(filePath.isBlank()) {
                throw new DukeException("Save File Must be specified");
            }
            if(validateFile(filePath)) {
                throw new DukeException("Invalid File Name");
            }
            setSaveFile(filePath);
        } catch (DukeException e) {
            System.out.println(e.getLocalizedMessage());
            System.out.println("Initializing save.txt");
        }
        finally
        {
            if(input.isBlank()) {
                setSaveFile("save.txt");
            }
        }
        try {
            load();
        } catch (DukeException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    /**
     * Sets up a save file for the lists of tasks
     *
     * @param file is the name of the save file
     */
    private void setSaveFile(String file)
    {
        this.input = file.substring(0, file.indexOf('.'));
        this.saveFileString = folderName + file;
        this.saveFile = new File(saveFileString);
    }

    /**
     * This method reads the folder.
     *
     */
    private void load() throws DukeException {
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
                List<String> lines = Files.readAllLines(folder.resolve(readable.get(file)));
                data.put(file, lines);
                //System.out.println("success for " + file);
            } catch (IOException e) {
                e.printStackTrace();
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
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.saveFile))) {
            for (int i = 0; i < list.size(); i++) {
                String fileContent = list.get(i).getType() + " | "
                        + (list.get(i).checkCompletion() ? "1" : "0") + " | " + list.get(i).getDescription();
                if (list.get(i).getType().matches("D|E")) {
                    fileContent += " | " + list.get(i).getDueDate();
                } else if (list.get(i).getType().matches("R|A|W|F")) {
                    fileContent += " | " + list.get(i).getAfter();
                }
                bufferedWriter.write(fileContent);
                bufferedWriter.newLine();
            }
        } catch (IOException | DukeException e) {
            throw new DukeException("Storage Attempt Failed");
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
