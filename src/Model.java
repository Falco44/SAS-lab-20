import javafx.collections.ObservableList;
import javafx.scene.control.MenuItem;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public class Model {

    private AbstractMyEvent event;

    //ObjectOutputStream os;//uno stream per file da usare piu volte -> reset?

    /*placeholder initialization, will be removed and processed in EventManagement UC*/
    public void initEventFile(File f) {
        try {
            ObjectOutputStream outs = new ObjectOutputStream(new FileOutputStream(f));
            //MyEvent(String name, String committee, GregorianCalendar start,
            //                  GregorianCalendar end, Periodical periodical,
            //                  String location, int nOfService, String[] typeOfService,
            //                  int nOfClients, String notes)
            outs.writeObject(new MyEvent (f.getName(), "Ditta srl", LocalDateTime.of(2020, 12, 20, 0, 0),
                             LocalDateTime.of(2020, 12, 22, 0, 0), MyEvent.Periodical.UNIQUE, 1,
                             "Torino, corso Regina M., 345/a", 3, new String[]{"aperitivo", "pranzo", "coffe-break"},
                             50, "") );
            //outs.flush();

            outs.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readEventOrServiceFile(File f) {
        String s = "";
        if(!f.exists())
            initServiceFile(f);
        try (ObjectInputStream ins = new ObjectInputStream(new FileInputStream(f))) {

            s = (event = (AbstractMyEvent) ins.readObject()).toString();//o farlo splittato in 2 casi sul file

        //}catch (EOFException eof) {
            ///?
        } catch (IOException | ClassNotFoundException | NullPointerException e) {
            e.printStackTrace();
        }
        return s;
    }

    /*placeholder initialization, will be removed and processed in EventManagement UC*/
    private void initServiceFile(File f) {
        Shift s1 = new Shift(9, 12);
        Shift s2 = new Shift(12, 15);
        try {
            ObjectOutputStream outs = new ObjectOutputStream(new FileOutputStream(f));

            outs.writeObject(new MyService(LocalDateTime.of(2020, 12, 20, 15, 0), LocalDateTime.of(2020, 12, 20, 18, 0),
                        new Shift[]{s1, s2},"placeholder description for service", "notes about service") );

            outs.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<MenuItem> getNEvents() {
        ArrayList<MenuItem> mi = new ArrayList<>();
        try {
            File f = new File("data/");
            File service = new File("data/");
            System.out.println("CONTROLLO : length data/ "+f.listFiles().length+"\n"+ Arrays.toString(f.listFiles()));
            for (int i = 0; i < f.listFiles().length -1; i++) {
                System.out.println("CONTROLLO : "+f.listFiles()[i].toString());
                if (f.listFiles()[i].isDirectory()){
                    System.out.println("CONTROLLO : "+ Arrays.toString(f.listFiles()[i].listFiles()));
                    for(int j= 0; j < f.listFiles()[i].listFiles().length ; j++) {
                        service = f.listFiles()[i].listFiles()[j];
                        mi.add(new MenuItem(f.listFiles()[i].getName() + "/" +service.getName()));
                    }
                }
            }
            //return mi;
        }catch (NullPointerException e){
            System.out.println("errore nella lettura degli eventi");
            e.printStackTrace();
        } return mi;
    }

    public void writeMenuItem(Recipe r, File f) {
        ArrayList<MyMenuItem> al = new ArrayList<>();
        File menu = new File(f.getPath() + "/menu.dat");
        System.out.println("CONTROLLO : file menu '"+menu.getPath()+"'");
        try (ObjectInputStream ins = new ObjectInputStream(new FileInputStream(menu))) {

            while (true)
                al.add((MyMenuItem) ins.readObject());
        }catch (EOFException eof){
            System.out.println("lettura finita");
            try {
                ObjectOutputStream outs = new ObjectOutputStream(new FileOutputStream(menu));

                al.add(new MyMenuItem(r, 0));
                for(MyMenuItem mi : al){
                    System.out.println("item: "+mi.toString());
                    outs.writeObject(mi);
                }
                outs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("scrittura finita");
    }

        public void writeNotes(File f, String s) {
        //MyEvent event;
        try (ObjectInputStream ins = new ObjectInputStream(new FileInputStream(f))) {
            //ObjectInputStream ins = new ObjectInputStream(new FileInputStream(f));
            ObjectOutputStream outs = new ObjectOutputStream(new FileOutputStream(f));

            //event = (MyEvent) ins.readObject();
            event.setNotes(s);
            outs.writeObject(event);

            outs.flush();

            outs.close();
            //ins.close();

            //ins = new ObjectInputStream(new FileInputStream(f));

            System.out.println("controllo: "+((AbstractMyEvent) ins.readObject()).toString()+"\n FINE CONTROLLO");
            ins.close();
        } catch (IOException | ClassNotFoundException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<MyMenuItem> readMenuFile(File dir) {
        ArrayList<MyMenuItem> ret = new ArrayList<>();
        Object o;
        File f = new File(dir.getPath()+"/menu.dat");

        if(!f.exists())
            initMenuFile(f);
        try (ObjectInputStream ins = new ObjectInputStream(new FileInputStream(f))) {
            o = ins.readObject();
            while (true) {
                //System.out.print("lettura");
                ret.add((MyMenuItem) o );
                //System.out.print(" fatta : " + tempRecList + "\n");
                o = ins.readObject();
            }

        }catch (EOFException eof){
            //finito il file correttamente
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        //ret = (ObservableListValue<MyMenuItem>) FXCollections.observableArrayList(al);
        return ret;
    }

    /*placeholder initialization, will be removed and processed in EventManagement UC*/
    private void initMenuFile(File f) {
        System.out.println("init menu..");
        ArrayList<MyMenuItem> al = new ArrayList<>();
        User ciccio = new User("Ciccio", true/*cook*/, false/*chef*/, false/*org*/, false/*srv*/);
        User carlo = new User("Carlo", true/*cook*/, false/*chef*/, false/*org*/, false/*srv*/);
        Recipe r = new Recipe("pasta", ciccio, "far bollire l'acqua, salare ecc..");
        Recipe b = new Recipe("bistecca", carlo, "scaldare la padella, sciogliere una noce di burro ecc..");
        al.add(new MyMenuItem(r, 20));
        al.add(new MyMenuItem(b, 10));

        try (ObjectOutputStream ins = new ObjectOutputStream(new FileOutputStream(f))) {
            for(MyMenuItem mi : al){
                ins.writeObject(mi);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<MyTask> readTaskFile(File dir) {
        ArrayList<MyTask> al = new ArrayList<>();
        Object o;
        File f = new File(dir.getPath()+"/task.dat");

        if(!f.exists())
            initTaskFile(f);
        try (ObjectInputStream ins = new ObjectInputStream(new FileInputStream(f))) {
            o = ins.readObject();
            while (true) {
                //System.out.print("lettura");
                al.add((MyTask) o);
                //System.out.print(" fatta : " + tempRecList + "\n");
                o = ins.readObject();
            }

        }catch (EOFException eof){
            //finito il file correttamente
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return al;
    }

    /*placeholder initialization, will be removed and processed in EventManagement or KitchenManagement UC*/
    private void initTaskFile(File f) {
        System.out.println("init task..");
        ArrayList<MyTask> al = new ArrayList<>();
        Shift s1 = new Shift(9, 12);
        Shift s2 = new Shift(12, 15);
        User ciccio = new User("Ciccio", true/*cook*/, false/*chef*/, false/*org*/, false/*srv*/);
        User carlo = new User("Carlo", true/*cook*/, false/*chef*/, false/*org*/, false/*srv*/);
        Recipe r = new Recipe("pasta", ciccio, "far bollire l'acqua, salare ecc..");
        Recipe b = new Recipe("bistecca", carlo, "scaldare la padella, sciogliere una noce di burro ecc..");
        al.add(new MyTask(ciccio, s1, r, 10));
        al.add(new MyTask(carlo, s2, b, 5));

        try (ObjectOutputStream ins = new ObjectOutputStream(new FileOutputStream(f))) {
            for(MyTask mt : al){
                ins.writeObject(mt);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MyMenuItem removeRecipe(MyMenuItem mi, File f) { //controllare il tipo all'interno del file
        ArrayList<MyMenuItem> mlist = new ArrayList<>();
        int idx = 0;
        MyMenuItem ret = null;

        try(ObjectInputStream ins = new ObjectInputStream(new FileInputStream(f))) {
            while (true)
                mlist.add((MyMenuItem) ins.readObject());
        }catch (EOFException eof){
            for(MyMenuItem m : mlist){
                if(m.toString().equals(mi.toString()))
                    idx = mlist.indexOf(m);
            }
            //idx = searchIndexMI(f, mlist, mi);
            ret = mlist.get(idx);
            mlist.remove(idx);
            try(ObjectOutputStream outs = new ObjectOutputStream(new FileOutputStream(f))){
                for (MyMenuItem m : mlist)
                    outs.writeObject(m);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public MyMenuItem editMI(File f, MyMenuItem item, String s){
        ArrayList<MyMenuItem> mlist = new ArrayList<>();
        int idx = 0;
        MyMenuItem ret = null;

        try(ObjectInputStream ins = new ObjectInputStream(new FileInputStream(f))) {
            while (true)
                mlist.add((MyMenuItem) ins.readObject());
        }catch (EOFException eof){
            for(MyMenuItem m : mlist){
                if(m.toString().equals(item.toString()))
                    idx = mlist.indexOf(m);
            }
            ret = mlist.get(idx);
            ret.setQty(Integer.parseInt(s));
            mlist.remove(idx);
            mlist.add(idx, ret);
            try(ObjectOutputStream outs = new ObjectOutputStream(new FileOutputStream(f))){
                for (MyMenuItem m : mlist)
                    outs.writeObject(m);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return ret;
    }

    /*public int searchIndexMI(File f, ArrayList<MyMenuItem> list, String mi){
        ArrayList<MyMenuItem> mlist = new ArrayList<>();
        int idx = 0;
        //MyMenuItem ret = null;
        try(ObjectInputStream ins = new ObjectInputStream(new FileInputStream(f))) {
            while (true)
                mlist.add((MyMenuItem) ins.readObject());
        }catch (EOFException eof) {
            for (MyMenuItem m : list) {
                if (m.toString().equals(mi))
                    idx = list.indexOf(m);
            }
        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return idx;
    }*/

    //public void writeMenuFile() //da estrarre da tutti?? e per tutti i file??s

    public void removeTask(MyTask task, File f) {
        System.out.println("CONTROLLO : file : '"+f.getPath()+"'");
        ArrayList<MyTask> tlist = new ArrayList<>();
        try(ObjectInputStream ins = new ObjectInputStream(new FileInputStream(f))) {
            while (true)
                tlist.add((MyTask) ins.readObject());
        }catch (EOFException eof){

            tlist.removeIf(t -> t.toString().equals(task.toString()));
            try(ObjectOutputStream outs = new ObjectOutputStream(new FileOutputStream(f))){
                for(MyTask t : tlist)
                    outs.writeObject(t);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void updateFile(File menuFile,  ObservableList<MyMenuItem> menuList) {
        System.out.print("updating menu file...");
        try (ObjectOutputStream menuOOS = new ObjectOutputStream(new FileOutputStream(menuFile)) ){

            //salvo errori di tipi nei file
            for(MyMenuItem m : menuList)
                menuOOS.writeObject(m);

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("DONE");
    }

    public MyTask addTask(File f, User user, Shift shift, String task) {
        System.out.print("updating task file...");
        ArrayList<MyTask> tlist = new ArrayList<>();
        MyTask t = new MyTask(user, shift, task);
        try (ObjectInputStream taskOIS = new ObjectInputStream(new FileInputStream(f))) {
            while (true)
                tlist.add((MyTask) taskOIS.readObject());
        }catch (EOFException eof){
            tlist.add(t);
            try (ObjectOutputStream taskOOS = new ObjectOutputStream(new FileOutputStream(f)) ){
                for(MyTask mt : tlist)
                    taskOOS.writeObject(mt);

            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("DONE");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return t;
    }
}
