import javafx.beans.value.ObservableListValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class GUIController extends AbstractController implements Initializable {
    private final Controller controller;

    public GUIController (Controller c){
        this.controller = c;
    }

    /*private String moveFrom = null;
    private String moveTo = null;*/

    @FXML
    private TextArea eventInfoTab;
    @FXML
    private Button menuBtn;
    @FXML
    private Button editMIbtn;
    @FXML
    private TableView<MyTask> taskTab;
    @FXML
    private TableColumn<MyTask, User> col_cook;
    @FXML
    private TableColumn<MyTask, Shift> col_shift;
    @FXML
    private TableColumn<MyTask, String> col_day;
    @FXML
    private TableColumn<MyTask, String> col_task;
    @FXML
    private Button addTaskbtn;
    @FXML
    private Button removeMIbtn;
    @FXML
    private ListView<MyMenuItem> menuInfoTab;
    @FXML
    private Button rmvTaskbtn;
    @FXML
    private Button tabellonebtn;
    @FXML
    private Button addnotebtn;
    @FXML
    private TextArea addnotetab;
    @FXML
    private MenuButton eventmenu;
    @FXML
    private TextField editMI;
    @FXML
    private TableView<MyTask> addtaskpane;
    @FXML
    private TableColumn<MyTask, User> add_cook_col;
    @FXML
    private TableColumn<MyTask, Shift> add_shift_col;
    @FXML
    private TableColumn<MyTask, String> add_day_col;
    @FXML
    private TableColumn<MyTask, String> add_task_col;

    private File currentEvent;

    public void buttonActionRecipeBook(ActionEvent e) throws Exception {
        System.out.println("ricettario");
        controller.openRecipeBook();
    }

    /*private void openRecipeBook() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("guiRecipeBook.fxml"));
        RecipeBookController rbc = new RecipeBookController(controller);
        loader.setController(rbc);
        Parent layout;
        //try {
        layout = loader.load();
        Scene scene = new Scene(layout);
        // this is the popup stage
        Stage popupStage = new Stage();
        // Giving the popup controller access to the popup stage (to allow the controller to close the stage)
        //rbc.setStage(popupStage);//?
        if(this.main!=null)
            popupStage.initOwner(main.getPrimaryStage());
        popupStage.initModality(Modality.WINDOW_MODAL);
        popupStage.setTitle("Recipe Book");
        popupStage.setScene(scene);
        popupStage.showAndWait();
        /*} catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    public void buttonActionEditMenuItem(ActionEvent e) {
        System.out.println("edit R");
        if(editMIbtn.getText().equals("Modifica")) {
            editMI.setVisible(true);
            editMIbtn.setText("Salva");
        } else if (editMIbtn.getText().equals("Salva")){
            MyMenuItem item = menuInfoTab.getSelectionModel().selectedItemProperty().getValue();
            String s = editMI.getText();
            MyMenuItem mi = controller.editMI(item, s);
            int i = menuInfoTab.getItems().indexOf(item);
            menuInfoTab.getItems().remove(item); //raw update view
            menuInfoTab.getItems().add(i, mi); //raw update view
            //update by file??
            editMIbtn.setText("Modifica");
            editMI.clear();
            editMI.setVisible(false);
        }
    }

    public void buttonActionAddTask(ActionEvent e) {
        System.out.println("add compito");
        if(addTaskbtn.getText().equals("+")) {
            addtaskpane.setVisible(true);
            addtaskpane.setItems(FXCollections.observableArrayList(new ArrayList<>(Collections.singletonList(new MyTask(
                    new User("cuoco", false, false, false, false), new Shift(0, 0), "2000-01-01", "compito"))) ) );
            addTaskbtn.setText("salva");
        } else if(addTaskbtn.getText().equals("salva")){
            User user = new User(add_cook_col.getCellData(0).toString(), true, false, false, false);
            Shift shift = new Shift(Integer.parseInt(add_shift_col.getCellData(0).toString().split("-")[0].strip()) ,
                                        Integer.parseInt(add_shift_col.getCellData(0).toString().split("-")[1].strip()) );
            String day = add_day_col.getCellData(0);
            String task = add_task_col.getCellData(0);
            MyTask t = controller.addTask(user, shift, day, task);
            addtaskpane.setVisible(false);
            addtaskpane.getItems().clear();
            addTaskbtn.setText("+");
            taskTab.getItems().add(t);//raw update view
            //update by file??
        }
    }

    public void buttonActionRemoveMenuIt(ActionEvent e) {
        System.out.println("rmv R");
        MyMenuItem s = menuInfoTab.getSelectionModel().selectedItemProperty().getValue();
        System.out.print(" elemento selez : '"+s.toString()+"'");
        MyMenuItem  mi = controller.removeRecipe(s);
        System.out.print("\nelemento ritornato : '"+mi.toString()+"'");
        menuInfoTab.getItems().remove(mi); //raw update view    non va
        //update by file?
    }

    /*public void selectMouseClick(MouseEvent e){ //per ora non lo invoca
        System.out.print("select click :");

        int temp = eventmenu.getItems().indexOf(e.getTarget());
        System.out.println(eventmenu.getItems().get(temp));
        //da finire
        /*
        * get selected item
        * show/update eventInfoTab
        * *
    }*/

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
        MyTask task = taskTab.getSelectionModel().selectedItemProperty().getValue();
        controller.removeTask(task);
        taskTab.getItems().remove(task); //update view
    }

    public void buttonActionOpenTaskTable(ActionEvent e) throws Exception {
        System.out.println("tabellone turni");
        controller.openTaskTable();
    }

    public void buttonActionAddNote(ActionEvent e) {
        System.out.println("note/save pressed");
        if(addnotebtn.getText().equals("Note")) {
            System.out.println("add note");
            addnotetab.setVisible(true);
            addnotebtn.setText("Salva");
        } else if(addnotebtn.getText().equals("Salva")){
            System.out.println("save note");
            addnotetab.setVisible(false);
            addnotebtn.setText("Note");
            //File f = new File("data/"+eventmenu.getText());//o nel controller
            controller.writeNotesEventFile(addnotetab.getText());
            addnotetab.clear();
            eventInfoTab.setText(controller.readEventFile(new File("data/"+eventmenu.getText())) ); //update view
        }
    }

    public void updateMenu(){//update view      ??con binding forse non serve
        /* prende info dalla tab menu e aggiorna il file (per l'ordine)*/
        System.out.println("CONTROLLO : menu file '"+eventmenu.getText()+"'");
        File menuFile = new File("data/"+eventmenu.getText()+"/menu.dat");
        ObservableList<MyMenuItem> menuList = menuInfoTab.getItems();
        System.out.println("CONTROLLO : lista {\n"+menuList+"\n\n}");

        controller.updateFile(menuFile, menuList);
    }

    private void initializeMenuButtonHandler(){
        AtomicReference<File> f = new AtomicReference<>();
        AtomicReference<File> dir = new AtomicReference<>();
        EventHandler<ActionEvent> myHandl = h -> { if ( ((MenuItem)h.getSource()).getText().contains(".txt") || ((MenuItem)h.getSource()).getText().contains(".dat") ) {
                                                        f.set(new File("data/"+((MenuItem) h.getSource()).getText()));
                                                        System.out.println(f.get().getPath() + " selected in if");
                                                        menuInfoTab.getItems().clear();
                                                        taskTab.getItems().clear();
                                                        controller.setCurrentEvent(f.get());
                                                        dir.set(f.get());
                                                    }else {
                                                            dir.set(new File("data/" + ((MenuItem) h.getSource()).getText()));
                                                            f.set(new File(dir.get().getPath() + "/info.dat"));
                                                            System.out.println(f.get().getPath() + " selected in else");
                                                            menuInfoTab.getItems().clear();
                                                            menuInfoTab.setItems(FXCollections.observableArrayList(controller.readMenuFile(dir.get())));//funziona, ma non bindata
                                                            //bind???
                                                            //System.out.println("CONTROLLO: item menu: '"+menuInfoTab.getItems().size()+"'");
                                                            taskTab.setItems(FXCollections.observableArrayList(controller.readTaskFile(dir.get())));//funziona, ma non bindata
                                                            //bind???
                                                            controller.setCurrentEvent(dir.get());
                                                    }
                                                   eventInfoTab.setText(controller.readEventFile(dir.get()) );

                                                   eventmenu.setText(dir.get().getPath().substring(5));
                                                };
        eventmenu.getItems().forEach(item -> item.setOnAction(myHandl));
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("init..");

        File eventFile = new File("data/event1/event-info.dat");
        controller.setCurrentEvent(eventFile);
        eventmenu.setText(eventFile.getPath().substring(5));
        /* MOVED to controller
        if(!eventFile.exists()){
            controller.initEventFile(eventFile);
        }*/
        //File menuFile = new File("data/event1/menu.dat");//controller.getPath("menu")
        //File taskFile = new File("data/event1/task.dat");//controller.getPath("task")

        eventmenu.getItems().addAll(controller.getNEvents());
        initializeMenuButtonHandler();

        eventInfoTab.setText(controller.readEventFile(eventFile));

        /*Taskpane*/
        col_cook.setCellValueFactory(new PropertyValueFactory<>("cook"));
        col_shift.setCellValueFactory(new PropertyValueFactory<>("shift"));
        col_day.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_task.setCellValueFactory(new PropertyValueFactory<>("task"));
        //taskTab.setItems(obsTask);

        /*add new task*/
        add_cook_col.setCellValueFactory(new PropertyValueFactory<>("cook"));
        add_cook_col.setCellFactory(TextFieldTableCell.<MyTask, User>forTableColumn(new StringConverter<User>() {
            @Override
            public String toString(User user) {
                return user.toString();
            }

            @Override
            public User fromString(String s) {
                return new User(s, false,false,false,false);
            }
        }));
        add_cook_col.setOnEditCommit(
                t -> ((MyTask) t.getTableView().getItems().get(t.getTablePosition().getRow()) ).
                        setCook(t.getNewValue())
        );
        add_shift_col.setCellValueFactory(new PropertyValueFactory<>("shift"));
        add_shift_col.setCellFactory(TextFieldTableCell.<MyTask, Shift>forTableColumn(new StringConverter<Shift>() {
            @Override
            public String toString(Shift shift) {
                return shift.toString();
            }

            @Override
            public Shift fromString(String s) {
                return new Shift(Integer.parseInt(s.split("-")[0].strip()) , Integer.parseInt(s.split("-")[1].strip()));
            }
        }));
        add_shift_col.setOnEditCommit(
                t -> ((MyTask) t.getTableView().getItems().get(t.getTablePosition().getRow()) ).
                        setShift(t.getNewValue())
        );
        add_day_col.setCellValueFactory(new PropertyValueFactory<>("date"));
        add_day_col.setCellFactory(TextFieldTableCell.<MyTask>forTableColumn());
        add_day_col.setOnEditCommit(
                t -> ((MyTask) t.getTableView().getItems().get(t.getTablePosition().getRow()) ).
                        setDate(t.getNewValue())
        );
        add_task_col.setCellValueFactory(new PropertyValueFactory<>("task"));
        add_task_col.setCellFactory(TextFieldTableCell.<MyTask>forTableColumn());
        add_task_col.setOnEditCommit(
                t -> ((MyTask) t.getTableView().getItems().get(t.getTablePosition().getRow()) ).
                        setTask(t.getNewValue())
        );

        //ArrayList<String> menuList = new ArrayList<>();//data/event1/menu.txt
        //menuList.add(new MyMenuItem(r, 20).toString());
        //menuList.add(new MyMenuItem(b, 20).toString());
        /*for(String a : menuList){
            System.out.println("list : "+a);
        }*/
        //ObservableList<String> obsMenu = FXCollections.observableArrayList(menuList);
        //DataFormat df = new DataFormat("MyMenuItem");
        //System.out.println("dataf : "+df);
        menuInfoTab.setCellFactory(param -> new MenuCell(this));
        //menuInfoTab.setItems(obsMenu);
    }

}
