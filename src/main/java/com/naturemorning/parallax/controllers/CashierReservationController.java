/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naturemorning.parallax.controllers;

import com.naturemorning.parallax.config.StageManager;
import com.naturemorning.parallax.models.Reservation;
import com.naturemorning.parallax.services.impl.ReservationService;
import com.naturemorning.parallax.views.FxmlView;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

@Controller
public class CashierReservationController implements Initializable {

    @FXML
    private TextField customerFirstName;

    @FXML
    private TextField customerLastName;
    @FXML
    private TextField email;
    @FXML
    private TextField phoneNo;
    @FXML
    private TextField guestNo;
    @FXML
    private DatePicker dob;
    @FXML
    private TextField time;
    @FXML
    private DatePicker rdob;
    @FXML
    private Button delete;
    @FXML
    private Label customerID;

    @FXML
    private TableView<Reservation> reservationTable;

    @FXML
    private TableColumn<Reservation, Long> colId;

    @FXML
    private TableColumn<Reservation, String> colFirstName;

    @FXML
    private TableColumn<Reservation, String> colLastName;

    @FXML
    private TableColumn<Reservation, String> colEmail;

    @FXML
    private TableColumn<Reservation, String> colPhoneNo;

    @FXML
    private TableColumn<Reservation, String> colGuestNo;

    @FXML
    private TableColumn<Reservation, LocalDate> colDate;

    @FXML
    private TableColumn<Reservation, LocalDate> colReservationDate;

    @FXML
    private TableColumn<Reservation, String> colCurrentTime;
    @FXML
    private TableColumn<Reservation, String> colPeriod;
    @FXML
    private TableColumn<Reservation, Boolean> colEdit;
    @FXML
    private Button confirm;
    @FXML
    private Button cancel;
    @FXML
    private Button reset;
    @FXML
    private RadioButton rbtn_AM;

    @FXML
    private RadioButton rbtn_PM;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    private ReservationService reservationService;

    private ObservableList<Reservation> reservationList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        reservationTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        setColumnProperties();
        loadUserDetails();
    }

    @FXML
    private void confirm(ActionEvent event) {
        if (validate("First Name", getcustomerFirstName(), "([a-zA-Z]{2,30}\\s*)+")
                && validate("Last Name", getcustomerLastName(), "([a-zA-Z]{2,30}\\s*)+")
                && validate("Email", getEmail(), "([a-zA-Z0-9\"#$%&'()*+,-./:;<=>?@]{3,30}\\s*)+")
                && validate("Phone Number", getPhoneNumber(), "([1,2,3,4,5,6,7,8,9,0]{1,30}\\s*)+")
                && validate("Guests", getGuestNumber(), "([1,2,3,4,5,6,7,8,9,0]{1,30}\\s*)+")
                && validate("Time", getTime(), "([1,2,3,4,5,6,7,8,9,0]{1,30}\\s*)+")
                && emptyValidation("DOB", dob.getEditor().getText().isEmpty())
                && emptyValidation("RDOB", rdob.getEditor().getText().isEmpty())) {
            if (customerID.getText() == null || "".equals(customerID.getText())) {
                if (true) {

                    Reservation user = new Reservation();
                    user.setCustomerFirstName(getcustomerFirstName());
                    user.setCustomerLastName(getcustomerLastName());
                    user.setEmail(getEmail());
                    user.setPhoneNo(getPhoneNumber());
                    user.setGuestNo(getGuestNumber());
                    user.setTime(getTime());
                    user.setPeriod(getPeriod());
                    user.setDob(getDob());
                    user.setRDOB(getRDOB());

                    Reservation newUser = reservationService.save(user);

                    saveAlert(newUser);
                }

            } else {
                Reservation user = reservationService.find(Long.parseLong(customerID.getText()));
                user.setCustomerFirstName(getcustomerFirstName());
                user.setCustomerLastName(getcustomerLastName());
                user.setEmail(getEmail());
                user.setPhoneNo(getPhoneNumber());
                user.setGuestNo(getGuestNumber());
                user.setTime(getTime());
                user.setPeriod(getPeriod());
                user.setDob(getDob());
                user.setRDOB(getRDOB());

                Reservation updatedUser = reservationService.update(user);
                updateAlert(updatedUser);
            }

            clearFields();
            loadUserDetails();
        }
    }

    private void saveAlert(Reservation user) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("User saved successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The user " + user.getCustomerFirstName() + " " + user.getCustomerLastName() + " has been created and \n" + " id is " + user.getId() + ".");
        alert.showAndWait();
        loadUserDetails();
    }

    private void updateAlert(Reservation user) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("User updated successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The user " + user.getCustomerFirstName() + " " + user.getCustomerLastName() + " has been updated.");
        alert.showAndWait();
    }

    private boolean validate(String field, String value, String pattern) {
        if (!value.isEmpty()) {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(value);
            if (m.find() && m.group().equals(value)) {
                return true;
            } else {
                validationAlert(field, false);
                return false;
            }
        } else {
            validationAlert(field, true);
            return false;
        }
    }

    private void validationAlert(String field, boolean empty) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        if (field.equals("Role")) {
            alert.setContentText("Please Select " + field);
        } else {
            if (empty) {
                alert.setContentText("Please Enter " + field);
            } else {
                alert.setContentText("Please Enter Valid " + field);
            }
        }
        alert.showAndWait();
    }

    private void clearFields() {
        customerFirstName.clear();
        customerLastName.clear();
        email.clear();
        phoneNo.clear();
        guestNo.clear();
        time.clear();
        dob.getEditor().clear();
        rdob.getEditor().clear();

    }

    public String getcustomerFirstName() {
        return customerFirstName.getText();
    }

    public String getcustomerLastName() {
        return customerLastName.getText();
    }

    public String getEmail() {
        return email.getText();
    }

    public String getPhoneNumber() {
        return phoneNo.getText();
    }

    public String getGuestNumber() {
        return guestNo.getText();
    }

    public String getTime() {
        return time.getText();
    }

    public LocalDate getDob() {
        return dob.getValue();
    }

    public LocalDate getRDOB() {
        return rdob.getValue();
    }

    public String getPeriod() {
        return rbtn_AM.isSelected() ? "AM" : "PM";
    }

    private boolean emptyValidation(String field, boolean empty) {
        if (!empty) {
            return true;
        } else {
            validationAlert(field, true);
            return false;
        }
    }

    private void setColumnProperties() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("customerFirstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("customerLastName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhoneNo.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));
        colGuestNo.setCellValueFactory(new PropertyValueFactory<>("guestNo"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colCurrentTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colPeriod.setCellValueFactory(new PropertyValueFactory<>("period"));
        colReservationDate.setCellValueFactory(new PropertyValueFactory<>("RDOB"));
        colEdit.setCellFactory(cellFactory);

    }
    Callback<TableColumn<Reservation, Boolean>, TableCell<Reservation, Boolean>> cellFactory
            = new Callback<TableColumn<Reservation, Boolean>, TableCell<Reservation, Boolean>>() {
        @Override
        public TableCell<Reservation, Boolean> call(final TableColumn<Reservation, Boolean> param) {
            final TableCell<Reservation, Boolean> cell = new TableCell<Reservation, Boolean>() {
                Image imgEdit = new Image(getClass().getResourceAsStream("/images/edit.png"));
                final Button btnEdit = new Button();

                @Override
                public void updateItem(Boolean check, boolean empty) {
                    super.updateItem(check, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        btnEdit.setOnAction(e -> {
                            Reservation user = getTableView().getItems().get(getIndex());
                            updateUser(user);
                        });

                        btnEdit.setStyle("-fx-background-color: transparent;");
                        ImageView iv = new ImageView();
                        iv.setImage(imgEdit);
                        iv.setPreserveRatio(true);
                        iv.setSmooth(true);
                        iv.setCache(true);
                        btnEdit.setGraphic(iv);

                        setGraphic(btnEdit);
                        setAlignment(Pos.CENTER);
                        setText(null);
                    }
                }

                private void updateUser(Reservation user) {
                    customerID.setText(Long.toString(user.getId()));
                    customerFirstName.setText(user.getCustomerFirstName());
                    customerLastName.setText(user.getCustomerLastName());
                    email.setText(user.getEmail());
                    phoneNo.setText(user.getPhoneNo());
                    guestNo.setText(user.getGuestNo());
                    time.setText(user.getTime());
                    dob.setValue(user.getDob());
                    rdob.setValue(user.getRDOB());
                    if (user.getPeriod().equals("AM")) {
                        rbtn_AM.setSelected(true);
                    } else {
                        rbtn_PM.setSelected(true);
                    }
                }
            };
            return cell;
        }
    };

    private void loadUserDetails() {
        reservationList.clear();
        reservationList.addAll(reservationService.findAll());
        reservationTable.setItems(reservationList);
    }

    @FXML
    private void cancel(ActionEvent event) {
        stageManager.switchScene(FxmlView.CASHIER);
    }

    @FXML
    void delete(ActionEvent event) {
        List<Reservation> users = reservationTable.getSelectionModel().getSelectedItems();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete the selected user?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            reservationService.deleteInBatch(users);
        }

        loadUserDetails();
    }

    @FXML
    void reset(ActionEvent event) {
        customerFirstName.clear();
        customerLastName.clear();
        email.clear();
        phoneNo.clear();
        guestNo.clear();
        time.clear();
        dob.getEditor().clear();
        rdob.getEditor().clear();
    }
}
