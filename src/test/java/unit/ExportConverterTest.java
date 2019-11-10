package unit;

import org.junit.jupiter.api.Test;
import spinbox.datapersistors.exporter.ExportConverter;
import spinbox.datapersistors.exporter.Exportable;
import spinbox.entities.items.File;
import spinbox.entities.items.GradedComponent;
import spinbox.entities.items.tasks.Todo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;

public class ExportConverterTest {
    private List<Exportable> exportableObjects;
    private List<String> expectedOutput;

    public ExportConverterTest() {
        exportableObjects = new ArrayList<>();
        expectedOutput = new ArrayList<>();
    }

    @Test
    public void exportConversion_listOfExportableItems_successFullyConverted() {
        exportableObjects.add(new GradedComponent("Essay", 20));
        expectedOutput.add("[NOT COMPLETED] Essay\n----/20.0");

        exportableObjects.add(new GradedComponent("Report", 26.7));
        expectedOutput.add("[NOT COMPLETED] Report\n----/26.7");

        exportableObjects.add(new File(0, "file1"));
        expectedOutput.add("[NOT DOWNLOADED] file1");

        exportableObjects.add(new Todo("task1"));
        expectedOutput.add("[T][NOT DONE] task1");

        List<String> converted = ExportConverter.convertForExport(exportableObjects);
        assertEquals(expectedOutput.size(), converted.size());
        assertEquals(converted, expectedOutput);
    }
}
