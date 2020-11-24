import java.io.Serializable;

public class MyMenuItem implements Serializable {
    private int qty;
    private Recipe recipe = null;
    private Preparation prep = null;

    public MyMenuItem(Recipe r, int qty){
        this.recipe = r;
        this.qty = qty;
    }

    public MyMenuItem(Preparation p, int qty){
        this.prep = p;
        this.qty = qty;
    }

    public MyMenuItem getMI(String s){
        return this.recipe.getName().equals(s) ? this : null;
    }

    public Recipe getRecipe(){
        return this.recipe;
    }

    @Override
    public String toString(){

        return recipe.toString()+", "+qty;
    }
}
