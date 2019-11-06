package command;
import command.Storage;
import common.AlphaNUSException;
import project.Project;

import java.util.LinkedHashMap;

public class beforeAftercommand {

    public static void beforedoCommand(Storage storage) throws AlphaNUSException {
        storage.writeToUndoFile(storage.readFromProjectsFile());
    }

    public static void afterCommand(Storage storage) throws AlphaNUSException {
        storage.writeToRedoFile(storage.readFromProjectsFile());
    }
}

