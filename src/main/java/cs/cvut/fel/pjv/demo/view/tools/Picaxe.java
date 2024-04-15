package cs.cvut.fel.pjv.demo.view.tools;

public class Picaxe {
    private int level;

    /**
     * Konštruktor pre vytvorenie inštancie krompáča s určitou úrovňou.
     *
     * @param level Úroveň krompáča.
     */
    public Picaxe(int level) {
        this.level = level;
    }

    /**
     * Metóda vracejúca úroveň krompáča.
     *
     * @return Úroveň krompáča.
     */
    public int getLevel() {
        return level;
    }
}
