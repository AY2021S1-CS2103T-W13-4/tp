package seedu.address.logic.commands.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CODE;

import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.commons.Code;
import seedu.address.model.module.Module;

public class DeleteModuleCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = Module.TYPE + " " + COMMAND_WORD
        + ": Deletes the module identified by the module code.\n"
        + "Parameters: " + PREFIX_CODE + "CODE (must be an existing code)\n"
        + String.format("Example: %s %s %s CS1231S", Module.TYPE, COMMAND_WORD, PREFIX_CODE);

    public static final String MESSAGE_DELETE_MODULE_SUCCESS = "Deleted Module: %1$s";

    private final Code targetCode;

    public DeleteModuleCommand(Code targetCode) {
        this.targetCode = targetCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Optional<Module> moduleToDelete = model.getModule(targetCode);
        if (moduleToDelete.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_MODULE_DOES_NOT_EXIST);
        }
        model.deleteModule(moduleToDelete.get());
        return new CommandResult(String.format(MESSAGE_DELETE_MODULE_SUCCESS, moduleToDelete.get()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DeleteModuleCommand // instanceof handles nulls
            && targetCode.equals(((DeleteModuleCommand) other).targetCode)); // state check
    }
}
