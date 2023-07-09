package AquaScan.Laboratory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

public class LaboratoryController implements Initializable {
    private TableViewSelectionModel selectionModel;
    private River selectedRiver;
    RiverList data;

    // For table
    @FXML
    private TextField tfFilter;
    @FXML
    private TableView<River> tvRiver;
    @FXML
    private TableColumn<River, String> tcRiverName;
    @FXML
    private TableColumn<River, String> tcDate;
    @FXML
    private TableColumn<River, String> tcRiverSide;
    @FXML
    private TableColumn<River, String> tcParameters;
    @FXML
    private TableColumn<River, Double> tcPH;
    @FXML
    private TableColumn<River, Double> tcTemperature;
    @FXML
    private TableColumn<River, Double> tcDissolvedOxygen;
    @FXML
    private TableColumn<River, Double> tcTurbidity;
    @FXML
    private TableColumn<River, Double> tcConductivity;
    @FXML
    private TableColumn<River, Double> tcTotalDissolvedSolids;

    // for input
    @FXML
    private TextField riverNameField;
    @FXML
    private DatePicker reportDateField;
    @FXML
    private ChoiceBox<String> riverSideChoiceBox;
    private String[] sides = { "East", "West", "South", "North" };
    @FXML
    private TextField dissolvedOxygenField;
    @FXML
    private Spinner<Double> phSpinner;
    SpinnerValueFactory<Double> phValueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(1.0, 14.0, 7.0, 0.1);
    @FXML
    private TextField temperatureField;
    @FXML
    private TextField conductivityField;
    @FXML
    private TextField totalDissolvedSolidsField;
    @FXML
    private TextField turbidityField;
    @FXML
    private Button saveButton;
    private FilteredList<River> filteredData;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        data = RiverDataXML.loadData();
        SpinnerValueFactory<Double> phValueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(1.0, 14.0, 7.0,
                0.1);
        phSpinner.setValueFactory(phValueFactory);
        riverSideChoiceBox.getItems().addAll(sides);
        tcRiverName.setCellValueFactory(new PropertyValueFactory<River, String>("RiverName"));
        tcDate.setCellValueFactory(new PropertyValueFactory<River, String>("Date"));
        tcPH.setCellValueFactory(new PropertyValueFactory<>("ph"));
        tcTemperature.setCellValueFactory(new PropertyValueFactory<>("temperature"));

        tcPH.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        tcTemperature.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        tcRiverSide.setCellValueFactory(new PropertyValueFactory<River, String>("RiverSide"));
        tcDissolvedOxygen.setCellValueFactory(new PropertyValueFactory<>("DissolvedOxygen"));
        tcDissolvedOxygen.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        tcTurbidity.setCellValueFactory(new PropertyValueFactory<>("Turbidity"));
        tcTurbidity.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        tcConductivity.setCellValueFactory(new PropertyValueFactory<>("conductivity"));
        tcConductivity.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        tcTotalDissolvedSolids.setCellValueFactory(new PropertyValueFactory<>("TotalDissolvedSolids"));
        tcTotalDissolvedSolids.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        tvRiver.setItems(data.getData());
        tvRiver.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tvRiver.getSelectionModel();
        filteredData = new FilteredList<>(data.getData(), b -> true);
        tfFilter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(data -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (data.getRiverName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (data.getDate().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else
                    return false;
            });
        });

        SortedList<River> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tvRiver.comparatorProperty());
        tvRiver.setItems(sortedData);

    }

    // ==========================Save Button
    // ===========================================
    // 22523230
    @FXML
    private void save(ActionEvent event) {
        String name = riverNameField.getText();
        LocalDate date = reportDateField.getValue();
        String dateString = date.toString();
        double ph1 = phSpinner.getValue();
        String tem = temperatureField.getText();
        double temperature;
        try {
            temperature = Double.parseDouble(tem);
        } catch (NumberFormatException e) {
            // showErrorAlert("Invalid temperature value. Please enter a valid number.");
            return;
        }
        String riverSide = riverSideChoiceBox.getValue().toString();
        String do1 = dissolvedOxygenField.getText();
        double dissolvedOxygen;
        try {
            dissolvedOxygen = Double.parseDouble(do1);
        } catch (NumberFormatException e) {
            showErrorAlert("Invalid dissolved oxygen value. Please enter a valid number.");
            return;
        }
        String tur = turbidityField.getText();
        double turbidity;
        try {
            turbidity = Double.parseDouble(tur);
        } catch (NumberFormatException e) {
            showErrorAlert("Invalid turbidity value. Please enter a valid number.");
            return;
        }
        String con = conductivityField.getText();
        double conductivity;
        try {
            conductivity = Double.parseDouble(con);
        } catch (NumberFormatException e) {
            showErrorAlert("Invalid conductivity value. Please enter a valid number.");
            return;
        }
        String tDS = totalDissolvedSolidsField.getText();
        double totalDissolvedSolids;
        try {
            totalDissolvedSolids = Double.parseDouble(tDS);
        } catch (NumberFormatException e) {
            showErrorAlert("Invalid total dissolved solids value. Please enter a valid number.");
            return;
        }

        List<String> emptyFields = new ArrayList<>();

        if (name.isEmpty()) {
            emptyFields.add("River Name");
        }
        if (date == null) {
            emptyFields.add("Report Date");
        }
        if (riverSide.isEmpty()) {
            emptyFields.add("River Side");
        }
        if (tem.isEmpty()) {
            emptyFields.add("Temperature");
        }
        if (do1.isEmpty()) {
            emptyFields.add("Dissolved Oxygen");
        }
        if (tur.isEmpty()) {
            emptyFields.add("Turbidity");
        }
        if (con.isEmpty()) {
            emptyFields.add("Conductivity");
        }
        if (tDS.isEmpty()) {
            emptyFields.add("Total Dissolved Solids");
        }

        if (!emptyFields.isEmpty()) {
            String errorMessage = "The following fields are empty:\n" + String.join("\n", emptyFields);
            showErrorAlert(errorMessage);
            return;
        }

        if (selectedRiver != null) {
            selectedRiver.setRiverName(name);
            selectedRiver.setDate(dateString);
            selectedRiver.setPh(ph1);
            selectedRiver.setTemperature(temperature);
            selectedRiver.setRiverSide(riverSide);
            selectedRiver.setDissolvedOxygen(dissolvedOxygen);
            selectedRiver.setTurbidity(turbidity);
            selectedRiver.setConductivity(conductivity);
            selectedRiver.setTotalDissolvedSolids(totalDissolvedSolids);
            selectedRiver = null;
        } else {
            data.setData(name, dateString, ph1, temperature, riverSide, dissolvedOxygen, turbidity, conductivity,
                    totalDissolvedSolids);
            RiverDataXML.saveData(data);
        }
        filteredData = new FilteredList<>(data.getData(), b -> true);
        // Set the filter predicate again
        tfFilter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(river -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (river.getRiverName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (river.getDate().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        // Create a new sorted list with the updated filtered data
        SortedList<River> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tvRiver.comparatorProperty());

        // Set the sorted and filtered data to the table view
        tvRiver.setItems(sortedData);
        tvRiver.refresh();
        clearFields();
        refreshData();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("River Added");
        alert.setHeaderText(null);
        alert.setContentText("The " + name + " river has been added successfully!");
        alert.showAndWait();
    }

    private void showErrorAlert(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }

    // ==========================Edit Button
    // ===========================================
    // 22523230
    @FXML
    private void editButtonAction(ActionEvent event) {
        try {
            selectedRiver = tvRiver.getSelectionModel().getSelectedItem();

            if (selectedRiver != null) {
                riverNameField.setText(selectedRiver.getRiverName());
                reportDateField.setValue(LocalDate.parse(selectedRiver.getDate()));
                phSpinner.getValueFactory().setValue(selectedRiver.getPh());
                temperatureField.setText(String.valueOf(selectedRiver.getTemperature()));
                riverSideChoiceBox.setValue(selectedRiver.getRiverSide());
                dissolvedOxygenField.setText(String.valueOf(selectedRiver.getDissolvedOxygen()));
                turbidityField.setText(String.valueOf(selectedRiver.getTurbidity()));
                conductivityField.setText(String.valueOf(selectedRiver.getConductivity()));
                totalDissolvedSolidsField.setText(String.valueOf(selectedRiver.getTotalDissolvedSolids()));
                RiverDataXML.saveData(data);
            } else {
                // Handle case when no river is selected
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Edit River");
                alert.setHeaderText(null);
                alert.setContentText("No river selected for editing.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            // Handle any exceptions that occur
            e.printStackTrace();
        }
    }

    // ==========================clear Button
    // ===========================================
    // 22523230
    @FXML
    private void clearButtonAction() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Clear Rivers");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete all rivers? This action cannot be undone.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            data.clear();
            tvRiver.refresh();
            RiverDataXML.saveData(data);

            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Clear Rivers");
            successAlert.setHeaderText(null);
            successAlert.setContentText("All rivers have been deleted.");
            successAlert.showAndWait();
        }
    }

    // ==========================Refresh Button
    // ===========================================
    // 22523230
    @FXML
    private void refreshButtonAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Refresh Data");
        alert.setHeaderText(null);
        alert.setContentText("The data has been refreshed");
        alert.showAndWait();
        refreshData();
        RiverDataXML.saveData(data);

    }

    // ==========================delete metheod Button for photo (dont use it)
    // ===========================================
    // 22523230
    @FXML
    private void remove(MouseEvent event) {
        Node source = (Node) event.getSource();
        // Clear the selected student
        if (source instanceof ImageView || source.getId().equals("delete")) {
            TableViewSelectionModel<River> selectionModel = tvRiver.getSelectionModel(); // Add this line

            River selectedRiver = selectionModel.getSelectedItem(); // Update this line

            if (selectedRiver != null) {
                data.getData().remove(selectedRiver);
                RiverDataXML.saveData(data);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete River");
                alert.setHeaderText(null);
                alert.setContentText("The River has been deleted successfully!");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete River");
                alert.setHeaderText(null);
                alert.setContentText("You do not select the River");
                alert.showAndWait();
            }
        }
    }

    // ==========================Delete Button
    // ===========================================
    // 22523230
    @FXML
    private void delete(ActionEvent event) {
        TableViewSelectionModel<River> selectionModel = tvRiver.getSelectionModel(); // Add this line
        River selectedRiver = selectionModel.getSelectedItem(); // Update this line
        if (selectedRiver != null) {
            data.getData().remove(selectedRiver);
            RiverDataXML.saveData(data);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete River");
            alert.setHeaderText(null);
            alert.setContentText("The River has been deleted successfully!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete River");
            alert.setHeaderText(null);
            alert.setContentText("You do not select the River");
            alert.showAndWait();
        }
    }

    // ==========================Refresh Button
    // ===========================================
    // 22523230
    private void refreshData() {
        FilteredList<River> filteredData = new FilteredList<>(data.getData(), b -> true);
        tfFilter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(river -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return river.getRiverName().toLowerCase().contains(lowerCaseFilter)
                        || river.getDate().toLowerCase().contains(lowerCaseFilter);
            });
        });
        SortedList<River> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tvRiver.comparatorProperty());
        tvRiver.setItems(sortedData);
        clearFields();
        selectedRiver = null;
    }

    // ==========================To clear the data after input it
    // 0 ===========================================
    // 22523230
    private void clearFields() {
        riverNameField.clear();
        temperatureField.clear();
        dissolvedOxygenField.clear();
        turbidityField.clear();
        conductivityField.clear();
        totalDissolvedSolidsField.clear();
        reportDateField.setValue(null);
        riverSideChoiceBox.getSelectionModel().clearSelection();
        phSpinner.setValueFactory(phValueFactory); // Set the value factory
        RiverDataXML.saveData(data);
    }

    // @FXML
    // private Button goPieChart;

    // @FXML
    // private void openPieChartPage(ActionEvent event) {
    // try {
    // FXMLLoader loader = new FXMLLoader(getClass().getResource("Pie.fxml"));
    // Parent root = loader.load();

    // // Get the controller instance
    // PieController pieController = loader.getController();

    // // Initialize the pie chart data
    // pieController.initialize(null, null);

    // Stage stage = new Stage();
    // stage.setTitle("Pie Chart");
    // stage.setScene(new Scene(root));
    // stage.show();
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // }

}
