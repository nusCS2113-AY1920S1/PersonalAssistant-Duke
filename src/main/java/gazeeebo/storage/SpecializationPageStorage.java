//@@author e0323290

package gazeeebo.storage;

import gazeeebo.commands.specialization.ModuleCategory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class SpecializationPageStorage {
    /**
     * Specialization storage file name.
     */
    private String relativePathSpecializationResource
            = "Specialization.txt";

    /**
     * This method writes to the file Specialization.txt.
     *
     * @param fileContent save the specializations and
     *                    technical electives into this file.
     * @throws IOException catch the error if the read file fails.
     */
    public void writeToSpecializationFile(String fileContent) throws IOException {
        FileWriter fileWriter = new FileWriter(relativePathSpecializationResource);
        fileWriter.write(fileContent);
        fileWriter.flush();
        fileWriter.close();
    }

    /**
     * This method read from the file
     * Specialization.txt and put the details into a HashMap.
     *
     * @return Returns the HashMap of specMap,
<<<<<<< HEAD
     * key is the specialization title and the value is
     * list of technical electives under that specialization.
=======
     *     key is the specialization title and the value is
     *     list of technical electives under that specialization.
>>>>>>> 71056c071ad3642f49ddb4af5c29b09b20be25ad
     * @throws FileNotFoundException catch the error if the read file fails.
     */
    public HashMap<String, ArrayList<ModuleCategory>> readFromSpecializationFile() throws FileNotFoundException {
        HashMap<String, ArrayList<ModuleCategory>> specMap = new HashMap<>();

        File file = new File(relativePathSpecializationResource);
        Scanner sc = new Scanner(file);
        while (sc.hasNext()) {
            String[] split = sc.nextLine().split("\\|");
            ArrayList<ModuleCategory> moduleBD = new ArrayList<>();
            ModuleCategory mc = new ModuleCategory(split[2]);
            moduleBD.add(mc);
            specMap.put(split[1], moduleBD);
        }

        return specMap;
    }
}
