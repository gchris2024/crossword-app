module csci205_final_project {
    requires java.sql;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    exports org.ChrisMeiersMollyNhi;
    exports org.ChrisMeiersMollyNhi.Controllers;

    requires okhttp3;            // For making HTTP requests with OkHttp
    requires com.google.gson;
    requires java.net.http;
    requires jdk.jconsole;
}