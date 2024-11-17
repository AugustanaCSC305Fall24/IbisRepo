module edu.augustana {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.desktop;
    requires java.sql;

    opens edu.augustana to javafx.fxml;
    exports edu.augustana;
}
