package AquaScan.Laboratory;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class River {

    private SimpleStringProperty riverName;
    private SimpleStringProperty date;

    private SimpleDoubleProperty ph;
    private SimpleDoubleProperty temperature;

    private SimpleStringProperty riverSide;
    private SimpleDoubleProperty dissolvedOxygen;
    private SimpleDoubleProperty turbidity;
    private SimpleDoubleProperty conductivity;
    private SimpleDoubleProperty totalDissolvedSolids;

    public River(String riverName, String date, double ph,
            double temperature, String riverSide, double dissolvedOxygen,
            double turbidity, double conductivity,
            double totalDissolvedSolids) {
        this.riverName = new SimpleStringProperty(riverName);
        this.date = new SimpleStringProperty(date);
        this.ph = new SimpleDoubleProperty(ph);
        this.temperature = new SimpleDoubleProperty(temperature);
        this.riverSide = new SimpleStringProperty(riverSide);
        this.dissolvedOxygen = new SimpleDoubleProperty(dissolvedOxygen);
        this.turbidity = new SimpleDoubleProperty(turbidity);
        this.conductivity = new SimpleDoubleProperty(conductivity);
        this.totalDissolvedSolids = new SimpleDoubleProperty(totalDissolvedSolids);

    }

    public River() {
        this("", "", 0, 0, "", 0.0, 0.0, 0, 0.0);
    }

    public String getRiverName() {
        return riverName.get();
    }

    public void setRiverName(String rn) {
        this.riverName.set(rn);
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String d) {
        this.date.set(d);
        ;
    }

    public double getPh() {
        return ph.get();
    }

    public void setPh(double ph) {
        this.ph.set(ph);
    }

    public double getTemperature() {
        return temperature.get();
    }

    public void setTemperature(double t) {
        this.temperature.set(t);
    }

    public String getRiverSide() {
        return riverSide.get();
    }

    public void setRiverSide(String rs) {
        this.riverSide.set(rs);
    }

    public double getDissolvedOxygen() {
        return dissolvedOxygen.get();
    }

    public void setDissolvedOxygen(double disO) {
        this.dissolvedOxygen.set(disO);
    }

    public double getTurbidity() {
        return turbidity.get();
    }

    public void setTurbidity(double tur) {
        this.turbidity.set(tur);
    }

    public double getConductivity() {
        return conductivity.get();
    }

    public void setConductivity(double cond) {
        this.conductivity.set(cond);
    }

    public double getTotalDissolvedSolids() {
        return totalDissolvedSolids.get();
    }

    public void setTotalDissolvedSolids(double toDisO) {
        this.totalDissolvedSolids.set(toDisO);
    }
}
