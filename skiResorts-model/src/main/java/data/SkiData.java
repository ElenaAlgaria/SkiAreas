package data;

import model.SkiRegion;
import model.SkiResort;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Elena Algaria
 * @author Lea Burki
 */
public class SkiData {
    private List<String> linesResorts;
    private Path resorts;
    private final Path tmp = Path.of("tmp.csv");
    private int lastID;
    private final ObservableList<SkiResort> list = FXCollections.observableArrayList();
    private final Map<String, String> regionsMapOutput = new HashMap<>();
    private final Map<SkiRegion, String> regionsMapInput = new HashMap<>();

    public SkiData() {
        readFromFileSkiRegions();
        readFromFileSkiResort();
    }

    public void readFromFileSkiRegions() {
        try {
            var regions = Path.of("skiRegions.csv");

            List<String> linesRegions = Files.readAllLines(regions);

            linesRegions.remove(0);

            for (String line : linesRegions) {
                String[] tokens = line.split(";");
                regionsMapOutput.put(tokens[0], tokens[1]);
                regionsMapInput.put(new SkiRegion((tokens[1])), tokens[0]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFromFileSkiResort() {
        try {
            resorts = Path.of("skiResorts.csv");

            linesResorts = Files.readAllLines(resorts);

            linesResorts.remove(0);

            for (String line : linesResorts) {
                String[] tokens = line.split(";");

                lastID = Integer.parseInt(tokens[0]);

                list.addAll(new SkiResort(new SimpleStringProperty(tokens[1]),
                    new SimpleStringProperty(tokens[3]),
                    new SimpleStringProperty(tokens.length < 16 ? "N/A" : tokens[15]),
                    new SimpleObjectProperty<>(new SkiRegion(regionsMapOutput.get(tokens[2]))),
                    new SimpleDoubleProperty(Double.parseDouble(tokens[4])),
                    new SimpleDoubleProperty(Double.parseDouble(tokens[5])),
                    new SimpleDoubleProperty(Double.parseDouble(tokens[6])),
                    new SimpleIntegerProperty(Integer.parseInt(tokens[7])),
                    new SimpleIntegerProperty(Integer.parseInt(tokens[8])),
                    new SimpleIntegerProperty(Integer.parseInt(tokens[9])),
                    new SimpleObjectProperty<>(tokens[10].equals("") ? 0 : Integer.parseInt(tokens[10])),
                    new SimpleObjectProperty<>(tokens[11].equals("") ? 0 : Integer.parseInt(tokens[11])),
                    new SimpleObjectProperty<>(tokens[12].equals("") ? 0 : Integer.parseInt(tokens[12])),
                    new SimpleBooleanProperty(Boolean.parseBoolean(tokens[13])),
                    new SimpleBooleanProperty(Boolean.parseBoolean(tokens[14]))));
            }
            Files.deleteIfExists(tmp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeResort(SkiResort showResortProperty) {
        list.set(list.size() - 1, showResortProperty);

        linesResorts.add(++lastID + ";" + showResortProperty.getName() + ";"
            + (regionsMapInput.get(showResortProperty.getRegion()) == null ? "" :
            regionsMapInput.get(showResortProperty.getRegion())) + ";"
            + showResortProperty.getCommunesInResort() + ";"
            + (showResortProperty.getMaslMin() == 0 ? "0" : showResortProperty.getMaslMin()) + ";"
            + (showResortProperty.getMaslMax() == 0 ? "0" : showResortProperty.getMaslMax()) + ";"
            + (showResortProperty.getSkiRuns() == 0 ? "0" : showResortProperty.getSkiRuns()) + ";"
            + (showResortProperty.getDragLifts() == 0 ? "0" : showResortProperty.getDragLifts()) + ";"
            + (showResortProperty.getChairLifts() == 0 ? "0" : showResortProperty.getChairLifts()) + ";"
            + (showResortProperty.getCableCars() == 0 ? "0" : showResortProperty.getCableCars()) + ";"
            + (showResortProperty.getOpenLifts() == null ? "" : showResortProperty.getOpenLifts()) + ";" +
            (showResortProperty.getSnowDepth() == null ? "" : showResortProperty.getSnowDepth()) + ";"
            + (showResortProperty.getVisitorsToday() == null ? "" :
            showResortProperty.getVisitorsToday()) + ";" +
            String.valueOf(showResortProperty.isCarFree()).toUpperCase() + ";" +
            String.valueOf(showResortProperty.isFunparkAvaiable()).toUpperCase() + ";"
            + showResortProperty.getImageUrl());
        updateFile();
    }

    private void updateFile() {
        try {
            Files.write(tmp, linesResorts);
            Files.delete(resorts);
            File file = tmp.toFile();
            file.renameTo(new File("skiResorts.csv"));
            Files.deleteIfExists(tmp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeRow(int index) {
        if (index < linesResorts.size()) {
            linesResorts.remove(index);
            updateFile();
        }
    }

    public ObservableList<SkiResort> getList() {
        return list;
    }

    public Map<SkiRegion, String> getRegionsMapInput() {
        return regionsMapInput;
    }
}
