package view;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.util.Objects;
/**
 * @author Elena Algaria
 * @author Sebastian Romanek
 */
public class SnowScale extends Group {

    private final DoubleProperty snowHeight = new SimpleDoubleProperty();
    public static final double MAX_SNOW_HEIGHT = 9.9;
    public static final double ROD_HEIGHT = 90;
    public static final double ROD_WIDTH = 20;
    public static final double ROOF_HEIGHT = 12.5;
    public static final double ROOF_WIDTH = 25;
    public static final double STROKE_WIDTH = 1.5;
    public static final double FONT_SIZE = 10;

    private Rectangle rod;
    private Polygon roof;
    private Label scaleLabel;
    private Label unitLabel;

    public SnowScale() {
        initializeSelf();
        initializeParts();
        layoutParts();
        setupValueChangeListeners();
    }

    private void initializeSelf() {
        loadFonts("/fonts/Roboto/Roboto-Regular.ttf", "/fonts/Roboto/Roboto_Bold.ttf");
        addStylesheetFiles("/css/style.css");
    }

    private void initializeParts() {
        rod = new Rectangle(ROD_WIDTH, ROD_HEIGHT);
        roof = new Polygon();
        scaleLabel = new Label("0.0");
        unitLabel = new Label("m");
        getChildren().addAll(rod, roof, scaleLabel, unitLabel);
    }

    private void layoutParts() {
        scaleLabel.getStyleClass().add("scale");
        unitLabel.getStyleClass().add("unit");
        roof.getStyleClass().add("roof");
        rod.getStyleClass().add("rod");

        roof.getPoints().addAll(
            (ROD_WIDTH - ROOF_WIDTH) / 2, STROKE_WIDTH,
            ROOF_WIDTH + (ROD_WIDTH - ROOF_WIDTH) / 2, STROKE_WIDTH,
            ROOF_WIDTH / 2 + (ROD_WIDTH - ROOF_WIDTH) / 2, -ROOF_HEIGHT + STROKE_WIDTH);
        roof.setTranslateY(-STROKE_WIDTH);

        rod.setTranslateY(-STROKE_WIDTH);

        scaleLabel.setFont(Font.font("Roboto", FONT_SIZE));
        scaleLabel.setTranslateX((ROD_WIDTH - 13) / 2);
        scaleLabel.setTranslateY(4);

        unitLabel.setTranslateX((ROD_WIDTH - 7) / 2);
        unitLabel.setTranslateY(scaleLabel.getTranslateY() + scaleLabel.getFont().getSize());
    }

    private void setupValueChangeListeners() {
        snowHeightProperty().addListener((obs, oldValue, newValue) -> {
            newValue = (Math.round(newValue.doubleValue() * 10) / 10.);
            if ((double) newValue > MAX_SNOW_HEIGHT) {
                newValue = MAX_SNOW_HEIGHT;
            }
            scaleLabel.setText(newValue.toString());
        });
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
    public DoubleProperty snowHeightProperty() {
        return snowHeight;
    }
}
