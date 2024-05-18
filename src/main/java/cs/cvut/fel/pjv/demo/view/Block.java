package cs.cvut.fel.pjv.demo.view;

import com.google.gson.annotations.Expose;
import cs.cvut.fel.pjv.demo.view.characters.Player;

public class Block extends Item {
    @Expose
    private boolean isCraftable;
    @Expose
    private int xCoord;
    @Expose
    private int yCoord;
    @Expose
    private final int size = 30;
    @Expose
    private boolean canFall;
    public boolean isPlaced = false;

    public Block(boolean isCraftable, boolean canFall, String type, String imagePath, String group) {
        super(group, imagePath, type);
        this.isCraftable = isCraftable;
        this.canFall = canFall;
        this.type = type;
    }

    public boolean isCraftable() {
        return isCraftable;
    }

    public int getXCoord() {
        return xCoord;
    }

    public int getYCoord() {
        return yCoord;
    }

    public int getSize() {
        return size;
    }

    public boolean isCanFall() {
        return canFall;
    }

    public void setCoords(int xCoord, int yCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public boolean isPlaced() {
        return isPlaced;
    }
}
