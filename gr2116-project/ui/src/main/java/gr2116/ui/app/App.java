package gr2116.ui.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * FXML Application, the booking system application.
 */
public class App extends Application {

  /**
   * Start the app, load FXML and show scene.
   */
  @Override
  public final void start(final Stage stage) throws Exception {
    System.out.println("Running LocalApp.");
    Parent parent = FXMLLoader.load(
        getClass().getClassLoader().getResource("App.fxml"));
    stage.setScene(new Scene(parent));
    stage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("icon.png")));
    stage.show();
  }

  /**
   * Main method to launch app.
   *
   * @param args Main method needs to have these
   */
  public static void main(final String[] args) {
    launch(App.class, args);
  }
}
