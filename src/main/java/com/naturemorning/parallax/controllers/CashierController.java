/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naturemorning.parallax.controllers;


import com.naturemorning.parallax.config.StageManager;
import com.naturemorning.parallax.views.FxmlView;
import java.net.URL;
import java.util.ResourceBundle;import javafx.event.ActionEvent;
;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import org.springframework.stereotype.Controller;
 import javafx.scene.input.MouseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
@Controller
public class CashierController implements Initializable {

    @FXML
    private Button order;
    @FXML
    private Button reservation;
    @FXML
    private ImageView create;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
      @Lazy
    @Autowired
    private StageManager stageManager;

    @FXML
    private void order(MouseEvent event) {
        stageManager.switchScene(FxmlView.CASHIERDELIVERY);
        
    }

    @FXML
    private void reservation(MouseEvent event) {
        stageManager.switchScene(FxmlView.CASHIERRESERVATION);
        
    }
    
 @FXML
   private void create(MouseEvent event) {
       stageManager.switchScene(FxmlView.CREATECASHIER);

    }
    
}
