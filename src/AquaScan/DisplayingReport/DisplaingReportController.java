package AquaScan.DisplayingReport;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import AquaScan.Reports.Pollution;
import AquaScan.Reports.PollutionDataXML;
import AquaScan.Reports.PollutionList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class DisplaingReportController {
    @FXML
    private PieChart myPieChart;

    @FXML
    private LineChart<String, Integer> myLineChart;

    @FXML
    private BarChart myBarChart;

    @FXML
    private ChoiceBox<String> riverChoiceBox;

    @FXML
    private ListView<Pane> responsiblePartyList;

    private PollutionList data;

    public void initialize() {
        // Load data from XML
        data = PollutionDataXML.loadData();

        // Prepare variables for storing status count
        int underInvestigationCount = 0;
        int inRemediationCount = 0;
        int resolvedCount = 0;
        int closedCount = 0;

        Map<LocalDate, Integer> pollutionCountByDate = new HashMap<>();

        // Count the occurrences of each status
        for (Pollution pollutionData : data.getData()) {
            String status = pollutionData.getStatus();
            switch (status) {
                case "Under Investigation":
                    underInvestigationCount++;
                    break;
                case "In Remediation":
                    inRemediationCount++;
                    break;
                case "Resolved":
                    resolvedCount++;
                    break;
                case "Closed":
                    closedCount++;
                    break;
            }

            String dateString = pollutionData.getDate();
            LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ISO_DATE);
            pollutionCountByDate.put(date, pollutionCountByDate.getOrDefault(date, 0) + 1);
        }

        // Calculate the total count
        int totalCount = underInvestigationCount + inRemediationCount + resolvedCount + closedCount;

        // Create PieChart.Data objects with the status counts and percentages
        PieChart.Data underInvestigationData = new PieChart.Data("Under Investigation", underInvestigationCount);
        PieChart.Data inRemediationData = new PieChart.Data("In Remediation", inRemediationCount);
        PieChart.Data resolvedData = new PieChart.Data("Resolved", resolvedCount);
        PieChart.Data closedData = new PieChart.Data("Closed", closedCount);

        // Add the data objects to the PieChart
        myPieChart.getData().addAll(underInvestigationData, inRemediationData, resolvedData, closedData);

        // Set labels to show the percentage and count on hover
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        myPieChart.getData().forEach(pieChartData -> {
            double percentage = (pieChartData.getPieValue() / totalCount) * 100;
            String label = pieChartData.getName() + "(" + decimalFormat.format(percentage) + "%), ("
                    +
                    pieChartData.getPieValue() + ")";
            pieChartData.setName(label);
        });

        // Prepare variables for storing pollution count and responsible party count
        int totalPollutionCount = data.getData().size();
        Map<String, Integer> responsiblePartyCount = new HashMap<>();

        // Count the occurrences of each responsible party
        for (Pollution pollutionData : data.getData()) {
            String responsibleParty = pollutionData.getResponsibleParty();
            responsiblePartyCount.put(responsibleParty, responsiblePartyCount.getOrDefault(responsibleParty, 0) + 1);
        }

        // Create a new series for the bar chart
        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        // Create BarChart.Data objects with the responsible party counts
        for (Map.Entry<String, Integer> entry : responsiblePartyCount.entrySet()) {
            String responsibleParty = entry.getKey();
            int count = entry.getValue();
            series.getData().add(new XYChart.Data<>(responsibleParty, count));
        }

        // Add the series to the bar chart
        myBarChart.getData().add(series);

        // Customize chart colors and styling
        List<String> colorPalette = Arrays.asList(
                "#2F4F4F", // Dark Slate Gray
                "#FF8C00", // Dark Orange
                "#00CED1", // Dark Turquoise
                "#FFD700", // Gold
                "#556B2F", // Dark Olive Green
                "#0000FF", // Blue
                "#A52A2A", // Brown
                "#00FFFF", // Cyan
                "#800080", // Purple
                "#808080" // Gray
        );

        // Create a list to store bar information (responsible party and color)
        List<Map.Entry<String, Integer>> barList = new ArrayList<>();

        // Assign colors to responsible parties based on their count
        for (Map.Entry<String, Integer> entry : responsiblePartyCount.entrySet()) {
            String responsibleParty = entry.getKey();
            int count = entry.getValue();
            barList.add(entry); // Add the responsible party and count to the list
            series.getData().add(new XYChart.Data<>(responsibleParty, count));
        }

        // Populate the responsible party list with colored bars
        ObservableList<Pane> items = FXCollections.observableArrayList();
        for (Map.Entry<String, Integer> entry : barList) {
            String responsibleParty = entry.getKey();
            int count = entry.getValue();
            String color = colorPalette.get(items.size() % colorPalette.size());

            Pane barPane = new Pane();
            barPane.setPrefSize(20, 10);
            barPane.setStyle("-fx-background-color: " + color + ";");

            // Create a label for the responsible party name
            Label nameLabel = new Label(responsibleParty);

            // Create a horizontal box to hold the label and the bar
            HBox barBox = new HBox(barPane, nameLabel);
            barBox.setSpacing(5); // Add spacing between the bar and the name label

            // Add the barBox to the items list
            items.add(barBox);

            // Assign the same color to the corresponding bar in the BarChart
            for (XYChart.Data<String, Integer> dataPoint : series.getData()) {
                if (dataPoint.getXValue().equals(responsibleParty)) {
                    Node bar = dataPoint.getNode();
                    bar.setStyle("-fx-bar-fill: " + color + ";");
                    break;
                }
            }
        }

        responsiblePartyList.setItems(items);
    }

    private void populateRiverChoiceBox() {
        // Get the unique river names from the data
        Set<String> rivers = data.getData().stream()
                .map(Pollution::getRiverName)
                .collect(Collectors.toSet());

        // Populate the riverChoiceBox
        riverChoiceBox.setItems(FXCollections.observableArrayList(rivers));
    }

    private void updateLineChart(String selectedRiver) {
        // Clear existing series data
        myLineChart.getData().clear();

        // Create a new series
        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        // Count the occurrences of pollutions for each date and add data points to the
        // series
        Map<String, Integer> pollutionCountByDate = new HashMap<>();
        for (Pollution pollutionData : data.getData()) {
            if (pollutionData.getRiverName().equals(selectedRiver)) {
                String dateString = pollutionData.getDate();
                pollutionCountByDate.put(dateString, pollutionCountByDate.getOrDefault(dateString, 0) + 1);
            }
        }

        // Populate series with data points for each date
        for (String date : pollutionCountByDate.keySet()) {
            int count = pollutionCountByDate.get(date);
            series.getData().add(new XYChart.Data<>(date, count));
        }

        // Update the X-axis labels manually
        List<String> xLabels = new ArrayList<>(pollutionCountByDate.keySet());
        Collections.sort(xLabels);

        // Clear the existing categories and add new categories
        CategoryAxis xAxis = (CategoryAxis) myLineChart.getXAxis();
        xAxis.getCategories().clear();
        xAxis.getCategories().addAll(xLabels);

        // Add the series to the line chart
        myLineChart.getData().add(series);
    }

    private void updateBarChart() {
        // Clear existing data in the bar chart
        myBarChart.getData().clear();

        // Create a new series for the bar chart
        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        // Iterate over the Pollution data and add data points to the series
        for (Pollution pollutionData : data.getData()) {
            String riverName = pollutionData.getRiverName();
            int incidentID = pollutionData.getIncidentID();

            // Add a data point to the series
            series.getData().add(new XYChart.Data<>(riverName, incidentID));
        }

        // Add the series to the bar chart
        myBarChart.getData().add(series);
    }

}