package moomoo.feature.storage;

import moomoo.feature.MooMooException;
import moomoo.feature.category.Category;
import moomoo.feature.category.CategoryList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CategoryStorage extends Storage {

    private static String filePath = "data/category.txt";

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
                return populateDefaultCategories(categoryList);
            } else {
                return populateDataCategories(categoryList);
            }
        } catch (IOException e) {
            throw new MooMooException("Sorry your data could not be loaded, but you may continue using the app.");
        }
    }

    private static CategoryList populateDataCategories(CategoryList categoryList) throws IOException {
        List<String> input = Files.readAllLines(Paths.get(filePath));
        for (String line : input) {
            String categoryName = line.trim();
            if (!categoryList.hasCategory(categoryName)) {
                categoryList.add(new Category(categoryName));
            }
        }
        return categoryList;
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
     * Removes any line of data in text file with starts with the entry name entered.
     * @param entry string to search for and delete
     * @throws MooMooException thrown if unable to write to file
     */
    public static void deleteFromFile(String entry) throws MooMooException {
        try {
            List<String> data = Files.readAllLines(Paths.get(filePath));
            for (String line : data) {
                if (line.contentEquals(entry)) {
                    data.remove(line);
                    break;
                }
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
