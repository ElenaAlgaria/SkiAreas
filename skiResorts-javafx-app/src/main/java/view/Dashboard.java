package view;

import presentationModel.SkiPM;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author Elena Algaria
 * @author Lea Burki
 */
public class Dashboard extends BorderPane {

    private final SkiPM pm;
    private Label titleLbl;
    private Label gebietLbl;
    private Label snowLbl;
    private Label lifteLbl;
    private Label openLbl;


    private static final double DEFAULT_WIDTH = 700;
    private static final double DEFAULT_HEIGHT = 310;

    // declare the custom control
    private DashControls dashControls;
    private DashImage dashImage;
    private HBox hBox;
    private HBox hBoxLbl;
    private VBox vBox;


    public Dashboard(SkiPM pm) {
        this.pm = pm;
        initializeControls();
        layoutControls();
        setupValueChangeListeners();
        setupBindings();
        setupEventListeners();
    }

    private void initializeControls() {
        setPadding(new Insets(15, 15, 5, 15));

        dashControls = new DashControls();
        dashImage = new DashImage(pm.getImageURL());
        dashImage.setImageViewWidth(DEFAULT_WIDTH);
        dashImage.setImageViewHeight(DEFAULT_HEIGHT);

        hBox = new HBox();
        hBox.getChildren().addAll(dashImage, dashControls);

        titleLbl = new Label();
        titleLbl.getStyleClass().add("titel");
        gebietLbl = new Label("-");
        snowLbl = new Label();
        lifteLbl = new Label();
        openLbl = new Label();
        hBoxLbl = new HBox();
        hBoxLbl.getStyleClass().add("titel2");

        hBoxLbl.getChildren()
            .addAll(gebietLbl, snowLbl, new Label("cm Schneehöhe"), lifteLbl, new Label("Lifte"), openLbl,
                new Label("geöffnet"));

        vBox = new VBox();

        vBox.getChildren().addAll(titleLbl, hBoxLbl);
        vBox.getStyleClass().add("titel");

    }


    private void layoutControls() {
        hBox.setSpacing(15);
        hBox.setPrefWidth(DEFAULT_WIDTH);
        hBox.setPrefHeight(DEFAULT_HEIGHT);

        hBoxLbl.setSpacing(15);
        setMargin(hBox, new Insets(0, 15, 0, 15));
        setMargin(vBox, new Insets(10, 0, 5, 15));
        setAlignment(vBox, Pos.CENTER);
        setCenter(hBox);
        setBottom(vBox);
    }

    private void setupValueChangeListeners() {
        openLbl.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                pm.isOpenProperty().set(newValue.equals("0"));
            }
        });

        pm.regionProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                gebietLbl.textProperty().bind(pm.regionProperty().asString());
            }
        });

        pm.snowDepthProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                dashControls.snowHeightProperty().set((newValue / 100));
            }
        });

    }

    private void setupBindings() {
        dashControls.isOpenProperty().bindBidirectional(pm.isOpenProperty());
        titleLbl.textProperty().bind(pm.nameProperty());
        snowLbl.textProperty().bind(pm.snowDepthProperty().asString());
        lifteLbl.textProperty().bind(pm.lifteProperty().asString());
        openLbl.textProperty().bind(pm.openLiftsProperty().asString());
        dashImage.pathProperty().bindBidirectional(pm.imageURLProperty());
    }

    private void setupEventListeners() {
        hBox.widthProperty().addListener((obs, oldValue, newValue) -> {
            double halfWidth = newValue.doubleValue() / 2;
            dashImage.setImageViewWidth(halfWidth);
            dashControls.setBoardViewWidth(halfWidth);
            if (dashImage.getFitWidth() < halfWidth) {
                dashControls.setTranslateX((halfWidth - dashImage.getFitWidth()));
                dashImage.setTranslateX((halfWidth - dashImage.getFitWidth()) / 2);
            } else {
                dashControls.setTranslateX(0);
            }
        });

        hBox.heightProperty().addListener((obs, oldValue, newValue) -> {
            double fullHeight = newValue.doubleValue();
            dashImage.setImageViewHeight(fullHeight);
            dashControls.setBoardViewHeight(fullHeight);
        });
    }

    public DashControls getDashControls() {
        return dashControls;
    }

    public DashImage getDashImage() {
        return dashImage;
    }

    public HBox getHBox() {
        return hBox;
    }

}
