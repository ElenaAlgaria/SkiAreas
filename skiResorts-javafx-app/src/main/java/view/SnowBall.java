package view;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.FillRule;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
/**
 * @author Elena Algaria
 * @author Sebastian Romanek
 */
public class SnowBall extends Path {

    private static final double BALL_MIN_SIZE = 5;

    public SnowBall(DoubleProperty centerX, DoubleProperty centerY, DoubleProperty radiusOuter, Color fillColor,
                    double ballSizeReduction) {
        double BALL_SCALING = 4;
        BALL_SCALING *= ballSizeReduction;
        DoubleProperty ballRadius = new SimpleDoubleProperty();
        ballRadius.bind(radiusOuter.add(BALL_MIN_SIZE).multiply(BALL_SCALING));

        setFill(fillColor);
        setStroke(fillColor);
        setFillRule(FillRule.EVEN_ODD);

        MoveTo moveFirst = new MoveTo();
        moveFirst.xProperty().bind(centerX);
        moveFirst.yProperty().bind(centerY);

        ArcTo arcToInner = new ArcTo();
        arcToInner.xProperty().bind(centerX);
        arcToInner.yProperty().bind(centerY);
        arcToInner.setRadiusX(0);
        arcToInner.setRadiusY(0);

        MoveTo moveSecond = new MoveTo();
        moveSecond.xProperty().bind(centerX);
        moveSecond.yProperty().bind(centerY);

        HLineTo hLineToRight = new HLineTo();
        hLineToRight.xProperty().bind(ballRadius.add(centerX));

        ArcTo arcToOuter = new ArcTo();
        arcToOuter.xProperty().bind(ballRadius.negate().add(centerX));
        arcToOuter.yProperty().bind(centerY);
        arcToOuter.radiusXProperty().bind(ballRadius);
        arcToOuter.radiusYProperty().bind(ballRadius);

        HLineTo hLineToLeft = new HLineTo();
        hLineToLeft.xProperty().bind(centerX);

        getElements().addAll(moveFirst, arcToInner, moveSecond, hLineToRight, arcToOuter, hLineToLeft);
    }
}
