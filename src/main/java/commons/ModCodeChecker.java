package commons;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.logging.Logger;

public class ModCodeChecker {
    private static HashMap<Character, HashMap<Character, TreeSet<String>>> mods = new HashMap<>();
    private final Logger logger = DukeLogger.getLogger(ModCodeChecker.class);
    private static ModCodeChecker modCodeChecker;

    /**
     * Creates a modcode checker object and read from ModCode.txt file.
     * @throws IOException when the ModCode.txt is not found
     */
    private ModCodeChecker() {
        try {
            String line;
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("documents/ModCode.txt");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);
            while ((line = reader.readLine()) != null) {
                char firstChar = Character.toLowerCase(line.charAt(0));
                char secondChar = Character.toLowerCase(line.charAt(1));
                if (!this.mods.containsKey(firstChar)) {
                    this.mods.put(firstChar, new HashMap<>());
                    this.mods.get(firstChar).put(secondChar, new TreeSet<>());
                    this.mods.get(firstChar).get(secondChar).add(line.toLowerCase());
                } else if (!this.mods.get(firstChar).containsKey(secondChar)) {
                    this.mods.get(firstChar).put(secondChar, new TreeSet<>());
                    this.mods.get(firstChar).get(secondChar).add(line.toLowerCase());
                } else {
                    this.mods.get(firstChar).get(secondChar).add(line.toLowerCase());
                }
            }
            reader.close();
            isr.close();
            is.close();
        } catch (IOException e) {
            logger.severe("ModCode.txt not found");
        }
    }

    /**
     * This method checks if user inputted modCode exist.
     * @param modCode The string of mod code
     * @return true if modCode exist
     */
    public boolean isModCode(String modCode) {
        modCode = modCode.trim();
        char firstChar = Character.toLowerCase(modCode.charAt(0));
        char secondChar = Character.toLowerCase(modCode.charAt(1));
        if (!mods.containsKey(firstChar)) {
            return false;
        } else if (!mods.get(firstChar).containsKey(secondChar)) {
            return false;
        } else if (!mods.get(firstChar).get(secondChar).contains(modCode.toLowerCase())) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Creates a single instance of ModCodeChecker object.
     */
    public static ModCodeChecker getInstance() {
        if (modCodeChecker == null) {
            synchronized (ModCodeChecker.class) {
                if (modCodeChecker == null) {
                    modCodeChecker = new ModCodeChecker();
                }
            }
        }
        return modCodeChecker;
    }
}
