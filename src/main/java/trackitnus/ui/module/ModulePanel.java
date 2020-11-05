package trackitnus.ui.module;

import java.util.logging.Logger;

import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import trackitnus.commons.core.LogsCenter;
import trackitnus.logic.Logic;
import trackitnus.logic.commands.exceptions.CommandException;
import trackitnus.model.contact.Contact;
import trackitnus.model.lesson.Lesson;
import trackitnus.model.module.Module;
import trackitnus.model.task.Task;
import trackitnus.ui.UiPart;
import trackitnus.ui.contact.ContactListPanel;
import trackitnus.ui.lesson.LessonListPanel;
import trackitnus.ui.task.TaskListPanel;

/**
 * Panel containing the list of modules.
 */
public class ModulePanel extends UiPart<Region> {
    private static final String FXML = "Module/ModulePanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ModulePanel.class);
    private final int defaultRowHeight = 50;
    private final int lessonRowHeight = 40;
    private final int paddingHeight = 10;
    private final LessonListPanel lessonListPanel;
    private final TaskListPanel taskListPanel;
    private final ContactListPanel contactListPanel;
    @FXML
    private HBox moduleHeader;
    @FXML
    private Circle moduleCircle;
    @FXML
    private Label moduleName;
    @FXML
    private StackPane lessonListPanelPlaceholder;
    @FXML
    private Label taskHeader;
    @FXML
    private StackPane taskListPanelPlaceholder;
    @FXML
    private Label contactHeader;
    @FXML
    private StackPane contactListPanelPlaceholder;


    /**
     * Creates a {@code ModuleListPanel} with the given {@code ObservableList}.
     */
    public ModulePanel(Module module, Logic logic) throws CommandException {
        super(FXML);
        moduleHeader.setAlignment(Pos.CENTER_LEFT);
        moduleHeader.setMaxWidth(500);
        moduleName.setWrapText(true);
        moduleName.setMaxWidth(500);
        moduleName.setText(module.getCode().code + " " + module.getName().value);

        int moduleIndex = logic.getModuleIndex(module).getZeroBased();
        colorRelevantElements(moduleIndex);

        ObservableList<Lesson> lessons = logic.getModuleLessons(module.getCode());
        lessonListPanel = new LessonListPanel(lessons);
        setUpLessonView(lessons);

        ObservableList<Task> tasks = logic.getModuleTasks(module.getCode());
        taskListPanel = new TaskListPanel(tasks);
        setUpTaskView(tasks);

        ObservableList<Contact> contacts = logic.getModuleContacts(module.getCode());
        contactListPanel = new ContactListPanel(contacts);
        setUpContactsView(contacts);
    }

    private void setUpLessonView(ObservableList<Lesson> lessons) {
        lessonListPanelPlaceholder.prefHeightProperty().bind(Bindings.size(lessons)
            .multiply(lessonRowHeight).add(paddingHeight));
        lessonListPanelPlaceholder.getChildren().add(lessonListPanel.getRoot());
    }

    private void setUpTaskView(ObservableList<Task> tasks) {
        taskListPanelPlaceholder.prefHeightProperty().bind(Bindings.size(tasks)
            .multiply(defaultRowHeight).add(paddingHeight));
        taskListPanelPlaceholder.getChildren().add(taskListPanel.getRoot());
    }

    private void setUpContactsView(ObservableList<Contact> contacts) {
        contactListPanelPlaceholder.prefHeightProperty().bind(Bindings.size(contacts)
            .multiply(defaultRowHeight).add(paddingHeight));
        contactListPanelPlaceholder.getChildren().add(contactListPanel.getRoot());
    }

    private String getColorHex(Color color) {
        java.awt.Color c = new java.awt.Color((float) color.getRed(),
            (float) color.getGreen(),
            (float) color.getBlue(),
            (float) color.getOpacity());
        return String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue());
    }

    private void colorRelevantElements(int moduleIndex) {
        Color moduleColor = Module.COLORS.get(moduleIndex % 10);
        moduleCircle.setFill(moduleColor);
        taskHeader.setStyle("-fx-text-fill: " + getColorHex(moduleColor) + ";");
        contactHeader.setStyle("-fx-text-fill: " + getColorHex(moduleColor) + ";");
    }
}
