package hotel.ui;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.api.FxAssert;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import gr2116.ui.controller.AppController;

public class HotelIntegrtionTest extends ApplicationTest {

    AppController appController;

    @Start
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("App.fxml"));
        Parent parent = fxmlLoader.load();
        appController = (AppController) fxmlLoader.getController();
        appController.setPrefix("test");
        appController.load();
        stage.setScene(new Scene(parent));
        stage.show();
    }
  

}

