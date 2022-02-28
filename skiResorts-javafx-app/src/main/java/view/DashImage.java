package view;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author Elena Algaria
 * @author Sebastian Romanek
 */
public class DashImage extends ImageView {
    //Provided Image
    private final ObjectProperty<Image> image = new SimpleObjectProperty<>();
    private final StringProperty path = new SimpleStringProperty();
    private double imageWidth, imageHeight;
    private double imageRatio = 16 / 9.;

    //Displayed Image
    private final DoubleProperty imageViewWidth = new SimpleDoubleProperty();
    private final DoubleProperty imageViewHeight = new SimpleDoubleProperty();

    public DashImage(String imagePath) {
        super();
        initialize(imagePath);
        setListeners();
    }

    private void initialize(String imagePath) {
        setImage(imagePath);
    }

    private void setListeners() {
        path.addListener((observable, oldValue, newValue) -> setImage(newValue));
        imageViewWidth.addListener(e -> redrawImage());
        imageViewHeight.addListener(e -> redrawImage());
    }

    //HELPER METHODS
    private void setImage(String filePath) {
        Image image;
        if (filePath.equals("N/A")) {
            try {
                image = new Image(new FileInputStream("skiResorts-javafx-app/src/main/resources/image/noImage.jpg"));
                this.setImage(image);
                imageWidth = image.getWidth();
                imageHeight = image.getHeight();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            image = new Image(filePath);
            this.setImage(image);
            imageWidth = image.getWidth();
            imageHeight = image.getHeight();
        }
        imageRatio = imageWidth / imageHeight;
    }

    public void redrawImage() {
        double imageViewRatio = getImageViewWidth() / getImageViewHeight();
        if (imageViewRatio > imageRatio) {
            this.setFitHeight(getImageViewHeight());
            this.setFitWidth(getImageViewHeight() * imageRatio);
        } else {
            this.setFitWidth(getImageViewWidth());
            this.setFitHeight(getImageViewWidth() / imageRatio);
            this.setTranslateY(
                0.9 * (getImageViewHeight() - (getImageViewWidth() / imageRatio))
            );
        }
    }

    public StringProperty pathProperty() {
        return path;
    }

    //GETTER & SETTER
    public double getImageViewWidth() {
        return imageViewWidth.get();
    }

    public void setImageViewWidth(double imageViewWidth) {
        this.imageViewWidth.set(imageViewWidth);
    }

    public double getImageViewHeight() {
        return imageViewHeight.get();
    }

    public void setImageViewHeight(double imageViewHeight) {
        this.imageViewHeight.set(imageViewHeight);
    }


}
