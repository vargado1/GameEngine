package cs.cvut.fel.pjv.demo.view;

public class Portal {
    private boolean isUnlocked = false;
    private String name;
    private int xCoors;
    private int yCoors;

    /**
     * Konštruktor pre vytvorenie inštancie portálu.
     *
     * @param name Názov portálu.
     * @param xCoors x-súradnice portálu.
     * @param yCoors y-súradnice portálu.
     */
    public Portal(String name, int xCoors, int yCoors) {
        this.name = name;
        this.xCoors = xCoors;
        this.yCoors = yCoors;
    }

    /**
     * Metóda na odomknutie portálu.
     *
     * @param key Kľúč potrebný na odomknutie portálu.
     */
    public void unlockPortal(Key key){}
}
