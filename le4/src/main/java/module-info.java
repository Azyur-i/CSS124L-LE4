module com.groupfour {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires javafx.graphics;

    opens com.groupfour to javafx.fxml;
    exports com.groupfour;
}
