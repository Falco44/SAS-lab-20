import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class MyEvent extends AbstractMyEvent /*implements Serializable*/ {
    private final String name;
    private final String committee;
    private final LocalDateTime start;
    private final LocalDateTime end;
    private Periodical periodical;
    private ArrayList<MyEvent> otherOfPeriod = new ArrayList<>();
    private final String location;
    private final int nOfService;
    private final String[] typeOfService;
    private final int nOfClients;
    //private final String notes;
    private User chef;

    public enum Periodical{
        UNIQUE,
        WEEKLY,
        MONTHLY,
        YEARLY
    }


    public MyEvent(String name, String committee, LocalDateTime start, LocalDateTime end, Periodical periodical, int rep, String location, int nOfService, String[] typeOfService,
                   int nOfClients, String notes) {
        this.name = name;
        this.committee = committee;
        this.start = start;
        this.end = end;
        setPeriodical(periodical, rep);
        this.location = location;
        this.nOfService = nOfService;
        this.typeOfService = typeOfService;
        this.nOfClients = nOfClients;
        this.notes = notes;
    }

    //public MyEvent(LocalDateTime start, LocalDateTime end, Shift[] shifts, String descr, String notes)

    public void setChef(User u){
        this.chef = u;
    }

    public void setPeriodical(Periodical p, int repetitions){
        this.periodical = p;
        MyEvent e;
        for(int i = 1; i<=repetitions; i++) {
            switch (p) {
                case WEEKLY -> {
                    e = new MyEvent(this.name, this.committee, this.start.plusWeeks(i), this.end.plusWeeks(i), p, repetitions, this.location, this.nOfService, this.typeOfService,
                            this.nOfClients, this.notes);
                }
                case MONTHLY -> {
                        e = new MyEvent(this.name, this.committee, this.start.plusMonths(i), this.end.plusMonths(i), p, repetitions, this.location, this.nOfService, this.typeOfService,
                                this.nOfClients, this.notes);
                }
                case YEARLY -> {
                        e = new MyEvent(this.name, this.committee, this.start.plusYears(i), this.end.plusYears(i), p, repetitions, this.location, this.nOfService, this.typeOfService,
                                this.nOfClients, this.notes);
                }
                case UNIQUE -> e = this;
                default -> throw new IllegalStateException("Unexpected value: " + p);
            }
            this.otherOfPeriod.add(e);
        }
    }

    /*public void setNotes(String s){
        this.notes = notes.concat(s);
    }*/

    public String toString(){
        int l;
        return "evento "+name+"\n"
                +"ordinante: "+committee+"\n"
                +"data inizio : "+ start.format(DateTimeFormatter.ISO_DATE)+"\n"
                +"data fine : "+end.format(DateTimeFormatter.ISO_DATE)+"\n"
                +"periodicita' : "+periodical.toString().toLowerCase()+" event\n" + ((l = otherOfPeriod.size()) != 1 ? l : "")
                +"luogo : "+location+"\n"
                +"numero di servizi : "+nOfService+"\n"
                +"tipologia servizi : "+ Arrays.toString(typeOfService) +"\n"
                +"numero ospiti : "+nOfClients+"\n"
                +"chef assegnato : "+chef+"\n"
                +"note : "+notes+"\n   FINE" ;
    }

}
