public class MyTask {
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

    public User getCook(){
        return this.cook;
    }

    public Shift getShift(){
        return this.shift;
    }

    public String getTask(){
        return this.task;
    }

    public String toString(){
        //return cook.toString()+" "+shift.toString()+" "+mi.toString();
        return cook.toString()+" "+shift.toString()+" "+task;
    }
}
