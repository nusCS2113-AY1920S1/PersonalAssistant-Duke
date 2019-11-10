package spinbox.datapersistors.exporter;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExportConverter {
    private static final Logger LOGGER = Logger.getLogger(ExportConverter.class.getName());
    private static final String CONVERSION_START = "Entering static method convertForExport";
    private static final String CONVERSION_END = "Exiting static method convertForExport";
    private static final String INDIVIDUAL_CONVERSION = "Converting for export: ";

    /**
     * Converts from a list of exportable objects to their stringified versions.
     * @param originalList The original list of objects that implement the exportable interface.
     * @return A list of the same objects converted to Strings to be sent for export.
     */
    public static List<String> convertForExport(List<? extends Exportable> originalList) {
        LOGGER.setUseParentHandlers(true);
        LOGGER.setLevel(Level.INFO);
        LOGGER.info(CONVERSION_START);
        List<String> returnList = new ArrayList<>();

        for (int index = 0; index < originalList.size(); index++) {
            Exportable currentElement = originalList.get(index);
            LOGGER.fine(INDIVIDUAL_CONVERSION + currentElement.exportString());
            returnList.add(currentElement.exportString());
        }

        LOGGER.info(CONVERSION_END);
        return returnList;
    }
}
