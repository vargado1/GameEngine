package cs.cvut.fel.pjv.demo.model;

import cs.cvut.fel.pjv.demo.view.Material;

public class Model {
    /**
     * Metóda na overenie receptu.
     *
     * @param materials Pole materiálov na overenie receptu.
     * @return True, ak recept existuje, inak false.
     */
    public boolean checkForRecipe(Material[] materials){return false;}

    /**
     * Metóda na zistenie kolízie.
     *
     * @return True, ak dochádza k kolízii, inak false.
     */
    public boolean isCollision(){
        return false;
    }

    /**
     * Metóda na pohyb hráča.
     */
    public void movePlayer(){}

}
