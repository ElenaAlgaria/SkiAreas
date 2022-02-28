package presentationModel;

import model.SkiRegion;
import model.SkiResort;
import data.SkiData;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.util.function.Predicate;
/**
 * @author Elena Algaria
 * @author Lea Burki
 */
public class SkiPM {
    private FilteredList<SkiResort> search;

    private final ObjectProperty<SkiResort> showResort = new SimpleObjectProperty<>();
    private final BooleanProperty deleteResort = new SimpleBooleanProperty(false);
    private final BooleanProperty goBack = new SimpleBooleanProperty(false);
    private final BooleanProperty goForward = new SimpleBooleanProperty(false);
    private final BooleanProperty addResort = new SimpleBooleanProperty(false);
    private final BooleanProperty saveResort = new SimpleBooleanProperty(false);

    private final BooleanProperty isOpen = new SimpleBooleanProperty(false);
    private final StringProperty imageURL = new SimpleStringProperty("/image/noImage.jpg");

    private final BooleanProperty carFree = new SimpleBooleanProperty(true);
    private final BooleanProperty funparkAvaiable = new SimpleBooleanProperty();

    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty communesInResort = new SimpleStringProperty();

    private final ObjectProperty<SkiRegion> region = new SimpleObjectProperty<>();

    private final DoubleProperty maslMin = new SimpleDoubleProperty();
    private final DoubleProperty maslMax = new SimpleDoubleProperty();
    private final DoubleProperty skiRuns = new SimpleDoubleProperty();

    private final IntegerProperty dragLifts = new SimpleIntegerProperty();
    private final IntegerProperty chairLifts = new SimpleIntegerProperty();
    private final IntegerProperty cableCars = new SimpleIntegerProperty();
    private final IntegerProperty lifte = new SimpleIntegerProperty();

    private final ObjectProperty<Integer> openLifts = new SimpleObjectProperty<>(0);
    private final ObjectProperty<Integer> snowDepth = new SimpleObjectProperty<>(0);
    private final ObjectProperty<Integer> visitorsToday = new SimpleObjectProperty<>(0);

    private final SkiData skiData;

    public SkiPM(SkiData skiData) {
        this.skiData = skiData;
        valueChangeListener();
        updateSearchList(skiData.getList());
    }


    public Predicate<SkiResort> createPredicate(String searchText) {
        return resort -> {
            if (searchText == null || searchText.isEmpty()) {
                return true;
            }
            return searchResort(resort, searchText);
        };
    }

    private boolean searchResort(SkiResort resort, String searchText) {
        String name = "";
        String gebiet = "";

        if (resort.getName() != null) {
            name = resort.getName();
        }

        if (resort.getRegion() != null) {
            gebiet = resort.getRegion().toString();
        }

        return name.toLowerCase().contains(searchText.toLowerCase()) ||
            gebiet.toLowerCase().contains(searchText.toLowerCase());
    }

    public void createNewResort() {
        SkiResort newResort = new SkiResort(new SimpleStringProperty(""),
            new SimpleStringProperty(""),
            new SimpleStringProperty("/image/noImage.jpg"),
            new SimpleObjectProperty<>(new SkiRegion("-")),
            new SimpleDoubleProperty(),
            new SimpleDoubleProperty(),
            new SimpleDoubleProperty(),
            new SimpleIntegerProperty(),
            new SimpleIntegerProperty(),
            new SimpleIntegerProperty(),
            new SimpleObjectProperty<>(0),
            new SimpleObjectProperty<>(0),
            new SimpleObjectProperty<>(0),
            new SimpleBooleanProperty(),
            new SimpleBooleanProperty());
        skiData.getList().add(newResort);
        showResort.set(newResort);
        addResortProperty().set(true);
    }

    public void saveResort() {
        addResortProperty().set(false);
        saveResortProperty().set(true);
        skiData.writeResort(showResortProperty().get());
    }

    public void updateSearchList(ObservableList<SkiResort> list) {
        search = new FilteredList<>(FXCollections.observableArrayList(list));
    }

    public void valueChangeListener() {
        showResortProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                name.unbindBidirectional(oldValue.nameProperty());
                communesInResort.unbindBidirectional(oldValue.communesInResortProperty());
                skiRuns.unbindBidirectional(oldValue.skiRunsProperty());
                snowDepth.unbindBidirectional(oldValue.snowDepthProperty());
                region.unbindBidirectional(oldValue.regionProperty());
                maslMin.unbindBidirectional(oldValue.maslMinProperty());
                maslMax.unbindBidirectional(oldValue.maslMaxProperty());
                chairLifts.unbindBidirectional(oldValue.chairLiftsProperty());
                dragLifts.unbindBidirectional(oldValue.dragLiftsProperty());
                cableCars.unbindBidirectional(oldValue.cableCarsProperty());
                openLifts.unbindBidirectional(oldValue.openLiftsProperty());
                visitorsToday.unbindBidirectional(oldValue.visitorsTodayProperty());
                carFree.unbindBidirectional(oldValue.carFreeProperty());
                funparkAvaiable.unbindBidirectional(oldValue.funparkAvaiableProperty());
                imageURL.unbindBidirectional(oldValue.imageUrlProperty());
            }

            name.bindBidirectional(newValue.nameProperty());
            communesInResort.bindBidirectional(newValue.communesInResortProperty());
            skiRuns.bindBidirectional(newValue.skiRunsProperty());
            snowDepth.bindBidirectional(newValue.snowDepthProperty());
            region.bindBidirectional(newValue.regionProperty());
            maslMin.bindBidirectional(newValue.maslMinProperty());
            maslMax.bindBidirectional(newValue.maslMaxProperty());
            chairLifts.bindBidirectional(newValue.chairLiftsProperty());
            dragLifts.bindBidirectional(newValue.dragLiftsProperty());
            cableCars.bindBidirectional(newValue.cableCarsProperty());
            openLifts.bindBidirectional(newValue.openLiftsProperty());
            lifte.set(getChairLifts() + getDragLifts() + getCableCars());
            visitorsToday.bindBidirectional(newValue.visitorsTodayProperty());
            carFree.bindBidirectional(newValue.carFreeProperty());
            funparkAvaiable.bindBidirectional(newValue.funparkAvaiableProperty());
            imageURL.bindBidirectional(newValue.imageUrlProperty());

        });
    }

    public SkiData getSkiData() {
        return skiData;
    }

    public IntegerProperty lifteProperty() {
        return lifte;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public BooleanProperty addResortProperty() {
        return addResort;
    }

    public FilteredList<SkiResort> getSearch() {
        return search;
    }

    public BooleanProperty deleteResortProperty() {
        return deleteResort;
    }

    public ObjectProperty<SkiResort> showResortProperty() {
        return showResort;
    }

    public BooleanProperty goBackProperty() {
        return goBack;
    }

    public void setGoBack(boolean goBack) {
        this.goBack.set(goBack);
    }

    public BooleanProperty goForwardProperty() {
        return goForward;
    }

    public void setGoForward(boolean goForward) {
        this.goForward.set(goForward);
    }

    public BooleanProperty saveResortProperty() {
        return saveResort;
    }

    public BooleanProperty isOpenProperty() {
        return isOpen;
    }

    public String getImageURL() {
        return imageURL.get();
    }

    public StringProperty imageURLProperty() {
        return imageURL;
    }

    public BooleanProperty carFreeProperty() {
        return carFree;
    }

    public BooleanProperty funparkAvaiableProperty() {
        return funparkAvaiable;
    }

    public StringProperty communesInResortProperty() {
        return communesInResort;
    }

    public ObjectProperty<SkiRegion> regionProperty() {
        return region;
    }

    public DoubleProperty maslMinProperty() {
        return maslMin;
    }

    public DoubleProperty maslMaxProperty() {
        return maslMax;
    }

    public DoubleProperty skiRunsProperty() {
        return skiRuns;
    }

    public int getDragLifts() {
        return dragLifts.get();
    }

    public IntegerProperty dragLiftsProperty() {
        return dragLifts;
    }

    public int getChairLifts() {
        return chairLifts.get();
    }

    public IntegerProperty chairLiftsProperty() {
        return chairLifts;
    }

    public int getCableCars() {
        return cableCars.get();
    }

    public IntegerProperty cableCarsProperty() {
        return cableCars;
    }

    public ObjectProperty<Integer> openLiftsProperty() {
        return openLifts;
    }

    public ObjectProperty<Integer> snowDepthProperty() {
        return snowDepth;
    }

    public ObjectProperty<Integer> visitorsTodayProperty() {
        return visitorsToday;
    }
}
