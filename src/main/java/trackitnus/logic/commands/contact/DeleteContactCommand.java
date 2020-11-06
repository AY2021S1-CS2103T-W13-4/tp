package trackitnus.logic.commands.contact;

import static java.util.Objects.requireNonNull;

import java.util.List;

import trackitnus.commons.core.Messages;
import trackitnus.commons.core.index.Index;
import trackitnus.logic.commands.Command;
import trackitnus.logic.commands.CommandResult;
import trackitnus.logic.commands.exceptions.CommandException;
import trackitnus.model.Model;
import trackitnus.model.contact.Contact;

/**
 * Deletes a contact identified using it's displayed index from the app.
 */
public final class DeleteContactCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = Contact.TYPE + " " + COMMAND_WORD
        + ": Deletes the contact identified by the index number currently displayed on the screen.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + Contact.TYPE + " " + COMMAND_WORD + " 1";

    private final Index targetIndex;

    public DeleteContactCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Contact> lastShownList = model.getFilteredContactList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
        }

        Contact contactToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteContact(contactToDelete);
        return new CommandResult(String.format(Messages.MESSAGE_DELETE_CONTACT_SUCCESS, contactToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DeleteContactCommand // instanceof handles nulls
            && targetIndex.equals(((DeleteContactCommand) other).targetIndex)); // state check
    }
}
