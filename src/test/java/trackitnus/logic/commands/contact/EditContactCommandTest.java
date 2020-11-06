package trackitnus.logic.commands.contact;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static trackitnus.logic.commands.CommandTestUtil.assertCommandFailure;
import static trackitnus.logic.commands.CommandTestUtil.assertCommandSuccess;
import static trackitnus.testutil.typical.TypicalTrackIter.getTypicalTrackIter;

import org.junit.jupiter.api.Test;

import trackitnus.commons.core.Messages;
import trackitnus.commons.core.index.Index;
import trackitnus.model.Model;
import trackitnus.model.ModelManager;
import trackitnus.model.TrackIter;
import trackitnus.model.UserPrefs;
import trackitnus.model.contact.Contact;
import trackitnus.testutil.builder.ContactBuilder;
import trackitnus.testutil.builder.EditContactDescriptorBuilder;
import trackitnus.testutil.typical.TypicalIndexes;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for EditContactCommand.
 */
public class EditContactCommandTest {

    private final Model model = new ModelManager(getTypicalTrackIter(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Contact editedContact = new ContactBuilder().build();
        EditContactCommand.EditContactDescriptor descriptor = new EditContactDescriptorBuilder(editedContact).build();
        EditContactCommand editContactCommand = new EditContactCommand(TypicalIndexes.INDEX_FIRST, descriptor);

        String expectedMessage = String.format(Messages.MESSAGE_EDIT_CONTACT_SUCCESS, editedContact);

        Model expectedModel = new ModelManager(new TrackIter(model.getTrackIter()), new UserPrefs());
        expectedModel.setContact(model.getFilteredContactList().get(0), editedContact);

        assertCommandSuccess(editContactCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastContact = Index.fromOneBased(model.getFilteredContactList().size());
        Contact lastContact = model.getFilteredContactList().get(indexLastContact.getZeroBased());

        ContactBuilder contactInList = new ContactBuilder(lastContact);
        Contact editedContact =
            contactInList.withName(ContactCommandTestUtil.VALID_NAME_BOB)
                .withPhone(ContactCommandTestUtil.VALID_PHONE_BOB)
                .withTags(ContactCommandTestUtil.VALID_TAG_HUSBAND).build();

        EditContactCommand.EditContactDescriptor descriptor =
            new EditContactDescriptorBuilder().withName(ContactCommandTestUtil.VALID_NAME_BOB)
                .withPhone(ContactCommandTestUtil.VALID_PHONE_BOB).withTags(ContactCommandTestUtil.VALID_TAG_HUSBAND)
                .build();
        EditContactCommand editContactCommand = new EditContactCommand(indexLastContact, descriptor);

        String expectedMessage = String.format(Messages.MESSAGE_EDIT_CONTACT_SUCCESS, editedContact);

        Model expectedModel = new ModelManager(new TrackIter(model.getTrackIter()), new UserPrefs());
        expectedModel.setContact(lastContact, editedContact);

        assertCommandSuccess(editContactCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        ContactCommandTestUtil.showContactAtIndex(model, TypicalIndexes.INDEX_FIRST);

        Contact contactInFilteredList =
            model.getFilteredContactList().get(TypicalIndexes.INDEX_FIRST.getZeroBased());
        Contact editedContact =
            new ContactBuilder(contactInFilteredList).withPhone("98989898").build();
        EditContactCommand editContactCommand = new EditContactCommand(TypicalIndexes.INDEX_FIRST,
            new EditContactDescriptorBuilder().withPhone("98989898").build());

        Model expectedModel = new ModelManager(new TrackIter(model.getTrackIter()), new UserPrefs());
        expectedModel.setContact(model.getFilteredContactList().get(0), editedContact);
        ContactCommandTestUtil.showContactAtIndex(expectedModel, TypicalIndexes.INDEX_FIRST);

        String expectedMessage = String.format(Messages.MESSAGE_EDIT_CONTACT_SUCCESS, editedContact);

        assertCommandSuccess(editContactCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_failure() {
        EditContactCommand editContactCommand = new EditContactCommand(TypicalIndexes.INDEX_FIRST,
            new EditContactCommand.EditContactDescriptor());

        assertCommandFailure(editContactCommand, model, Messages.MESSAGE_NOT_EDITED);
    }

    @Test
    public void execute_unchangedFilteredList_failure() {
        ContactCommandTestUtil.showContactAtIndex(model, TypicalIndexes.INDEX_FIRST);
        EditContactCommand editContactCommand = new EditContactCommand(TypicalIndexes.INDEX_FIRST,
            new EditContactDescriptorBuilder().withPhone("94351253").build());

        assertCommandFailure(editContactCommand, model, Messages.MESSAGE_CONTACT_UNCHANGED);
    }

    @Test
    public void execute_duplicateContactUnfilteredList_failure() {
        Contact firstContact = model.getFilteredContactList().get(TypicalIndexes.INDEX_FIRST.getZeroBased());
        EditContactCommand.EditContactDescriptor descriptor = new EditContactDescriptorBuilder(firstContact).build();
        EditContactCommand editContactCommand = new EditContactCommand(TypicalIndexes.INDEX_SECOND, descriptor);

        assertCommandFailure(editContactCommand, model, Messages.MESSAGE_DUPLICATE_CONTACT);
    }

    @Test
    public void execute_duplicateContactFilteredList_failure() {
        ContactCommandTestUtil.showContactAtIndex(model, TypicalIndexes.INDEX_FIRST);

        // edit contact in filtered list into a duplicate in address book
        Contact contactInList =
            model.getTrackIter().getContactList().get(TypicalIndexes.INDEX_SECOND.getZeroBased());
        EditContactCommand editContactCommand = new EditContactCommand(TypicalIndexes.INDEX_FIRST,
            new EditContactDescriptorBuilder(contactInList).build());

        assertCommandFailure(editContactCommand, model, Messages.MESSAGE_DUPLICATE_CONTACT);
    }

    @Test
    public void execute_invalidContactIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredContactList().size() + 1);
        EditContactCommand.EditContactDescriptor descriptor =
            new EditContactDescriptorBuilder().withName(ContactCommandTestUtil.VALID_NAME_BOB).build();
        EditContactCommand editContactCommand = new EditContactCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editContactCommand, model, Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of trackiter
     */
    @Test
    public void execute_invalidContactIndexFilteredList_failure() {
        ContactCommandTestUtil.showContactAtIndex(model, TypicalIndexes.INDEX_FIRST);
        Index outOfBoundIndex = TypicalIndexes.INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of trackiter list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTrackIter().getContactList().size());

        EditContactCommand editContactCommand = new EditContactCommand(outOfBoundIndex,
            new EditContactDescriptorBuilder().withName(ContactCommandTestUtil.VALID_NAME_BOB).build());

        assertCommandFailure(editContactCommand, model, Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditContactCommand standardCommand = new EditContactCommand(TypicalIndexes.INDEX_FIRST,
            ContactCommandTestUtil.DESC_AMY);

        // same values -> returns true
        EditContactCommand.EditContactDescriptor copyDescriptor =
            new EditContactCommand.EditContactDescriptor(ContactCommandTestUtil.DESC_AMY);
        EditContactCommand commandWithSameValues = new EditContactCommand(TypicalIndexes.INDEX_FIRST,
            copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditContactCommand(TypicalIndexes.INDEX_SECOND,
            ContactCommandTestUtil.DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditContactCommand(TypicalIndexes.INDEX_FIRST,
            ContactCommandTestUtil.DESC_BOB)));
    }

}
