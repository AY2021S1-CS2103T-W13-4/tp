package trackitnus.ui.upcoming;

import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import trackitnus.logic.Logic;
import trackitnus.logic.commands.exceptions.CommandException;
import trackitnus.model.task.Task;
import trackitnus.ui.UiPart;
import trackitnus.ui.task.OverdueFutureTaskCard;

/**
 * A UI component that displays information of a {@code UpcomingSection}
 */
public class UpcomingSectionCard extends UiPart<Region> {
    private static final String FXML = "Upcoming/CalendarSectionCard.fxml";
    private static final int TASK_ROW_HEIGHT = 45;
    public final UpcomingSection section;
    private final Logic logic;
    private String title;

    @FXML
    private ListView<Task> taskListView;
    @FXML
    private Label sectionTitle;

    /**
     * Constructor for a section in the calendar
     *
     * @param section  a section in the calendar
     * @param taskList the tasklist to display
     * @param logic    logic
     */
    public UpcomingSectionCard(UpcomingSection section, ObservableList<Task> taskList, Logic logic) {
        super(FXML);
        this.section = section;
        this.logic = logic;
        sectionTitle.setText(section.getTitle());
        if (section.getTitle().equals("Overdue")) {
            sectionTitle.setStyle("-fx-text-fill: #D53636");
        }
        setUpTaskView(taskList);
    }

    private void setUpTaskView(ObservableList<Task> taskList) {
        taskListView.prefHeightProperty().bind(Bindings.size(taskList).multiply(TASK_ROW_HEIGHT).add(10));
        taskListView.setItems(taskList);
        taskListView.setCellFactory(listView -> new TaskListViewCell());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof UpcomingSectionCard)) {
            return false;
        }

        // state check
        UpcomingSectionCard card = (UpcomingSectionCard) other;
        return title.equals(card.title);

    }

    class TaskListViewCell extends ListCell<Task> {
        @Override
        protected void updateItem(Task task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                try {
                    setGraphic(new OverdueFutureTaskCard(task, logic.getTaskIndex(task).getOneBased()).getRoot());
                } catch (CommandException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
