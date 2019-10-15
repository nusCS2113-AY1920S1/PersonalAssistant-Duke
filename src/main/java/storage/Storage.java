package storage;

import exception.DukeException;
import task.TaskList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;
import java.io.FileReader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


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
    private List<String> fileNames = new ArrayList<>();

    /**
     * Constructs storage class with the target file.
     *
     * @param filePath Directory the save file as a string
     */
    public Storage(String filePath) {
        //URL path = Duke.class.getResource(filePath);
        //forDuke working directory affects!!!!
        //String file = "./data/" + filePath;
        //for runTest
        saveFileString = folderName + filePath;
        this.saveFile = new File(saveFileString);
        System.out.println(saveFileString);
        /*encoding is ANSI */
    }

    /**
     * This method reads the folder.
     *
     * @return String this returns the file's lines separated by "\\n" line symbols.
     */
    public String load() throws DukeException {
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(folder)) {
            for (Path path : directoryStream) {
                fileNames.add(path.toString());
                //System.out.println(path.toString());
            }
        } catch (IOException ex) {
            throw new DukeException("Error Reading Files");
        }
        for (String file : fileNames) {
            try {
                // put the file's name and its content into the data structure
                List<String> lines = Files.readAllLines(folder.resolve(file));
                data.put(file, lines);
                //System.out.println("success for " + file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        /*
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(saveFile))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                this.input += line + "\n";
                line = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            // exception handling
            System.out.println("file not found");
        } catch (IOException e) {
            // exception handling
            System.out.println("I/O Issues");
        }
        return this.input;
        */
        String result = "";
        for(String lines: data.get(saveFileString))
        {
            result = result.concat(lines);
            result = result.concat("\n");
        }
        return result;
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
            // exception handling
            throw new DukeException("Storage Attempt Failed");
        }
    }

}
