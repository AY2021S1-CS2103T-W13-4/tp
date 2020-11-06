package trackitnus.ui.contact;

import java.util.logging.Logger;

import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import trackitnus.commons.core.LogsCenter;
import trackitnus.model.contact.Contact;
import trackitnus.ui.UiPart;


public class ContactPanel extends UiPart<Region> {
    private static final String FXML = "Contact/ContactPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ContactPanel.class);
    private static final int DEFAULT_ROW_HEIGHT = 50;
    private static final int PADDING_HEIGHT = 10;

    @FXML
    private StackPane contactListPanelPlaceholder;

    /**
     * Creates a {@code ContactListPanel} with the given {@code ObservableList}.
     */
    public ContactPanel(ObservableList<Contact> contactList) {
        super(FXML);

        // Allow height of lists to update automatically
        contactListPanelPlaceholder.prefHeightProperty().bind(Bindings.size(contactList)
            .multiply(DEFAULT_ROW_HEIGHT).add(PADDING_HEIGHT));

        ContactListPanel contactListPanel = new ContactListPanel(contactList);
        contactListPanelPlaceholder.getChildren().add(contactListPanel.getRoot());
    }


}
