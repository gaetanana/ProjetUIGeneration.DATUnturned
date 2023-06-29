module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens Vue to javafx.fxml;
    exports Vue;
    exports Controller;
    opens Controller to javafx.fxml;
}