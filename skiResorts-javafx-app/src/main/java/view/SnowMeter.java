package view;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Path;
/**
 * @author Elena Algaria
 * @author Sebastian Romanek
 */
public class SnowMeter extends Group {

    private final DoubleProperty ViewWidth = new SimpleDoubleProperty();
    private final DoubleProperty ViewHeight = new SimpleDoubleProperty();

    private final DoubleProperty SnowHeight = new SimpleDoubleProperty();

    private static double MAX_BALL_RADIUS = 9.9;
    private static final double MIN_BALL_RADIUS = 0;
    private static final double SCROLL_SPEED_FACTOR = 0.2;
    private final DoubleProperty ballRadius = new SimpleDoubleProperty(MIN_BALL_RADIUS);

    private Path snowBall1, snowBall2, snowBall3;
    private DoubleProperty snowBall1x, snowBall2x, snowBall3x;
    private Line invisibleBoardStretcher;
    private SnowScale snowScale;

    public SnowMeter() {
        initializeParts();
        layoutParts();
        setupBindings();
        setupValueChangeListeners();
    }

    private void initializeParts() {
        snowBall1x = new SimpleDoubleProperty();
        snowBall2x = new SimpleDoubleProperty();
        snowBall3x = new SimpleDoubleProperty();
        snowBall1 = new SnowBall(snowBall1x, ViewHeight, ballRadius, Color.SNOW, 0.5);
        snowBall2 = new SnowBall(snowBall2x, ViewHeight, ballRadius, Color.valueOf("#f1f1f1"), 1);
        snowBall3 = new SnowBall(snowBall3x, ViewHeight, ballRadius, Color.SNOW, 0.7);
        invisibleBoardStretcher = new Line();
        snowScale = new SnowScale();
        MAX_BALL_RADIUS = SnowScale.MAX_SNOW_HEIGHT;
        this.getChildren().addAll(snowScale, snowBall2, snowBall3, snowBall1, invisibleBoardStretcher);
    }

    private void layoutParts() {
        invisibleBoardStretcher.startXProperty().bind(viewWidthProperty());
        invisibleBoardStretcher.startYProperty().bind(viewHeightProperty());
        invisibleBoardStretcher.setOpacity(0);
        snowScale.setRotate(15);
    }

    private void setupBindings() {
        snowScale.translateYProperty().bind(ViewHeight.add(-SnowScale.ROD_HEIGHT));
        snowScale.snowHeightProperty().bindBidirectional(ballRadius);
        snowScale.snowHeightProperty().bindBidirectional(snowHeightProperty());

        snowBall1x.bind(ViewWidth.divide(4));
        snowBall2x.bind(ViewWidth.divide(4).add(30));
        snowBall3x.bind(ViewWidth.divide(4).add(60));
        snowScale.translateXProperty().bind(ViewWidth.divide(4).add(30));
    }

    private void setupValueChangeListeners() {
        snowBall1.setOnScroll(this::changeBallRadius);
        snowBall2.setOnScroll(this::changeBallRadius);
        snowBall3.setOnScroll(this::changeBallRadius);
        snowScale.setOnScroll(this::changeBallRadius);
    }

    private void changeBallRadius(ScrollEvent scroll) {
        ballRadius.set(ballRadius.get() + scroll.getDeltaY() * SCROLL_SPEED_FACTOR / 20);
        limitRadius();
    }


    //HELPER METHODS
    private void limitRadius() {
        if (ballRadius.get() > MAX_BALL_RADIUS) {
            ballRadius.set(MAX_BALL_RADIUS);
        } else if (ballRadius.get() < MIN_BALL_RADIUS) {
            ballRadius.set(MIN_BALL_RADIUS);
        }
    }


    //GETTER & SETTER

    public DoubleProperty snowHeightProperty() {
        return SnowHeight;
    }

    public DoubleProperty viewWidthProperty() {
        return ViewWidth;
    }

    public void setViewWidth(double viewWidth) {
        this.ViewWidth.set(viewWidth);
    }

    public DoubleProperty viewHeightProperty() {
        return ViewHeight;
    }

    public void setViewHeight(double viewHeight) {
        this.ViewHeight.set(viewHeight);
    }
}
