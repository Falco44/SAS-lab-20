public class Shift {
    private final int startH;
    private final int endH;

    public Shift (int s, int e){
        this.startH = s;
        this.endH = e;
    }

    @Override
    public String toString(){
        return this.startH+" - "+this.endH;
    }
}
