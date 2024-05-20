package cs.cvut.fel.pjv.demo.model;

import cs.cvut.fel.pjv.demo.view.Direction;
import cs.cvut.fel.pjv.demo.view.Material;
import cs.cvut.fel.pjv.demo.view.Realm;
import cs.cvut.fel.pjv.demo.view.characters.Player;
import javafx.scene.layout.StackPane;

public class Model {

    /**
     * check if there is a block under the player
     *
     * @param realm world where player is placed
     * @param player player
     * @return true if there is a block under, false if not
     */
    public boolean isBlockUnder(Realm realm, Player player) {
        int playerXCoord = player.getxCoord();
        int playerYCoord = player.getyCoord();

        if (realm.map[playerXCoord][playerYCoord + 1] == null) {
            return false;
        }

        return true;
    }

    /**
     * check if there will be colision based on players direction
     *
     * @param realm world where player is situated
     * @param player player
     * @return true if there will be colision, false if not
     */
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

    /**
     * calculates coordinates from my coordinate system to scene's system
     *
     * @param xCoord x coordination in my system
     * @param yCoord y coordination in my system
     * @param size size of a block
     * @param realm world with my coordination system
     * @return coordination's in the scene
     */
    public int[] getCoordsFromListToScreen(int xCoord, int yCoord, int size, Realm realm) {
        xCoord = xCoord - (realm.getXMaxCoords()/2);
        yCoord = yCoord - (realm.getYMaxCoords()/2);

        xCoord = xCoord * size;
        yCoord = yCoord * size;

        int[] coords = new int[]{xCoord, yCoord};

        return coords;
    }

    /**
     * calculate coordinates from scene to my system
     *
     * @param xCoord x coordination on scene
     * @param yCoord y coordination on scene
     * @param size size of block
     * @param realm world with my coordination system
     * @return coordination's in my system
     */
    public int[] getCoordsFromScreenToList(int xCoord, int yCoord, int size, Realm realm) {
        xCoord = xCoord/size;
        yCoord = yCoord/size;

        xCoord = xCoord + realm.getXMaxCoords()/2;
        yCoord = yCoord + (realm.getYMaxCoords()/2);

        int[] coords = new int[]{xCoord,yCoord};

        return coords;

    }

    /**
     * checks if player is near any object
     *
     * @param player player
     * @param xCoords x coordination of said object in my system
     * @param yCoords y coordination of said object in my system
     * @return true if player is near said object, false if not
     */
    public boolean isNearObject(Player player, int xCoords, int yCoords) {
        int playerXCoord = player.getxCoord();
        int playerYCoord = player.getyCoord();

        if ((playerXCoord == xCoords && playerYCoord == yCoords)
                || ((playerXCoord - 1) == xCoords && playerYCoord == yCoords)
                || ((playerXCoord + 1) == xCoords && playerYCoord == yCoords))
        {
            return true;
        }
        return false;
    }

}
