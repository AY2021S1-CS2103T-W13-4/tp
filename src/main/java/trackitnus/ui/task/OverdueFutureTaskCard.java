package trackitnus.ui.task;

import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import trackitnus.model.task.Task;

/**
 * An UI component that displays information of an {@code overdue or future task}.
 */
public class OverdueFutureTaskCard extends TaskCard {

    @FXML
    private Label date;
    @FXML
    private Label code;

    /**
     * Creates a {@code OverdueFutureTaskCard} with the given {@code Task} and index to display.
     */
    public OverdueFutureTaskCard(Task task, int displayedIndex) {
        super(task, displayedIndex);
        code.setText(task.getCode().isPresent() ? task.getCode().get().code + " " : "");

        LocalDate today = LocalDate.now();
        if (task.getDate().isAfter(today.plusDays(7))) {
            date.setStyle("-fx-text-fill: #68C2E8");
        } else {
            date.setStyle("-fx-text-fill: #d53636");
        }

    }

}
