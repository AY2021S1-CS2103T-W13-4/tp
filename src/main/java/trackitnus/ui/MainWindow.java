package trackitnus.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import trackitnus.commons.core.GuiSettings;
import trackitnus.commons.core.LogsCenter;
import trackitnus.commons.core.Messages;
import trackitnus.logic.Logic;
import trackitnus.logic.commands.CommandResult;
import trackitnus.logic.commands.exceptions.CommandException;
import trackitnus.logic.parser.exceptions.ParseException;
import trackitnus.model.contact.Contact;
import trackitnus.model.module.Module;
import trackitnus.ui.contact.ContactPanel;
import trackitnus.ui.module.ModulePanel;
import trackitnus.ui.upcoming.UpcomingPanel;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private final Stage primaryStage;
    private final Logic logic;
    private String tabInContext = "";

    private ResultDisplay resultDisplay;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private StackPane modulePanelPlaceholder;

    @FXML
    private StackPane contactPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private BorderPane borderPane;

    @FXML
    private StackPane sidePanelPlaceholder;

    @FXML
    private StackPane tabPanelPlaceholder;

    @FXML
    private StackPane upcomingPanelPlaceholder;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

//        setAccelerators();

    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

//    private void setAccelerators() {
//        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
//    }

    /**
     * Sets the accelerator of a MenuItem.
     *
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() throws CommandException {

        UpcomingPanel upcomingPanel = new UpcomingPanel(logic);

        SidePanel sidePanel = new SidePanel(tabValues -> {
            try {
                switchTab(tabValues);
            } catch (CommandException e) {
                e.printStackTrace();
            }
        }, logic);
        sidePanelPlaceholder.getChildren().add(sidePanel.getRoot());

        //Default tab open
        ArrayList<Object> upcomingValues = new ArrayList<>(Arrays.asList((Object) "U"));
        switchTab(upcomingValues);

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getTrackIterFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    private void switchTab(ArrayList<Object> tabValues) throws CommandException {
        assert (tabValues.size() >= 1);
        logger.info("Switching tab to: " + tabValues.get(0));
        tabPanelPlaceholder.getChildren().clear();
        String tabName = String.valueOf(tabValues.get(0));
        tabInContext = tabName;
        logic.clearAllList();

        switch (tabName) {
        case UpcomingPanel.TYPE:
            tabPanelPlaceholder.getChildren().add(new UpcomingPanel(logic).getRoot());
            break;
        case Module.TYPE:
            assert (tabValues.size() == 2);
            Module tabModule = (Module) tabValues.get(1);
            tabInContext = tabModule.getCode().toString();
            logger.info("Module: " + tabModule);
            // Independent Ui parts residing in this Ui container
            ModulePanel modulePanel = new ModulePanel(tabModule, logic);
            tabPanelPlaceholder.getChildren().add(modulePanel.getRoot());
            break;
        case Contact.TYPE:
            ContactPanel contactPanel = new ContactPanel(logic.getAllContacts());
            tabPanelPlaceholder.getChildren().add(contactPanel.getRoot());
            break;
        case HelpPanel.TYPE:
            HelpPanel helpPanel = new HelpPanel();
            tabPanelPlaceholder.getChildren().add(helpPanel.getRoot());
            break;
        default:
            throw new IllegalArgumentException(Messages.MESSAGE_INVALID_TAB_VALUE);
        }
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
            (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        primaryStage.hide();
    }

    /**
     * Executes the command and returns the result.
     *
     * @see Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isExit()) {
                handleExit();
            }

            if (commandResult.getNameOfEditedModule().equals(tabInContext)) {
                switchTab(new ArrayList<>(Arrays.asList((Object) "U")));
            }

            if (commandResult.isShowHelp()) {
                switchTab(new ArrayList<>(Arrays.asList((Object) "H")));
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
