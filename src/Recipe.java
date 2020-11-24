import java.io.Serializable;

public class Recipe implements Serializable {
    /**/
    private final String name;
    private final User owner;
    private final String description;

    public Recipe(String n, User own, String desc){
        this.name = n;
        this.owner = own;
        this.description = desc;
    }

    public String getName(){
        return this.name;
    }

    public User getOwner(){
        return this.owner;
    }

    public String getDescription(){
        return this.description;
    }

    public Recipe getRec(String s){
        return this.name.equals(s) ? this : null;
    }

    @Override
    public String toString(){
        return getName()+", "+getOwner().toString()+" : \n{ "+ getDescription()+"\n}";
    }
}
