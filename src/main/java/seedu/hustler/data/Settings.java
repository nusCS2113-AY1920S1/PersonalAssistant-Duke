package seedu.hustler.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

/**
 * A class that stores User's customized settings.
 */
public class Settings {

    public static final String FILEPATH = "data/settings.txt";

    /**
     * The Date Format that Hustler allows.
     */
    private SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy HHmm");
    private SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy/MM/dd HHmm");
    private SimpleDateFormat dateFormat3 = new SimpleDateFormat("MM/dd/yyyy HHmm");

    private static Formatter formatter;

    private ArrayList<String> quotesOfTheDay;
    private SimpleDateFormat preferredDateFormat;

    /**
     * Default constructor of settings which initializes the default
     * settings.
     */
    public Settings() {
        this.quotesOfTheDay = new ArrayList<>();
        this.preferredDateFormat = dateFormat1;
    }

    /**
     * Loads up the current avatar on the destination of this
     * Hustler app.
     * @return the Avatar that is currently loaded.
     * @throws FileNotFoundException when file is not found, will create a
     *     new txtfile to start data storage.
     */
    public Settings load() throws FileNotFoundException {
        System.out.println("hello");
        try {
            Scanner settingsTxt = new Scanner(new File(FILEPATH));
            return this;
        } catch (FileNotFoundException e) {
            formatter = new Formatter(FILEPATH);
            Settings newSettings = new Settings();
            return newSettings;
        } catch (NullPointerException e) {
            System.out.println("hello");
            return new Settings();
        }
    }

    /**
     * Updates and saves the new avatar's name onto the txtfile.
     * @param preferredName the updated avatar name to be saved.
     * @return the updated Settings.
     * @throws IOException when writing of file has errors.
     */
    public Settings save(String preferredName) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(FILEPATH)));
        writer.write(this.toString());
        writer.close();
        return this;
    }


}