package tests;

import duke.data.Impression;
import duke.data.Patient;
import duke.exception.DukeException;
import duke.ui.context.Context;
import duke.ui.context.UiContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UiContextTest {
    private UiContext uiContext;
    private Patient patient;
    private Impression impression;

    @BeforeEach
    public void setupComprehensivePatient_validDetails() throws DukeException {
        patient = new Patient("John Doe", "A105", "Cinnarizine", 170, 50,
                30, 98765432, "NUS", "Heart attack 5 years ago");
        impression = new Impression("Impression 1", "Description 1", patient);
        patient.addNewImpression(impression);
    }

    @BeforeEach
    public void setupUiContext() {
        uiContext = new UiContext();
    }

    @Test
    public void testUiContext_noInputs_currentContextIsHomeContext() {
        assertEquals(uiContext.getContext(), Context.HOME);
        assertNull(uiContext.getObject());
    }

    @Test
    public void testOpen_patient_currentContextBecomesPatientContext() {
        uiContext.open(patient);
        assertEquals(uiContext.getContext(), Context.PATIENT);
        assertEquals(uiContext.getObject(), patient);
    }

    @Test
    public void testOpen_impression_currentContextBecomesImpressionContext() {
        uiContext.open(impression);
        assertEquals(uiContext.getContext(), Context.IMPRESSION);
        assertEquals(uiContext.getObject(), impression);
        assertEquals(uiContext.getObject().getParent(), patient);
    }

    @Test
    public void testMoveUpOneContext_currentlyAtHomeContext_exceptionThrown() {
        assertThrows(DukeException.class, () -> {
            uiContext.moveUpOneContext();
        });
    }

    @Test
    public void testMoveUpOneContext_currentlyAtPatientContext_moveUpToHomeContext() throws DukeException {
        uiContext.open(patient);
        uiContext.moveUpOneContext();
        assertEquals(uiContext.getContext(), Context.HOME);
        assertNull(uiContext.getObject());
    }

    @Test
    public void testMoveUpOneContext_currentlyAtImpressionContext_moveUpToPatientContext() throws DukeException {
        uiContext.open(impression);
        uiContext.moveUpOneContext();
        assertEquals(uiContext.getContext(), Context.PATIENT);
        assertEquals(uiContext.getObject(), patient);
    }

    @Test
    public void testMoveBackOneContext_noPreviousContexts_exceptionThrown() {
        assertThrows(DukeException.class, () -> {
            uiContext.moveBackOneContext();
        });
    }

    @Test
    public void testMoveBackOneContext_OnePreviousContexts_moveBackToPreviousContext() throws DukeException {
        uiContext.open(impression);
        uiContext.moveBackOneContext();
        assertEquals(uiContext.getContext(), Context.HOME);
        assertNull(uiContext.getObject());
    }

    @Test
    public void testMoveBackOneContext_ThreePreviousContexts_moveBackAllTheWayToHomeContext() throws DukeException {
        uiContext.open(impression);
        uiContext.moveUpOneContext();
        uiContext.open(impression);

        uiContext.moveBackOneContext();
        assertEquals(uiContext.getContext(), Context.PATIENT);
        assertEquals(uiContext.getObject(), patient);

        uiContext.moveBackOneContext();
        assertEquals(uiContext.getContext(), Context.IMPRESSION);
        assertEquals(uiContext.getObject(), impression);

        uiContext.moveBackOneContext();
        assertEquals(uiContext.getContext(), Context.HOME);
        assertNull(uiContext.getObject());
    }

    @Test
    public void testAddListener_transitionBetweenVariousContexts_success() {
        uiContext.addListener(event -> {
            switch ((Context) event.getNewValue()) {
            case HOME:
                assertEquals(uiContext.getContext(), Context.HOME);
                assertNull(uiContext.getObject());
                break;
            case PATIENT:
                assertEquals(uiContext.getContext(), Context.PATIENT);
                assertEquals(uiContext.getContext(), Context.PATIENT);
                break;
            case IMPRESSION:
                assertEquals(uiContext.getContext(), Context.IMPRESSION);
                assertEquals(uiContext.getObject(), impression);
                break;
            }
        });

        uiContext.open(impression);
        uiContext.open(patient);
        uiContext.open(null);
    }
}
