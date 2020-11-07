package trackitnus.ui.upcoming;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import trackitnus.model.task.Task;
import trackitnus.ui.task.TaskCard;

/**
 * An UI component that displays information of a {@code task} in the Upcoming Tab.
 */
public class UpcomingTaskCard extends TaskCard {

    @FXML
    private Label date;
    @FXML
    private Label code;

    /**
     * Creates a {@code UpcomingTaskCard} with the given {@code Task} and index to display.
     */
    public UpcomingTaskCard(Task task, int displayedIndex) {
        super(task, displayedIndex);
        date.setText("");
        code.setText(task.getCode().isPresent() ? task.getCode().get().code + " " : "");
    }
}
