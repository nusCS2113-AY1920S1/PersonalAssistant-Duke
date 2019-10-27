package entertainment.pro.storage.utils;

import entertainment.pro.commons.enums.COMMANDKEYS;
import entertainment.pro.logic.parsers.CommandStructure;
import javafx.fxml.LoadException;

import java.io.*;
import java.util.TreeMap;

public class HelpStorage {

    private static TreeMap<COMMANDKEYS, String> cmdHelp = new TreeMap<>();

    public static void initialiseAllHelp() {
        for (COMMANDKEYS root: CommandStructure.AllRoots) {
            try {
                cmdHelp.put(root , getHelpInstructions(root.toString()));
            } catch (IOException e) {
                System.out.println("FILE NOT FOUND");
            }
        }

    }

    public static TreeMap<COMMANDKEYS, String> getCmdHelp() {
        return cmdHelp;
    }

    private static String getHelpInstructions(String root) throws IOException {
        InputStream configStream = HelpStorage.class.getResourceAsStream(String.format("/helpData/%s.txt", root.toLowerCase()));
        if (configStream == null) {
            return "No help data found";
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(configStream, "UTF-8"));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }

            return sb.toString();
        } finally {
            br.close();
        }
    }
}
