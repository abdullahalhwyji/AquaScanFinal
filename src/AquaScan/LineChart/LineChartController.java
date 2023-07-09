package AquaScan.LineChart;

import java.net.URL;
import java.util.ResourceBundle;

import AquaScan.Laboratory.River;
import AquaScan.Laboratory.RiverDataXML;
import AquaScan.Laboratory.RiverList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class LineChartController implements Initializable {

    @FXML
    private LineChart<String, Number> myLineChart;
    @FXML
    private NumberAxis yAxis;

    @FXML
    private LineChart<String, Number> myLineChart1;

    @FXML
    private NumberAxis yAxis1;

    private RiverList data;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        data = RiverDataXML.loadData();
        yAxis.setLabel("pH");

        for (River river : data.getData()) {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(river.getRiverName());
            series.getData().add(new XYChart.Data<>(river.getDate(), river.getPh()));
            myLineChart.getData().add(series);
        }

        yAxis1.setLabel("Temperature (°C)");

        for (River river : data.getData()) {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(river.getRiverName());
            series.getData().add(new XYChart.Data<>(river.getDate(), river.getTemperature()));
            myLineChart1.getData().add(series);

        }
    }
}
