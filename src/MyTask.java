import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MyTask implements Serializable {

    private User cook;
    private Shift shift;
    private LocalDate date;
    private String task;

    /* Cook, Shift, Prep/Recipe, qty/portions */
    public MyTask(User cook, Shift s, String day, Recipe r, /*MyMenuItem mi,*/ int qty){
        this.cook = cook;
        this.shift = s;
        this.date = LocalDate.parse(day);
        //this.mi = mi;
        this.task = new MyMenuItem(r, qty).toString();
        //this.qty = qty;
    }

    public MyTask(User user, Shift s, String day, String t) {
        this.cook = user;
        this.shift = s;
        this.date = LocalDate.parse(day);
        this.task = t;
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

    /*public LocalDate getDate(){
        return this.date;
    }*/

    public String getDate() {
        return this.date.toString();
    }

    public void setDate(String day){
        this.date = LocalDate.parse(day);
    }

    public String getTask(){
        return this.task;
    }

    public void setTask(String newTask){
        this.task = newTask;
    }

    public String toString(){
        return cook.toString()+" "+shift.toString()+" "+date.toString()+" "+task;
    }

}
