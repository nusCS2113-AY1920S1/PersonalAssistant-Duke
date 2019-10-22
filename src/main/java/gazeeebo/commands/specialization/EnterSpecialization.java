package gazeeebo.commands.specialization;

import gazeeebo.UI.Ui;
import gazeeebo.storage.Storage;

import java.io.IOException;
import java.util.ArrayList;

public class EnterSpecialization {
    public EnterSpecialization (Ui ui, Storage storage, ArrayList<String> spec) throws IOException {
        ArrayList<String> specList = new ArrayList<>();
        specList.add("Communications & Networking");
        specList.add("Embedded Computing");
        specList.add("Intelligent Systems");
        specList.add("Interactive Digital Media");
        specList.add("Large-Scale Computing");
        specList.add("System-On-A-Chip Design");


    }
}
