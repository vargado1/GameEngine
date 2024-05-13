package cs.cvut.fel.pjv.demo.view;

import com.google.gson.annotations.Expose;
import cs.cvut.fel.pjv.demo.view.characters.Player;

public class Block extends Item{
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

    /**
     * Metóda vracejúca informáciu o tom, či je blok vytvárateľný.
     *
     * @return True, ak je blok vytvárateľný, inak false.
     */
    public boolean isCraftable() {
        return isCraftable;
    }

    /**
     * Metóda vracajúca X-ovú súradnicu bloku.
     *
     * @return X-ová súradnica bloku.
     */
    public int getXCoord() {
        return xCoord;
    }

    /**
     * Metóda vracajúca Y-ovú súradnicu bloku.
     *
     * @return Y-ová súradnica bloku.
     */
    public int getYCoord() {
        return yCoord;
    }

    /**
     * Metóda vracajúca veľkosť bloku.
     *
     * @return Veľkosť bloku.
     */
    public int getSize() {
        return size;
    }

    /**
     * Metóda vracajúca informáciu o tom, či môže blok padať.
     *
     * @return True, ak môže blok padať, inak false.
     */
    public boolean isCanFall() {
        return canFall;
    }


    /**
     * Metóda nastavujúca X-ovú súradnicu bloku.
     *
     * @param xCoord Nová X-ová súradnica bloku.
     */
    public void setCoords(int xCoord, int yCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }


    /**
     * Metóda na vrátenie bloku do inventáru.
     */
    public void returnBlockToInventory(){

    }


}
