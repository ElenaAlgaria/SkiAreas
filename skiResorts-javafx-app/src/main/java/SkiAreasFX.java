import presentationModel.SkiPM;
import view.*;
import data.SkiData;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Elena Algaria
 * @author Lea Burki
 */
public class SkiAreasFX extends Application {

    @Override
    public void start(Stage primaryStage) {
        SkiData skiData = new SkiData();
        SkiPM skiPM = new SkiPM(skiData);
        OverviewTable overviewTable = new OverviewTable(skiPM);
        DetailsPane detailsPane = new DetailsPane(skiPM);
        ResortsSummary resortsSummary = new ResortsSummary(skiPM);
        Dashboard dashboard = new Dashboard(skiPM);

        Toolbar toolbar = new Toolbar(skiPM);
        CenterPane centerPane = new CenterPane(overviewTable, detailsPane, dashboard);
        MainView mainView = new MainView(toolbar, overviewTable, centerPane, resortsSummary);
        primaryStage.setTitle("SkiAreasFX");
        primaryStage.setScene(new Scene(mainView));
        primaryStage.setWidth(1200);
        primaryStage.setHeight(600);
        primaryStage.setMinWidth(900);
        primaryStage.setMinHeight(500);
        primaryStage.show();

        layoutHBox(dashboard);
    }

    private void layoutHBox(Dashboard rootPanel) {
        rootPanel.getDashImage().setTranslateX(
            (rootPanel.getHBox().widthProperty().doubleValue() / 2 - rootPanel.getDashImage().getFitWidth()) / 2);
        rootPanel.getDashControls().setTranslateX(
            (rootPanel.getHBox().widthProperty().doubleValue() / 2 - rootPanel.getDashImage().getFitWidth()));
    }

    public static void main(String[] args) {
        launch(args);
    }
}