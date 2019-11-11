package repositorytests;

import models.project.Project;
import org.junit.jupiter.api.Test;
import repositories.ProjectRepository;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

//@@author Lucria
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
        while (projectRepository.getAll().size() != 0) {
            projectRepository.deleteItem(1);
        }
        simulatedUserinput = "create Thor Fanclub";
        projectRepository.addToRepo(simulatedUserinput);
        Project simulatedProject = projectRepository.getItem(1);
        Project expectedProject = new Project("Thor Fanclub");
        assertEquals(expectedProject.getName(), simulatedProject.getName());
        assertEquals(expectedProject.getNumOfMembers(), simulatedProject.getNumOfMembers());
        assertEquals(expectedProject.getNumOfTasks(), simulatedProject.getNumOfTasks());
        assertEquals(expectedProject.getMemberList().getAllMemberDetails(),
                simulatedProject.getMemberList().getAllMemberDetails());
        assertEquals(expectedProject.getTaskList().getAllTaskDetails(expectedProject.getTasksAndAssignedMembers(),
            projectRepository.getItem(1)),
                simulatedProject.getTaskList().getAllTaskDetails(simulatedProject.getTasksAndAssignedMembers(),
                    projectRepository.getItem(1)));
    }

    @Test
    void addToRepo_incorrectInputs_falseReturned() {
        simulatedUserinput = "create";
        assertFalse(projectRepository.addToRepo(simulatedUserinput));
        simulatedUserinput = "create ";
        assertFalse(projectRepository.addToRepo(simulatedUserinput));
    }

    @Test
    void getItem_projectExists_projectReturned() {
        while (projectRepository.getAll().size() != 0) {
            projectRepository.deleteItem(1);
        }
        assertEquals(projectRepository.getAll().size(), 0);
        simulatedUserinput = "create Ironman Fanclub";
        projectRepository.addToRepo(simulatedUserinput);
        Project expectedProject = new Project("Ironman Fanclub");
        Project simulatedProject = projectRepository.getItem(1);
        assertEquals(expectedProject.getName(), simulatedProject.getName());
        assertEquals(expectedProject.getNumOfMembers(), simulatedProject.getNumOfMembers());
        assertEquals(expectedProject.getNumOfTasks(), simulatedProject.getNumOfTasks());
        assertEquals(expectedProject.getMemberList().getAllMemberDetails(),
                simulatedProject.getMemberList().getAllMemberDetails());
        assertEquals(expectedProject.getTaskList().getAllTaskDetails(expectedProject.getTasksAndAssignedMembers(),
            projectRepository.getItem(1)),
                simulatedProject.getTaskList().getAllTaskDetails(simulatedProject.getTasksAndAssignedMembers(),
                    projectRepository.getItem(1)));
    }

    @Test
    void deleteItem_projectExists_successfulDeletion() {
        while (projectRepository.getAll().size() != 0) {
            projectRepository.deleteItem(1);
        }
        assertEquals(projectRepository.getAll().size(), 0);
        simulatedUserinput = "create Ironman Fanclub";
        projectRepository.addToRepo(simulatedUserinput);
        assertEquals(projectRepository.getAll().size(), 1);
        projectRepository.deleteItem(1);
        assertEquals(projectRepository.getAll().size(), 0);
    }

    @Test
    void deleteItem_projectDontExist_errorPrinted() {
        while (projectRepository.getAll().size() != 0) {
            projectRepository.deleteItem(1);
        }
        assertEquals(projectRepository.getAll().size(), 0);
        String[] simulatedOutput = projectRepository.deleteItem(1);
        String[] expectedOutput = new String[] {
            "Error occurred! There could be three possibilities:",
            "You could have attempted to delete a Project after renaming it's JSON file",
            "You could have entered a Project index is out of bounds.",
            "You could have attempted to delete the default Project loaded immediately. "
                + "Do not panic if this was you. The default Project is deleted correctly"
        };
        assertArrayEquals(expectedOutput, simulatedOutput);
    }
}
