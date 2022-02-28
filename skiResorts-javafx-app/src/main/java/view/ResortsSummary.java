package view;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import presentationModel.SkiPM;

/**
 * @author Elena Algaria
 * @author Lea Burki
 */
public class ResortsSummary extends HBox {
    private final SkiPM pm;
    private Label gebietLbl;
    private Label lifteLbl;
    private Label pisteLbl;
    private Label anzLifteLbl;
    private Label pistenKmLbl;

    private static final double DEFAULT_WIDTH = 700;
    private static final double DEFAULT_HEIGHT = 50;

    // declare the custom control
    private DashControls dashControls;
    private HBox hBoxLbl;

    public ResortsSummary(SkiPM pm) {
        this.pm = pm;
        initializeControls();
        layoutControls();
        setupValueChangeListeners();
        setupBindings();
        setupEventListeners();
    }

    private void initializeControls() {
        setPadding(new Insets(10));
        dashControls = new DashControls();
        gebietLbl = new Label("-");
        gebietLbl.getStyleClass().add("titel");
        lifteLbl = new Label();
        pisteLbl = new Label();
        anzLifteLbl = new Label();
        pistenKmLbl = new Label();
        hBoxLbl = new HBox();

        int anzahlLifteWallis = pm.getSkiData().getList()
            .stream()
            .filter(
                gebiet -> "Waadt und Wallis".equals(gebiet.getRegion().getName()))
            .map(gebiet -> gebiet.getDragLifts() + gebiet.getChairLifts() + gebiet.getCableCars())
            .reduce(0, Integer::sum);

        int anzLifteBern = pm.getSkiData().getList()
            .stream()
            .filter(
                gebiet -> "Berner Oberland".equals(gebiet.getRegion().getName()))
            .map(gebiet -> gebiet.getDragLifts() + gebiet.getChairLifts() + gebiet.getCableCars())
            .reduce(0, Integer::sum);

        int anzLifteZentral = pm.getSkiData().getList()
            .stream()
            .filter(
                gebiet -> "Zentralschweiz".equals(gebiet.getRegion().getName()))
            .map(gebiet -> gebiet.getDragLifts() + gebiet.getChairLifts() + gebiet.getCableCars())
            .reduce(0, Integer::sum);

        int anzLifteGraubuenden = pm.getSkiData().getList()
            .stream()
            .filter(
                gebiet -> "Graubünden".equals(gebiet.getRegion().getName()))
            .map(gebiet -> gebiet.getDragLifts() + gebiet.getChairLifts() + gebiet.getCableCars())
            .reduce(0, Integer::sum);

        int anzLifteOst = pm.getSkiData().getList()
            .stream()
            .filter(
                gebiet -> "Ostschweiz".equals(gebiet.getRegion().getName()))
            .map(gebiet -> gebiet.getDragLifts() + gebiet.getChairLifts() + gebiet.getCableCars())
            .reduce(0, Integer::sum);

        int anzLifteTessin = pm.getSkiData().getList()
            .stream()
            .filter(
                gebiet -> "Tessin".equals(gebiet.getRegion().getName()))
            .map(gebiet -> gebiet.getDragLifts() + gebiet.getChairLifts() + gebiet.getCableCars())
            .reduce(0, Integer::sum);

        double pisteKmWaadtWallis = pm.getSkiData().getList()
            .stream()
            .filter(
                gebiet -> "Waadt und Wallis".equals(gebiet.getRegion().getName()))
            .mapToInt(gebiet -> (int) gebiet.getSkiRuns()).sum();

        double pisteKmBern = pm.getSkiData().getList()
            .stream()
            .filter(
                gebiet -> "Berner Oberland".equals(gebiet.getRegion().getName()))
            .mapToInt(gebiet -> (int) gebiet.getSkiRuns()).sum();

        double pisteKmZentral = pm.getSkiData().getList()
            .stream()
            .filter(
                gebiet -> "Zentralschweiz".equals(gebiet.getRegion().getName()))
            .mapToInt(gebiet -> (int) gebiet.getSkiRuns()).sum();

        double pisteKmGraubuenden = pm.getSkiData().getList()
            .stream()
            .filter(
                gebiet -> "Graubünden".equals(gebiet.getRegion().getName()))
            .mapToInt(gebiet -> (int) gebiet.getSkiRuns()).sum();

        double pisteKmOst = pm.getSkiData().getList()
            .stream()
            .filter(
                gebiet -> "Ostschweiz".equals(gebiet.getRegion().getName()))
            .mapToInt(gebiet -> (int) gebiet.getSkiRuns()).sum();

        double pisteKmTessin = pm.getSkiData().getList()
            .stream()
            .filter(
                gebiet -> "Tessin".equals(gebiet.getRegion().getName()))
            .mapToInt(gebiet -> (int) gebiet.getSkiRuns()).sum();

        pm.showResortProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.getRegion() != null) {
                int anzahlLifte = 0;
                double pisteKm = 0.0;
                if (newValue.getRegion().getName() != null) {
                    switch (newValue.getRegion().getName()) {
                    case "Waadt und Wallis" -> {
                        anzahlLifte = anzahlLifteWallis;
                        pisteKm = pisteKmWaadtWallis;
                    }
                    case "Berner Oberland" -> {
                        anzahlLifte = anzLifteBern;
                        pisteKm = pisteKmBern;
                    }
                    case "Zentralschweiz" -> {
                        anzahlLifte = anzLifteZentral;
                        pisteKm = pisteKmZentral;
                    }
                    case "Graubünden" -> {
                        anzahlLifte = anzLifteGraubuenden;
                        pisteKm = pisteKmGraubuenden;
                    }
                    case "Ostschweiz" -> {
                        anzahlLifte = anzLifteOst;
                        pisteKm = pisteKmOst;
                    }
                    case "Tessin" -> {
                        anzahlLifte = anzLifteTessin;
                        pisteKm = pisteKmTessin;
                    }
                    }
                    anzLifteLbl.setText(String.valueOf(anzahlLifte));
                    pistenKmLbl.setText(String.valueOf(pisteKm));
                }
            }
        });
        hBoxLbl.getChildren().addAll(gebietLbl, anzLifteLbl, new Label("Lifte"), pistenKmLbl, new Label("Pistenkm"));
        getChildren().addAll(hBoxLbl);
    }

    private void layoutControls() {
        setSpacing(15);
        setPrefWidth(DEFAULT_WIDTH);
        setPrefHeight(DEFAULT_HEIGHT);
        hBoxLbl.setSpacing(70);
        setMargin(this, new Insets(0, 15, 50, 15));

        for (Node node : hBoxLbl.getChildren()) {
            if (node instanceof Label) {
                if (!node.equals(hBoxLbl.getChildren().get(0))) {
                    (node).getStyleClass().add("titel2");
                    ((Label) node).setMinSize(80, 50);
                }
            }
        }
    }

    private void setupValueChangeListeners() {
        pm.regionProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                gebietLbl.textProperty().bind(pm.regionProperty().asString());
            }
        });
    }

    private void setupBindings() {
        lifteLbl.textProperty().bind(pm.lifteProperty().asString());
        pisteLbl.textProperty().bind(pm.skiRunsProperty().asString());

    }

    private void setupEventListeners() {
        heightProperty().addListener((obs, oldValue, newValue) -> {
            double fullHeight = newValue.doubleValue();
            dashControls.setBoardViewHeight(fullHeight);
        });

    }
}
