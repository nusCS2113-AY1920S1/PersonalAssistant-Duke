package command;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import common.AlphaNUSException;
import project.Project;


import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * command.Storage that saves and loads the tasklist of the user.
 */
public class Storage {
    private static String ProjectsFilePath = "localdata/Projects.json";
    private static String CommandListFilePath = "localdata/history.json";

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Writes current projectmap in ProjectManager to local storage.
     * @param projectmap LinkedHashMap of projects.
     * @throws AlphaNUSException If the file cannot be written to.
     */
    public void writeToProjectsFile(LinkedHashMap<String, Project> projectmap) throws AlphaNUSException {
        String toWriteStr = gson.toJson(projectmap);
        try {
            File file = new File(ProjectsFilePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            for (String lineStr : toWriteStr.split("\n")) {
                bufferedWriter.write(lineStr);
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException e) {
            throw new AlphaNUSException("Unable to write to file: " + ProjectsFilePath);
        }
    }

    /**
     * Read HashMap of projects from local storage and returns it.
     * @return HashMap of Project objects stored in local storage.
     * @throws AlphaNUSException If the file cannot be read.
     */
    public LinkedHashMap<String, Project> readFromProjectsFile() throws AlphaNUSException {
        Type projectmaptype = new TypeToken<LinkedHashMap<String, Project>>(){}.getType();
        LinkedHashMap<String, Project> projectmap;
        try {
            File file = new File(ProjectsFilePath);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            projectmap = gson.fromJson(bufferedReader, projectmaptype);
            bufferedReader.close();
        } catch (Exception e) {
            throw new AlphaNUSException("Unable to read file");
        }
        return projectmap;
    }

    /**
     * Saves the tasklist of the user as an ArrayList containing the task object.
     * @param str TODO
     *
     */
    public static void remove(String str){
        //TODO
    }

    public void writeToCommandsFile(String command) throws AlphaNUSException {
        String toWriteStr = gson.toJson(command);
        try {
            File file = new File(CommandListFilePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
                bufferedWriter.write(toWriteStr);
                bufferedWriter.newLine();
                bufferedWriter.close();
        } catch (IOException e) {
            throw new AlphaNUSException("Unable to write to file: " + CommandListFilePath);
        }
    }
    public ArrayList<String> readFromCommandsFile() throws AlphaNUSException {
        //Type commandListtype = new TypeToken<ArrayList<String>>(){}.getType();
        String line = null;
        ArrayList<String> List = new ArrayList<String>();
        try {
            File file = new File(CommandListFilePath);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            while((line = bufferedReader.readLine()) != null) {
               List.add(line);
            }
            bufferedReader.close();
        } catch (Exception e) {
            throw new AlphaNUSException("Unable to read file");
        }
        return List;
    }
}