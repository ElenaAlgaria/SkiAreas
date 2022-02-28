package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Elena Algaria
 * @author Lea Burki
 */
public class SkiResort {
    private final StringProperty name;
    private final StringProperty communesInResort;
    private final StringProperty imageUrl;

    private final ObjectProperty<SkiRegion> region;

    private final DoubleProperty maslMin;
    private final DoubleProperty maslMax;
    private final DoubleProperty skiRuns;

    private final IntegerProperty dragLifts;
    private final IntegerProperty chairLifts;
    private final IntegerProperty cableCars;

    private final ObjectProperty<Integer> openLifts;
    private final ObjectProperty<Integer> snowDepth;
    private final ObjectProperty<Integer> visitorsToday;

    private final BooleanProperty carFree;
    private final BooleanProperty funparkAvaiable;


    public SkiResort(StringProperty name, StringProperty communesInResort, StringProperty imageUrl,
                     ObjectProperty<SkiRegion> region, DoubleProperty maslMin, DoubleProperty maslMax,
                     DoubleProperty skiRuns, IntegerProperty dragLifts, IntegerProperty chairLifts,
                     IntegerProperty cableCars, ObjectProperty<Integer> openLifts,
                     ObjectProperty<Integer> snowDepth,
                     ObjectProperty<Integer> visitorsToday, BooleanProperty carFree,
                     BooleanProperty funparkAvaiable) {
        this.name = name;
        this.communesInResort = communesInResort;
        this.imageUrl = imageUrl;
        this.region = region;
        this.maslMin = maslMin;
        this.maslMax = maslMax;
        this.skiRuns = skiRuns;
        this.dragLifts = dragLifts;
        this.chairLifts = chairLifts;
        this.cableCars = cableCars;
        this.openLifts = openLifts;
        this.snowDepth = snowDepth;
        this.visitorsToday = visitorsToday;
        this.carFree = carFree;
        this.funparkAvaiable = funparkAvaiable;
    }

    public String getName() {
        return name.get() == null ? "" : name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getCommunesInResort() {
        return communesInResort.get() == null ? "" : communesInResort.get();
    }

    public StringProperty communesInResortProperty() {
        return communesInResort;
    }

    public String getImageUrl() {
        return imageUrl.get() == null ? "" : imageUrl.get();
    }

    public StringProperty imageUrlProperty() {
        return imageUrl;
    }

    public SkiRegion getRegion() {
        return region.get();
    }

    public ObjectProperty<SkiRegion> regionProperty() {
        return region;
    }

    public double getMaslMin() {
        return maslMin.get();
    }

    public DoubleProperty maslMinProperty() {
        return maslMin;
    }

    public double getMaslMax() {
        return maslMax.get();
    }

    public DoubleProperty maslMaxProperty() {
        return maslMax;
    }

    public double getSkiRuns() {
        return skiRuns.get();
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

    public Integer getOpenLifts() {
        return openLifts.get();
    }

    public ObjectProperty<Integer> openLiftsProperty() {
        return openLifts;
    }

    public Integer getSnowDepth() {
        return snowDepth.get();
    }

    public ObjectProperty<Integer> snowDepthProperty() {
        return snowDepth;
    }

    public Integer getVisitorsToday() {
        return visitorsToday.get();
    }

    public ObjectProperty<Integer> visitorsTodayProperty() {
        return visitorsToday;
    }

    public boolean isCarFree() {
        return carFree.get();
    }

    public BooleanProperty carFreeProperty() {
        return carFree;
    }

    public boolean isFunparkAvaiable() {
        return funparkAvaiable.get();
    }

    public BooleanProperty funparkAvaiableProperty() {
        return funparkAvaiable;
    }
}
