package view;

import javafx.scene.layout.Priority;
import presentationModel.SkiPM;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import java.awt.Toolkit;

/**
 * @author Elena Algaria
 * @author Lea Burki
 */
public class Toolbar extends HBox {
    private ButtonBar btnBar;
    private Button add;
    private Button delete;
    private Button back;
    private Button forward;
    private Button save;
    private TextField search;

    private static final String SAVE = "\uf0c7";
    private static final String ADD = "\uf067";
    private static final String DELETE = "\uf00d";
    private static final String BACK = "\uf0e2";
    private static final String FORWARD = "\uf01e";

    private Region region;
    private final SkiPM pm;

    public Toolbar(SkiPM pm) {
        this.pm = pm;
        initializeParts();
        layoutControls();
        setupEventHandler();
        setupValueChangeListener();

    }

    public void initializeParts() {
        save = new Button(SAVE);
        add = new Button(ADD);
        delete = new Button(DELETE);
        back = new Button(BACK);
        forward = new Button(FORWARD);

        btnBar = new ButtonBar();
        btnBar.getButtons().addAll(save, add, delete, back, forward);

        setStyle("-fx-background-color: #0CBEDE;");

        for (int i = 0; i < btnBar.getButtons().size(); i++) {

            btnBar.getButtons().get(i).getStyleClass().add("icon"); // zuweisen zu welcher schriftart im css es gehört
        }
        search = new TextField();
        search.setId("Search");
        region = new Region();
    }

    public void layoutControls() {
        BorderPane.setMargin(btnBar, new Insets(2, 15, 15, 15));

        region.setPrefWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2);
        getChildren().addAll(btnBar, region, search);
        for (int i = 0; i < btnBar.getButtons().size(); i++) {
            ButtonBar.setButtonData(btnBar.getButtons().get(i), ButtonBar.ButtonData.LEFT);
        }
        region.setPrefWidth(50);
        search.setPrefWidth(250);
        search.setPrefHeight(5);
        search.setPromptText("Suche nach Skiresort oder Gebiet");

        setPadding(new Insets(8, 15, 3, 5));

        HBox.setHgrow(region, Priority.ALWAYS);

    }

    private void setupEventHandler() {
        add.setOnAction(event -> pm.createNewResort());
        delete.setOnAction(event -> pm.deleteResortProperty().set(true));
        back.setOnAction(event -> pm.setGoBack(true));
        forward.setOnAction(event -> pm.setGoForward(true));
        save.setOnAction(event -> pm.saveResort());
    }

    public void setupValueChangeListener() {
        search.textProperty().addListener((observable, oldValue, newValue) ->
            pm.getSearch().setPredicate(pm.createPredicate(newValue))
        );
    }

}
