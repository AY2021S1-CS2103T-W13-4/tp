package trackitnus.logic.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import trackitnus.commons.core.Messages;
import trackitnus.logic.commands.Command;
import trackitnus.logic.commands.ExitCommand;
import trackitnus.logic.commands.HelpCommand;
import trackitnus.logic.commands.contact.AddContactCommand;
import trackitnus.logic.commands.contact.ClearContactCommand;
import trackitnus.logic.commands.contact.DeleteContactCommand;
import trackitnus.logic.commands.contact.EditContactCommand;
import trackitnus.logic.commands.contact.FindContactCommand;
import trackitnus.logic.commands.contact.ListContactCommand;
import trackitnus.logic.commands.lesson.AddLessonCommand;
import trackitnus.logic.commands.lesson.DeleteLessonCommand;
import trackitnus.logic.commands.lesson.EditLessonCommand;
import trackitnus.logic.commands.lesson.ListLessonCommand;
import trackitnus.logic.commands.lesson.ViewLessonCommand;
import trackitnus.logic.commands.module.AddModuleCommand;
import trackitnus.logic.commands.module.DeleteModuleCommand;
import trackitnus.logic.commands.module.EditModuleCommand;
import trackitnus.logic.commands.module.ViewModuleCommand;
import trackitnus.logic.commands.task.AddTaskCommand;
import trackitnus.logic.commands.task.DeleteTaskCommand;
import trackitnus.logic.commands.task.EditTaskCommand;
import trackitnus.logic.commands.task.ListTaskCommand;
import trackitnus.logic.commands.task.ViewTaskCommand;
import trackitnus.logic.parser.contact.AddContactCommandParser;
import trackitnus.logic.parser.contact.DeleteContactCommandParser;
import trackitnus.logic.parser.contact.EditContactCommandParser;
import trackitnus.logic.parser.contact.FindContactCommandParser;
import trackitnus.logic.parser.exceptions.ParseException;
import trackitnus.logic.parser.lesson.AddLessonCommandParser;
import trackitnus.logic.parser.lesson.DeleteLessonCommandParser;
import trackitnus.logic.parser.lesson.EditLessonCommandParser;
import trackitnus.logic.parser.lesson.ListLessonCommandParser;
import trackitnus.logic.parser.lesson.ViewLessonCommandParser;
import trackitnus.logic.parser.module.AddModuleCommandParser;
import trackitnus.logic.parser.module.DeleteModuleCommandParser;
import trackitnus.logic.parser.module.EditModuleCommandParser;
import trackitnus.logic.parser.module.ViewModuleCommandParser;
import trackitnus.logic.parser.task.AddTaskCommandParser;
import trackitnus.logic.parser.task.DeleteTaskCommandParser;
import trackitnus.logic.parser.task.EditTaskCommandParser;
import trackitnus.logic.parser.task.ViewTaskCommandParser;
import trackitnus.model.contact.Contact;
import trackitnus.model.lesson.Lesson;
import trackitnus.model.module.Module;
import trackitnus.model.task.Task;

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
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
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
                throw new ParseException(Messages.MESSAGE_UNKNOWN_COMMAND);
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

            default:
                throw new ParseException(Messages.MESSAGE_UNKNOWN_COMMAND);
            }
        case Task.TYPE:
            switch (commandWord) {
            case AddTaskCommand.COMMAND_WORD:
                return new AddTaskCommandParser().parse(arguments);

            case EditTaskCommand.COMMAND_WORD:
                return new EditTaskCommandParser().parse(arguments);

            case DeleteTaskCommand.COMMAND_WORD:
                return new DeleteTaskCommandParser().parse(arguments);

            case ListTaskCommand.COMMAND_WORD:
                return new ListTaskCommand();

            case ViewTaskCommand.COMMAND_WORD:
                return new ViewTaskCommandParser().parse(arguments);

            default:
                throw new ParseException(Messages.MESSAGE_UNKNOWN_COMMAND);
            }
        case Lesson.TYPE:
            switch (commandWord) {
            case AddLessonCommand.COMMAND_WORD:
                return new AddLessonCommandParser().parse(arguments);

            case EditLessonCommand.COMMAND_WORD:
                return new EditLessonCommandParser().parse(arguments);

            case DeleteLessonCommand.COMMAND_WORD:
                return new DeleteLessonCommandParser().parse(arguments);

            case ListLessonCommand.COMMAND_WORD:
                return new ListLessonCommandParser().parse(arguments);

            case ViewLessonCommand.COMMAND_WORD:
                return new ViewLessonCommandParser().parse(arguments);

            default:
                throw new ParseException(Messages.MESSAGE_UNKNOWN_COMMAND);
            }
        case "":
            switch (commandWord) {
            case ExitCommand.COMMAND_WORD:
                return new ExitCommand();

            case HelpCommand.COMMAND_WORD:
                return new HelpCommand();

            default:
                throw new ParseException(Messages.MESSAGE_UNKNOWN_COMMAND);
            }
        default:
            throw new ParseException(Messages.MESSAGE_INVALID_TYPE);
        }
    }

}
