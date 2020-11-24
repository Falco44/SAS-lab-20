import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GUIController extends AbstractController implements Initializable {
    private final Controller controller;

    public GUIController (Controller c){
        this.controller = c;
    }

    private String moveFrom = null;
    private String moveTo = null;

    @FXML
    private TextArea eventInfoTab;
    @FXML
    private Button menuBtn;
    @FXML
    private Button addMIbtn;
    @FXML
    private TableView<MyTask> taskTab;
    @FXML
    private TableColumn<MyTask, User> col_cook;
    @FXML
    private TableColumn<MyTask, Shift> col_shift;
    @FXML
    private TableColumn<MyTask, String> col_task;
    @FXML
    private Button addTaskbtn;
    @FXML
    private Button removeMIbtn;
    @FXML
    private ListView<String> menuInfoTab;
    @FXML
    private Button rmvTaskbtn;
    @FXML
    private Button tabellonebtn;
    @FXML
    private Button neweventbtn;
    @FXML
    private MenuButton eventmenu;

    public void buttonActionRecipeBook(ActionEvent e) {
        System.out.println("ricettario");
        openRecipeBook();
    }

    private void openRecipeBook(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("guiRecipeBook.fxml"));
        // initializing the controller
        //RecipeBookController popupController = new RecipeBookController();
        //loader.setController(popupController);
        Parent layout;
        try {
            layout = loader.load();
            Scene scene = new Scene(layout);
            // this is the popup stage
            Stage popupStage = new Stage();
            // Giving the popup controller access to the popup stage (to allow the controller to close the stage)
            //popupController.setStage(popupStage);
            if(this.main!=null) {
                popupStage.initOwner(main.getPrimaryStage());
            }
            popupStage.initModality(Modality.WINDOW_MODAL);
            popupStage.setScene(scene);
            popupStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void buttonActionAddMenuItem(ActionEvent e) { //da spostare nel recipebook
        System.out.println("add R");
    }

    public void buttonActionAddTask(ActionEvent e) {
        System.out.println("add compito");
    }

    public void buttonActionRemoveMenuIt(ActionEvent e) {
        System.out.println("rmv R");
    }

    public void selectMouseClick(MouseEvent e){
        System.out.print("select click :");
        int temp = eventmenu.getItems().indexOf(e.getTarget());
        System.out.println(eventmenu.getItems().get(temp));
    }

    /*public void onMouseClick(MouseEvent e) {
        int s = e.getPickResult().toString().indexOf("text=\"");
        int f = e.getPickResult().toString().indexOf("\",");
        moveFrom = e.getPickResult().toString().substring(s+6, f);
        System.out.println("selected : "+moveFrom);
    }*/

    /*public void onMouseSort(MouseEvent e) {
        int s = e.getPickResult().toString().indexOf("text=\"");
        int f = e.getPickResult().toString().indexOf("\",");
        moveTo = e.getPickResult().toString().substring(s+6, f);
        System.out.println("dropped on: "+moveTo);
        //sortMenu(moveFrom, moveTo);
    }*/

    /*private void sortMenu (String mvFrom, String mvTo){
        String recFrom = mvFrom.split(", ")[0];
        int qty = Integer.parseInt(mvFrom.split(", ")[1]);
        System.out.println("string FROM split : '"+ recFrom +"'\t'"+qty+"'");
        String recTo = mvTo.split(", ")[0];
        //int
        System.out.println("string TO split : '"+ recTo +"'");
        int from, to;
        //int f = menuInfoTab.getItems().indexOf(new MyMenuItem(new Recipe(recFrom), qty) );
        //System.out.println(menuInfoTab.getItems().forEach(MyMenuItem mi -> mi.getMI(recFrom)).toString().contains(recFrom));
        for (MyMenuItem mi : menuInfoTab.getItems()){
            if(mi.getRecipe().getName().equals(recFrom)){
                from = menuInfoTab.getItems().indexOf(mi);
            }
            if(mi.getRecipe().getName().equals(recTo)){
                to = menuInfoTab.getItems().indexOf(mi);
            }
        }
        menuInfoTab.
        //int t = menuInfoTab.getItems().indexOf(new MyMenuItem(new Recipe(recFrom), qty) );
        //System.out.println("prova indici "+f+" - "+t);
    }*/

    public void buttonActionRemoveTask(ActionEvent e) {
        System.out.println("rmv compito");
    }

    public void buttonActionOpenTaskTable(ActionEvent e) {
        System.out.println("tabellone turni");
    }

    public void listEvent(ContextMenuEvent e) {
        System.out.println("list event");
    }

    public void buttonActionNewEvent(ActionEvent e) {
        System.out.println("new event");
        eventInfoTab.clear();
        eventInfoTab.setEditable(true);

        //menuInfoTab. clear()
        //taskTab. clear()
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("init..");

        eventmenu.getItems().addAll(controller.getNEvents());

        File eventFile = new File("data/event1/event-info.txt");//controller.getPath("event")
        if(!eventFile.exists()){
            controller.initEventFile(eventFile); //da spostare nel model + rimbalzo controller prima
        }
        File menuFile = new File("data/event1/menu.txt");//controller.getPath("menu")
        File taskFile = new File("data/event1/task.dat");//controller.getPath("task")

        eventInfoTab.setText(controller.readEventFile(eventFile)); //da spostare nel model + rimbalzo controller prima

        User aldo = new User("Aldo", true/*cook*/, false/*chef*/, false/*org*/, false/*srv*/);
        Shift s1 = new Shift(9, 12);
        Shift s2 = new Shift(12, 15);
        Recipe r = new Recipe("pasta", new User("Ciccio", true/*cook*/, false/*chef*/, false/*org*/, false/*srv*/), "far bollire l'acqua, salare ecc..");
        Recipe b = new Recipe("bistecca", new User("Carlo", true/*cook*/, false/*chef*/, false/*org*/, false/*srv*/), "scaldare la padella, sciogliere una noce di burro ecc..");
        //MyMenuItem mi = new MyMenuItem(r, 50);
        ArrayList<MyTask> taskList = new ArrayList<>();//data/event1/task.dat
        taskList.add(new MyTask(aldo, s1, r, 50));
        taskList.add(new MyTask(aldo, s2, b, 20));
        /*for(MyTask a : taskList){
            System.out.println(a.toString());
        }*/
        ObservableList<MyTask> obsTask = FXCollections.observableArrayList(taskList);

        col_cook.setCellValueFactory(new PropertyValueFactory<>("cook"));
        col_shift.setCellValueFactory(new PropertyValueFactory<>("shift"));
        col_task.setCellValueFactory(new PropertyValueFactory<>("task"));
        taskTab.setItems(obsTask);

        ArrayList<String> menuList = new ArrayList<>();//data/event1/menu.txt
        menuList.add(new MyMenuItem(r, 20).toString());
        menuList.add(new MyMenuItem(b, 20).toString());
        /*for(String a : menuList){
            System.out.println("list : "+a);
        }*/
        ObservableList<String> obsMenu = FXCollections.observableArrayList(menuList);
        //DataFormat df = new DataFormat("MyMenuItem");
        //System.out.println("dataf : "+df);
        menuInfoTab.setCellFactory(param -> new MenuCell());
        menuInfoTab.setItems(obsMenu);
    }

}
