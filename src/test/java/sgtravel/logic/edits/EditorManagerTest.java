package sgtravel.logic.edits;

import sgtravel.commons.exceptions.DukeException;
import sgtravel.commons.exceptions.EmptyVenueException;
import sgtravel.commons.exceptions.EventNotSelectedException;
import sgtravel.logic.commands.EditCommand;
import sgtravel.logic.commands.PromptCommand;
import sgtravel.logic.commands.results.PanelResult;
import sgtravel.model.Event;
import sgtravel.model.lists.EventList;
import sgtravel.model.lists.VenueList;

import javafx.scene.input.KeyCode;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EditorManagerTest {

    @Test
    void activate() throws DukeException {
        assertThrows(EmptyVenueException.class, () -> EditorManager.activate(new EventList(), new VenueList()));
        Event e = new Event("Pulau Tekong", LocalDateTime.now(), LocalDateTime.now());
        VenueList venues = new VenueList();
        EventList events = new EventList();
        events.add(e);
        venues.add(e.getLocation());
        EditorManager.activate(events, venues);
    }

    @Test
    void deactivate() {
        EditorManager.deactivate();
        assertFalse(EditorManager.isActive());
    }

    @Test
    void isActive() throws DukeException {
        EditorManager.deactivate();
        assertFalse(EditorManager.isActive());
        Event e = new Event("Pulau Tekong", LocalDateTime.now(), LocalDateTime.now());
        VenueList venues = new VenueList();
        EventList events = new EventList();
        events.add(e);
        venues.add(e.getLocation());
        EditorManager.activate(events, venues);
        assertTrue(EditorManager.isActive());
    }

    @Test
    void edit() throws DukeException {
        EditorManager.deactivate();
        Event e = new Event("Pulau Tekong", LocalDateTime.now(), LocalDateTime.now());
        VenueList venues = new VenueList();
        EventList events = new EventList();
        events.add(e);
        venues.add(e.getLocation());
        Event e2 = new Event("Tuas", LocalDateTime.now(), LocalDateTime.now());
        events.add(e2);
        venues.add(e2.getLocation());
        EditorManager.activate(events, venues);
        assertTrue(EditorManager.isActive());
        assertThrows(EventNotSelectedException.class, () -> EditorManager.edit("Yew Tee"));
        EditorManager.edit(KeyCode.ENTER);
        assertTrue(EditorManager.edit("Yew Tee") instanceof PromptCommand);
        EditorManager.edit(KeyCode.DOWN);
        assertTrue(EditorManager.edit("Mon") instanceof PromptCommand);
        EditorManager.edit(KeyCode.DOWN);
        assertTrue(EditorManager.edit("Mon") instanceof PromptCommand);
        EditorManager.edit(KeyCode.DOWN);
        assertTrue(EditorManager.edit("Boon lay") instanceof PromptCommand);
        assertTrue(EditorManager.edit("x") instanceof EditCommand);
    }

    @Test
    void testEdit() throws DukeException {
        EditorManager.deactivate();
        assertFalse(EditorManager.isActive());
        Event e = new Event("Pulau Tekong", LocalDateTime.now(), LocalDateTime.now());
        VenueList venues = new VenueList();
        EventList events = new EventList();
        events.add(e);
        venues.add(e.getLocation());
        EditorManager.activate(events, venues);
        PanelResult result = EditorManager.edit(KeyCode.K);
        assertTrue(result.isReady());
        result = EditorManager.edit(KeyCode.UP);
        assertTrue(result.isReady());
        result = EditorManager.edit(KeyCode.ENTER);
        assertTrue(result.isReady());
        result = EditorManager.edit(KeyCode.ENTER);
        assertTrue(result.isReady());
        result = EditorManager.edit(KeyCode.ESCAPE);
        assertTrue(result.isReady());
        result = EditorManager.edit(KeyCode.ESCAPE);
        assertTrue(result.isReady());
    }
}
