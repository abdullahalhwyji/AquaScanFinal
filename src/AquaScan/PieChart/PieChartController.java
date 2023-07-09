package AquaScan.PieChart;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import AquaScan.Laboratory.River;
import AquaScan.Laboratory.RiverDataXML;
import AquaScan.Laboratory.RiverList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class PieChartController implements Initializable {

    @FXML
    private PieChart myPieChart;

    @FXML
    private PieChart myPieChart2;
    @FXML
    private PieChart myPieChart3;

    @FXML
    private PieChart myPieChart4;

    @FXML
    private PieChart myPieChart5;
    @FXML
    private PieChart myPieChart6;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Get the data from RiverList
        RiverList data = RiverDataXML.loadData();
        //

        // Calculate the percentage of safe rivers based on pH value
        int totalRivers = data.getData().size();
        int safeRiversPH = 0;
        int safeRiversTemperature = 0;

        for (River river : data.getData()) {
            if (river.getPh() >= 6.5 && river.getPh() <= 8.5) {
                safeRiversPH++;
            }
            if (river.getTemperature() >= 10 && river.getTemperature() <= 30) {
                safeRiversTemperature++;
            }
        }

        double safePercentagePH = (double) safeRiversPH / totalRivers * 100;
        double unsafePercentagePH = 100 - safePercentagePH;

        double safePercentageTemperature = (double) safeRiversTemperature / totalRivers * 100;
        double unsafePercentageTemperature = 100 - safePercentageTemperature;

        // Create PieChart Data for pH
        ObservableList<PieChart.Data> pieChartDataPH = FXCollections.observableArrayList(
                new PieChart.Data("Unsafe Rivers", unsafePercentagePH),
                new PieChart.Data("Safe Rivers", safePercentagePH));

        // Set the data to the PieChart for pH
        myPieChart.setData(pieChartDataPH);

        // Create PieChart Data for temperature
        ObservableList<PieChart.Data> pieChartDataTemperature = FXCollections.observableArrayList(
                new PieChart.Data("Unsafe Rivers", unsafePercentageTemperature),
                new PieChart.Data("Safe Rivers", safePercentageTemperature));

        // Set the data to the PieChart for temperature
        myPieChart2.setData(pieChartDataTemperature);

        // Calculate the percentage of safe rivers based on both pH and temperature
        int safeRiversBoth = 0;
        int unsafeRiversBoth = 0;
        int veryRiskyRiversBoth = 0;

        for (River river : data.getData()) {
            if (river.getPh() >= 6.5 && river.getPh() <= 8.5 && river.getTemperature() >= 10
                    && river.getTemperature() <= 30) {
                safeRiversBoth++;
            } else if ((river.getPh() < 6.5 || river.getPh() > 8.5)
                    && (river.getTemperature() < 10 || river.getTemperature() > 30)) {
                veryRiskyRiversBoth++;
            } else {
                unsafeRiversBoth++;
            }
        }

        double safePercentageBoth = (double) safeRiversBoth / totalRivers * 100;
        double unsafePercentageBoth = (double) unsafeRiversBoth / totalRivers * 100;
        double veryRiskyPercentageBoth = (double) veryRiskyRiversBoth / totalRivers * 100;

        // Create PieChart Data for summary
        ObservableList<PieChart.Data> pieChartDataSummary = FXCollections.observableArrayList();
        if (veryRiskyPercentageBoth > 0) {
            pieChartDataSummary.add(new PieChart.Data("Very Risky Rivers", veryRiskyPercentageBoth));
        }
        if (unsafePercentageBoth > 0) {
            pieChartDataSummary.add(new PieChart.Data("Unsafe Rivers", unsafePercentageBoth));
        }

        if (safePercentageBoth > 0) {
            pieChartDataSummary.add(new PieChart.Data("Safe Rivers", safePercentageBoth));
        }

        // Set the data to the PieChart for summary
        myPieChart3.setData(pieChartDataSummary);

        // Set labels to show the percentage on hover
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        myPieChart3.getData().forEach(pieChartData -> {
            String label = pieChartData.getName() + " (" + decimalFormat.format(pieChartData.getPieValue()) + "%)";
            pieChartData.setName(label);
        });

        // ...

        // Calculate the percentage of safe rivers based on dissolved oxygen
        int safeRiversDissolvedOxygen = 0;
        int unsafeRiversDissolvedOxygen = 0;

        for (River river : data.getData()) {
            double dissolvedOxygen = river.getDissolvedOxygen();

            if (dissolvedOxygen >= 5) {
                safeRiversDissolvedOxygen++;
            } else {
                unsafeRiversDissolvedOxygen++;
            }

        }

        // ...

        double safePercentageDissolvedOxygen = (double) safeRiversDissolvedOxygen / totalRivers * 100;
        double unsafePercentageDissolvedOxygen = (double) unsafeRiversDissolvedOxygen / totalRivers * 100;

        // ...

        // Create PieChart Data for dissolved oxygen
        ObservableList<PieChart.Data> pieChartDataDissolvedOxygen = FXCollections.observableArrayList(
                new PieChart.Data("Unsafe Rivers", unsafePercentageDissolvedOxygen),
                new PieChart.Data("Safe Rivers", safePercentageDissolvedOxygen));

        // ...

        myPieChart4.setData(pieChartDataDissolvedOxygen);

        // ...

        // Calculate the percentage of safe rivers based on turbidity
        int safeRiversTurbidity = 0;
        int unsafeRiversTurbidity = 0;

        for (River river : data.getData()) {
            double turbidity = river.getTurbidity();

            if (turbidity >= 0 && turbidity <= 1) {
                safeRiversTurbidity++;
            } else {
                unsafeRiversTurbidity++;
            }

        }

        // ...

        double safePercentageTurbidity = (double) safeRiversTurbidity / totalRivers * 100;
        double unsafePercentageTurbidity = (double) unsafeRiversTurbidity / totalRivers * 100;

        // ...

        // Create PieChart Data for turbidity
        ObservableList<PieChart.Data> pieChartDataTurbidity = FXCollections.observableArrayList(
                new PieChart.Data("Unsafe Rivers", unsafePercentageTurbidity),
                new PieChart.Data("Safe Rivers", safePercentageTurbidity));

        // ...

        myPieChart5.setData(pieChartDataTurbidity);

        // ...

        // Calculate the percentage of safe rivers based on total dissolved solids
        // ...

        // ...

        int distilledWaterRivers = 0;
        int drinkingWaterRivers = 0;
        int freshwaterRivers = 0;
        int seawaterRivers = 0;

        for (River river : data.getData()) {
            double conductivity = river.getConductivity();

            if (conductivity >= 0.5 && conductivity <= 3) {
                distilledWaterRivers++;
            } else if (conductivity < 500) {
                drinkingWaterRivers++;
            } else if (conductivity >= 50 && conductivity <= 1500) {
                freshwaterRivers++;
            } else if (conductivity >= 30000 && conductivity <= 60000) {
                seawaterRivers++;
            }

        }

        // Calculate the total number of rivers
        double totalRivers1 = distilledWaterRivers + drinkingWaterRivers + freshwaterRivers + seawaterRivers;

        // Calculate the percentage of rivers in each conductivity range
        double distilledWaterPercentage = (double) distilledWaterRivers / totalRivers * 100;
        double drinkingWaterPercentage = (double) drinkingWaterRivers / totalRivers * 100;
        double freshwaterPercentage = (double) freshwaterRivers / totalRivers * 100;
        double seawaterPercentage = (double) seawaterRivers / totalRivers * 100;

        // Create PieChart Data for conductivity ranges
        ObservableList<PieChart.Data> pieChartDataConductivity = FXCollections.observableArrayList(
                new PieChart.Data("Distilled Water", distilledWaterPercentage),
                new PieChart.Data("Drinking Water", drinkingWaterPercentage),
                new PieChart.Data("Freshwater", freshwaterPercentage),
                new PieChart.Data("Seawater", seawaterPercentage));

        myPieChart6.setData(pieChartDataConductivity);

    }

}
