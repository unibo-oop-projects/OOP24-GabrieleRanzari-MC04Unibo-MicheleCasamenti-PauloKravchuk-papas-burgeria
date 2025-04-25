package it.unibo.papasburgeria;

import it.unibo.papasburgeria.model.api.Hamburger;
import it.unibo.papasburgeria.model.impl.HamburgerImpl;
import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.impl.IngredientImpl;
import it.unibo.papasburgeria.model.impl.MeatImpl;

public class Main {

    public static void main(final String[] args) {
        // main stuff here
        Hamburger burger1 = new HamburgerImpl();
        try {
            burger1.addIngredient(new IngredientImpl(IngredientEnum.BOTTOMBUN));
            burger1.addIngredient(new MeatImpl());
            burger1.addIngredient(new IngredientImpl(IngredientEnum.BBQ));
            burger1.addIngredient(new IngredientImpl(IngredientEnum.KETCHUP));
            burger1.addIngredient(new IngredientImpl(IngredientEnum.MAYO));
            burger1.addIngredient(new IngredientImpl(IngredientEnum.TOMATO));
            burger1.addIngredient(new IngredientImpl(IngredientEnum.LETTUCE));
            burger1.addIngredient(new IngredientImpl(IngredientEnum.TOPBUN));
            System.out.println(burger1.toString());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
