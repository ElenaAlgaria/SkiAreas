package view;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.layout.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.text.Font;

import java.util.Locale;
import java.util.Objects;

/**
 * Dashboard für ein Skiapp. Zeigt den aktuellen Schneestand an und ob das Skigebiet geöffnet ist. Beide Parameter können per GUI-Interaktion verändert werden.
 *
 * @author Elena Algaria
 * @author Sebastian Romanek
 */
public class DashControls extends Pane {

    private final DoubleProperty boardViewWidth = new SimpleDoubleProperty();
    private final DoubleProperty boardViewHeight = new SimpleDoubleProperty();
    private final BooleanProperty isOpen = new SimpleBooleanProperty(false);
    private final IntegerProperty snowHeight = new SimpleIntegerProperty();
    private SnowMeter snowMeter;
    private ResortStatus resortStatus;

    public DashControls() {
        initializeSelf();
        initializeParts();
        layoutParts();
        setupBindings();
    }


    private void initializeSelf() {
        loadFonts("/fonts/Roboto/Roboto-Regular.ttf", "/fonts/Roboto/Roboto_Bold.ttf");
        addStylesheetFiles("/css/style.css");
        getStyleClass().add("dashBoard");
    }

    private void initializeParts() {
        snowMeter = new SnowMeter();
        resortStatus = new ResortStatus();
    }

    private void layoutParts() {
        getChildren().addAll(resortStatus, snowMeter);
    }

    private void setupBindings() {
        isOpen.bindBidirectional(resortStatus.isOpenProperty());
        snowHeight.bindBidirectional(snowMeter.snowHeightProperty());
    }

    //HELPER METHODS
    private void loadFonts(String... font) {
        for (String f : font) {
            Font.loadFont(getClass().getResourceAsStream(f), 0);
        }
    }

    private void addStylesheetFiles(String... stylesheetFile) {
        for (String file : stylesheetFile) {
            String stylesheet = Objects.requireNonNull(getClass().getResource(file))
                .toExternalForm();
            getStylesheets().add(stylesheet);
        }
    }

    //GETTER & SETTER
    public void setBoardViewWidth(double boardViewWidth) {
        this.boardViewWidth.set(boardViewWidth);
        snowMeter.setViewWidth(boardViewWidth);
    }

    public void setBoardViewHeight(double boardViewHeight) {
        this.boardViewHeight.set(boardViewHeight);
        snowMeter.setViewHeight(boardViewHeight);
    }

    public BooleanProperty isOpenProperty() {
        return isOpen;
    }

    public IntegerProperty snowHeightProperty() {
        return snowHeight;
    }
}
