package cs.cvut.fel.pjv.demo.view.items;

public class Portal {
    private boolean isUnlocked = false;
    private String name;

    /**
     * Konštruktor pre vytvorenie inštancie portálu.
     *
     * @param name Názov portálu.
     */
    public Portal(String name) {
        this.name = name;
    }

    /**
     * Metóda na odomknutie portálu.
     *
     * @param key Kľúč potrebný na odomknutie portálu.
     */
    public void unlockPortal(Key key){}
}
