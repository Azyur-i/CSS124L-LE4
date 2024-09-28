module com.groupfour {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;

    opens com.groupfour to javafx.fxml;
    exports com.groupfour;
}
