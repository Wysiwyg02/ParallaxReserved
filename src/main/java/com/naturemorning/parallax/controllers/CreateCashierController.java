/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naturemorning.parallax.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Controller;

import com.naturemorning.parallax.models.Cashier;
import com.naturemorning.parallax.services.impl.CashierService;
import com.naturemorning.parallax.config.StageManager;
import com.naturemorning.parallax.views.FxmlView;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

@Controller
public class CreateCashierController implements Initializable {

    @FXML
    private Label cashierID;
    @FXML
    private TableView<Cashier> cashierTable;

    @FXML
    private TableColumn<Cashier, Long> colID;
    @FXML
    private TableColumn<Cashier, String> coluserName;

    @FXML
   private TableColumn<Cashier, String> colPass;

    @FXML
    private TableColumn<Cashier, Boolean> colEdit;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button cancel;
    @FXML
    private Button save;
    @FXML
    private Button delete;

    @FXML
    private Button reset;
    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    private CashierService cashierService;

    private ObservableList<Cashier> cashierList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cashierTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        setColumnProperties();
        loadUserDetails();
    }

    @FXML
    private void cancel(ActionEvent event) {

        stageManager.switchScene(FxmlView.CASHIER);

    }

    @FXML
    private void save(ActionEvent event) {

        if (validate("Username", getUsername(), "([a-zA-Z]{3,30}\\s*)+")
                && validate("Password", getPassword(), "([a-zA-Z0-9\"#$%&'()*+,-./:;<=>?@]{8,30}\\s*)+")) {
            if (cashierID.getText() == null || "".equals(cashierID.getText())) {
                if (true) {

                    Cashier user = new Cashier();
                    user.setUsername(getUsername());
                    user.setPassword(getPassword());
                    
                    Cashier newUser = cashierService.save(user);

                    saveAlert(newUser);
                }

            } else {
                Cashier user = cashierService.find(Long.parseLong(cashierID.getText()));
                user.setUsername(getUsername());
                user.setPassword(getPassword());
                
                Cashier updatedUser = cashierService.update(user);
                updateAlert(updatedUser);
            }

            clearFields();
            loadUserDetails();
        }

    }

    private void saveAlert(Cashier user) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("User saved successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The user " + user.getUsername() + " " + user.getPassword() + " has been created and \n" + " id is " + user.getId() + ".");
        alert.showAndWait();
        loadUserDetails();
    }

    public String getUsername() {
        return username.getText();
    }

    public String getPassword() {
        return password.getText();
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

    private boolean emptyValidation(String field, boolean empty) {
        if (!empty) {
            return true;
        } else {
            validationAlert(field, true);
            return false;
        }
    }

    private void setColumnProperties() {
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        coluserName.setCellValueFactory(new PropertyValueFactory<>("username"));
        colPass.setCellValueFactory(new PropertyValueFactory<>("password"));
        colEdit.setCellFactory(cellFactory);

    }
    Callback<TableColumn<Cashier, Boolean>, TableCell<Cashier, Boolean>> cellFactory
            = new Callback<TableColumn<Cashier, Boolean>, TableCell<Cashier, Boolean>>() {
        @Override
        public TableCell<Cashier, Boolean> call(final TableColumn<Cashier, Boolean> param) {
            final TableCell<Cashier, Boolean> cell = new TableCell<Cashier, Boolean>() {
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
                            Cashier user = getTableView().getItems().get(getIndex());
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

                private void updateUser(Cashier user) {
                    cashierID.setText(Long.toString(user.getId()));
                    username.setText(user.getUsername());
                    password.setText(user.getPassword());

                }
            };
            return cell;
        }
    };

    private void loadUserDetails() {
        cashierList.clear();
        cashierList.addAll(cashierService.findAll());
        cashierTable.setItems(cashierList);
    }

    private void clearFields() {
        username.clear();
        password.clear();

    }

    private void updateAlert(Cashier user) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("User updated successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The user " + user.getUsername() + " " + user.getPassword() + " has been updated.");
        alert.showAndWait();
    }

    @FXML
    private void delete(ActionEvent event) {

        List<Cashier> users = cashierTable.getSelectionModel().getSelectedItems();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete the selected user?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            cashierService.deleteInBatch(users);
        }

        loadUserDetails();
    }

    @FXML
    private void reset(ActionEvent event) {
        username.clear();
        password.clear();
    }
}
