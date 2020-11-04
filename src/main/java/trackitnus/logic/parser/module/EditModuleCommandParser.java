package trackitnus.logic.parser.module;

import static java.util.Objects.requireNonNull;
import static trackitnus.commons.core.Messages.MESSAGE_NOT_EDITED;
import static trackitnus.logic.parser.CliSyntax.PREFIX_CODE;
import static trackitnus.logic.parser.CliSyntax.PREFIX_NAME;

import trackitnus.commons.core.Messages;
import trackitnus.logic.commands.module.EditModuleCommand;
import trackitnus.logic.parser.ArgumentMultimap;
import trackitnus.logic.parser.ArgumentTokenizer;
import trackitnus.logic.parser.Parser;
import trackitnus.logic.parser.ParserUtil;
import trackitnus.logic.parser.exceptions.ParseException;
import trackitnus.model.commons.Code;

/**
 * Parses input arguments and creates a new EditModuleCommand object
 */
public class EditModuleCommandParser implements Parser<EditModuleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditModuleCommand
     * and returns an EditModuleCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditModuleCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_CODE, PREFIX_NAME);

        Code code;

        try {
            if (argMultimap.getPreamble().isEmpty()) {
                throw new ParseException("No module code is provided");
            }
            code = ParserUtil.parseCode(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                EditModuleCommand.MESSAGE_USAGE), pe);
        }

        EditModuleCommand.EditModuleDescriptor editModuleDescriptor = new EditModuleCommand.EditModuleDescriptor();
        if (argMultimap.getValue(PREFIX_CODE).isPresent()) {
            editModuleDescriptor.setCode(ParserUtil.parseCode(argMultimap.getValue(PREFIX_CODE).get()));
        }

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editModuleDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }

        if (!editModuleDescriptor.isAnyFieldEdited()) {
            throw new ParseException(MESSAGE_NOT_EDITED);
        }

        return new EditModuleCommand(code, editModuleDescriptor);
    }

}
