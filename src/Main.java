import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

public class Main extends Application {
    private Stage primaryStage;
    public static void main(String [] args){
        System.out.println("Benvenuto in Gestire compiti cucina");

        launch(args);
        System.out.println("closing...");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Controller c = new Controller(new Model());
        GUIController guic = new GUIController(c);
        FXMLLoader l = new FXMLLoader(getClass().getResource("gui.fxml"));
        l.setController(guic);
        Parent root = l.load();
        primaryStage.setTitle("CAT&RING - Gestione Compiti");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setMinHeight(440);
        primaryStage.setMinWidth(600);
        primaryStage.show();
    }

    public Window getPrimaryStage() {
        return primaryStage;
    }
}
