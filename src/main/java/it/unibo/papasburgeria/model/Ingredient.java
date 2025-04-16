package it.unibo.papasburgeria.model;


public interface Ingredient {

    /*
     * @return The type of ingredient
     */
    public IngredientEnum getIngredientType();

    /*
     * @param a double value representing how accurately the ingredient was placed (in %)
     */
    public void setPlacementAccuracy(double accuracy);

    /*
     * @return a double value representing how accurately the ingredient was placed (in %)
     */
    public double getPlacementAccuracy();

    /*
     * @return a string with the ingredient and their current status
     */
    public String toString();
}