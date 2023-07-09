package AquaScan.Reports;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PollutionList {

    private ObservableList<Pollution> dataList;

    public PollutionList() {
        dataList = FXCollections.observableArrayList();
    }

    public ObservableList<Pollution> getData() {
        return this.dataList;
    }

    public void setData(int incidentID, String riverName, String date, String pollutionSource, String responsibleParty,
            String status, String actionTaken, String impact) {
        dataList.add(new Pollution(incidentID, riverName, date, pollutionSource, responsibleParty, status, actionTaken,
                impact));
    }

    public void setDummy() {
        dataList = FXCollections.observableArrayList();
        dataList.add(new Pollution(1, "Ali", "7/5/2023", "1", "2", "3", "4", "5"));
    }

    public void clear() {
        dataList.clear();
    }
}
