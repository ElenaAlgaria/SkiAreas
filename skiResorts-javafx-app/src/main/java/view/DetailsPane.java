package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import model.SkiRegion;
import presentationModel.SkiPM;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;

/**
 * @author Elena Algaria
 * @author Lea Burki
 */
public class DetailsPane extends GridPane {
    private final SkiPM skiPM;
    private TextField nameTxt;
    private ChoiceBox<SkiRegion> gebietChB;
    private TextField pisteTxt;
    private TextField snowTxt;
    private TextField orteTxt;
    private TextField talStationTxt;
    private TextField bergstationTxt;
    private TextField schleppLifteTxt;
    private TextField sesselLifteTxt;
    private TextField gondelnTxt;
    private TextField lifteTxt;
    private TextField openTxt;
    private TextField besucherTxt;
    private CarControl carControl;
    private SnowmanControl snowmanControl;
    private TextField hyperlink;


    public DetailsPane(SkiPM skiPM) {
        this.skiPM = skiPM;
        initializeParts();
        createGridPane();
        bindings();
        valueChangeListener();
        layoutControls();
    }

    public void initializeParts() {
        nameTxt = new TextField();
        gebietChB = new ChoiceBox<>();
        gebietChB.getItems().addAll(skiPM.getSkiData().getRegionsMapInput().keySet());
        gebietChB.getStyleClass().add("choice-box");
        gebietChB.setDisable(true);
        pisteTxt = new TextField();
        snowTxt = new TextField();
        orteTxt = new TextField();
        talStationTxt = new TextField();
        bergstationTxt = new TextField();
        schleppLifteTxt = new TextField();
        sesselLifteTxt = new TextField();
        gondelnTxt = new TextField();
        lifteTxt = new TextField();
        openTxt = new TextField();
        besucherTxt = new TextField();
        snowmanControl = new SnowmanControl();
        hyperlink = new TextField();
        HBox hb = new HBox();
        carControl = new CarControl();
        hb.setSpacing(10);
        hb.setAlignment(Pos.CENTER);
    }


    private void createGridPane() {
        Label nameLabel = new Label("Name");
        nameLabel.setTextAlignment(TextAlignment.RIGHT);
        add(nameLabel, 0, 13);
        add(nameTxt, 1, 13);

        add(new Label("Gebiet"), 2, 13);
        add(gebietChB, 3, 13);

        add(new Label("Orte im Gebiet"), 0, 14);
        add(orteTxt, 1, 14, 3, 1);

        add(new Label("Talstation (m.ü.M)"), 0, 15);
        add(talStationTxt, 1, 15);

        Label bergstationLabel = new Label("Bergstation (m.ü.M)");
        add(bergstationLabel, 2, 15);
        add(bergstationTxt, 3, 15);

        add(new Label("Pistenlänge (km)"), 0, 16);
        add(pisteTxt, 1, 16);

        add(new Label("Schlepplifte"), 2, 16);
        add(schleppLifteTxt, 3, 16);

        add(new Label("Sessellifte"), 0, 17);
        add(sesselLifteTxt, 1, 17);

        add(new Label("Gondeln"), 2, 17);
        add(gondelnTxt, 3, 17);

        add(new Label("Lifte"), 0, 18);
        add(lifteTxt, 1, 18);

        add(new Label("Geöffnet"), 2, 18);
        add(openTxt, 3, 18);

        add(new Label("Schneehöhe (cm)"), 0, 19);
        add(snowTxt, 1, 19);

        add(new Label("Besucher"), 2, 19);
        add(besucherTxt, 3, 19);

        add(new Label("autofrei"), 0, 20);
        add(carControl, 1, 20);
        carControl.setMaxSize(30, 30);

        add(new Label("Funpark geöffnet"), 2, 20);
        add(snowmanControl, 3, 20);
        snowmanControl.setMaxSize(100, 80);

        add(new Label("Bild URL"), 0, 21);
        add(hyperlink, 1, 21, 3, 1);
    }

    public void bindings() {
        nameTxt.textProperty().bindBidirectional(skiPM.nameProperty());
        gebietChB.valueProperty().bindBidirectional(skiPM.regionProperty());
        pisteTxt.textProperty().bindBidirectional(skiPM.skiRunsProperty(), new NumberStringConverter());
        snowTxt.textProperty().bindBidirectional(skiPM.snowDepthProperty(), new IntegerStringConverter());
        orteTxt.textProperty().bindBidirectional(skiPM.communesInResortProperty());
        talStationTxt.textProperty().bindBidirectional(skiPM.maslMinProperty(), new NumberStringConverter());
        bergstationTxt.textProperty().bindBidirectional(skiPM.maslMaxProperty(), new NumberStringConverter());
        sesselLifteTxt.textProperty().bindBidirectional(skiPM.chairLiftsProperty(), new NumberStringConverter());
        schleppLifteTxt.textProperty().bindBidirectional(skiPM.dragLiftsProperty(), new NumberStringConverter());
        gondelnTxt.textProperty().bindBidirectional(skiPM.cableCarsProperty(), new NumberStringConverter());
        lifteTxt.textProperty().bindBidirectional(skiPM.lifteProperty(), new NumberStringConverter());
        openTxt.textProperty().bindBidirectional(skiPM.openLiftsProperty(), new IntegerStringConverter());
        besucherTxt.textProperty().bindBidirectional(skiPM.visitorsTodayProperty(), new IntegerStringConverter());
        carControl.carFreeProperty().bindBidirectional(skiPM.carFreeProperty());
        snowmanControl.valueProperty().bindBidirectional(skiPM.funparkAvaiableProperty());
        hyperlink.textProperty().bindBidirectional(skiPM.imageURLProperty());
    }

    public void valueChangeListener() {
        skiPM.addResortProperty().addListener((observable, oldValue, newValue) -> gebietChB.setDisable(!newValue));
    }

    private void layoutControls() {
        setPadding(new Insets(5, 15, 15, 15));
        setAlignment(Pos.CENTER);
        setHgap(5);
        setVgap(5);
    }
}
