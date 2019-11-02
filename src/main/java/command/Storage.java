package command;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import common.AlphaNUSException;
import project.Project;

import java.io.*;
import java.lang.reflect.Type;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileWriter;
import java.util.LinkedHashMap;

/**
 * command.Storage that saves and loads the tasklist of the user.
 */
public class Storage {
    private static String ProjectsFilePath = "localdata/Projects.json";
    private static String CommandListFilePath = "localdata/CommandList.json";

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
        Type ProjectMapType = new TypeToken<LinkedHashMap<String, Project>>(){}.getType();
        LinkedHashMap<String, Project> projectmap;
        try {
            File file = new File(ProjectsFilePath);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            projectmap = gson.fromJson(bufferedReader, ProjectMapType);
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
}