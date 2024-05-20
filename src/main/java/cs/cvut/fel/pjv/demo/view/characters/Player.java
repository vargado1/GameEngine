package cs.cvut.fel.pjv.demo.view.characters;

import com.google.gson.annotations.Expose;
import cs.cvut.fel.pjv.demo.view.*;

public class Player {
    @Expose
    private boolean isMoving = false;
    @Expose
    private Direction direction = Direction.RIGHT;
    @Expose
    private int speed;
    @Expose
    private Item[] hotBar = new Item[9];
    @Expose
    private Item selectetItem = hotBar[0];
    @Expose
    private String playerImage = "player.png";
    @Expose
    private int xCoords;
    @Expose
    private int yCoords;
    @Expose
    private int HP;

    public Player(int xCoords, int yCoords, int HP) {
        this.xCoords = xCoords;
        this.yCoords = yCoords;
        this.HP = HP;
    }

    public void setSelectetItem(Item selectetItem) {
        this.selectetItem = selectetItem;
    }

    public boolean isPlayerMoving(){
        return isMoving;
    }

    public void setDirection(Direction direction){
        this.direction = direction;
    }

    public void setPlayerSpeed(int speed) {
        this.speed = speed;
    }

    public void addToHotBar(Item item, int position){
        hotBar[position] = item;
    }

    /**
     * Based on type of sword deals damage to enemy
     *
     * @param enemy enemy to deal damage
     */
    public void punch(Enemy enemy){
        int swordBonus = 0;
        int enHP = enemy.getHP();

        if (selectetItem.getImagePath().equals("wooden_sword.png")) {
            swordBonus = 5;
        } else if (selectetItem.getImagePath().equals("stone_sword.png")) {
            swordBonus = 10;
        } else if (selectetItem.getImagePath().equals("iron_sword.png")) {
            swordBonus = 15;
        }

        enemy.setHP(enHP - 2 - swordBonus);
    }

    /**
     * If hot bar has free space block will be added after it was broken.
     *
     * @param blockToDestroy block to destroy
     */
    public boolean breakBlock(Block blockToDestroy){
        for (int i = 0; i < hotBar.length; i++) {
            if (hotBar[i] == null) {
                addToHotBar(blockToDestroy, i);
                return true;
            }
        }
        return false;
    }

    /**
     * Method to move player left.
     *
     * @return new coordination's of player
     */
    public int[] moveLEFT() {
        xCoords = xCoords - speed;

        int[] coords = new int[]{xCoords, yCoords};

        return coords;

    }

    /**
     * Method to move player right.
     *
     * @return new coordination's of player
     */

    public int[] moveRIGHT() {
        xCoords = xCoords + speed;

        int[] coords = new int[]{xCoords, yCoords};

        return coords;
    }

    /**
     * Method to move player up.
     *
     * @return new coordination's of a player
     */
    public int[] moveUP() {
        yCoords = yCoords - speed;
        int[] coords = new int[]{xCoords, yCoords};

        return coords;
    }

    /**
     * Method to move player down.
     *
     * @return new coordination's of a player
     */
    public int[] moveDOWN() {
        yCoords = yCoords + speed;
        int[] coords = new int[]{xCoords, yCoords};

        return coords;

    }

    public String getPlayerImage() {
        return playerImage;
    }

    public int getxCoord() {
        return xCoords;
    }

    public int getyCoord() {
        return yCoords;
    }

    public int getHP() {
        return HP;
    }

    public Item[] getHotBar() {
        return hotBar;
    }

    public Item getSelectetItem() {
        return selectetItem;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getSpeed() {
        return speed;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }
}
