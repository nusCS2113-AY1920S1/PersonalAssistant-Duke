package repositorytests;

import controllers.ConsoleInputController;
import controllers.ProjectInputController;
import models.data.Project;
import org.junit.jupiter.api.Test;
import repositories.ProjectRepository;
import views.CLIView;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ProjectRepositoryTest {
    private ProjectRepository projectRepository;
    private String simulatedUserinput;

    /**
     * Junit Tests for Project Repository. This is to test the creation of projects.
     */
    ProjectRepositoryTest() {
        this.projectRepository = new ProjectRepository();
    }

    /**
     * Always true test just to test if jUnit is working.
     */
    @Test
    void alwaysTrue() {
        assertEquals(2, 2);
    }

    @Test
    void addToRepo_correctInputs_projectReturned() {
        simulatedUserinput = "create n/Thor Fanclub";
        projectRepository.addToRepo(simulatedUserinput);
        Project simulatedProject = projectRepository.getItem(1);
        Project expectedProject = new Project("Thor Fanclub");
        assertEquals(expectedProject.getDescription(), simulatedProject.getDescription());
        assertEquals(expectedProject.getNumOfMembers(), simulatedProject.getNumOfMembers());
        assertEquals(expectedProject.getNumOfTasks(), simulatedProject.getNumOfTasks());
        assertEquals(expectedProject.getMembers().getAllMemberDetails(),
                simulatedProject.getMembers().getAllMemberDetails());
        assertEquals(expectedProject.getTasks().getAllTaskDetails(),
                simulatedProject.getTasks().getAllTaskDetails());
    }

    @Test
    void addToRepo_incorrectInputs_falseReturned() {
        simulatedUserinput = "create";
        assertFalse(projectRepository.addToRepo(simulatedUserinput));
        simulatedUserinput = "create ";
        assertFalse(projectRepository.addToRepo(simulatedUserinput));
        simulatedUserinput = "create n/";
        assertFalse(projectRepository.addToRepo(simulatedUserinput));
        simulatedUserinput = "create n";
        assertFalse(projectRepository.addToRepo(simulatedUserinput));
    }

    @Test
    void getItem_ProjectExists_projectReturned() {
        assertEquals(projectRepository.getAll().size(), 0);
        simulatedUserinput = "create n/Ironman Fanclub";
        projectRepository.addToRepo(simulatedUserinput);
        Project expectedProject = new Project("Ironman Fanclub");
        Project simulatedProject = projectRepository.getItem(1);
        assertEquals(expectedProject.getDescription(), simulatedProject.getDescription());
        assertEquals(expectedProject.getNumOfMembers(), simulatedProject.getNumOfMembers());
        assertEquals(expectedProject.getNumOfTasks(), simulatedProject.getNumOfTasks());
        assertEquals(expectedProject.getMembers().getAllMemberDetails(),
                simulatedProject.getMembers().getAllMemberDetails());
        assertEquals(expectedProject.getTasks().getAllTaskDetails(),
                simulatedProject.getTasks().getAllTaskDetails());
    }

    @Test
    void deleteItem_ProjectExists_successfulDeletion() {
        assertEquals(projectRepository.getAll().size(), 0);
        simulatedUserinput = "create n/Ironman Fanclub";
        projectRepository.addToRepo(simulatedUserinput);
        assertEquals(projectRepository.getAll().size(), 1);
        projectRepository.deleteItem(1);
        assertEquals(projectRepository.getAll().size(), 0);
    }
}
