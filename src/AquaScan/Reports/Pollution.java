package AquaScan.Reports;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Pollution {
    private SimpleIntegerProperty incidentID;

    public int getIncidentID() {
        return incidentID.get();
    }

    public void setIncidentID(int inID) {
        this.incidentID.set(inID);
    }

    private SimpleStringProperty riverName;

    public String getRiverName() {
        return riverName.get();
    }

    public void setRiverName(String rn) {
        this.riverName.set(rn);
    }

    private SimpleStringProperty date;

    public String getDate() {
        return date.get();
    }

    public void setDate(String d) {
        this.date.set(d);
    }

    private SimpleStringProperty pollutionSource;

    public String getPollutionSource() {
        return pollutionSource.get();
    }

    public void setPollutionSource(String pS) {
        this.pollutionSource.set(pS);
    }

    private SimpleStringProperty responsibleParty;

    public String getResponsibleParty() {
        return responsibleParty.get();
    }

    public void setResponsibleParty(String rP) {
        this.responsibleParty.set(rP);
    }

    private SimpleStringProperty status;

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String s) {
        this.status.set(s);
    }

    private SimpleStringProperty actionTaken;

    public String getActionTaken() {
        return actionTaken.get();
    }

    public void setActionTaken(String aT) {
        this.actionTaken.set(aT);
    }

    private SimpleStringProperty impact;

    public String getImpact() {
        return impact.get();
    }

    public void setImpact(String im) {
        this.impact.set(im);
    }

    public Pollution(int incidentID, String riverName, String date, String pollutionSource, String responsibleParty,
            String status, String actionTaken, String impact) {
        this.incidentID = new SimpleIntegerProperty(incidentID);
        this.riverName = new SimpleStringProperty(riverName);
        this.date = new SimpleStringProperty(date);
        this.pollutionSource = new SimpleStringProperty(pollutionSource);
        this.responsibleParty = new SimpleStringProperty(responsibleParty);
        this.status = new SimpleStringProperty(status);
        this.actionTaken = new SimpleStringProperty(actionTaken);
        this.impact = new SimpleStringProperty(impact);
    }

    public Pollution() {
        this(0, "", "", "", "", "", "", "");
    }

}
