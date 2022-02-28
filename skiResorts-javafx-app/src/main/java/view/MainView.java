package view;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;

import java.util.Objects;

/**
 * @author Elena Algaria
 * @author Lea Burki
 */
public class MainView extends BorderPane {
    private final Toolbar toolbar;
    private final OverviewTable overviewTable;
    private final CenterPane centerPane;
    private final ResortsSummary resortsSummary;


    public MainView(Toolbar toolbar, OverviewTable overviewTable, CenterPane centerPane,
                    ResortsSummary resortsSummary) {
        this.toolbar = toolbar;
        this.overviewTable = overviewTable;
        this.centerPane = centerPane;
        this.resortsSummary = resortsSummary;

        initializeParts();
        layoutControls();
    }

    public void initializeParts() {
        Font.loadFont(getClass().getResourceAsStream("/fonts/fontawesome-webfont.ttf"), 12);
        String stylesheet = Objects.requireNonNull(getClass().getResource("/css/style.css")).toExternalForm();
        getStylesheets().add(stylesheet);

    }

    public void layoutControls() {
        this.setTop(toolbar);
        this.setCenter(centerPane);
        this.setBottom(resortsSummary);
        BorderPane.setMargin(centerPane, new Insets(15, 15, 15, 15));
        BorderPane.setMargin(overviewTable, new Insets(15, 15, 15, 15));
        BorderPane.setMargin(resortsSummary, new Insets(15, 15, 15, 15));
    }

}
