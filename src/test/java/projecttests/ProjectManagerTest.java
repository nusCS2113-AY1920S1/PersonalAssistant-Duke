package projecttests;

import command.Storage;
import common.AlphaNUSException;
import org.junit.jupiter.api.Test;
import project.Project;
import project.ProjectManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class ProjectManagerTest {
    private Storage storage = new Storage();
    private ProjectManager projectmanager;

    public ProjectManagerTest() throws AlphaNUSException {
    }

    @Test
    void alwaysTrue() {
        assertEquals(2, 2);
    }

    @Test
    void testProjectManager() throws AlphaNUSException {
        projectmanager = new ProjectManager();
    }

}
