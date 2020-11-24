import javafx.scene.control.MenuItem;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

public class Controller {
    Model model;
    public Controller(Model m){
        this.model = m;
    }

    public String readEventFile(File f) {
        return this.model.readEventFile(f);
    }

    public void initEventFile(File f) {
        model.initEventFile(f);
    }

    public ArrayList<MenuItem> getNEvents() {
        return model.getNEvents();
    }
}
