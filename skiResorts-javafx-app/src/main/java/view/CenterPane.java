package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import java.util.Objects;

/**
 * @author Elena Algaria
 * @author Lea Burki
 */
public class CenterPane extends SplitPane {
    public final OverviewTable overviewTable;
    public final VBox vBox;


    public CenterPane(OverviewTable overviewTable, DetailsPane detailsPane, Dashboard dashboard) {
        this.overviewTable = overviewTable;
        vBox = new VBox();
        vBox.getChildren().addAll(dashboard, detailsPane);
        vBox.setAlignment(Pos.CENTER);
        initializeParts();
        layoutControls();
    }

    public void initializeParts() {
        Font.loadFont(getClass().getResourceAsStream("/fonts/fontawesome-webfont.ttf"), 12);
        String stylesheet = Objects.requireNonNull(getClass().getResource("/css/style.css")).toExternalForm();
        getStylesheets().add(stylesheet);
    }

    public void layoutControls() {
        ScrollPane scroller = new ScrollPane();
        scroller.setContent(vBox);
        getItems().addAll(overviewTable, scroller);
        BorderPane.setMargin(overviewTable, new Insets(15, 15, 15, 15));
    }
}
