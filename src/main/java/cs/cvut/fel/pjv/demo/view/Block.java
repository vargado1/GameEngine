package cs.cvut.fel.pjv.demo.view;

public class Block {
    private boolean isCraftable;
    private int xCoord;
    private int yCoord;
    private final int size = 1;
    private boolean canFall;
    private String type;
    private boolean placed = false;

    public Block(boolean isCraftable, boolean canFall, String type) {
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
     * Metóda vracajúca typ bloku.
     *
     * @return Typ bloku.
     */
    public String getType(){
        return type;
    }

    /**
     * Metóda nastavujúca X-ovú súradnicu bloku.
     *
     * @param xCoord Nová X-ová súradnica bloku.
     */
    public void setXCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    /**
     * Metóda nastavujúca Y-ovú súradnicu bloku.
     *
     * @param yCoord Nová Y-ová súradnica bloku.
     */
    public void setYCoord(int yCoord) {
        this.yCoord = yCoord;
    }

    /**
     * Metóda nastavujúca informáciu o tom, či je blok umiestnený.
     *
     * @param placed True, ak je blok umiestnený, inak false.
     */
    public void setPlaced(boolean placed) {
        this.placed = placed;
    }

    /**
     * Metóda na vrátenie bloku do inventáru.
     */
    public void returnBlock(){}

}
