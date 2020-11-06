package trackitnus.ui.task;

import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import trackitnus.model.task.Task;
import trackitnus.ui.UiPart;

/**
 * An UI component that displays information of a {@code task}.
 */
public class TaskCard extends UiPart<Region> {
    private static final String FXML = "Task/TaskListCard.fxml";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd MMM EEEE");
    public final Task task;
    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label code;
    @FXML
    private Label name;
    @FXML
    private Label remark;
    @FXML
    private Label date;

    /**
     * Creates a {@code TaskCard} with the given {@code Task} and index to display.
     */
    public TaskCard(Task task, int displayedIndex) {
        super(FXML);
        this.task = task;
        id.setText("[" + displayedIndex + "] ");
        name.setText(task.getName().toString());
        remark.setText(task.getRemark());
        code.setText("");
        date.setText(" - " + task.getDate().format(DATE_TIME_FORMATTER));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TaskCard)) {
            return false;
        }

        // state check
        TaskCard card = (TaskCard) other;
        return id.getText().equals(card.id.getText())
            && name.equals(card.name);
    }
}
