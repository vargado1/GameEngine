package cs.cvut.fel.pjv.demo.view;

public class Key {
    private boolean isDropped = false;

    private String name;

    /**
     * Konštruktor pre vytvorenie inštancie kľúča.
     *
     * @param name Názov kľúča.
     */
    public Key(String name) {
        this.name = name;
    }

    /**
     * Metóda vracejúca názov kľúča.
     *
     * @return Názov kľúča.
     */
    public String getName() {
        return name;
    }
}
