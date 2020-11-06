package trackitnus.logic.commands.contact;

import static java.util.Objects.requireNonNull;
import static trackitnus.commons.core.Messages.MESSAGE_DUPLICATE_CONTACT;
import static trackitnus.logic.parser.CliSyntax.PREFIX_EMAIL;
import static trackitnus.logic.parser.CliSyntax.PREFIX_NAME;
import static trackitnus.logic.parser.CliSyntax.PREFIX_PHONE;
import static trackitnus.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import trackitnus.commons.core.Messages;
import trackitnus.commons.core.index.Index;
import trackitnus.commons.util.CollectionUtil;
import trackitnus.logic.commands.Command;
import trackitnus.logic.commands.CommandResult;
import trackitnus.logic.commands.exceptions.CommandException;
import trackitnus.model.Model;
import trackitnus.model.commons.Name;
import trackitnus.model.contact.Contact;
import trackitnus.model.contact.Email;
import trackitnus.model.contact.Phone;
import trackitnus.model.tag.Tag;

/**
 * Edits the details of an existing contact in the app.
 */
public final class EditContactCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = Contact.TYPE + " " + COMMAND_WORD
        + ": Edits the details of the contact identified "
        + "by the index number currently displayed on the screen. "
        + "Existing values will be overwritten by the input values.\n"
        + "Parameters: INDEX (must be a positive integer) "
        + "[" + PREFIX_NAME + "NAME] "
        + "[" + PREFIX_PHONE + "PHONE] "
        + "[" + PREFIX_EMAIL + "EMAIL] "
        + "[" + PREFIX_TAG + "TAG]...\n"
        + "Example: " + Contact.TYPE + " " + COMMAND_WORD + " 1 "
        + PREFIX_PHONE + "91234567 "
        + PREFIX_EMAIL + "johndoe@example.com";

    private final Index index;
    private final EditContactDescriptor editContactDescriptor;

    /**
     * @param index                 of the contact in the filtered contact list to edit
     * @param editContactDescriptor details to edit the contact with
     */
    public EditContactCommand(Index index, EditContactDescriptor editContactDescriptor) {
        requireNonNull(index);
        requireNonNull(editContactDescriptor);

        this.index = index;
        this.editContactDescriptor = new EditContactDescriptor(editContactDescriptor);
    }

    /**
     * Creates and returns a {@code Contact} with the details of {@code contactToEdit}
     * edited with {@code editContactDescriptor}.
     */
    private static Contact createEditedContact(Contact contactToEdit, EditContactDescriptor editContactDescriptor) {
        assert contactToEdit != null;

        Name updatedName = editContactDescriptor.getName().orElse(contactToEdit.getName());

        Phone updatedPhone = editContactDescriptor.getIsPhoneChanged()
            ? editContactDescriptor.getPhone().orElse(null)
            : contactToEdit.getPhone().orElse(null);
        Email updatedEmail = editContactDescriptor.getIsEmailChanged()
            ? editContactDescriptor.getEmail().orElse(null)
            : contactToEdit.getEmail().orElse(null);

        Set<Tag> updatedTags = editContactDescriptor.getTags().orElse(contactToEdit.getTags());

        return new Contact(updatedName, updatedPhone, updatedEmail, updatedTags);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Contact> lastShownList = model.getFilteredContactList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
        }

        if (!editContactDescriptor.isAnyFieldEdited()) {
            throw new CommandException(Messages.MESSAGE_NOT_EDITED);
        }

        Contact contactToEdit = lastShownList.get(index.getZeroBased());
        Contact editedContact = createEditedContact(contactToEdit, editContactDescriptor);

        if (contactToEdit.equals(editedContact)) {
            throw new CommandException(Messages.MESSAGE_CONTACT_UNCHANGED);
        }

        if (!contactToEdit.isSameContact(editedContact) && model.hasContact(editedContact)) {
            throw new CommandException(MESSAGE_DUPLICATE_CONTACT);
        }

        model.setContact(contactToEdit, editedContact);
        return new CommandResult(String.format(Messages.MESSAGE_EDIT_CONTACT_SUCCESS, editedContact));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditContactCommand)) {
            return false;
        }

        // state check
        EditContactCommand e = (EditContactCommand) other;
        return index.equals(e.index)
            && editContactDescriptor.equals(e.editContactDescriptor);
    }

    /**
     * Stores the details to edit the contact with. Each non-empty field value will replace the
     * corresponding field value of the contact.
     */
    public static final class EditContactDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Set<Tag> tags;
        private boolean isPhoneChanged;
        private boolean isEmailChanged;

        /**
         * Constructor for EditContactDescriptor.
         * By default both isChanged fields should be false
         */
        public EditContactDescriptor() {
            isPhoneChanged = false;
            isEmailChanged = false;
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditContactDescriptor(EditContactDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setTags(toCopy.tags);
            isPhoneChanged = toCopy.isPhoneChanged;
            isEmailChanged = toCopy.isEmailChanged;
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, tags) || isPhoneChanged || isEmailChanged;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setPhone(Phone phone) {
            isPhoneChanged = true;
            this.phone = phone;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setEmail(Email email) {
            isEmailChanged = true;
            this.email = email;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditContactDescriptor)) {
                return false;
            }

            // state check
            EditContactDescriptor e = (EditContactDescriptor) other;

            return getName().equals(e.getName())
                && getPhone().equals(e.getPhone())
                && getEmail().equals(e.getEmail())
                && getTags().equals(e.getTags());
        }

        public boolean getIsPhoneChanged() {
            return isPhoneChanged;
        }

        public boolean getIsEmailChanged() {
            return isEmailChanged;
        }
    }
}
