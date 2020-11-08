package trackitnus.logic.parser.lesson;

import trackitnus.commons.core.index.Index;
import trackitnus.logic.commands.lesson.DeleteLessonCommand;
import trackitnus.logic.parser.Parser;
import trackitnus.logic.parser.ParserUtil;
import trackitnus.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteLessonCommand object
 */
public class DeleteLessonCommandParser implements Parser<DeleteLessonCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteLessonCommand
     * and returns a DeleteLessonCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteLessonCommand parse(String args) throws ParseException {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteLessonCommand(index);
    }

}
