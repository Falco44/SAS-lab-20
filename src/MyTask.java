import java.io.Serializable;

public class MyTask implements Serializable {
    /* Cook, Shift, Prep/Recipe, qty/portions */
    private User cook;
    private Shift shift;
    private MyMenuItem mi;
    private Recipe rec;
    private int qty;
    private String task;

    public MyTask(User cook, Shift s, Recipe r, /*MyMenuItem mi,*/ int qty){
        this.cook = cook;
        this.shift = s;
        //this.mi = mi;
        this.task = new MyMenuItem(r, qty).toString();
        //this.qty = qty;
    }

    public MyTask(User user, Shift shift, String task) {
        this.cook = user;
        this.shift = shift;
        this.task = task;
    }

    public User getCook(){
        return this.cook;
    }

    public void setCook(User newCook) {
        this.cook = newCook;
    }

    public Shift getShift(){
        return this.shift;
    }

    public void setShift(Shift newShift) {
        this.shift = newShift;
    }

    public String getTask(){
        return this.task;
    }

    public void setTask(String newTask){
        this.task = newTask;
    }

    public String toString(){
        //return cook.toString()+" "+shift.toString()+" "+mi.toString();
        return cook.toString()+" "+shift.toString()+" "+task;
    }

}
