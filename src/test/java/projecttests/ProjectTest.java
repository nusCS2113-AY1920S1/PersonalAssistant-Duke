package projecttests;

import org.junit.jupiter.api.Test;
import project.Project;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProjectTest {
    @Test
    void alwaysTrue() {
        assertEquals(2, 2);
    }

    @Test
    void testAddBudget() {
        Project project = new Project("Rag");
        assertEquals(0.0, project.budget);
        project.addBudget(100.00);
        assertEquals(100.00, project.budget);
    }

    @Test
    void testGiveProject() {
        Project project = new Project("Rag");
        project.addBudget(100.00);
        assertEquals(100.00, project.budget);
        assertEquals("\t" + "Project name:  Rag" + "\n" + "\t"
                + "Allocated Budget:  100.0" + "\n" + "\t"
                + "Total Spending: 0.0", project.giveProject());
    }

    @Test
    void testProject_withBudget() {
        Project project = new Project("Rag", 100.0);
        assertEquals(100.0, project.budget);
        assertEquals("\t" + "Project name:  Rag" + "\n" + "\t"
                + "Allocated Budget:  100.0" + "\n" + "\t"
                + "Total Spending: 0.0", project.giveProject());
    }
}
