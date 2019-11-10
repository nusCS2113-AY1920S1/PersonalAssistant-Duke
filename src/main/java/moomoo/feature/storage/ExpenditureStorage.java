package moomoo.feature.storage;

import moomoo.feature.MooMooException;
import moomoo.feature.category.CategoryList;
import moomoo.feature.category.Expenditure;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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
     * @throws MooMooException if unable to read file
     */
    public static void loadFromFile(CategoryList categoryList) throws MooMooException {
        try {
            File myNewFile = new File(filePath);
            if (!myNewFile.createNewFile()) {
                populateExpenditures(categoryList);
            }
        } catch (IOException e) {
            throw new MooMooException("Sorry your data could not be loaded, but you may continue using the app.");
        }
    }

    private static void populateExpenditures(CategoryList categoryList) throws IOException, MooMooException {
        List<String> input = Files.readAllLines(Paths.get(filePath));
        for (String line : input) {
            String[] entry = line.split(" \\| ");
            if (entry.length == 4) {
                loadExpenditure(categoryList, entry, line);
            } else {
                deleteFromFile(line);
            }
        }
    }

    private static void loadExpenditure(CategoryList categoryList, String[] entry, String line) throws MooMooException {
        String category = entry[0];
        String name = entry[1];
        double cost;
        LocalDate date;
        try {
            cost = Double.parseDouble(entry[2]);
            date = LocalDate.parse(entry[3]);
            Expenditure expenditure = new Expenditure(name, cost, date, category);
            if (categoryList.hasCategory(category)) {
                categoryList.get(category).add(expenditure);
            } else {
                deleteFromFile(line);
            }
        } catch (NumberFormatException | DateTimeParseException e) {
            deleteFromFile(line);
        }
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
            throw new MooMooException("Unable to write to file. Expenditure will not be saved.");
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
                if (line.startsWith(entry)) {
                    data.remove(line);
                    break;
                }
            }
            Files.write(Paths.get(filePath), data);
        } catch (IOException e) {
            throw new MooMooException("Unable to write to file. But you may continue using the app.");
        }
    }
}
