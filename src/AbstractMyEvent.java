import java.io.Serializable;

public abstract class AbstractMyEvent implements Serializable {
    String notes = "";
    public void setNotes(String s){
        this.notes = notes.concat(s);
    }
}
