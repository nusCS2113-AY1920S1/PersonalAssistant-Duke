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

public class ExpenditureStorage extends Storage {

    private static String filePath = "data/expenditure.txt";

    /**
     * Initializes storage and the filepath for each file.
     *
     * @param filePath category file path
     */
    public ExpenditureStorage(String filePath) {
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
                return populateDefaultCategories(categoryList);
            } else {
                return populateDataExpenditure(categoryList);
            }
        } catch (IOException e) {
            throw new MooMooException("Sorry your data could not be loaded, but you may continue using the app.");
        }
    }

    private static CategoryList populateDataExpenditure(CategoryList categoryList) throws IOException, MooMooException {
        List<String> input = Files.readAllLines(Paths.get(filePath));
        for (String line : input) {
            String[] entry = line.split(" \\| ");
            if (entry.length == 4) {
                loadExpenditure(categoryList, entry);
            } else if (entry.length == 1) {
                loadCategory(categoryList, entry[0]);
            } else {
                deleteFromFile(line);
            }
        }
        saveToFile(categoryList.getCategoryList());
        return categoryList;
    }

    private static void loadCategory(CategoryList categoryList, String s) {
        String categoryName = s.trim();
        if (!categoryList.hasCategory(categoryName)) {
            categoryList.add(new Category(categoryName));
        }
    }

    private static void loadExpenditure(CategoryList categoryList, String[] entry) throws MooMooException {
        String category = entry[0];
        String name = entry[1];
        double cost = Double.parseDouble(entry[2]);
        LocalDate date = LocalDate.parse(entry[3]);
        Expenditure expenditure = new Expenditure(name, cost, date, category);
        if (!categoryList.hasCategory(category)) {
            categoryList.add(new Category(category));
        }
        categoryList.get(category).add(expenditure);
    }

    /**
     * Creates a file if necessary and stores the entry into the file.
     * @param entry entry to be stored in file
     * @throws MooMooException thrown if file is not written to
     */
    public static void saveToFile(String entry) throws MooMooException {
        createFileAndDirectory(filePath);
        try {
            String readString = Files.readString(Paths.get(filePath));
            readString += (entry + "\n");
            Files.writeString(Paths.get(filePath), readString);
        } catch (IOException e) {
            throw new MooMooException("Unable to write to file. Category will not be saved.");
        }
    }

    /**
     * Creates a file if necessary an entire category list into the file.
     *
     * @param categoryList the category list to be saved
     * @throws MooMooException thrown if file is not written to
     */
    private static void saveToFile(ArrayList<Category> categoryList) throws MooMooException {
        createFileAndDirectory(filePath);
        try {
            String readString = "";
            Files.writeString(Paths.get(filePath), readString);
        } catch (IOException e) {
            throw new MooMooException("Unable to write to file. Category will not be saved.");
        }
        for (Category category : categoryList) {
            for (Expenditure expenditure : category.getCategory()) {
                saveToFile(expenditure.toString());
            }
        }
    }

    /**
     * Removes any line of data in text file with starts with the entry name entered.
     * @param entry string to search for and delete
     * @throws MooMooException thrown if unable to write to file
     */
    public static void deleteFromFile(String entry) throws MooMooException {
        try {
            List<String> data = Files.readAllLines(Paths.get(filePath));
            ArrayList<String> toDelete = new ArrayList<>();
            for (String line : data) {
                if (line.startsWith(entry)) {
                    toDelete.add(line);
                }
            }
            for (String line : toDelete) {
                data.remove(line);
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
    private static CategoryList populateDefaultCategories(CategoryList categoryList)
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
