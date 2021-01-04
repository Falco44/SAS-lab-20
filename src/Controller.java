import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class Controller extends AbstractController{
    Model model;
    private File currentEvent;

    public Controller(Model m){
        this.model = m;
    }

    public void writeNotesEventFile(String s){
        File f;
        if(currentEvent.getPath().contains(".dat"))
            f = currentEvent;
        else f = new File(currentEvent.getPath()+"/info.dat");
        this.model.writeNotes(f, s);
    }

    public String readEventFile(File f) {
        if(f.getPath().contains(".dat")){
            if(currentEvent.length() == 0){
                initEventFile(currentEvent);
            }
            return this.model.readEventOrServiceFile(f);
        } else
            return this.model.readEventOrServiceFile(new File(f.getPath()+"/info.dat"));
    }

    public void initEventFile(File f) {
        model.initEventFile(f);
    }

    public ArrayList<MenuItem> getNEvents() {
        return model.getNEvents();
    }


    /*placeholder initialization, will be removed and processed in RecipeManagement UC*/
    public void initBook(ObjectOutputStream outs) {
        //bookFile = new File("data/recipe_book.dat");
        User ciccio = new User("Ciccio", true/*cook*/, false/*chef*/, false/*org*/, false/*srv*/);
        User aldo = new User("Aldo", true,false,false,false);
        User giulio = new User("Giulio", false,true,false,false);
        User carlo = new User("Carlo", true,false,false,false);
        writeBook(outs, new Recipe("bistecca", aldo, "scaldare la padella, sciogliere una noce di burro ecc.."));
        System.out.println("scritto");
        writeBook(outs, new Recipe("pasta", giulio, "far bollire l'acqua, salare ecc.."));
        System.out.println("scritto");
        writeBook(outs, new Recipe("tramezzini", ciccio, "tagliare il pan cassetta a triangoli, farcire ecc.."));
        System.out.println("scritto");
        writeBook(outs, new Recipe("torta salata", carlo, "stendere la sfoglia, farcire, richiudere, cuocere"));
        writeBook(outs, new Recipe("pizzette", aldo, "pizza margherita, ma piccola"));
        writeBook(outs, new Recipe("salatini", carlo, "sfoglia, ripiena a piacere tagliata a finger food"));
        System.out.println("FINE");
    }

    private void writeBook(ObjectOutputStream outs, Recipe r) {
        try {

            outs.writeObject(r);
            //outs.flush();//?

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void openRecipeBook() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("guiRecipeBook.fxml"));
        RecipeBookController rbc = new RecipeBookController(this);
        /*loader.setController(rbc);
        Parent layout;
        //try {
            layout = loader.load();
            Scene scene = new Scene(layout);
            // this is the popup stage
            Stage popupStage = new Stage();
            // Giving the popup controller access to the popup stage (to allow the controller to close the stage)
            rbc.setStage(popupStage);//?
            if(this.main!=null)
                popupStage.initOwner(main.getPrimaryStage());
            popupStage.initModality(Modality.WINDOW_MODAL);
            popupStage.setTitle("Recipe Book");
            popupStage.setScene(scene);
            popupStage.showAndWait();*/
        newWindow(loader, rbc, "Recipe Book");
    }

    private void newWindow (FXMLLoader load, AbstractController c, String title) throws Exception {
        load.setController(c);
        Parent layout;
        layout = load.load();
        Scene scene = new Scene(layout);
        // this is the popup stage
        Stage popupStage = new Stage();
        // Giving the popup controller access to the popup stage (to allow the controller to close the stage)
        //assert c.getClass() == RecipeBookController.class || c.getClass() == TaskTableController.class;
        //c.setStage(popupStage);//?
        if (this.main != null)
            popupStage.initOwner(main.getPrimaryStage());
        popupStage.initModality(Modality.WINDOW_MODAL);
        popupStage.setTitle(title);
        popupStage.setScene(scene);
        popupStage.showAndWait();
    }

    public ArrayList<Recipe> readBook(ObjectInputStream ins){
        ArrayList<Recipe> tempRecList = new ArrayList<>();
        Object o;

        try (ins) {
            o = ins.readObject();
            while (true) {
                //System.out.print("lettura");
                tempRecList.add((Recipe) o);
                //System.out.print(" fatta : " + tempRecList + "\n");
                o = ins.readObject();
            }

        }catch (EOFException eof){
            //finito il file correttamente
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return tempRecList;
    }

    public void writeMenuItem(Recipe r){
        this.model.writeMenuItem(r, currentEvent);
    }

    public ArrayList<MyMenuItem> readMenuFile(File dir) {
        return model.readMenuFile(dir);
    }

    public ArrayList<MyTask> readTaskFile(File dir) {
        return model.readTaskFile(dir);
    }

    public void setCurrentEvent(File f) {
        this.currentEvent = f;
    }

    public void /*MyMenuItem*/ removeRecipe(MyMenuItem s) {
        File f = new File(currentEvent.getPath()+"/menu.dat");
        /*return*/ model.removeRecipe(s, f);
    }

    public void removeTask(MyTask task) {
        File f = new File(currentEvent.getPath()+"/task.dat");
        model.removeTask(task, f);
    }

    public void updateFile(File menuFile, ObservableList<MyMenuItem> menuList) {
        model.updateFile(menuFile, menuList);
    }

    public MyMenuItem editMI(MyMenuItem item, String s) {
        File f = new File(currentEvent.getPath()+"/menu.dat");
        return model.editMI(f, item, s);
    }

    public MyTask addTask(User user, Shift shift, String day, String task) {
        File f = new File(currentEvent.getPath()+"/task.dat");
        return model.addTask(f, user, shift, day, task);
    }

    public void modTask(MyTask task) {
        File f = new File(currentEvent.getPath()+"/task.dat");
        model.modTask(f, task);
    }

    public void openTaskTable() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("guiTaskTable.fxml"));
        TaskTableController ttc = new TaskTableController(this);
        /*loader.setController(ttc);
        Parent layout;
        layout = loader.load();
        Scene scene = new Scene(layout);
        // this is the popup stage
        Stage popupStage = new Stage();
        // Giving the popup controller access to the popup stage (to allow the controller to close the stage)
        ttc.setStage(popupStage);//?
        if (this.main != null)
            popupStage.initOwner(main.getPrimaryStage());
        popupStage.initModality(Modality.WINDOW_MODAL);
        popupStage.setTitle("General Task Table");
        popupStage.setScene(scene);
        popupStage.showAndWait();*/
        newWindow(loader, ttc, "General Task Table");
    }
}
