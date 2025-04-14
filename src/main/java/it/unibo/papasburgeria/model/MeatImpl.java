package it.unibo.papasburgeria.model;

public class MeatImpl extends IngredientImpl{

    private double cookLevel;

    MeatImpl(IngredientEnum type) {
        super(type);
        cookLevel = 0;
    }
    
    public double getCookLevel(){
        return cookLevel;
    }

    public void setCookLevel(double cookLevel){
        this.cookLevel = cookLevel;
    }
}
