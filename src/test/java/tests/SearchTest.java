package tests;

import duke.data.Impression;
import duke.data.Investigation;
import duke.data.Medicine;
import duke.data.Observation;
import duke.data.Patient;
import duke.data.PatientData;
import duke.data.Plan;
import duke.data.Result;
import duke.exception.DukeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class SearchTest {

    private Patient patient;
    private PatientData patientData;
    private static final String duplicateBed = "bedNo";
    private static final String pattern1 = "this is my amazingly complex pattern";
    private static final String pattern2 = "but wouldn't you think that im cooler";
    private static final String pattern3 = "psst they're knaves, i'm the true knight";
    private static final String pattern4 = "If there are 2 knights, who is the knight?";

    /**
     * Sets up the core of this object to have a patient and impression, opens the impression, and stores those
     * references for comparison.
     */
    @BeforeEach
    public void setupTest() {
        patientData = new PatientData();
        int i = 0;
        try {
            patientData.addPatient(setupPatient(pattern1, i));
            i += 1;
            patientData.addPatient(setupPatient(pattern2, i));
            Patient temp = patientData.getPatientByBed(duplicateBed + i);
            temp.addNewImpression(setUpComplexImpression(pattern3, temp));
            temp.addNewImpression(setupImpressionSimple(pattern3, temp));
        } catch (DukeException e) {
            fail("Exception thrown while setting up basic patient! " + e.getMessage());
        }


    }

    @Test
    public void getSearchResultPatientDataTest() {
        try {
            assertEquals(patientData.findPatients(pattern3).getSearchList().size(), 0);
            assertEquals(patientData.findPatients(pattern2).getSearchList().size(), 1);
            assertEquals(patientData.searchAll(pattern1).getSearchList().size(), 2);
        } catch (DukeException e) {
            fail("Could not excute find");
        }
    }

    @Test
    public void getSearchResultPatientTest() {
        try {
            patient = patientData.getPatientByBed(duplicateBed + 1);
            patient.update();
            assertEquals(patient.findImpressions("testImpression").getSearchList().size(), 1);
            assertEquals(patient.searchAll(pattern3).getSearchList().size(), 4);
            assertEquals(patient.searchAll(pattern4).getSearchList().size(), 1);
            assertEquals(patient.findCriticalsByName("inv").getSearchList().size(), 1);
            assertEquals(patient.findCriticalsByName("med blah").getSearchList().size(), 0);
            assertEquals(patient.findFollowUpsByName("med blah").getSearchList().size(), 1);
        } catch (DukeException e) {
            fail("Could not excute find");
        }
    }

    @Test
    public void getSearchResultImpressionTest() {
        try {
            patient = patientData.getPatientByBed(duplicateBed + 1);
            Impression imp = patient.getImpression(pattern3);
            assertEquals(imp.findEvidences(pattern4).getSearchList().size(), 0);
            assertEquals(imp.findEvidences(pattern1).getSearchList().size(), 1);
            assertEquals(imp.findEvidences("").getSearchList().size(), 2);
            assertEquals(imp.findEvidences(pattern3).getSearchList().size(), 1);
            assertEquals(imp.findEvidences("knight").getSearchList().size(), 1);
            assertEquals(imp.searchAll(pattern3).getSearchList().size(), 2);
            assertEquals(imp.findEvidences("summary").getSearchList().size(), 2);
            assertEquals(imp.findTreatments("knight").getSearchList().size(), 2);
            assertEquals(imp.findTreatments("summary").getSearchList().size(), 0);
            assertEquals(imp.findTreatmentsByName("sum").getSearchList().size(), 0);
            assertEquals(imp.findTreatmentsByName("med").getSearchList().size(), 1);
            assertEquals(imp.findEvidencesByName("med").getSearchList().size(), 0);
            assertEquals(imp.findEvidencesByName(pattern3).getSearchList().size(), 1);
            assertEquals(imp.findByName(pattern3).getSearchList().size(), 2);
        } catch (DukeException e) {
            fail("Could not excute find");
        }
    }

    public Impression setupImpressionSimple(String pattern, Patient parent) {
        return new Impression("testImpression", pattern, parent);
    }

    /**
     * Sets up a complex impression.
     * @param pattern the pattern to be used for the impression
     * @param parent patient
     * @return the impression set up
     * @throws DukeException if the treatments and evidences cannot be added
     */
    public Impression setUpComplexImpression(String pattern, Patient parent) throws DukeException {
        Impression imp = new Impression(pattern, "blah", parent);
        setupEvidences(pattern3, pattern1, imp);
        setupTreatments(pattern3, pattern4, "blah", imp);
        return imp;
    }

    /**
     * Sets up Patients.
     * @param pattern the pattern to be enbedded in the patient
     * @param x to prevent duplicate beds
     * @return the patient constructed
     */
    public Patient setupPatient(String pattern, int x) {
        if (pattern.charAt(0) > 'i') {
            return new Patient("name", duplicateBed + x, "allergies", 0, 0,
                    0, 0, pattern, "");
        } else {
            return new Patient("name", duplicateBed + x, pattern, 0, 0,
                    0, 0, "", "");
        }
    }

    /**
     * Helper method to create treatments.
     *
     * @param invName  investigation
     * @param planName plan
     * @param medName  medicine
     * @throws DukeException duplicate names found
     */
    public void setupTreatments(String invName, String planName, String medName, Impression impression)
            throws DukeException {
        impression.addNewTreatment(createPlan(planName, impression));
        impression.addNewTreatment(createInvestigation(invName, impression));
        impression.addNewMedicine(createMedicine(medName, impression));
    }

    /**
     * Helper method to create evidences.
     *
     * @param obsName observation
     * @param resName result
     * @throws DukeException duplicate names found
     */
    private void setupEvidences(String obsName, String resName, Impression impression) throws DukeException {
        impression.addNewEvidence(createObservation(obsName, impression));
        impression.addNewEvidence(createResult(resName, impression));
    }

    private Plan createPlan(String name, Impression impression) throws DukeException {
        return new Plan("plan " + name, impression, 3, "1", "sum of " + name);
    }

    private Medicine createMedicine(String name, Impression impression) throws DukeException {
        return new Medicine("med " + name, impression, 0, "1", "333",
                LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy")), "14");
    }

    private Investigation createInvestigation(String name, Impression impression) throws DukeException {
        return new Investigation("inv " + name, impression, 1, "0", "sum of " + name);
    }

    private Result createResult(String name, Impression impression) throws DukeException {
        return new Result("res " + name, impression, 2, "summary of " + name);
    }

    private Observation createObservation(String name, Impression impression) throws DukeException {
        return new Observation("obs " + name, impression, 1, "summary of " + name,
                false);
    }
}
