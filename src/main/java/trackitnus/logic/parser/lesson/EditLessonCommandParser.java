package trackitnus.logic.parser.lesson;

import static java.util.Objects.requireNonNull;
import static trackitnus.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static trackitnus.logic.parser.CliSyntax.PREFIX_CODE;
import static trackitnus.logic.parser.CliSyntax.PREFIX_DATE;
import static trackitnus.logic.parser.CliSyntax.PREFIX_TYPE;

import trackitnus.commons.core.Messages;
import trackitnus.commons.core.index.Index;
import trackitnus.logic.commands.lesson.EditLessonCommand;
import trackitnus.logic.commands.lesson.EditLessonCommand.EditLessonDescriptor;
import trackitnus.logic.parser.ArgumentMultimap;
import trackitnus.logic.parser.ArgumentTokenizer;
import trackitnus.logic.parser.Parser;
import trackitnus.logic.parser.ParserUtil;
import trackitnus.logic.parser.exceptions.ParseException;
import trackitnus.model.commons.Address;
import trackitnus.model.commons.Code;
import trackitnus.model.lesson.LessonDateTime;
import trackitnus.model.lesson.Type;

/**
 * Parses input arguments and creates a new EditLessonCommand object
 */
public class EditLessonCommandParser implements Parser<EditLessonCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EditLessonCommand
     * and returns an EditLessonCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditLessonCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args,
                PREFIX_CODE, PREFIX_TYPE, PREFIX_DATE, PREFIX_ADDRESS);

        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
        EditLessonDescriptor editLessonDescriptor = new EditLessonDescriptor();

        if (argMultimap.getValue(PREFIX_CODE).isPresent()) {
            Code code = ParserUtil.parseCode(argMultimap.getValue(PREFIX_CODE).get());
            editLessonDescriptor.setCode(code);
        }

        if (argMultimap.getValue(PREFIX_TYPE).isPresent()) {
            Type type = ParserUtil.parseType(argMultimap.getValue(PREFIX_TYPE).get());
            editLessonDescriptor.setType(type);
        }

        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            LessonDateTime date = ParserUtil.parseLessonDateTime(argMultimap.getValue(PREFIX_DATE).get());
            editLessonDescriptor.setDate(date);
        }

        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
            editLessonDescriptor.setAddress(address);
        }

        if (!editLessonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(Messages.MESSAGE_NOT_EDITED);
        }

        return new EditLessonCommand(index, editLessonDescriptor);
    }
}
