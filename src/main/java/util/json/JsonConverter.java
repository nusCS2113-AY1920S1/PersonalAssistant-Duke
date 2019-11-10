package util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import exceptions.DukeException;
import models.project.Project;
import util.log.ArchDukeLogger;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class JsonConverter {
    private String userDirectory = System.getProperty("user.dir");

    /**
     * Method that is responsible for saving Projects Data by using GSON library to convert to a human editable JSON
     * file.
     */
    public void saveProject(Project project) {
        Gson gson = new GsonBuilder()
                .enableComplexMapKeySerialization()
                .setPrettyPrinting()
                .create();
        try {
            ArchDukeLogger.logDebug(JsonConverter.class.getName(), "Saving to file.");
            FileWriter fileWriter = new FileWriter(userDirectory + "/"
                                                    + project.getName() + ".json");
            gson.toJson(project, fileWriter);
            fileWriter.flush();
            fileWriter.close();
            ArchDukeLogger.logDebug(JsonConverter.class.getName(), "File saved.");
        } catch (IOException err) {
            ArchDukeLogger.logError(JsonConverter.class.getName(), "Save file is not found or not created");
        }
    }

    /**
     * Method to delete the relevant json of original Project when user wishes to delete a project.
     * @param project : selected Project to be deleted
     * @throws DukeException : Exception thrown when JSON of project cannot be found
     */
    public void deleteProject(Project project) throws DukeException {
        String selectedProjectJson = userDirectory + "/" + project.getName() + ".json";
        File file = new File(selectedProjectJson);
        if (!file.delete()) {
            throw new DukeException("JSON of project not found");
        }
    }

    /**
     * Method responsible for loading Projects Data from hard coded directory where savedProjects.json file is located
     */
    public ArrayList<Project> loadAllProjectsData() {
        Gson gson = new Gson();
        ArrayList<Project> allProjects = new ArrayList<>();
        File directory = new File(userDirectory);
        File[] allProjectJson = directory.listFiles((file, name) -> name.endsWith(".json"));
        for (File projectJson : allProjectJson) {
            try (FileReader fileReader = new FileReader(projectJson)) {
                ArchDukeLogger.logDebug(JsonConverter.class.getName(), "Loading saved file.");
                Project newProject = gson.fromJson(fileReader, new TypeToken<Project>(){}.getType());
                allProjects.add(newProject);
                ArchDukeLogger.logDebug(JsonConverter.class.getName(), "Saved file loaded.");
            } catch (IOException err) {
                ArchDukeLogger.logError(JsonConverter.class.getName(), "Saved file not loaded");
                return allProjects;
            }
        }

        return allProjects;
    }

    /**
     * Opens an InputStream to try get resources from within a packaged jar.
     * Resources are hardcoded in.
     * @return : Returns an ArrayList of Project representing all Projects packaged in the jar file.
     */
    public ArrayList<Project> getResourcesInJar() {
        Gson gson = new Gson();
        ArrayList<Project> allProjects = new ArrayList<>();
        try {
            InputStream is = getClass().getResourceAsStream("/initdata/avengers.json");
            InputStreamReader isr = new InputStreamReader(is);
            Project newProject = gson.fromJson(isr, new TypeToken<Project>(){}.getType());
            allProjects.add(newProject);
        } catch (NullPointerException err) {
            return allProjects;
        }
        return allProjects;
    }
}
