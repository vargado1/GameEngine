package cs.cvut.fel.pjv.demo.view.items.tools;

public class Showel{
    private int level;

    /**
     * Konštruktor pre vytvorenie inštancie lopaty s určitou úrovňou.
     *
     * @param level Úroveň lopaty.
     */
    public Showel(int level) {
        this.level = level;
    }

    /**
     * Metóda vracejúca úroveň lopaty.
     *
     * @return Úroveň lopaty.
     */
    public int getLevel() {
        return level;
    }
}
