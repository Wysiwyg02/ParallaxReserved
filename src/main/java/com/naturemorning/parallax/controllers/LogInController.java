/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naturemorning.parallax.controllers;

import com.naturemorning.parallax.config.StageManager;
import com.naturemorning.parallax.views.FxmlView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

@Controller
public class LogInController implements Initializable {

    @Lazy
    @Autowired
    private StageManager stageManager;
    @FXML
    private TextField username;
   @FXML
    private PasswordField password;
    @FXML
    private Button login;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    void login(ActionEvent event) {
        stageManager.switchScene(FxmlView.CASHIER);
    }

}
