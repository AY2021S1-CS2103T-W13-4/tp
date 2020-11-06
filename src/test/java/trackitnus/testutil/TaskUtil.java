package trackitnus.testutil;

import static trackitnus.logic.parser.CliSyntax.PREFIX_CODE;
import static trackitnus.logic.parser.CliSyntax.PREFIX_DATE;
import static trackitnus.logic.parser.CliSyntax.PREFIX_NAME;
import static trackitnus.logic.parser.CliSyntax.PREFIX_REMARK;

import trackitnus.logic.commands.task.AddTaskCommand;
import trackitnus.logic.commands.task.EditTaskCommand;
import trackitnus.logic.parser.ParserUtil;
import trackitnus.model.task.Task;

/**
 * A utility class for Task.
 */
public class TaskUtil {

    /**
     * Returns an add command string for adding the {@code contact}.
     */
    public static String getAddCommand(Task task) {
        return AddTaskCommand.COMMAND_WORD + " " + getTaskDetails(task);
    }

    /**
     * Returns the part of command string for the given {@code task}'s details.
     */
    public static String getTaskDetails(Task task) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + task.getName().value + " ")
            .append(PREFIX_DATE + task.getDate().format(ParserUtil.DATE_PATTERN) + " ")
            .append(PREFIX_REMARK + task.getRemark() + " ");
        if (task.getCode().isPresent()) {
            sb.append(PREFIX_CODE + task.getCode().get().code + " ");
        }
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditTaskDescriptor}'s details.
     */
    public static String getEditTaskDescriptorDetails(EditTaskCommand.EditTaskDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.value).append(" "));
        descriptor.getDate().ifPresent(date -> sb.append(PREFIX_DATE)
            .append(date.format(ParserUtil.DATE_PATTERN)).append(" "));
        if (descriptor.getIsCodeChanged()) {
            sb.append(PREFIX_CODE);
            descriptor.getCode().ifPresent(code -> sb.append(code.code));
            sb.append(" ");
        }
        if (descriptor.getIsRemarkChanged()) {
            sb.append(PREFIX_REMARK);
            descriptor.getRemark().ifPresent(remark -> sb.append(remark));
            sb.append(" ");
        }
        return sb.toString();
    }
}
