import java.io.Serializable;

public class User implements Serializable {
    private final String name;
    private final boolean cook;
    private final boolean chef;
    private final boolean org;
    private final boolean srv;

    public User(String n, boolean cook, boolean chef, boolean org, boolean srv){
        this.name = n;
        this.cook = cook;
        this.chef = chef;
        this.org = org;
        this.srv = srv;
    }

    public String getName(){
        return this.name;
    }

    public boolean isCook(){
        return this.cook;
    }

    public boolean isChef(){
        return this.chef;
    }

    public boolean isOrg(){
        return this.org;
    }

    public boolean isSrv(){
        return this.srv;
    }

    @Override
    public String toString(){
        return this.name;
    }
}
