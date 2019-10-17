package moomoo.task;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Handles storage and retrieval of the tasks.
 */
public class Storage {
    private DecimalFormat df;
    private String budgetFilePath;
    private String expenditureFilePath;
    private String categoryFilePath;
    private String scheduleFilePath;

    /**
     * Initializes storage and the filepath for each file.
     * @param budgetFilePath File path to store the budget into.
     * @param expenditureFilePath File path to store all expenditures
     * @param categoryFilePath File path to store all categories
     */
    public Storage(String budgetFilePath, String expenditureFilePath, String categoryFilePath,
                   String scheduleFilePath) {
        this.budgetFilePath = budgetFilePath;
        this.expenditureFilePath = expenditureFilePath;
        this.categoryFilePath = categoryFilePath;
        this.scheduleFilePath = scheduleFilePath;
        df = new DecimalFormat("#.00");
    }

    /**
     * Loads in categories from an existing file into a created ArrayList object.
     * @return ArrayList object consisting of the categories read from the file.
     * @throws MooMooException Thrown when the file does not exist
     */
    public ArrayList<Category> loadCategories() throws MooMooException {
        ArrayList<Category> categoryArrayList = new ArrayList<Category>();

        return categoryArrayList;
    }

    /**
     * Loads in expenditures from an existing file into a created ArrayList object.
     * @return ArrayList object consisting of the expenditures read from the file.
     * @throws MooMooException Thrown when the file does not exist
     */
    public ArrayList<Expenditure> loadExpenditures() throws MooMooException {
        ArrayList<Expenditure> expenditureArrayList = new ArrayList<Expenditure>();

        return expenditureArrayList;
    }

    /**
     * Loads in budgetFile not found. New file will be created from an existing file into a created HashMap object.
     * @return HashMap object consisting of the categories and corresponding budget read from file.
     * @throws MooMooException Thrown when the file does not exist
     */
    public HashMap<String, Double> loadBudget(CategoryList catList) throws MooMooException {
        try {
            if (Files.isRegularFile(Paths.get(this.budgetFilePath))) {
                HashMap<String, Double> loadedBudgets = new HashMap<String, Double>();
                List<String> input = Files.readAllLines(Paths.get(this.budgetFilePath));

                for (String value : input) {
                    if (value.charAt(0) == 'B') {
                        String[] splitInput = value.split(" \\| ");
                        String category = "";
                        double budget = 0;
                        for (int i = 1; i < splitInput.length; ++i) {
                            if (i % 2 == 0) {

                                if (!category.equals("")) {
                                    budget = Double.parseDouble(splitInput[i]);
                                    loadedBudgets.put(category, budget);
                                }
                                category = "";
                            } else {
                                if (inCategoryList(catList, splitInput[i])) {
                                    category = splitInput[i];
                                }
                            }
                        }
                        return loadedBudgets;
                    }
                }
                if (loadedBudgets == null) {
                    throw new MooMooException("Unable to load budget from file. Please reset your budget.");
                }
                return loadedBudgets;
            } else {
                throw new MooMooException("Budget File not found. New file will be created");
            }
        } catch (IOException e) {
            throw new MooMooException("Unable to write to file. Please retry again.");
        }
    }

    /**
     * Loads scheduled payments from file into an ArrayList object.
     * @return ArrayList object consisting of the scheduled payments read from the file
     * @throws MooMooException Thrown when file does not exist
     */
    public ArrayList<SchedulePayment> loadCalendar() throws MooMooException {
        ArrayList<SchedulePayment> scheduleArray = new ArrayList<>();
        try {
            if (Files.isRegularFile(Paths.get(this.scheduleFilePath))) {
                List<String> input = Files.readAllLines(Paths.get(this.scheduleFilePath));
                for (String s : input) {
                    if (s.startsWith("d/")) {
                        String[] splitInput = s.split(" ", 2);
                        String date = splitInput[0].replace("d/","");
                        String task = splitInput[1].replace("t/", "");
                        SchedulePayment day = new SchedulePayment(date, task);
                        scheduleArray.add(day);
                    }
                }
                return scheduleArray;
            } else {
                throw new MooMooException("File not found. New file will be created");
            }
        } catch (IOException e) {
            throw new MooMooException("Unable to read file. Please retry again.");
        }
    }

    /**
     * Creates the directory and file as given by the file path initialized in the constructor.
     */
    private void createFileAndDirectory(String filePath) throws MooMooException {
        try {
            File myNewFile = new File(filePath);
            Files.createDirectory(Paths.get(myNewFile.getParent()));
            Files.createFile(Paths.get(filePath));
        } catch (FileAlreadyExistsException e) {
            return;
        } catch (IOException e) {
            throw new MooMooException("Unable to create file. Please restart the program");
        }
    }

    private LocalDateTime parseDate(String dateToParse) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
            return LocalDateTime.parse(dateToParse, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Converts the LocalDateTime object into printable string for writing to file.
     * @param dateTime LocalDateTime object to be converted
     * @return String format of the LocalDateTime object
     */
    private String unparseDate(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        return dateTime.format(formatter);
    }

    /**
     * Creates a file if necessary and stores each category and its budget into the file.
     * @param budget Budget object that stores the budget for each category
     * @throws MooMooException thrown if file cannot be written to.
     */
    public void saveBudgetToFile(Budget budget) throws MooMooException {
        createFileAndDirectory(this.budgetFilePath);
        String toSave = "B";
        Iterator budgetIterator = budget.getBudget().entrySet().iterator();
        while (budgetIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry)budgetIterator.next();
            toSave += " | " + mapElement.getKey() + " | " + mapElement.getValue();
        }
        try {
            Files.writeString(Paths.get(this.budgetFilePath), toSave);
        } catch (Exception e) {
            throw new MooMooException("Unable to write to file. Please retry again.");
        }
    }

    /**
     * Writes scheduled payments to file.
     */
    public void saveScheduleToFile(ScheduleList calendar) throws MooMooException {
        createFileAndDirectory(this.scheduleFilePath);

        String list = "Schedule: \n";
        System.out.println(calendar.fullSchedule.size());
        for (SchedulePayment c : calendar.fullSchedule) {
            list += "d/" + c.date + " t/" + c.tasks + "\n";
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
    private boolean inCategoryList(CategoryList catList, String value) {
        for (Category cat : catList.getCategoryList()) {
            if (cat.toString().equals(value)) {
                return true;
            }
        }
        return false;
    }
}