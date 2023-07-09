package AquaScan.Reports;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import AquaScan.Laboratory.River;
import AquaScan.Laboratory.RiverDataXML;
import AquaScan.Laboratory.RiverList;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.util.converter.IntegerStringConverter;

public class ReportsController implements Initializable {
    private TableViewSelectionModel selectionModel;
    private Pollution selectedPollution;
    PollutionList data;
    private RiverList riverList;

    private int nextIncidentID;

    @FXML
    private TextField tfFilter;

    // for the table
    @FXML
    private TableView<Pollution> tvPollution;
    @FXML
    private TableColumn<Pollution, Integer> tcIncidentID;
    @FXML
    private TableColumn<Pollution, String> tcRiverName;
    @FXML
    private TableColumn<Pollution, String> tcDate;
    @FXML
    private TableColumn<Pollution, String> tcPollutionSource;
    @FXML
    private TableColumn<Pollution, String> tcResponsibleParty;
    @FXML
    private TableColumn<Pollution, String> tcStatus;
    @FXML
    private TableColumn<Pollution, String> tcActionTaken;
    @FXML
    private TableColumn<Pollution, String> tcImpact;

    // for input
    @FXML
    private ChoiceBox<String> cbRiverName;

    @FXML
    private DatePicker dpDate;

    @FXML
    private TextField pollutionSourceField;
    @FXML
    private ChoiceBox<String> cbResponsibleParty;
    private String[] responsibleParties = { "ABC Manufacturing Co.",
            "Environmental Protection Agency (EPA)",
            "XYZ Chemicals Ltd.",
            "Department of Natural Resources (DNR)",
            "Municipal Water and Sewage Authority",
            "Smith Industrial Solutions",
            "Green Earth ",
            "Johnson Agrochemicals",
            "City Council Waste Management Department",
            "Global Energy Corporation",
            "Other" };
    @FXML
    private ChoiceBox<String> cbStatus;
    private String[] statuses = { "Under Investigation", "In Remediation", "Resolved", "Closed" };
    @FXML
    private TextField actionTakenField;
    @FXML
    private TextField impactField;

    @FXML
    private Button saveButton;
    private FilteredList<Pollution> filteredData;

    private void populateRiverNames() {
        List<String> riverNames = riverList.getData().stream()
                .map(River::getRiverName)
                .filter(name -> !isRiverSelectedOrDeleted(name))
                .collect(Collectors.toList());
        cbRiverName.setItems(FXCollections.observableArrayList(riverNames));
    }

    private boolean isRiverSelectedOrDeleted(String riverName) {
        return data.getData().stream().anyMatch(pollution -> pollution.getRiverName().equals(riverName));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        cbStatus.getItems().addAll(statuses);
        cbResponsibleParty.getItems().addAll(responsibleParties);
        riverList = RiverDataXML.loadData();
        data = PollutionDataXML.loadData();

        populateRiverNames();
        // data = new PollutionList(); // Instantiate the data object
        // data.setDummy();
        tvPollution.setItems(data.getData());
        tcIncidentID.setCellValueFactory(new PropertyValueFactory<>("incidentID"));
        tcIncidentID.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        tcRiverName.setCellValueFactory(new PropertyValueFactory<Pollution, String>("riverName"));
        tcDate.setCellValueFactory(new PropertyValueFactory<Pollution, String>("date"));
        tcPollutionSource.setCellValueFactory(new PropertyValueFactory<Pollution, String>("pollutionSource"));
        tcImpact.setCellValueFactory(new PropertyValueFactory<Pollution, String>("impact"));
        tcStatus.setCellValueFactory(new PropertyValueFactory<Pollution, String>("status"));
        tcActionTaken.setCellValueFactory(new PropertyValueFactory<Pollution, String>("actionTaken"));
        tcResponsibleParty.setCellValueFactory(new PropertyValueFactory<Pollution, String>("responsibleParty"));

        tvPollution.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tvPollution.getSelectionModel();
        filteredData = new FilteredList<>(data.getData(), b -> true);
        tfFilter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(data -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (data.getStatus().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (data.getPollutionSource().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (data.getRiverName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else
                    return false;
            });
        });

        SortedList<Pollution> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tvPollution.comparatorProperty());
        tvPollution.setItems(sortedData);
    }

    @FXML
    private void save(ActionEvent event) {
        String riverName = cbRiverName.getValue();
        LocalDate date = dpDate.getValue();
        String dateString = date.toString();
        String pollutionSource = pollutionSourceField.getText();
        String responsibleParty = cbResponsibleParty.getValue();
        String status = cbStatus.getValue();
        String actionTaken = actionTakenField.getText();
        String impact = impactField.getText();

        List<String> emptyFields = new ArrayList<>();

        if (riverName == null || riverName.isEmpty()) {
            emptyFields.add("River Name");
        }
        if (date == null) {
            emptyFields.add("Date");
        }
        if (pollutionSource.isEmpty()) {
            emptyFields.add("Pollution Source");
        }
        if (responsibleParty == null || responsibleParty.isEmpty()) {
            emptyFields.add("Responsible Party");
        }
        if (status == null || status.isEmpty()) {
            emptyFields.add("Status");
        }
        if (actionTaken.isEmpty()) {
            emptyFields.add("Action Taken");
        }
        if (impact.isEmpty()) {
            emptyFields.add("Impact");
        }

        if (!emptyFields.isEmpty()) {
            String errorMessage = "The following fields are empty:\n" + String.join("\n",
                    emptyFields);
            showErrorAlert(errorMessage);
            return;
        }

        int incidentID = data.getData().size() + 1;

        // Create a new Pollution object with the entered data
        Pollution newPollution = new Pollution(
                incidentID,
                riverName,
                dateString,
                pollutionSource,
                responsibleParty,
                status,
                actionTaken,
                impact);

        // Add the new pollution to the data list
        data.getData().add(newPollution);

        cbRiverName.getItems().remove(riverName);
        PollutionDataXML.saveData(data);

        // Clear the input fields
        cbRiverName.setValue(null);
        dpDate.setValue(null);
        pollutionSourceField.clear();
        cbResponsibleParty.setValue(null);
        cbStatus.setValue(null);
        actionTakenField.clear();
        impactField.clear();

        // Refresh the table view
        tvPollution.refresh();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Pollution Added");
        alert.setHeaderText(null);
        alert.setContentText("Pollution incident ID: " + incidentID + " has been added successfully!");
        alert.showAndWait();
    }

    private void showErrorAlert(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }

    @FXML
    private void ClearButtonAction() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Clear Pollutions");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete all pollutions? This action cannot be undone.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Get the river names from the data list
            List<String> riverNames = data.getData().stream()
                    .map(Pollution::getRiverName)
                    .distinct()
                    .collect(Collectors.toList());

            // Add the river names back to the choice box
            cbRiverName.getItems().addAll(riverNames);

            data.clear();
            tvPollution.refresh();
            PollutionDataXML.saveData(data);

            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Clear Pollutions");
            successAlert.setHeaderText(null);
            successAlert.setContentText("All pollutions have been deleted.");
            successAlert.showAndWait();
        }
    }

    @FXML
    private void editButtonAction(ActionEvent event) {
        try {
            selectedPollution = tvPollution.getSelectionModel().getSelectedItem();

            if (selectedPollution != null) {
                // Populate the input fields with the selected pollution's data
                cbRiverName.setValue(selectedPollution.getRiverName());
                dpDate.setValue(LocalDate.parse(selectedPollution.getDate()));
                pollutionSourceField.setText(selectedPollution.getPollutionSource());
                cbResponsibleParty.setValue(selectedPollution.getResponsibleParty());
                cbStatus.setValue(selectedPollution.getStatus());
                actionTakenField.setText(selectedPollution.getActionTaken());
                impactField.setText(selectedPollution.getImpact());
                String previousRiverName = selectedPollution.getRiverName();

                // Populate the input fields with the selected pollution's data
                cbRiverName.setValue(previousRiverName);
                // ...

                // Remove the previous river name from the choice box temporarily
                cbRiverName.getItems().remove(previousRiverName);
            } else {
                // Handle case when no pollution is selected
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Edit Pollution");
                alert.setHeaderText(null);
                alert.setContentText("No pollution selected for editing.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            // Handle any exceptions that occur
            e.printStackTrace();
        }
    }

    @FXML
    private void updateButtonAction(ActionEvent event) {
        try {
            // Retrieve the updated values from the input fields
            String riverName = cbRiverName.getValue();
            LocalDate date = dpDate.getValue();
            String dateString = date.toString();
            String pollutionSource = pollutionSourceField.getText();
            String responsibleParty = cbResponsibleParty.getValue();
            String status = cbStatus.getValue();
            String actionTaken = actionTakenField.getText();
            String impact = impactField.getText();

            // Perform validation on the updated values
            List<String> emptyFields = new ArrayList<>();

            if (riverName == null || riverName.isEmpty()) {
                emptyFields.add("River Name");
            }
            if (date == null) {
                emptyFields.add("Date");
            }
            if (pollutionSource.isEmpty()) {
                emptyFields.add("Pollution Source");
            }
            if (responsibleParty.isEmpty()) {
                emptyFields.add("Responsible Party");
            }
            if (status == null || status.isEmpty()) {
                emptyFields.add("Status");
            }
            if (actionTaken.isEmpty()) {
                emptyFields.add("Action Taken");
            }
            if (impact.isEmpty()) {
                emptyFields.add("Impact");
            }

            if (!emptyFields.isEmpty()) {
                String errorMessage = "The following fields are empty:\n" + String.join("\n", emptyFields);
                showErrorAlert(errorMessage);
                return;
            }

            // Remove the previous river name from the choice box if it has changed
            if (selectedPollution != null && !selectedPollution.getRiverName().equals(riverName)) {
                cbRiverName.getItems().add(selectedPollution.getRiverName());
                cbRiverName.getItems().remove(riverName);
            }

            // Update the selected pollution with the new values
            selectedPollution.setRiverName(riverName);
            selectedPollution.setDate(dateString);
            selectedPollution.setPollutionSource(pollutionSource);
            selectedPollution.setResponsibleParty(responsibleParty);
            selectedPollution.setStatus(status);
            selectedPollution.setActionTaken(actionTaken);
            selectedPollution.setImpact(impact);

            // Clear the input fields
            cbRiverName.setValue(null);
            dpDate.setValue(null);
            pollutionSourceField.clear();
            cbResponsibleParty.setValue(null);
            cbStatus.setValue(null);
            actionTakenField.clear();
            impactField.clear();

            // Refresh the table view
            tvPollution.refresh();

            // Save the updated data to XML
            PollutionDataXML.saveData(data);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Pollution Updated");
            alert.setHeaderText(null);
            alert.setContentText("The selected pollution has been updated successfully!");
            alert.showAndWait();
        } catch (Exception e) {
            // Handle any exceptions that occur
            e.printStackTrace();
        }
    }

    @FXML
    private void removeButtonAction(ActionEvent event) {
        Pollution selectedPollution = tvPollution.getSelectionModel().getSelectedItem();

        if (selectedPollution != null) {
            String riverName = selectedPollution.getRiverName();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Pollution");
            alert.setHeaderText(null);
            alert.setContentText(
                    "Are you sure you want to delete this pollution incident? This action cannot be undone.");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                data.getData().remove(selectedPollution);
                PollutionDataXML.saveData(data);

                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Delete Pollution");
                successAlert.setHeaderText(null);
                successAlert.setContentText("The pollution incident has been deleted successfully!");
                successAlert.showAndWait();
            }
            // Add the river back to the choice box
            if (!cbRiverName.getItems().contains(riverName)) {
                cbRiverName.getItems().add(riverName);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete Pollution");
            alert.setHeaderText(null);
            alert.setContentText("No pollution incident selected for deletion.");
            alert.showAndWait();
        }
    }
}