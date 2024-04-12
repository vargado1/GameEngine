package cs.cvut.fel.pjv.demo.view.items.tools;

public class Sword {
    private int level;

    /**
     * Konštruktor pre vytvorenie inštancie meča s určitou úrovňou.
     *
     * @param level Úroveň meča.
     */
    public Sword(int level) {
        this.level = level;
    }

    /**
     * Metóda vracejúca úroveň meča.
     *
     * @return Úroveň meča.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Metóda na útok mečom.
     */
    public void attack(){}

}
