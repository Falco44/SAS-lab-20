import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class MyService extends AbstractMyEvent /*implements Serializable*/ {
    private final LocalDateTime start;
    private final LocalDateTime end;
    private final Shift[] shifts;
    private final String description;
    //private final String notes;

    public MyService(LocalDateTime start, LocalDateTime end, Shift[] shifts, String descr, String notes){
        this.start = start;
        this.end = end;
        this.shifts = shifts;
        this.description = descr;
        this.notes = notes;
    }

    @Override
    public String toString(){
        return "data inizio : "+ start.format(DateTimeFormatter.ofPattern("yyyy MM dd , kk:mm"))+"\n"
                +"data fine : "+end.format(DateTimeFormatter.ofPattern("yyyy MM dd , kk:mm"))+"\n"
                +"turni interessati : "+ Arrays.toString(shifts)+"\n"
                +"descrizione : "+description
                +"\nnote : "+notes+"\n   FINE" ;
    }
}
