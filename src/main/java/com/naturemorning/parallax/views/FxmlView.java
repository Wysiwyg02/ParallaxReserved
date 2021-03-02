package com.naturemorning.parallax.views;

import java.util.ResourceBundle;

/**
 * OOP Class 20-21
 *
 * @author Gerald Villaran
 */
public enum FxmlView {

  LOGIN {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("login.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/LogIn.fxml";
        }
    },CASHIER {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("cashier.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/Cashier.fxml";
        }
    },CASHIERDELIVERY {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("cashierdelivery.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/CashierMenuDelivery.fxml";
        }
    },CASHIERRESERVATION {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("cashierreservation.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/CashierReservation.fxml";
        }
    },CREATECASHIER{
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("createcashier.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/CreateCashier.fxml";
        }
    
    };

    public abstract String getTitle();

    public abstract String getFxmlFile();

    String getStringFromResourceBundle(String key) {
        return ResourceBundle.getBundle("Bundle").getString(key);
    }

}
