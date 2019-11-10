package planner.logic.exceptions.planner;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlannerExceptionTest {

    private ModBadGradeException badGradeException = new ModBadGradeException();
    private ModBadRequestStatus modBadRequestStatus = new ModBadRequestStatus();
    private ModBadSuException modBadSuException = new ModBadSuException();
    private ModFailedJsonException modFailedJsonException = new ModFailedJsonException();
    private ModNoPrerequisiteException modNoPrerequisiteException = new ModNoPrerequisiteException();
    private ModNotFoundException modNotFoundException = new ModNotFoundException();
    private ModSameModuleException modSameModuleException = new ModSameModuleException();
    private ModTamperedDataException modTamperedDataException = new ModTamperedDataException();
    private ModUpdateErrorException modUpdateErrorException = new ModUpdateErrorException();

    @Test
    void testModGrad() {
        assertEquals("Error: Please enter a valid letter grade!", badGradeException.getMessage());
    }

    @Test
    void testModBadRequest() {
        assertEquals("Error: Bad Status Connection!", modBadRequestStatus.getMessage());
    }

    @Test
    void testModSu() {
        assertEquals("Error: S/U option is not allowed for this module!", modBadSuException.getMessage());
    }

    @Test
    void testFailedJson() {
        assertEquals("Error: Failed to parse data file!", modFailedJsonException.getMessage());
    }

    @Test
    void testNoPrerequisite() {
        assertEquals("Error: This module has no prerequisites to predict a CAP for!",
                modNoPrerequisiteException.getMessage());
    }

    @Test
    void testModNotFound() {
        assertEquals("Error: Module not found :(", modNotFoundException.getMessage());
    }

    @Test
    void testModSameModuleException() {
        assertEquals("Error: This module has already been added!", modSameModuleException.getMessage());
    }

    @Test
    void testModTamperedData() {
        assertEquals("Error: Data has been tampered, not using saved data!",
                modTamperedDataException.getMessage());
    }

    @Test
    void testModUpdateError() {
        assertEquals("Error: You can only update modules using \"module\" !",
                modUpdateErrorException.getMessage());
    }
}
