package view;

import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.util.Objects;
/**
 * @author Elena Algaria
 * @author Sebastian Romanek
 */
public class ResortStatus extends Pane {
    private final BooleanProperty isOpen = new SimpleBooleanProperty(false);
    private Rectangle rectangle;
    private Line line;
    private Line line2;
    private Circle circle;
    private Text open;

    public ResortStatus() {
        initializeSelf();
        initializeParts();
        layoutParts();
        setupValueChangeListeners();
        setupEventHandler();
    }

    private void initializeSelf() {
        loadFonts("/fonts/Roboto/Roboto-Regular.ttf", "/fonts/Roboto/Roboto_Bold.ttf");
        addStylesheetFiles("/css/style.css");
    }

    private void initializeParts() {
        rectangle = new Rectangle(160, 60);
        rectangle.getStyleClass().add("board");

        line = new Line(148, 83, 188, 14);
        line.getStyleClass().add("line");
        line.setStrokeLineCap(StrokeLineCap.ROUND);

        line2 = new Line(255, 73, 190, 14);
        line2.getStyleClass().add("line2");
        line2.setStrokeLineCap(StrokeLineCap.ROUND);

        circle = new Circle();
        circle.getStyleClass().add("circle");

        open = new Text("OPEN");
        open.getStyleClass().add("statusText");
    }

    private void layoutParts() {
        rectangle.setX(120);
        rectangle.setY(80);

        rectangle.setArcHeight(30);
        rectangle.setArcWidth(30);

        circle.setCenterY(10);
        circle.setCenterX(188.5);
        circle.setRadius(5);

        open.setX(146);
        open.setY(125);

        this.getChildren().addAll(rectangle, open, line, line2, circle);
        this.setScaleX(0.7);
        this.setScaleY(0.7);
    }

    private void initializeAnimations(boolean isOpen) {
        RotateTransition rotate = new RotateTransition(Duration.seconds(0.5), this);
        rotate.setAxis(Rotate.Y_AXIS);
        rotate.setCycleCount(1);
        if (!isOpen) {
            rotate.setFromAngle(0.0);
            rotate.setByAngle(180);
            rotate.setOnFinished(event -> {
                open.setText("CLOSED");
                open.setRotationAxis(Rotate.Y_AXIS);
                open.setRotate(180);
                open.setX(125);
            });

        } else {
            rotate.setFromAngle(180);
            rotate.setByAngle(180);
            rotate.setOnFinished(event -> {
                open.setText("OPEN");
                open.setRotationAxis(Rotate.Y_AXIS);
                open.setRotate(10);
                open.setX(145);
            });
        }
        if (!rotate.getStatus().equals(Animation.Status.RUNNING)) {
            rotate.play();
        }
    }


    private void setupValueChangeListeners() {
        isOpenProperty().addListener((observable, oldValue, newValue) -> initializeAnimations(newValue));
    }

    private void setupEventHandler() {
        this.setOnMouseClicked((e) -> {
            if (isOpen.get()) {
                isOpen.set(false);
                initializeAnimations(true);
            } else {
                isOpen.set(true);
                initializeAnimations(false);
            }
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
    public BooleanProperty isOpenProperty() {
        return isOpen;
    }

}
