package trackitnus.logic.parser.task;

import trackitnus.commons.core.index.Index;
import trackitnus.logic.commands.task.DeleteTaskCommand;
import trackitnus.logic.parser.Parser;
import trackitnus.logic.parser.ParserUtil;
import trackitnus.logic.parser.exceptions.ParseException;

public class DeleteTaskCommandParser implements Parser<DeleteTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteTaskCommand
     * and returns a DeleteTaskCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteTaskCommand parse(String args) throws ParseException {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteTaskCommand(index);
    }
}
