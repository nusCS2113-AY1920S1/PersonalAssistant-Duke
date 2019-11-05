package moomoo.feature.storage;

import moomoo.feature.MooMooException;
import moomoo.feature.category.Category;
import moomoo.feature.category.CategoryList;
import moomoo.feature.category.Expenditure;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CategoryStorage extends Storage {

    private static String filePath = "data/expenditure.txt";

    /**
     * Initializes storage and the filepath for each file.
     *
     * @param filePath category file path
     */
    public CategoryStorage(String filePath) {
        super(filePath, filePath);
    }

    /**
     * Reads from a text file and loads data into a category list.
     * @return category with data read from file
     * @throws MooMooException if unable to read file
     */
    public static CategoryList loadFromFile() throws MooMooException {
        CategoryList categoryList = new CategoryList();
        try {
            File myNewFile = new File(filePath);
            if (myNewFile.createNewFile()) {
                return populateDefaultExpenditures(categoryList);
            } else {
                List<String> input = Files.readAllLines(Paths.get(filePath));
                for (String line : input) {
                    String[] entry = line.split(" \\| ");
                    if (entry.length == 4) {
                        String category = entry[0];
                        String name = entry[1];
                        double cost = Double.parseDouble(entry[2]);
                        LocalDate date = LocalDate.parse(entry[3]);
                        Expenditure expenditure = new Expenditure(name, cost, date);
                        if (!categoryList.hasCategory(category)) {
                            categoryList.add(new Category(category));
                        }
                        categoryList.get(category).add(expenditure);
                    } else if (entry.length == 1) {
                        String categoryName = entry[0];
                        if (!categoryList.hasCategory(categoryName)) {
                            categoryList.add(new Category(categoryName));
                        }
                    } else {
                        deleteFromFile(line);
                    }
                }
                return categoryList;
            }
        } catch (IOException e) {
            throw new MooMooException("Unable to read file. Please retry again.");
        }
    }

    /**
     * Creates a file if necessary and stores each expenditure and its category into the file.
     * @param expenditure Expenditure object that stores the name, amount and date for each expenditure.
     * @param category Category that the expenditure is stored in.
     * @throws MooMooException thrown if file cannot be written to.
     */
    public static void saveToFile(Expenditure expenditure, String category) throws MooMooException {
        createFileAndDirectory(filePath);
        try {
            String newExpenditure = Files.readString(Paths.get(filePath));
            newExpenditure += (category + " | " + expenditure.toString() + " | " + expenditure.getCost()
                    + " | " + expenditure.getDate() + "\n");
            Files.writeString(Paths.get(filePath), newExpenditure);
        } catch (IOException e) {
            throw new MooMooException("Unable to write to file. Expenditure will not be saved.");
        }
    }

    /**
     * Creates a file if necessary and stores the category into the file.
     * @param category category to be stored in file
     * @throws MooMooException thrown if file is not written to
     */
    public static void saveToFile(String category) throws MooMooException {
        createFileAndDirectory(filePath);
        try {
            String readString = Files.readString(Paths.get(filePath));
            readString += (category + "\n");
            Files.writeString(Paths.get(filePath), readString);
        } catch (IOException e) {
            throw new MooMooException("Unable to write to file. Category will not be saved.");
        }
    }

    /**
     * Removes any line of data in text file with starts with the category name entered.
     * @param category string to search for and delete
     * @throws MooMooException thrown if unable to write to file
     */
    public static void deleteFromFile(String category) throws MooMooException {
        try {
            List<String> data = Files.readAllLines(Paths.get(filePath));
            ArrayList<String> toDelete = new ArrayList<>();
            for (String entry : data) {
                if (entry.startsWith(category)) {
                    toDelete.add(entry);
                }
            }
            for (String entry : toDelete) {
                data.remove(entry);
            }
            Files.write(Paths.get(filePath), data);
        } catch (IOException e) {
            throw new MooMooException("Unable to write to file. Please retry again.");
        }
    }


    /**
     * Creates a populated array of default categories.
     * @param categoryList category list
     * @return populated category list
     * @throws MooMooException throws exception if file cannot be found
     */
    private static CategoryList populateDefaultExpenditures(CategoryList categoryList)
            throws MooMooException {
        categoryList.add(new Category("misc"));
        categoryList.add(new Category("food"));
        categoryList.add(new Category("transportation"));
        categoryList.add(new Category("shopping"));
        saveToFile("misc");
        saveToFile("food");
        saveToFile("transportation");
        saveToFile("shopping");
        return categoryList;
    }
}
