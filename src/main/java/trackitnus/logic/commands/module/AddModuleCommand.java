package trackitnus.logic.commands.module;

import static java.util.Objects.requireNonNull;
import static trackitnus.logic.parser.CliSyntax.PREFIX_CODE;
import static trackitnus.logic.parser.CliSyntax.PREFIX_NAME;

import trackitnus.commons.core.Messages;
import trackitnus.logic.commands.Command;
import trackitnus.logic.commands.CommandResult;
import trackitnus.logic.commands.exceptions.CommandException;
import trackitnus.model.Model;
import trackitnus.model.module.Module;

public class AddModuleCommand extends Command {

    public static final String COMMAND_WORD = "add";
    public static final String MESSAGE_USAGE = Module.TYPE + " " + COMMAND_WORD
        + ": Adds a module to the app.\n"
        + "Parameters: "
        + PREFIX_CODE + "MODULE_CODE "
        + PREFIX_NAME + "NAME\n"
        + "Example: " + Module.TYPE + " " + COMMAND_WORD + " "
        + PREFIX_CODE + "CS1231S "
        + PREFIX_NAME + "Discrete Structures";

    private final Module toAdd;

    /**
     * Creates an AddModule to add the specified {@code Module}
     */
    public AddModuleCommand(Module module) {
        requireNonNull(module);
        toAdd = module;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasModule(toAdd)) {
            throw new CommandException(Messages.MESSAGE_DUPLICATE_MODULE);
        }

        try {
            model.addModule(toAdd);
        } catch (CommandException ce) {
            throw ce;
        }

        return new CommandResult(String.format(Messages.MESSAGE_ADD_MODULE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof AddModuleCommand // instanceof handles nulls
            && toAdd.equals(((AddModuleCommand) other).toAdd));
    }
}
