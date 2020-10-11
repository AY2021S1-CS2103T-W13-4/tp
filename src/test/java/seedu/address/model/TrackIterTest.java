package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalTrackIter;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.exceptions.DuplicateContactException;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.module.Module;
import seedu.address.model.task.Task;
import seedu.address.testutil.PersonBuilder;

public class TrackIterTest {

    private final TrackIter trackIter = new TrackIter();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), trackIter.getContactList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> trackIter.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyTrackIter_replacesData() {
        TrackIter newData = getTypicalTrackIter();
        trackIter.resetData(newData);
        assertEquals(newData, trackIter);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two contacts with the same identity fields
        Contact editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
            .build();
        List<Contact> newContacts = Arrays.asList(ALICE, editedAlice);
        TrackIterStub newData = new TrackIterStub(newContacts);

        assertThrows(DuplicateContactException.class, () -> trackIter.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> trackIter.hasContact(null));
    }

    @Test
    public void hasPerson_personNotInTrackIter_returnsFalse() {
        assertFalse(trackIter.hasContact(ALICE));
    }

    @Test
    public void hasPerson_personInTrackIter_returnsTrue() {
        trackIter.addContact(ALICE);
        assertTrue(trackIter.hasContact(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInTrackIter_returnsTrue() {
        trackIter.addContact(ALICE);
        Contact editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
            .build();
        assertTrue(trackIter.hasContact(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> trackIter.getContactList().remove(0));
    }

    /**
     * A stub ReadOnlyTrackIter whose contacts list can violate interface constraints.
     */
    private static class TrackIterStub implements ReadOnlyTrackIter {
        private final ObservableList<Contact> contacts = FXCollections.observableArrayList();
        private final ObservableList<Module> modules = FXCollections.observableArrayList();
        private final ObservableList<Task> tasks = FXCollections.observableArrayList();
        private final ObservableList<Lesson> lessons = FXCollections.observableArrayList();

        TrackIterStub(Collection<Contact> contacts) {
            this.contacts.setAll(contacts);
        }

        @Override
        public ObservableList<Contact> getContactList() {
            return contacts;
        }

        @Override
        public ObservableList<Module> getModuleList() {
            return modules;
        }

        @Override
        public ObservableList<Task> getTaskList() {
            return tasks;
        }

        @Override
        public ObservableList<Lesson> getLessonList() {
            return lessons;
        }
    }

}
