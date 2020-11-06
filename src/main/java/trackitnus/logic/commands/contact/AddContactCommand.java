package trackitnus.logic.commands.contact;

import static java.util.Objects.requireNonNull;
import static trackitnus.logic.parser.CliSyntax.PREFIX_EMAIL;
import static trackitnus.logic.parser.CliSyntax.PREFIX_NAME;
import static trackitnus.logic.parser.CliSyntax.PREFIX_PHONE;
import static trackitnus.logic.parser.CliSyntax.PREFIX_TAG;

import trackitnus.commons.core.Messages;
import trackitnus.logic.commands.Command;
import trackitnus.logic.commands.CommandResult;
import trackitnus.logic.commands.exceptions.CommandException;
import trackitnus.model.Model;
import trackitnus.model.contact.Contact;

/**
 * Adds a contact to the app.
 */
public final class AddContactCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = Contact.TYPE + " " + COMMAND_WORD
        + ": Adds a contact to the app.\n"
        + "Parameters: "
        + PREFIX_NAME + "NAME "
        + PREFIX_PHONE + "PHONE "
        + PREFIX_EMAIL + "EMAIL "
        + "[" + PREFIX_TAG + "TAG]...\n"
        + "Example: " + Contact.TYPE + " " + COMMAND_WORD + " "
        + PREFIX_NAME + "John Doe "
        + PREFIX_PHONE + "98765432 "
        + PREFIX_EMAIL + "johnd@example.com "
        + PREFIX_TAG + "friends "
        + PREFIX_TAG + "owesMoney";

    private final Contact toAdd;

    /**
     * Creates an AddContactCommand to add the specified {@code Contact}
     */
    public AddContactCommand(Contact contact) {
        requireNonNull(contact);
        toAdd = contact;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasContact(toAdd)) {
            throw new CommandException(Messages.MESSAGE_DUPLICATE_CONTACT);
        }

        model.addContact(toAdd);
        return new CommandResult(String.format(Messages.MESSAGE_ADD_CONTACT_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof AddContactCommand // instanceof handles nulls
            && toAdd.equals(((AddContactCommand) other).toAdd));
    }
}
