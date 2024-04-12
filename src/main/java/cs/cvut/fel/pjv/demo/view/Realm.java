package cs.cvut.fel.pjv.demo.view;

public class Realm {
    private final int xMaxCoords;
    private final int yMaxCoords;
    private String name;
    private String bossName;
    private int difficulty;

    /**
     * Konštruktor pre vytvorenie inštancie realmov.
     *
     * @param xMaxCoords Maximálne súradnice v osi X.
     * @param yMaxCoords Maximálne súradnice v osi Y.
     * @param name       Názov realmov.
     * @param bossName   Názov bossa v danom realme.
     * @param difficulty Obtiažnosť daného realmu.
     */
    public Realm(int xMaxCoords, int yMaxCoords, String name, String bossName, int difficulty) {
        this.xMaxCoords = xMaxCoords;
        this.yMaxCoords = yMaxCoords;
        this.name = name;
        this.bossName = bossName;
        this.difficulty = difficulty;
    }

    /**
     * Metóda vracejúca maximálne súradnice v osi X.
     *
     * @return Maximálne súradnice v osi X.
     */
    public int getXMaxCoords() {
        return xMaxCoords;
    }

    /**
     * Metóda vracejúca maximálne súradnice v osi Y.
     *
     * @return Maximálne súradnice v osi Y.
     */
    public int getYMaxCoords() {
        return yMaxCoords;
    }

    /**
     * Metóda vracejúca názov realmov.
     *
     * @return Názov realmu.
     */
    public String getName() {
        return name;
    }

    /**
     * Metóda vracejúca názov bossa v danom realme.
     *
     * @return Názov bossa v danom realmu.
     */
    public String getBossName() {
        return bossName;
    }

    /**
     * Metóda vracejúca obtiažnosť daného realmu.
     *
     * @return Obtiažnosť daného realmu.
     */
    public int getDifficulty() {
        return difficulty;
    }
}
