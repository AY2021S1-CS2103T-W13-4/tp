package trackitnus.ui.upcoming;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import trackitnus.model.lesson.Lesson;
import trackitnus.ui.lesson.LessonCard;

/**
 * An UI component that displays information of a {@code Lesson} in the Upcoming Tab.
 */
public class UpcomingLessonCard extends LessonCard {

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on TrackIter level 4</a>
     */

    @FXML
    private Label code;
    @FXML
    private Label date;


    /**
     * Creates a {@code UpcomingLessonCard} with the given {@code Lesson} and index to display.
     */
    public UpcomingLessonCard(Lesson lesson, int displayedIndex, Color lessonColor) {
        super(lesson, displayedIndex);
        code.setText(lesson.getCode().code);
        date.setText(lesson.getTime().toString().substring(4));
        date.setStyle("-fx-text-fill: " + getColorHex(lessonColor) + ";");
    }

    /**
     * Get the hexcode color from a java.scene.paint.Color class for Label fill.
     *
     * @param color the color to generate hexcode.
     * @return String the hexcode of the color.
     */
    private String getColorHex(Color color) {
        java.awt.Color c = new java.awt.Color((float) color.getRed(),
            (float) color.getGreen(),
            (float) color.getBlue(),
            (float) color.getOpacity());
        String hex = String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue());
        return hex;
    }
}

