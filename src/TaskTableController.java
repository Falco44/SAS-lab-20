import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class TaskTableController extends AbstractController implements Initializable {

    private Stage stage;
    private ArrayList<File> taskFile = new ArrayList<>();
    private String move;
    private final Controller contr;

    @FXML
    private TableView<MyTask> tableView;
    @FXML
    private TableColumn<Recipe, String> cook_col;
    @FXML
    private TableColumn<Recipe, String> shift_col;
    @FXML
    private TableColumn<Recipe, String> task_col;

    public TaskTableController(Controller c){
        this.contr = c;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("init popup...");
        cook_col.setCellValueFactory(new PropertyValueFactory<>("cook"));
        shift_col.setCellValueFactory(new PropertyValueFactory<>("shift"));
        task_col.setCellValueFactory(new PropertyValueFactory<>("task"));

        try {
            File data = new File("data/");
            File event;
            File srv;
            System.out.println("CONTROLLO : length data/ " + data.listFiles().length + "\n" + Arrays.toString(data.listFiles()));
            for (int i = 0; i < data.listFiles().length - 1; i++) {
                System.out.println("CONTROLLO event : " + data.listFiles()[i].toString());
                event = data.listFiles()[i];
                if (event.isDirectory()) {
                    System.out.println("CONTROLLO serv : " + Arrays.toString(event.listFiles()));
                    for(int j = 0; j < event.listFiles().length ; j++) {
                        srv = event.listFiles()[j];
                        if(srv.isDirectory()) {
                            System.out.println("CONTROLLO file[] : " + Arrays.toString(srv.listFiles()));
                            for (int k = 0; k < srv.listFiles().length; k++) {
                                System.out.println("CONTROLLO file : " + srv.listFiles()[k]);
                                if (srv.listFiles()[k].getName().contains("task.dat")) {
                                    taskFile.add(new File(srv.getPath()));
                                    System.out.println("add");
                                }
                            }
                        }
                    }
                }
            }
        } catch (NullPointerException e) {
            System.out.println("errore nella lettura degli eventi");
            e.printStackTrace();
        }

        ArrayList<MyTask> arr = new ArrayList<>();
        for (File f : taskFile) {
            arr.addAll(contr.readTaskFile(f));
        }
        ObservableList<MyTask> obs = FXCollections.observableArrayList(arr);
        tableView.setItems(obs);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void closeStage() {
        if(stage!=null) {
            stage.close();
        }
    }
}
