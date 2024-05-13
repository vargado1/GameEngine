package cs.cvut.fel.pjv.demo.model;

import cs.cvut.fel.pjv.demo.view.Material;
import cs.cvut.fel.pjv.demo.view.Realm;
import javafx.scene.layout.StackPane;

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

    public int[] getCoordsFromListToScreen(int xCoord, int yCoord, int size, Realm realm) {
        xCoord = xCoord - (realm.getXMaxCoords()/2);
        yCoord = yCoord - (realm.getYMaxCoords()/2);

        xCoord = xCoord * size;
        yCoord = yCoord * size;

        int[] coords = new int[]{xCoord, yCoord};

        return coords;
    }

    public int[] getCoordsFromScreenToList(int xCoord, int yCoord, int size, Realm realm) {
        xCoord = xCoord/size;
        yCoord = yCoord/size;

        xCoord = xCoord + realm.getXMaxCoords()/2;
        yCoord = (yCoord * (-1)) + (realm.getXMaxCoords()/2);

        int[] coords = new int[]{xCoord,yCoord};

        return coords;

    }

}
