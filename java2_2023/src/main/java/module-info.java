module java1_2022_opa0023 {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
	requires transitive java.desktop;
    requires java.sql;
    requires transitive javafx.base;
    requires java.base;
    requires transitive javafx.graphics;
	requires lombok;
	requires transitive jakarta.persistence;
	requires transitive org.hibernate.orm.core;
	requires java.compiler;
	requires org.apache.logging.log4j;
    opens presentation_layer to javafx.fxml, javafx.graphics, javafx.base, javafx.controls;
    opens domain_layer to org.hibernate.orm.core;
    exports presentation_layer;
    exports domain_layer;
    exports data_layer;
}