package view;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;
import javafx.util.Duration;

import java.util.Objects;

/**
 * This control shows if the whether the ski resort is car-free or not.
 * <p>
 * Link to Figma-file: https://www.figma.com/file/Tw9orh0W8QAxJ1XUUs19t8/Dashboard---cuie?node-id=2%3A2
 *
 * @author jan.waelti@students.fhnw.ch
 * @author martin.ott@students.fhnw.ch
 */
public class CarControl extends Region {
    public enum Status {
        OPEN(new Point2D(47, 153), new Point2D(47, 47)), CLOSED(new Point2D(47, 153), new Point2D(153, 47));

        private final Point2D lineStart;
        private final Point2D lineEnd;

        Status(Point2D lineStart, Point2D lineEnd) {
            this.lineStart = lineStart;
            this.lineEnd = lineEnd;
        }

    }

    private static final double ARTBOARD_WIDTH = 200;
    private static final double ARTBOARD_HEIGHT = 200;
    private static final double ASPECT_RATIO = ARTBOARD_WIDTH / ARTBOARD_HEIGHT;
    private static final double MINIMUM_WIDTH = 100;
    private static final double MINIMUM_HEIGHT = MINIMUM_WIDTH / ASPECT_RATIO;
    private static final double MAXIMUM_WIDTH = 1000;

    //Car
    private SVGPath car;

    //Line
    private Line line;

    //Properties
    private final ObjectProperty<Status> status = new SimpleObjectProperty<>();
    private final BooleanProperty carFree = new SimpleBooleanProperty();

    //Timeline
    private final Timeline timeline = new Timeline();

    private ScaleTransition scaleDown;
    private ScaleTransition scaleUp;

    private Pane drawingPane;

    public CarControl() {
        initializeSelf();
        initializeParts();
        initializeDrawingPane();
        initializeAnimations();
        layoutParts();
        setupEventHandlers();
        setupValueChangeListeners();
        animations();
    }

    private void initializeSelf() {
        addStylesheetFiles("/css/style.css");
        getStyleClass().add("car");
    }

    private void initializeParts() {
        //Car
        car = new SVGPath();
        String pathCar =
            "M14.7235 39.75C14.0402 39.75 13.5581 39.0799 13.7754 38.432L23.1046 10.6195C23.2412 10.2121 23.6229 9.9375 24.0527 9.9375H95.9473C96.3771 9.9375 96.7588 10.2121 96.8954 10.6195L106.225 38.432C106.442 39.0799 105.96 39.75 105.276 39.75H14.7235ZM96.6667 72.875C94.0145 72.875 91.471 71.828 89.5956 69.9644C87.7202 68.1007 86.6667 65.5731 86.6667 62.9375C86.6667 60.3019 87.7202 57.7743 89.5956 55.9106C91.471 54.047 94.0145 53 96.6667 53C99.3188 53 101.862 54.047 103.738 55.9106C105.613 57.7743 106.667 60.3019 106.667 62.9375C106.667 65.5731 105.613 68.1007 103.738 69.9644C101.862 71.828 99.3188 72.875 96.6667 72.875ZM23.3333 72.875C20.6812 72.875 18.1376 71.828 16.2623 69.9644C14.3869 68.1007 13.3333 65.5731 13.3333 62.9375C13.3333 60.3019 14.3869 57.7743 16.2623 55.9106C18.1376 54.047 20.6812 53 23.3333 53C25.9855 53 28.529 54.047 30.4044 55.9106C32.2798 57.7743 33.3333 60.3019 33.3333 62.9375C33.3333 65.5731 32.2798 68.1007 30.4044 69.9644C28.529 71.828 25.9855 72.875 23.3333 72.875ZM106.133 6.625C104.8 2.7825 101.067 0 96.6667 0H23.3333C18.9333 0 15.2 2.7825 13.8667 6.625L2.79014 38.3768C0.943205 43.6712 0 49.2386 0 54.8459V99.375C0 101.132 0.702379 102.817 1.95262 104.06C3.20286 105.302 4.89856 106 6.66667 106H13.3333C15.1014 106 16.7971 105.302 18.0474 104.06C19.2976 102.817 20 101.132 20 99.375V93.75C20 93.1977 20.4477 92.75 21 92.75H99C99.5523 92.75 100 93.1977 100 93.75V99.375C100 101.132 100.702 102.817 101.953 104.06C103.203 105.302 104.899 106 106.667 106H113.333C115.101 106 116.797 105.302 118.047 104.06C119.298 102.817 120 101.132 120 99.375V54.8459C120 49.2386 119.057 43.6712 117.21 38.3768L106.133 6.625Z";

        car.setContent(pathCar);
        car.setTranslateX((ARTBOARD_WIDTH - 120) / 2);
        car.setTranslateY((ARTBOARD_HEIGHT - 106) / 2);
        car.getStyleClass().add("car-background");

        //Line
        line = new Line();
        line.getStyleClass().add("line");
        line.setMouseTransparent(true);
    }

    private void initializeDrawingPane() {
        drawingPane = new Pane();
        drawingPane.getStyleClass().add("drawing-pane");
        drawingPane.setMaxSize(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);
        drawingPane.setMinSize(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);
        drawingPane.setPrefSize(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);
    }

    private void initializeAnimations() {
        scaleDown = new ScaleTransition(Duration.millis(300), car);
        scaleDown.setFromX(1);
        scaleDown.setFromY(1);
        scaleDown.setToX(0.95);
        scaleDown.setToY(0.95);

        scaleUp = new ScaleTransition(Duration.millis(300), car);
        scaleUp.setFromX(0.95);
        scaleUp.setFromY(0.95);
        scaleUp.setToX(1.0);
        scaleUp.setToY(1.0);
    }

    private void layoutParts() {
        drawingPane.getChildren().addAll(car, line);
        getChildren().add(drawingPane);
    }

    private void setupEventHandlers() {
        car.setOnMouseClicked(event -> setCarFree(!getCarFree()));

        car.setOnMousePressed(event -> scaleDown());

        car.setOnMouseReleased(event -> scaleUp());
    }

    private void setupValueChangeListeners() {

        carFreeProperty().addListener(((observable, oldValue, newValue) -> {
            if (getCarFree()) {
                setStatus(Status.CLOSED);
            } else {
                setStatus(Status.OPEN);
            }
        }));

        carFreeProperty().addListener((observable, oldValue, newValue) -> updateUI());
    }

    private void updateUI() {
        timeline.stop();

        timeline.getKeyFrames().setAll(
            new KeyFrame(Duration.millis(300), new KeyValue(line.startXProperty(), getStatus().lineStart.getX()),
                new KeyValue(line.startYProperty(), getStatus().lineStart.getY()),
                new KeyValue(line.endXProperty(), getStatus().lineEnd.getX()),
                new KeyValue(line.endYProperty(), getStatus().lineEnd.getY())));

        timeline.play();

        animations();
    }

    private void scaleDown() {
        if (scaleDown.getStatus().equals(Animation.Status.RUNNING)) {
            scaleDown.stop();
        }
        scaleDown.play();
    }

    private void scaleUp() {
        if (scaleUp.getStatus().equals(Animation.Status.RUNNING)) {
            scaleUp.stop();
        }
        scaleUp.play();
    }

    public void animations() {

        //Transition
        FadeTransition fadeTransition;
        if (!getCarFree()) {
            fadeTransition = new FadeTransition(Duration.millis(300), line);
            fadeTransition.setFromValue(1);
            fadeTransition.setToValue(0);
            scaleDown();
            scaleUp();
        } else {
            fadeTransition = new FadeTransition(Duration.millis(300), line);
            fadeTransition.setFromValue(0);
            fadeTransition.setToValue(1);
            scaleDown();
            scaleUp();
        }
        if (!fadeTransition.getStatus().equals(Animation.Status.RUNNING)) {
            fadeTransition.play();
        }
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        resize();
    }

    private void resize() {
        Insets padding = getPadding();
        double availableWidth = getWidth() - padding.getLeft() - padding.getRight();
        double availableHeight = getHeight() - padding.getTop() - padding.getBottom();

        double width =
            Math.max(Math.min(Math.min(availableWidth, availableHeight * ASPECT_RATIO), MAXIMUM_WIDTH), MINIMUM_WIDTH);

        double scalingFactor = width / ARTBOARD_WIDTH;

        if (availableWidth > 0 && availableHeight > 0) {
            relocateDrawingPaneCentered();
            drawingPane.setScaleX(scalingFactor);
            drawingPane.setScaleY(scalingFactor);
        }
    }

    private void relocateDrawingPaneCentered() {
        drawingPane.relocate((getWidth() - ARTBOARD_WIDTH) * 0.5, (getHeight() - ARTBOARD_HEIGHT) * 0.5);
    }

    private void addStylesheetFiles(String... stylesheetFile) {
        for (String file : stylesheetFile) {
            String stylesheet = Objects.requireNonNull(getClass().getResource(file)).toExternalForm();
            getStylesheets().add(stylesheet);
        }
    }

    /**
     * Berechnet den Punkt auf einem Kreis mit gegebenen Radius im angegebenen Winkel
     *
     * @param cX     x-Position des Zentrums
     * @param cY     y-Position des Zentrums
     * @param radius Kreisradius
     * @param angle  Winkel zwischen 0 und 360 Grad
     * @return Punkt auf dem Kreis
     */
    private Point2D pointOnCircle(double cX, double cY, double radius, double angle) {
        return new Point2D(cX - (radius * Math.sin(Math.toRadians(angle - 180))),
            cY + (radius * Math.cos(Math.toRadians(angle - 180))));
    }

    // compute sizes
    @Override
    protected double computeMinWidth(double height) {
        Insets padding = getPadding();
        double horizontalPadding = padding.getLeft() + padding.getRight();

        return MINIMUM_WIDTH + horizontalPadding;
    }

    @Override
    protected double computeMinHeight(double width) {
        Insets padding = getPadding();
        double verticalPadding = padding.getTop() + padding.getBottom();

        return MINIMUM_HEIGHT + verticalPadding;
    }

    @Override
    protected double computePrefWidth(double height) {
        Insets padding = getPadding();
        double horizontalPadding = padding.getLeft() + padding.getRight();

        return ARTBOARD_WIDTH + horizontalPadding;
    }

    @Override
    protected double computePrefHeight(double width) {
        Insets padding = getPadding();
        double verticalPadding = padding.getTop() + padding.getBottom();

        return ARTBOARD_HEIGHT + verticalPadding;
    }

    // alle getter und setter  (generiert via "Code -> Generate... -> Getter and Setter)

    public Status getStatus() {
        return status.get();
    }

    public void setStatus(Status status) {
        this.status.set(status);
    }

    public boolean getCarFree() {
        return carFree.get();
    }

    public BooleanProperty carFreeProperty() {
        return carFree;
    }

    public void setCarFree(boolean carFree) {
        this.carFree.set(carFree);
    }
}
