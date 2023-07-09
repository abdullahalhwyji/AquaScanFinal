package AquaScan.BarChart;

import AquaScan.Laboratory.River;
import AquaScan.Laboratory.RiverDataXML;
import AquaScan.Laboratory.RiverList;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PageLayout;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.util.Duration;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class BarChartController implements Initializable {

    @FXML
    private Button applyButton;

    @FXML
    private BarChart<String, Number> myBarChart;

    @FXML
    private ChoiceBox<String> myChoiceBox;

    private CategoryAxis xAxis;
    private NumberAxis yAxis;
    private ObservableList<River> riverData;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Create axes
        xAxis = new CategoryAxis();
        yAxis = new NumberAxis();
        myBarChart.setTitle("River Data");
        myBarChart.setLegendVisible(false);

        // Set axes to the chart
        myBarChart.setHorizontalGridLinesVisible(false);
        myBarChart.setVerticalGridLinesVisible(false);
        myBarChart.setHorizontalZeroLineVisible(false);

        // Load river data
        RiverList riverList = RiverDataXML.loadData();
        riverData = riverList.getData();

        // Initialize choice box
        initializeChoiceBox();

        applyButton.setOnAction(event -> {
            String selectedParameter = myChoiceBox.getValue();
            if (selectedParameter != null) {
                displayRiverData(selectedParameter);
            }
        });

        // Display default river data
        displayRiverData("Average pH");

    }

    private void initializeChoiceBox() {
        myChoiceBox.getItems().addAll(
                "Average pH",
                "Average Temperature",
                "Average Dissolved Oxygen",
                "Average Turbidity",
                "Average Conductivity",
                "Average Total Dissolved Solids");

        myChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                displayRiverData(newValue);

            }
        });

    }

    private void displayRiverData(String parameter) {
        // Apply animations
        animateChart();
        myBarChart.getData().clear();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(parameter);

        double totalValue = 0;
        int count = 0;
        double minValue = Double.MAX_VALUE;
        double maxValue = Double.MIN_VALUE;

        // Calculate the total value for the selected parameter
        for (River river : riverData) {
            double value = 0;
            switch (parameter) {
                case "Average pH":
                    value = river.getPh();
                    break;
                case "Average Temperature":
                    value = river.getTemperature();
                    break;
                case "Average Dissolved Oxygen":
                    value = river.getDissolvedOxygen();
                    break;
                case "Average Turbidity":
                    value = river.getTurbidity();
                    break;
                case "Average Conductivity":
                    value = river.getConductivity();
                    break;
                case "Average Total Dissolved Solids":
                    value = river.getTotalDissolvedSolids();
                    break;
            }

            totalValue += value;
            count++;

            series.getData().add(new XYChart.Data<>(river.getRiverName(), value));
            minValue = Math.min(minValue, value);
            maxValue = Math.max(maxValue, value);
        }

        double averageValue = totalValue / count;

        myBarChart.setTitle(parameter);
        series.getData().add(new XYChart.Data<>("Average", averageValue));
        myBarChart.getData().add(series);

        // Set custom labels for each bar
        for (XYChart.Data<String, Number> data : series.getData()) {
            String barLabel = data.getXValue() + " (" + data.getYValue() + ")";
            data.setExtraValue(barLabel);
        }

        // Set different color for each parameter
        List<String> colorPalette = Arrays.asList("#FF8C00", "#00CED1", "#FFD700", "#7CFC00", "#8A2BE2", "#FF1493");

        for (int i = 0; i < myChoiceBox.getItems().size(); i++) {
            if (myChoiceBox.getItems().get(i).equals(parameter)) {
                String parameterColor = colorPalette.get(i % colorPalette.size());
                for (XYChart.Data<String, Number> data : series.getData()) {
                    Node bar = data.getNode();
                    if (data.getXValue().equals("Average")) {
                        // Apply a different color to the average bar
                        bar.setStyle("-fx-bar-fill: green;");
                    } else {
                        // Apply parameter-specific color to the other bars
                        bar.setStyle("-fx-bar-fill: " + parameterColor + ";");
                    }
                }
                break;
            }
        }

        // Adjust yAxis range based on the parameter
        double range = (maxValue - minValue) * 0.75; // Use 75% of the data range
        double lowerBound = Math.max(minValue - range, 0); // Ensure the lower bound is not negative
        double upperBound = maxValue + range;
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(lowerBound);
        yAxis.setUpperBound(upperBound);

    }

    private void animateChart() {

        // Apply fade-in animation to the chart
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), myBarChart);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();

        // Apply translation animation to the bars
        for (Series<String, Number> series : myBarChart.getData()) {
            for (Data<String, Number> data : series.getData()) {
                Node bar = data.getNode();
                TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), bar);
                translateTransition.setFromY(myBarChart.getHeight());
                translateTransition.setToY(0);
                translateTransition.play();
            }
        }

        // Add tooltips to the bars
        for (Series<String, Number> series : myBarChart.getData()) {
            for (Data<String, Number> data : series.getData()) {
                Node bar = data.getNode();
                Tooltip tooltip = new Tooltip(data.getExtraValue().toString());
                Tooltip.install(bar, tooltip);
            }
        }

        // Customize chart colors and styling
        for (Series<String, Number> series : myBarChart.getData()) {
            for (Data<String, Number> data : series.getData()) {
                Node bar = data.getNode();
                // Apply custom color palette to the bars
                bar.setStyle("-fx-bar-fill: #FF8C00;");
            }
        }
    }

}