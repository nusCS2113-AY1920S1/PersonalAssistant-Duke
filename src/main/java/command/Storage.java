package command;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import common.AlphaNUSException;
import project.Fund;
import project.Project;


import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;

/**
 * command.Storage that saves and loads the tasklist of the user.
 */
public class Storage {
    private static String projectsfilepath = "localdata/Projects.json";
    private static String commandlistfilepath = "localdata/CommandList.json";
    private static String fundfilepath = "localdata/Fund.json";

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Writes current projectmap in ProjectManager to local storage.
     * @param projectmap LinkedHashMap of projects.
     * @throws AlphaNUSException If the file cannot be written to.
     */
    public void writeToProjectsFile(LinkedHashMap<String, Project> projectmap) throws AlphaNUSException {
        String toWriteStr = gson.toJson(projectmap);
        try {
            File file = new File(projectsfilepath);
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
            throw new AlphaNUSException("Unable to write to file: " + projectsfilepath);
        }
    }

    /**
     * Writes current Fund to local storage.
     * @param fund Fund object containing fund details.
     * @throws AlphaNUSException If the file cannot be written to.
     */
    public void writeToFundFile(Fund fund) throws AlphaNUSException {
        String toWriteStr = gson.toJson(fund);
        try {
            File file = new File(fundfilepath);
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
            throw new AlphaNUSException("Unable to write to file: " + fundfilepath);
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
            File file = new File(projectsfilepath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            projectmap = gson.fromJson(bufferedReader, projectmaptype);
            bufferedReader.close();
            if (projectmap == null) {
                projectmap = new LinkedHashMap<>();
            }
        } catch (Exception e) {
            throw new AlphaNUSException("Unable to read file");
        }
        return projectmap;
    }

    /**
     * Read Fund from local storage and return it.
     * @return Fund stored in local storage.
     * @throws AlphaNUSException If the file cannot be read.
     */
    public Fund readFromFundFile() throws AlphaNUSException {
        Type fundtype = new TypeToken<Fund>(){}.getType();
        Fund fund;
        try {
            File file = new File(fundfilepath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            fund = gson.fromJson(bufferedReader, fundtype);
            bufferedReader.close();
            if (fund == null) {
                fund = new Fund();
            }
        } catch (Exception e) {
            throw new AlphaNUSException("Unable to read file");
        }
        return fund;
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