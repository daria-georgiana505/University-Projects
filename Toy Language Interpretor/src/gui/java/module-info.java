module gui_resources.a7_map_final {
    requires javafx.controls;
    requires javafx.fxml;


    opens gui_resources.a7_map_final to javafx.fxml;
    exports gui_resources.a7_map_final;
}