package projecttests;

import Storage.Storage;
import common.AlphaNUSException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import project.ProjectManager;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

//@@author leowyh
class ProjectManagerTest {
    private ProjectManager projectmanager = new ProjectManager();

    ProjectManagerTest() throws AlphaNUSException {
    }

    @Test
    void alwaysTrue() {
        assertEquals(2, 2);
    }

    @BeforeEach
    void initProjectManagerTest() {
        projectmanager.currentprojectname = "Rag";
        assertEquals("Rag", projectmanager.currentprojectname);

        projectmanager.projectmap = new LinkedHashMap<>();
        projectmanager.projectmap.put("Rag", new Project("Rag"));
        projectmanager.projectmap.put("Flag", new Project("Flag", 100));

        assertEquals(0, projectmanager.projectmap.get("Rag").budget);
        assertEquals(0, projectmanager.projectmap.get("Rag").spending);
        assertEquals("Flag", projectmanager.projectmap.get("Flag").projectname);
        assertEquals(100, projectmanager.projectmap.get("Flag").budget);
    }


    @Test
    void testAddProject_withoutBudgetAndCurrentProject_WorksCorrectly() {
        String expectedprojectname = "Arts Night";
        projectmanager.currentprojectname = null;

        Project outputproject = projectmanager.addProject("Arts Night");
        String outputprojectname = outputproject.projectname;
        assertEquals(expectedprojectname, outputprojectname);
        assertEquals(expectedprojectname, projectmanager.currentprojectname);
    }

    @Test
    void testAddProject_withBudgetAndCurrentProject_WorksCorrectly() {
        String expectedprojectname = "Arts Night";
        double expectedprojectBudget = 100.0;
        Project outputproject = projectmanager.addProject("Arts Night", 100.0);
        String outputprojectname = outputproject.projectname;
        double outputprojectBudget = outputproject.budget;
        assertEquals(expectedprojectname, outputprojectname);
        assertEquals(expectedprojectBudget, outputprojectBudget);
    }

    @Test
    void testDeleteProject() {
        String expectedprojectname = "Arts Night";
        double expectedprojectBudget = 100.0;
        Project outputproject = projectmanager.addProject("Arts Night", 100.0);
        String outputprojectname = outputproject.projectname;
        double outputprojectBudget = outputproject.budget;
        assertEquals(expectedprojectname, outputprojectname);
        assertEquals(expectedprojectBudget, outputprojectBudget);
    }

    @Test
    void testGotoProject_withStringInput_success() {
        assertEquals("Rag", projectmanager.currentprojectname);
        String expectedcurrentproject = "Flag";
        String outputcurrentproject = projectmanager.gotoProject("Flag");
        assertEquals(expectedcurrentproject, outputcurrentproject);
    }

    @Test
    void testGotoProject_withIntegerInput_success() {
        assertEquals("Rag", projectmanager.currentprojectname);
        String expectedcurrentproject = "Flag";

        String outputcurrentproject = projectmanager.gotoProject(1);
        assertEquals(expectedcurrentproject, outputcurrentproject);

        expectedcurrentproject = "Rag";
        outputcurrentproject = projectmanager.gotoProject(0);
        assertEquals(expectedcurrentproject, outputcurrentproject);
    }

    @Test
    void testListProjects() {
        Project project1 = new Project("Rag");
        Project project2 = new Project("Flag", 100.0);
        ArrayList<Project> expectedprojectslist = new ArrayList<>();
        expectedprojectslist.add(project1);
        expectedprojectslist.add(project2);

        ArrayList<Project> outputprojectslist = projectmanager.listProjects();
        assertEquals(project1.giveProject(), outputprojectslist.get(0).giveProject());
        assertEquals(project2.giveProject(), outputprojectslist.get(1).giveProject());
    }

    //TODO jiayu
    @Test
    void testAssignBudget() {
    }

    @Test
    void testLoadBackup() {
        LinkedHashMap<String, Project> expectedprojectmap = new LinkedHashMap<>();
        expectedprojectmap.put("Arts Night", new Project("Arts Night"));

        projectmanager.loadBackup(expectedprojectmap);
        LinkedHashMap<String, Project> outputprojectmap = projectmanager.projectmap;
        String outputprojectname = projectmanager.currentprojectname;
        assertEquals(outputprojectmap, expectedprojectmap);
        assertNull(outputprojectname);

    }
}
