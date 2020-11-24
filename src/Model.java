import javafx.scene.control.MenuItem;

import java.io.*;
import java.util.ArrayList;

public class Model {


    public void initEventFile(File f) {

    }

    public String readEventFile(File f) { //da spostare nel model + rimbalzo controller prima
        StringBuilder s = new StringBuilder();
        try (ObjectInputStream ins = new ObjectInputStream(new FileInputStream(f))) {
            while (true) {
                if (s.length() == 0)
                    s = new StringBuilder(ins.toString());
                else s.append(ins.toString());
            }
        }catch (EOFException eof) {
            //eof reached
        } catch (IOException e) {
            e.printStackTrace();
        } return s.toString();
    }

    public ArrayList<MenuItem> getNEvents() {
        ArrayList<MenuItem> mi = new ArrayList<>();
        File f = new File("data/");
        for(int i=0; i < f.listFiles().length; i++){
            if(f.listFiles()[i].isDirectory())
                mi.add(new MenuItem(f.listFiles()[i].getName()));
        } return mi;
    }
}
