package cs.cvut.fel.pjv.demo.model;

import cs.cvut.fel.pjv.demo.view.Direction;
import cs.cvut.fel.pjv.demo.view.Material;
import cs.cvut.fel.pjv.demo.view.Realm;
import cs.cvut.fel.pjv.demo.view.characters.Player;
import javafx.scene.layout.StackPane;

public class Model {
    /**
     * Metóda na overenie receptu.
     *
     * @param materials Pole materiálov na overenie receptu.
     * @return True, ak recept existuje, inak false.
     */
    public boolean checkForRecipe(Material[] materials){return false;}

    public boolean isBlockUnder(Realm realm, Player player) {
        int playerXCoord = player.getxCoord();
        int playerYCoord = player.getyCoord();

        if (realm.map[playerXCoord][playerYCoord + 1] == null) {
            return false;
        }

        return true;
    }

    public boolean isCollision(Realm realm, Player player){
        Direction dir = player.getDirection();
        int playerXCoord = player.getxCoord();
        int playerYCoord = player.getyCoord();

        switch (dir) {
            case RIGHT ->
            {
                if (realm.map[playerXCoord + 1][playerYCoord] != null || realm.map[playerXCoord + 1][playerYCoord - 1] != null) {
                    return true;
                }
                break;
            }
            case LEFT ->
            {
                if (realm.map[playerXCoord - 1][playerYCoord] != null || realm.map[playerXCoord - 1][playerYCoord - 1] != null) {
                    return true;
                }
                break;
            }
            case JUMP ->
            {
                if (realm.map[playerXCoord][playerYCoord - 2] != null || (realm.map[playerXCoord][playerYCoord + 1] != null && player.isPlayerMoving())) {
                    return true;
                }
                break;
            }
        }

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
//        yCoord = (yCoord * (-1)) + (realm.getYMaxCoords()/2);
        yCoord = yCoord + (realm.getYMaxCoords()/2);

        int[] coords = new int[]{xCoord,yCoord};

        return coords;

    }

}
