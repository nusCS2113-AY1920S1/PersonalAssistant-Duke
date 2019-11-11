package moomoo.feature.storage;

import moomoo.MooMoo;
import moomoo.feature.Budget;
import moomoo.feature.MooMooException;
import moomoo.feature.ScheduleList;
import moomoo.feature.Ui;
import moomoo.feature.category.Category;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Handles storage and retrieval of the tasks.
 */
public class Storage {
    private String budgetFilePath;
    private String scheduleFilePath;

    /**
     * Initializes storage and the filepath for each file.
     * @param budgetFilePath File path to store the budget into.
     * @param scheduleFilePath File path to store all categories
     */
    public Storage(String budgetFilePath, String scheduleFilePath) {
        this.budgetFilePath = budgetFilePath;
        this.scheduleFilePath = scheduleFilePath;
    }

    public Storage() {

    }

    /**
     * Creates the directory and file as given by the file path initialized in the constructor.
     */
    public static void createFileAndDirectory(String filePath) throws MooMooException {
        try {
            File myNewFile = new File(filePath);
            myNewFile.getParentFile().mkdir();
            myNewFile.createNewFile();
        } catch (IOException e) {
            throw new MooMooException("Unable to create file. Your data will not be saved.");
        }
    }

    /**
     * Loads in budgetFile not found. New file will be created from an existing file into a created HashMap object.

     * @return HashMap object consisting of the categories and corresponding budget read from file.
     */
    public HashMap<String, Double> loadBudget(ArrayList<Category> catList) {
        try {
            if (Files.isRegularFile(Paths.get(this.budgetFilePath))) {
                HashMap<String, Double> loadedBudgets = new HashMap<>();
                List<String> readInput = Files.readAllLines(Paths.get(this.budgetFilePath));
                String category = "";
                double budget;

                for (int i = 0; i < readInput.size(); ++i) {
                    if (i % 2 == 1) {
                        if (!"".equals(category)) {
                            try {
                                budget = Double.parseDouble(readInput.get(i));
                            } catch (NumberFormatException e) {
                                Ui.setOutput("Budget file corrupted, please delete it. Your data will be reset.");
                                return null;
                            }
                            loadedBudgets.put(category, budget);
                        }
                        category = "";
                    } else {
                        if (isInCategoryList(catList, readInput.get(i))) {
                            category = readInput.get(i).toLowerCase();
                        }
                    }
                }
                return loadedBudgets;
            } else {
                Ui.setOutput("Budget File not found. New file will be created");
                createFileAndDirectory(this.budgetFilePath);
                return null;
            }
        } catch (IOException | MooMooException e) {
            Ui.setOutput("Unable to write to file. Please retry again.");
        }
        return null;
    }

    /**
     * Loads scheduled payments from file into an ArrayList object.
     * @return ArrayList object consisting of the scheduled payments read from the file
     */
    public HashMap<String, ArrayList<String>> loadCalendar() {
        HashMap<String, ArrayList<String>> scheduleMap = new HashMap<>();
        try {
            if (Files.isRegularFile(Paths.get(this.scheduleFilePath))) {
                List<String> input = Files.readAllLines(Paths.get(this.scheduleFilePath));
                for (String s : input) {
                    if (s.startsWith("d/")) {
                        String[] splitInput = s.split(" ", 2);
                        String date = splitInput[0].replace("d/","");
                        String task = splitInput[1].replace("n/", "");
                        if (scheduleMap.containsKey(date)) {
                            ArrayList<String> tasks = scheduleMap.get(date);
                            tasks.add(task);
                            scheduleMap.replace(date, tasks);
                        } else {
                            ArrayList<String> newTasks = new ArrayList<>();
                            newTasks.add(task);
                            scheduleMap.put(date, newTasks);
                        }
                    }
                }
                return scheduleMap;
            } else {
                Ui.setOutput("Schedule File not found. New file will be created");
                createFileAndDirectory(this.scheduleFilePath);
            }
        } catch (IOException | MooMooException e) {
            Ui.setOutput("Unable to read file. Please retry again.");
        }
        return null;
    }

    /**
     * Creates a file if necessary and stores each category and its budget into the file.
     * @param budget Budget object that stores the budget for each category
     * @throws MooMooException thrown if file cannot be written to.
     */
    public void saveBudgetToFile(Budget budget) throws MooMooException {
        createFileAndDirectory(this.budgetFilePath);
        String toSave = "";
        Iterator<Map.Entry<String, Double>> budgetIterator = budget.getBudget().entrySet().iterator();
        while (budgetIterator.hasNext()) {
            Map.Entry<String, Double> mapElement = budgetIterator.next();
            toSave += mapElement.getKey() + "\n" + mapElement.getValue() + "\n";
        }
        try {
            Files.writeString(Paths.get(this.budgetFilePath), toSave);
        } catch (Exception e) {
            throw new MooMooException("Unable to write to budget file. Please retry again.");
        }
    }

    /**
     * Writes scheduled payments to file.
     */
    public void saveScheduleToFile(ScheduleList calendar) throws MooMooException {
        createFileAndDirectory(this.scheduleFilePath);

        String list = "Schedule: \n";
        Iterator<Map.Entry<String, ArrayList<String>>> scheduleIterator = calendar.calendar.entrySet().iterator();
        while (scheduleIterator.hasNext()) {
            Map.Entry<String, ArrayList<String>> element = scheduleIterator.next();
            for (String c : element.getValue()) {
                list += "d/" + element.getKey() + " n/" + c + "\n";
            }
        }
        try {
            Files.writeString(Paths.get(this.scheduleFilePath), list);
        } catch (Exception e) {
            throw new MooMooException("Unable to write to file. Please try again.");
        }
    }

    /**
     * Checks if a category is found in the list of categories.
     * @return true if it exists.
     */
    private boolean isInCategoryList(ArrayList<Category> catList, String value) {
        for (Category cat : catList) {
            if (cat.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Preload data by copying from resources directory in jar file.
     * @throws MooMooException Thrown when file does not exist in jar file.
     */
    public void preloadData() throws MooMooException {
        File myNewFile = new File("data");

        if (!myNewFile.isDirectory()) {
            myNewFile.mkdir();

            InputStream inStream = null;
            OutputStream outputStream = null;
            String jarFileLocation;

            String[] outputFiles = {"budget.txt", "category.txt", "expenditure.txt", "schedule.txt"};

            try {
                for (int i = 0; i < 4; ++i) {
                    String outputFile = outputFiles[i];
                    inStream = MooMoo.class.getResourceAsStream("/" + outputFile);
                    if (inStream == null) {
                        throw new MooMooException("Error creating default files. Please add your own values.");
                    }

                    int readBytes;
                    byte[] buffer = new byte[2048];
                    jarFileLocation = new File(MooMoo.class.getProtectionDomain().getCodeSource()
                            .getLocation().toURI().getPath()).getParentFile().getPath().replace('\\', '/');
                    outputStream = new FileOutputStream(jarFileLocation + "/data/" + outputFile);
                    while ((readBytes = inStream.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, readBytes);
                    }
                }
                inStream.close();
                outputStream.close();
            } catch (Exception e) {
                throw new MooMooException("Error creating default files. Please add your own values.");
            }
        }

    }
}