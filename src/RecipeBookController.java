import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RecipeBookController extends AbstractController implements Initializable {
    private Stage stage;
    private File bookFile = null;
    private String move;
    private final Controller contr;

    @FXML
    private TableView<Recipe> tableView;
    @FXML
    private TableColumn<Recipe, String> rec_col;
    @FXML
    private TableColumn<Recipe, String> owner_col;
    @FXML
    private TableColumn<Recipe, String> descr_col;
    @FXML
    private TableColumn<Recipe, Button> add_col;

    public RecipeBookController(Controller c){
        this.contr = c;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("init popup...");
        rec_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        owner_col.setCellValueFactory(new PropertyValueFactory<>("owner"));
        descr_col.setCellValueFactory(new PropertyValueFactory<>("description"));
        add_col.setCellFactory(ActionButtonTableCell.forTableColumn("Add",
                (r -> {int pos = tableView.getItems().indexOf(r);
                        Recipe rec = tableView.getItems().get(pos);
                        //scrivo append in event1/menu.txt
                        System.out.println("scrivero' su file "+rec);
                        contr.writeMenuItem(rec);
                        return rec;
                })));
        bookFile = new File("data/recipe_book.dat");
        ObjectOutputStream outStream = null;
        ObjectInputStream inStream = null;
        if (!(bookFile.exists())) {
            System.out.println("init file");
            try {
                outStream = new ObjectOutputStream(new FileOutputStream(this.bookFile, true));
            } catch (IOException e) {
                e.printStackTrace();
            }
            contr.initBook(outStream);
            assert outStream != null;
            try {
                outStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            inStream = new ObjectInputStream(new FileInputStream(this.bookFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Recipe> arr = new ArrayList<>(contr.readBook(inStream));
        ObservableList<Recipe> obs = FXCollections.observableArrayList(arr);
        tableView.setItems(obs);

    }

    /*private void initBook(ObjectOutputStream outs) {
        //bookFile = new File("data/recipe_book.dat");
        //User ciccio = new User("Ciccio", true, false, false, false);//nome, cook, chef, org, srv
        User aldo = new User("Aldo", true,false,false,false);
        User giulio = new User("Giulio", false,true,false,false);
        User carlo = new User("Carlo", true,false,false,false);
        writeFile(outs, new Recipe("bistecca", aldo, "scaldare la padella, sciogliere una noce di burro ecc.."));
        System.out.println("scritto");
        writeFile(outs, new Recipe("pasta", giulio, "far bollire l'acqua, salare ecc.."));
        System.out.println("scritto");
        writeFile(outs, new Recipe("tramezzini", ciccio, "tagliare il pan cassetta a triangoli, farcire ecc.."));
        System.out.println("scritto");
        writeFile(outs, new Recipe("torta salata", carlo, "stendere la sfoglia, farcire, richiudere, cuocere"));
        writeFile(outs, new Recipe("pizzette", aldo, "pizza margherita, ma piccola"));
        writeFile(outs, new Recipe("salatini", carlo, "sfoglia, ripiena a piacere tagliata a finger food"));
        System.out.println("FINE");
    }*/

    /*private void writeFile(ObjectOutputStream outs, Recipe recipe) {
        try {

            outs.writeObject(recipe);
            //outs.flush();//?

        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    /*private ArrayList<Recipe> readFile(ObjectInputStream ins){
        ArrayList<Recipe> tempRecList = new ArrayList<>();
        Object o;

        try (ins) {
            o = ins.readObject();
            while (true) {
                System.out.print("lettura");
                tempRecList.add((Recipe) o);
                System.out.print(" fatta : " + tempRecList + "\n");
                o = ins.readObject();
            }

        }catch (EOFException eof){
            //finito il file, return qui?
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return tempRecList;
    }*/

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void closeStage() {
        if(stage!=null) {
            stage.close();
        }
    }
}
