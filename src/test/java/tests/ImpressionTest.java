package tests;

import duke.data.Evidence;
import duke.data.Impression;
import duke.data.Investigation;
import duke.data.Medicine;
import duke.data.Observation;
import duke.data.Patient;
import duke.data.Plan;
import duke.data.Result;
import duke.data.Treatment;
import duke.exception.DukeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ImpressionTest {

    private static final String duplicateBed = "bedNo";
    private Impression impression;
    private Patient patient;

    /**
     * Sets up the core of this object to have a patient and impression, opens the impression, and stores those
     * references for comparison.
     */
    @BeforeEach
    public void setupImpression() {
        patient = new Patient("name", duplicateBed, "allergies", 0, 0,
                0, 0, "", "");
        impression = new Impression("testImpression", "Impression Test description", patient);
    }

    /**
     * Test if lists are sorted properly after addition.
     */
    @Test
    public void addTest() {
        final String addTest = "addTest";
        try {
            setupEvidences(addTest, addTest);
            setupTreatments(addTest, addTest, addTest);
        } catch (DukeException e) {
            fail("Could not add items for testing!");
        }
        int highestPri = 1;
        for (Evidence evidence : impression.getEvidences()) {
            if (evidence.getPriority() < highestPri && evidence.getPriority() != 0) {
                fail("evidence sorting failed");
            } else if (highestPri == 0 && evidence.getPriority() != 0) {
                fail("evidence failed to sort 0 behind");
            }
            highestPri = evidence.getPriority();
        }
        highestPri = 1;
        int highestStatus = 0;
        for (Treatment treatment : impression.getTreatments()) {
            if (treatment.getPriority() != 0
                    && (treatment.getPriority() < highestPri || treatment.getStatusIdx() < highestStatus)) {
                fail("treatment sorting failed");
            } else if (highestPri == 0 && treatment.getPriority() != 0) {
                fail("treatment failed to sort 0 behind");
            }
            if (treatment.getPriority() != highestPri) {
                highestPri = treatment.getPriority();
                highestStatus = 0;
            } else {
                highestStatus = treatment.getStatusIdx();
            }
        }
    }

    @Test
    public void getParentTest() {
        assertTrue(patient.equals(impression.getParent()));
    }

    /**
     * Helper method to create treatments.
     *
     * @param invName  investigation
     * @param planName plan
     * @param medName  medicine
     * @throws DukeException duplicate names found
     */
    public void setupTreatments(String invName, String planName, String medName) throws DukeException {
        impression.addNewTreatment(createPlan(planName));
        impression.addNewTreatment(createInvestigation(invName));
        impression.addNewMedicine(createMedicine(medName));
    }

    /**
     * Helper method to create evidences.
     *
     * @param obsName observation
     * @param resName result
     * @throws DukeException duplicate names found
     */
    private void setupEvidences(String obsName, String resName) throws DukeException {
        impression.addNewEvidence(createObservation(obsName));
        impression.addNewEvidence(createResult(resName));
    }

    private Plan createPlan(String name) throws DukeException {
        return new Plan("plan " + name, impression, 3, "1", "sum of " + name);
    }

    private Medicine createMedicine(String name) throws DukeException {
        return new Medicine("med " + name, impression, 0, "1", "333",
                LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy")), "14");
    }

    private Investigation createInvestigation(String name) throws DukeException {
        return new Investigation("inv " + name, impression, 1, "0", "sum of " + name);
    }

    private Result createResult(String name) throws DukeException {
        return new Result("res " + name, impression, 2, "summary of " + name);
    }

    private Observation createObservation(String name) throws DukeException {
        return new Observation("obs " + name, impression, 1, "summary of " + name,
                false);
    }
}
