package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TYPE;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.contact.AddContactCommand;
import seedu.address.logic.commands.contact.ClearContactCommand;
import seedu.address.logic.commands.contact.DeleteContactCommand;
import seedu.address.logic.commands.contact.EditContactCommand;
import seedu.address.logic.commands.contact.FindContactCommand;
import seedu.address.logic.commands.contact.ListContactCommand;
import seedu.address.logic.commands.module.AddModuleCommand;
import seedu.address.logic.commands.module.DeleteModuleCommand;
import seedu.address.logic.commands.module.EditModuleCommand;
import seedu.address.logic.commands.module.ListModuleCommand;
import seedu.address.logic.commands.module.ViewModuleCommand;
import seedu.address.logic.parser.contact.AddContactCommandParser;
import seedu.address.logic.parser.contact.DeleteContactCommandParser;
import seedu.address.logic.parser.contact.EditContactCommandParser;
import seedu.address.logic.parser.contact.FindContactCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.module.AddModuleCommandParser;
import seedu.address.logic.parser.module.DeleteModuleCommandParser;
import seedu.address.logic.parser.module.EditModuleCommandParser;
import seedu.address.logic.parser.module.ViewModuleCommandParser;
import seedu.address.model.contact.Contact;
import seedu.address.model.module.Module;

/**
 * Parses user input.
 */
public class TrackIterParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<type>[A-Z]\\s+)?"
        + "(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String type = matcher.group("type") == null ? "" : matcher.group("type").trim();
        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (type) {
        case Contact.TYPE:
            switch (commandWord) {
            case AddContactCommand.COMMAND_WORD:
                return new AddContactCommandParser().parse(arguments);

            case EditContactCommand.COMMAND_WORD:
                return new EditContactCommandParser().parse(arguments);

            case DeleteContactCommand.COMMAND_WORD:
                return new DeleteContactCommandParser().parse(arguments);

            case ClearContactCommand.COMMAND_WORD:
                return new ClearContactCommand();

            case FindContactCommand.COMMAND_WORD:
                return new FindContactCommandParser().parse(arguments);

            case ListContactCommand.COMMAND_WORD:
                return new ListContactCommand();

            default:
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
            }
        case Module.TYPE:
            switch (commandWord) {
            case AddModuleCommand.COMMAND_WORD:
                return new AddModuleCommandParser().parse(arguments);

            case EditModuleCommand.COMMAND_WORD:
                return new EditModuleCommandParser().parse(arguments);

            case DeleteModuleCommand.COMMAND_WORD:
                return new DeleteModuleCommandParser().parse(arguments);

            case ViewModuleCommand.COMMAND_WORD:
                return new ViewModuleCommandParser().parse(arguments);

            case ListModuleCommand.COMMAND_WORD:
                return new ListModuleCommand();

            default:
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
            }
        case "":
            switch (commandWord) {
            case ExitCommand.COMMAND_WORD:
                return new ExitCommand();

            case HelpCommand.COMMAND_WORD:
                return new HelpCommand();

            default:
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
            }
        default:
            throw new ParseException(MESSAGE_INVALID_TYPE);
        }
    }

}
