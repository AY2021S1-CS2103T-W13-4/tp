package seedu.address.logic.commands.contact;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalTrackIter;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TrackIter;
import seedu.address.model.UserPrefs;

public class ClearContactCommandTest {

    @Test
    public void execute_emptyTrackIter_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearContactCommand(), model, ClearContactCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyTrackIter_success() {
        Model model = new ModelManager(getTypicalTrackIter(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalTrackIter(), new UserPrefs());
        expectedModel.setTrackIter(new TrackIter());

        assertCommandSuccess(new ClearContactCommand(), model, ClearContactCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
