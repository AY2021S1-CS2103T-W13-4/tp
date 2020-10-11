package seedu.address.logic.commands.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MODULES;

import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.commons.Code;
import seedu.address.model.commons.Name;
import seedu.address.model.module.Module;

public class EditModuleCommand extends Command {
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = Module.TYPE + COMMAND_WORD + ": Edits the details of the module "
        + "identified "
        + "by the module code "
        + "Existing values will be overwritten by the input values.\n"
        + "Parameters: "
        + PREFIX_CODE + "CODE (must be an existing code) "
        + "[" + PREFIX_NAME + "NAME] "
        + "[" + PREFIX_DESC + "DESC] "
        + String.format("Example: %s %s %sCS1231S %sDiscrete Structures %sIntroductory mathematical tools",
        Module.TYPE, COMMAND_WORD, PREFIX_CODE, PREFIX_NAME, PREFIX_DESC);

    public static final String MESSAGE_EDIT_MODULE_SUCCESS = "Edited Module: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_MODULE = "This module already exists";

    private final Code code;
    private final EditModuleDescriptor editModuleDescriptor;

    /**
     * @param code                 of the module in the filtered module list to edit
     * @param editModuleDescriptor details to edit the module with
     */
    public EditModuleCommand(Code code, EditModuleDescriptor editModuleDescriptor) {
        requireNonNull(code);
        requireNonNull(editModuleDescriptor);

        this.code = code;
        this.editModuleDescriptor = new EditModuleDescriptor(editModuleDescriptor);
    }

    /**
     * Creates and returns a {@code Model} with the details of {@code moduleToEdit}
     * edited with {@code editModuleDescriptor}.
     */
    private static Module createEditedModule(Module moduleToEdit,
                                             EditModuleDescriptor editModuleDescriptor) {
        assert moduleToEdit != null;

        Code updatedCode = editModuleDescriptor.getCode().orElse(moduleToEdit.getCode());
        Name updatedName = editModuleDescriptor.getName().orElse(moduleToEdit.getName());
        String updatedDesc = editModuleDescriptor.getDesc().orElse(moduleToEdit.getDesc());

        return new Module(updatedCode, updatedName, updatedDesc);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Optional<Module> moduleToEdit = model.getModule(code);
        if (moduleToEdit.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_MODULE_DOES_NOT_EXIST);
        }

        Module editedModule = createEditedModule(moduleToEdit.get(), editModuleDescriptor);

        if (!moduleToEdit.get().isSameModule(editedModule) && model.hasModule(editedModule)) {
            throw new CommandException(MESSAGE_DUPLICATE_MODULE);
        }

        model.setModule(moduleToEdit.get(), editedModule);
        model.updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
        return new CommandResult(String.format(MESSAGE_EDIT_MODULE_SUCCESS, editedModule));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditModuleCommand)) {
            return false;
        }

        // state check
        EditModuleCommand e = (EditModuleCommand) other;
        return code.equals(e.code)
            && editModuleDescriptor.equals(e.editModuleDescriptor);
    }

    /**
     * Stores the details to edit the module with. Each non-empty field value will replace the
     * corresponding field value of the module.
     */
    public static class EditModuleDescriptor {

        private Code code;
        private Name name;
        private String desc;

        public EditModuleDescriptor() {
        }

        /**
         * Copy constructor.
         */
        public EditModuleDescriptor(EditModuleDescriptor toCopy) {
            setCode(toCopy.code);
            setName(toCopy.name);
            setDesc(toCopy.desc);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(code, name, desc);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditModuleDescriptor)) {
                return false;
            }

            // state check
            EditModuleDescriptor e = (EditModuleDescriptor) other;

            return getCode().equals(e.getCode())
                && getName().equals(e.getName())
                && getDesc().equals(e.getDesc());
        }

        public Optional<Code> getCode() {
            return Optional.ofNullable(code);
        }

        public void setCode(Code code) {
            this.code = code;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<String> getDesc() {
            return Optional.ofNullable(desc);
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
