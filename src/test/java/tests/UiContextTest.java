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

//@@author gowgos5
public class UiContextTest {
    private UiContext uiContext;
    private Patient patient;
    private Impression impression;

    /**
     * Sets up Patient and Impression so context can be navigated into.
     * @throws DukeException if the impression cannot be added to the patient
     */
    @BeforeEach
    public void setupComprehensivePatient_validDetails() throws DukeException {
        patient = new Patient("John Doe", "A105", "Cinnarizine", 170, 50,
                30, 98765432, "NUS", "Heart attack 5 years ago");
        impression = new Impression("Impression 1", "Description 1", patient);
        patient.addNewImpression(impression);
    }

    /**
     * Sets up the {@code uiContext} object for testing.
     */
    @BeforeEach
    public void setupUiContext() {
        uiContext = new UiContext();
    }

    /**
     * Ensures that {@code uiContext} starts out in the HOME context.
     */
    @Test
    public void testUiContext_noInputs_currentContextIsHomeContext() {
        assertEquals(uiContext.getContext(), Context.HOME);
        assertNull(uiContext.getObject());
    }

    /**
     * Tests open for a UiContext object. The UiContext object should transit from the HOME context to the PATIENT
     * context, and have a reference of the associated Patient object.
     */
    @Test
    public void testOpen_patient_currentContextBecomesPatientContext() {
        uiContext.open(patient);
        assertEquals(uiContext.getContext(), Context.PATIENT);
        assertEquals(uiContext.getObject(), patient);
    }

    /**
     * Tests open for a UiContext object. The UiContext object should transit from the HOME context to the IMPRESSION
     * context without needing to hierarchically access the PATIENT context, and have a reference of the
     * associated Impression object.
     */
    @Test
    public void testOpen_impression_currentContextBecomesImpressionContext() {
        uiContext.open(impression);
        assertEquals(uiContext.getContext(), Context.IMPRESSION);
        assertEquals(uiContext.getObject(), impression);
        assertEquals(uiContext.getObject().getParent(), patient);
    }

    /**
     * Tests moving up a context for a UiContext object. The UiContext object is currently at the HOME context, and
     * hence shouldn't be able to hierarchically move up a context. An exception will therefore be thrown.
     */
    @Test
    public void testMoveUpOneContext_currentlyAtHomeContext_exceptionThrown() {
        assertThrows(DukeException.class, () -> {
            uiContext.moveUpOneContext();
        });
    }

    /**
     * Tests moving up a context for a UiContext object. The UiContext object is currently at the PATIENT context, and
     * should be able to hierarchically move up a context to the HOME context.
     */
    @Test
    public void testMoveUpOneContext_currentlyAtPatientContext_moveUpToHomeContext() throws DukeException {
        uiContext.open(patient);
        uiContext.moveUpOneContext();
        assertEquals(uiContext.getContext(), Context.HOME);
        assertNull(uiContext.getObject());
    }

    /**
     * Tests moving up a context for a UiContext object. The UiContext object is currently at the IMPRESSION context,
     * and should be able to hierarchically move up a context to the PATIENT context.
     */
    @Test
    public void testMoveUpOneContext_currentlyAtImpressionContext_moveUpToPatientContext() throws DukeException {
        uiContext.open(impression);
        uiContext.moveUpOneContext();
        assertEquals(uiContext.getContext(), Context.PATIENT);
        assertEquals(uiContext.getObject(), patient);
    }

    /**
     * Tests moving back a context for a UiContext object. The UiContext object has no previous contexts stored in its
     * stack and hence shouldn't be able to move back a context. An exception will therefore be thrown.
     */
    @Test
    public void testMoveBackOneContext_noPreviousContexts_exceptionThrown() {
        assertThrows(DukeException.class, () -> {
            uiContext.moveBackOneContext();
        });
    }

    /**
     * Tests moving back a context for a UiContext object. The UiContext object has one previous context stored in its
     * stack and hence should be able to move back a context. The UiContext object is currently at the IMPRESSION
     * context, and will move back to the HOME context (where it started out at).
     */
    @Test
    public void testMoveBackOneContext_OnePreviousContexts_moveBackToPreviousContext() throws DukeException {
        uiContext.open(impression);
        uiContext.moveBackOneContext();
        assertEquals(uiContext.getContext(), Context.HOME);
        assertNull(uiContext.getObject());
    }

    /**
     * Tests moving back multiple contexts for a UiContext object. The UiContext object started out at the HOME context,
     * moved to the IMPRESSION context, then moved hierarchically up to the PATIENT context, before finally moving to
     * the IMPRESSION context. The UiContext object will then move back one context to the PATIENT context, then back to
     * the IMPRESSION context, and finally back to HOME context.
     */
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

    /**
     * Tests add listener method for a UiContext object.
     */
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
            default:
                break;
            }
        });

        uiContext.open(impression);
        uiContext.open(patient);
        uiContext.open(null);
    }
}
