package view;

import model.SkiRegion;
import model.SkiResort;
import presentationModel.SkiPM;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.awt.Toolkit;

/**
 * @author Elena Algaria
 * @author Lea Burki
 */
public class OverviewTable extends TableView<SkiResort> {

    private final SkiPM skiPM;
    private int index;

    public OverviewTable(SkiPM skiPM) {
        this.skiPM = skiPM;
        initializeParts();
        layoutControls();
        setupValueChangeListener();
    }

    private void initializeParts() {
        HBox hb = new HBox();
        hb.setSpacing(10);
        createTable();
    }

    @SuppressWarnings("unchecked")
    private void createTable() {
        setItems(skiPM.getSkiData().getList());

        TableColumn<SkiResort, String> resortNameCol = new TableColumn<>("Name");
        TableColumn<SkiResort, SkiRegion> regionNameCol = new TableColumn<>("Gebiet");
        TableColumn<SkiResort, Number> pistenLengthCol = new TableColumn<>("Pistenlänge (km)");
        TableColumn<SkiResort, Integer> snowHeightCol = new TableColumn<>("Schneehöhe (cm)");

        resortNameCol.setCellValueFactory(cell -> cell.getValue().nameProperty());
        regionNameCol.setCellValueFactory(cell -> cell.getValue().regionProperty());
        pistenLengthCol.setCellValueFactory(cell -> cell.getValue().skiRunsProperty());
        snowHeightCol.setCellValueFactory(cell -> cell.getValue().snowDepthProperty());

        getColumns().addAll(resortNameCol, regionNameCol, pistenLengthCol, snowHeightCol);

        setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public void layoutControls() {
        setPrefWidth(Toolkit.getDefaultToolkit().getScreenSize().width / 3.5);
    }

    private void setupValueChangeListener() {
        skiPM.getSearch().predicateProperty().addListener((observable, oldValue, newValue) -> {
            skiPM.updateSearchList(skiPM.getSkiData().getList());
            setItems(skiPM.getSearch());
        });

        skiPM.addResortProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                index = skiPM.getSkiData().getList().size() - 1;
                scrollTo(index);
                getSelectionModel().clearSelection();
                getSelectionModel().getTableView().refresh();
                getSelectionModel().select(index);
                requestFocus();
            }
        });

        skiPM.deleteResortProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                skiPM.getSkiData().removeRow(getSelectionModel().getSelectedIndex());
                getItems().removeAll(
                    getSelectionModel().getSelectedItems());
                skiPM.deleteResortProperty().set(false);
            }
        });

        getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                skiPM.showResortProperty().set(newValue);
            }
        });


        skiPM.goBackProperty().addListener((observable, oldValue, newValue) -> {
            index = getSelectionModel().getSelectedIndex();
            if (newValue && index > 0) {
                getSelectionModel().select(--index);
                skiPM.setGoBack(false);
            }
        });

        skiPM.goForwardProperty().addListener((observable, oldValue, newValue) -> {
            index = getSelectionModel().getSelectedIndex();
            if (newValue && index < skiPM.getSkiData().getList().size()) {
                getSelectionModel().select(++index);
                skiPM.setGoForward(false);
            }
        });
    }
}
