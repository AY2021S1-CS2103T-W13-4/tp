package trackitnus.ui.lesson;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import trackitnus.model.lesson.Lesson;
import trackitnus.ui.UiPart;

/**
 * An UI component that displays information of a {@code Lesson}.
 */
public class LessonCard extends UiPart<Region> {

    private static final String FXML = "Lesson/LessonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on TrackIter level 4</a>
     */

    public final Lesson lesson;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label type;
    @FXML
    private Label date;
    @FXML
    private Label address;
    @FXML
    private Label code;

    /**
     * Creates a {@code ContactCode} with the given {@code Lesson} and index to display.
     */
    public LessonCard(Lesson lesson, int displayedIndex) {
        super(FXML);
        this.lesson = lesson;
        id.setText("[" + displayedIndex + "]");
        type.setText(lesson.getType().name());
        date.setText(lesson.getTime().toString());
        address.setText(lesson.getAddress().toString());
        code.setText("");
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LessonCard)) {
            return false;
        }

        // state check
        LessonCard card = (LessonCard) other;
        return id.getText().equals(card.id.getText())
            && lesson.equals(card.lesson);
    }
}

