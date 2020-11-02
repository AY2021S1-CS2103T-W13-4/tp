package trackitnus.logic.commands.module;

import static trackitnus.logic.commands.CommandTestUtil.assertCommandFailure;
import static trackitnus.logic.commands.CommandTestUtil.assertCommandSuccess;
import static trackitnus.testutil.typical.TypicalTrackIter.getTypicalTrackIter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import trackitnus.commons.core.Messages;
import trackitnus.logic.commands.exceptions.CommandException;
import trackitnus.model.Model;
import trackitnus.model.ModelManager;
import trackitnus.model.UserPrefs;
import trackitnus.model.module.Module;
import trackitnus.testutil.builder.ModuleBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddModuleCommand}.
 */
public class AddModuleCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalTrackIter(), new UserPrefs());
    }

    @Test
    public void execute_newModule_success() {
        Module validModule = new ModuleBuilder().build();

        Model expectedModel = new ModelManager(model.getTrackIter(), new UserPrefs());
        try {
            expectedModel.addModule(validModule);
        } catch (CommandException ce) {
            assert(false);
        }

        assertCommandSuccess(new AddModuleCommand(validModule), model,
            String.format(Messages.MESSAGE_ADD_MODULE_SUCCESS, validModule), expectedModel);
    }

    @Test
    public void execute_duplicateModule_throwsCommandException() {
        Module moduleInList = model.getTrackIter().getModuleList().get(0);
        assertCommandFailure(new AddModuleCommand(moduleInList), model, Messages.MESSAGE_DUPLICATE_MODULE);
    }

}
