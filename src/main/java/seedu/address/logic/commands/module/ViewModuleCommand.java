package seedu.address.logic.commands.module;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Code;
import seedu.address.model.module.Module;

public class ViewModuleCommand extends Command {
    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "sample";

    public static final String MESSAGE_DELETE_MODULE_SUCCESS = "Deleted Module: %1$s";
    private static final String MESSAGE_VIEW_MODULE_SUCCESS = "Here is the module you requested: %1$s";

    private final Code codeToView;

    public ViewModuleCommand(Code codeToView) {
        this.codeToView = codeToView;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Optional<Module> requestedModule = model.getModule(codeToView);


        if (requestedModule.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_MODULE_DOES_NOT_EXIST);
        }

        return new CommandResult(String.format(MESSAGE_VIEW_MODULE_SUCCESS, requestedModule.get()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ViewModuleCommand // instanceof handles nulls
            && codeToView.equals(((ViewModuleCommand) other).codeToView)); // state check
    }
}
