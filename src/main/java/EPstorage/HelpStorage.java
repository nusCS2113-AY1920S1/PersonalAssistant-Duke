package EPstorage;

import commands.COMMANDKEYS;
import commands.CommandStructure;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
        BufferedReader br = new BufferedReader(new FileReader("helpData/" + root.toLowerCase().trim() + ".txt"));
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
