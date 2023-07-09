package AquaScan.Laboratory;

import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RiverList {
    private ObservableList<River> dataList;

    public RiverList() {
        dataList = FXCollections.observableArrayList();
    }

    public ObservableList<River> getData() {
        return this.dataList;
    }

    public void setData(String riverName, String date, double ph,
            double temperature, String riverSide, double dissolvedOxygen,
            double turbidity, double conductivity,
            double totalDissolvedSolids) {
        dataList.add(new River(riverName, date, ph, temperature, riverSide, dissolvedOxygen, turbidity, conductivity,
                totalDissolvedSolids));
    }

    public void setDummy() {
        // dataList = FXCollections.observableArrayList();
        // dataList.add(new River("Nile", LocalDate.of(2023, 4, 9).toString(), 9, 7,
        // "Side 3", "8", "2", 2, "8"));
        // dataList.add(new River("Amazon", LocalDate.of(2020, 7, 3).toString(), 7, 30,
        // "Side 2", "2", "5", 3, "12"));
        // dataList.add(
        // new River("Mississippi", LocalDate.of(2017, 3, 17).toString(), 7.7, 27, "Side
        // 1", "5", "11", 6, "2"));

    }

    public void clear() {
        dataList.clear();
    }
}
