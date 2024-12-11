module edu.augustana {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.desktop;
    requires java.sql;
    requires java.net.http;
    requires org.json;

    opens edu.augustana to javafx.fxml;
    exports edu.augustana;
}
